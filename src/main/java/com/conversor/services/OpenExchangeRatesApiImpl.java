package com.conversor.services;

import com.conversor.models.Moeda;
import com.conversor.models.TaxaCambio;
import com.conversor.utils.LoggerUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OpenExchangeRatesApiImpl implements ApiTaxaCambio {
    private static final Logger logger = LoggerUtil.getLogger(OpenExchangeRatesApiImpl.class.getName());
    private final HttpClient client;
    private final Gson gson;
    private final String appId; // OpenExchangeRates requires an App ID

    public OpenExchangeRatesApiImpl(String appId) {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.gson = new Gson();
        this.appId = appId;
    }

    private double parseOpenExchangeRatesResponse(String jsonResponse, Moeda origem, Moeda destino) throws JsonSyntaxException, IllegalArgumentException {
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
        if (jsonObject == null || !jsonObject.has("rates")) {
            throw new JsonSyntaxException("Resposta JSON inválida: 'rates' não encontrado.");
        }
        JsonObject rates = jsonObject.getAsJsonObject("rates");
        if (!rates.has(destino.getCodigo())) {
            throw new IllegalArgumentException("Moeda de destino não encontrada nas taxas da API.");
        }
        return rates.get(destino.getCodigo()).getAsDouble();
    }

    @Override
    public TaxaCambio obterTaxaCambio(Moeda origem, Moeda destino) throws IOException, InterruptedException, RuntimeException {
        String url = String.format("https://openexchangerates.org/api/latest.json?app_id=%s&base=%s&symbols=%s",
                appId, origem.getCodigo(), destino.getCodigo());
        logger.log(Level.INFO, "Realizando requisição para OpenExchangeRates: {0}", url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode();
        String responseBody = response.body();

        if (statusCode == 200) {
            logger.log(Level.INFO, "Requisição OpenExchangeRates bem-sucedida. Resposta: {0}", responseBody);
            try {
                double conversionRate = parseOpenExchangeRatesResponse(responseBody, origem, destino);
                return new TaxaCambio(origem, destino, conversionRate, LocalDateTime.now());
            } catch (JsonSyntaxException e) {
                logger.log(Level.SEVERE, "Erro de parsing JSON da OpenExchangeRates: {0}", e.getMessage());
                throw new RuntimeException("Erro ao processar resposta da OpenExchangeRates.", e);
            }
        } else if (statusCode == 401) {
            logger.log(Level.SEVERE, "Erro 401: App ID inválido para OpenExchangeRates. URL: {0}", url);
            throw new RuntimeException("App ID inválido para OpenExchangeRates. Verifique sua chave.");
        } else if (statusCode == 403) {
            logger.log(Level.SEVERE, "Erro 403: Acesso negado para OpenExchangeRates. URL: {0}", url);
            throw new RuntimeException("Acesso negado à OpenExchangeRates. Verifique sua assinatura.");
        } else if (statusCode >= 500) {
            logger.log(Level.SEVERE, "Erro de servidor OpenExchangeRates {0}. Resposta: {1}", new Object[]{statusCode, responseBody});
            throw new RuntimeException("Erro interno do servidor da OpenExchangeRates. Tente novamente mais tarde.");
        } else {
            logger.log(Level.WARNING, "Erro inesperado OpenExchangeRates {0}. Resposta: {1}", new Object[]{statusCode, responseBody});
            throw new RuntimeException("Erro ao obter taxa de câmbio da OpenExchangeRates: " + responseBody);
        }
    }
}
