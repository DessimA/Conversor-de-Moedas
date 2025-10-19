package com.conversor.services;

import com.conversor.models.Moeda;
import com.conversor.models.TaxaCambio;
import com.conversor.utils.ApiConfig;
import com.conversor.services.ApiTaxaCambio;
import com.conversor.utils.LoggerUtil;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExchangeRateApiImpl implements ApiTaxaCambio {
    private static final Logger logger = LoggerUtil.getLogger(ExchangeRateApiImpl.class.getName());
    private final HttpClient client;
    private final ApiConfig apiConfig;
    private final JsonParser jsonParser;

    public ExchangeRateApiImpl(ApiConfig apiConfig) { // Constructor now takes ApiConfig
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.apiConfig = apiConfig; // Use injected ApiConfig
        this.jsonParser = new JsonParser();
    }

    public TaxaCambio obterTaxaCambio(Moeda origem, Moeda destino) throws IOException, InterruptedException, RuntimeException {
        String url = apiConfig.buildPairConversionUrl(origem.getCodigo(), destino.getCodigo());
        logger.log(Level.INFO, "Realizando requisição para: {0}", url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode();
        String responseBody = response.body();

        if (statusCode == 200) {
            logger.log(Level.INFO, "Requisição bem-sucedida. Resposta: {0}", responseBody);
            double conversionRate = jsonParser.parseConversionRate(responseBody, origem.getCodigo(), destino.getCodigo());
            return new TaxaCambio(origem, destino, conversionRate, LocalDateTime.now());
        } else if (statusCode == 404) {
            logger.log(Level.WARNING, "Erro 404: Recurso não encontrado. URL: {0}", url);
            throw new RuntimeException("Moeda não encontrada ou URL inválida.");
        } else if (statusCode == 403) {
            logger.log(Level.SEVERE, "Erro 403: Acesso negado. Verifique sua chave de API. URL: {0}", url);
            throw new RuntimeException("Acesso negado à API. Verifique sua chave de API.");
        } else if (statusCode >= 500) {
            logger.log(Level.SEVERE, "Erro de servidor {0}. Resposta: {1}", new Object[]{statusCode, responseBody});
            throw new RuntimeException("Erro interno do servidor da API. Tente novamente mais tarde.");
        } else {
            logger.log(Level.WARNING, "Erro inesperado {0}. Resposta: {1}", new Object[]{statusCode, responseBody});
            throw new RuntimeException("Erro ao obter taxa de câmbio: " + responseBody);
        }
    }


}
