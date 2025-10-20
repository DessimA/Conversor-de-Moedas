
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

public class CoinGeckoApiImpl implements ApiTaxaCambio {
    private static final Logger logger = LoggerUtil.getLogger(CoinGeckoApiImpl.class.getName());
    private final HttpClient client;
    private final Gson gson;
    private final String apiKey;

    public CoinGeckoApiImpl(String apiKey) {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.gson = new Gson();
        this.apiKey = apiKey;
    }

    private double parseCoinGeckoResponse(String jsonResponse, Moeda origem, Moeda destino) throws JsonSyntaxException, IllegalArgumentException {
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
        if (jsonObject == null || !jsonObject.has(origem.getNomeCompleto().toLowerCase())) {
            throw new JsonSyntaxException("Resposta JSON inválida: ID da criptomoeda não encontrado.");
        }
        JsonObject cryptoObject = jsonObject.getAsJsonObject(origem.getNomeCompleto().toLowerCase());
        if (!cryptoObject.has(destino.getCodigo().toLowerCase())) {
            throw new IllegalArgumentException("Moeda de destino não encontrada nas taxas da API CoinGecko.");
        }
        return cryptoObject.get(destino.getCodigo().toLowerCase()).getAsDouble();
    }

    @Override
    public TaxaCambio obterTaxaCambio(Moeda origem, Moeda destino) throws IOException, InterruptedException, RuntimeException {
        String url = String.format("https://api.coingecko.com/api/v3/simple/price?ids=%s&vs_currencies=%s&x_cg_demo_api_key=%s",
                origem.getNomeCompleto().toLowerCase(), destino.getCodigo().toLowerCase(), apiKey);
        logger.log(Level.INFO, "Realizando requisição para CoinGecko: {0}", url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode();
        String responseBody = response.body();

        if (statusCode == 200) {
            logger.log(Level.INFO, "Requisição CoinGecko bem-sucedida. Resposta: {0}", responseBody);
            try {
                double conversionRate = parseCoinGeckoResponse(responseBody, origem, destino);
                return new TaxaCambio(origem, destino, conversionRate, LocalDateTime.now());
            } catch (JsonSyntaxException e) {
                logger.log(Level.SEVERE, "Erro de parsing JSON da CoinGecko: {0}", e.getMessage());
                throw new RuntimeException("Erro ao processar resposta da CoinGecko.", e);
            }
        } else if (statusCode == 401) {
            logger.log(Level.SEVERE, "Erro 401: API Key inválida para CoinGecko. URL: {0}", url);
            throw new RuntimeException("API Key inválida para CoinGecko. Verifique sua chave.");
        } else if (statusCode == 403) {
            logger.log(Level.SEVERE, "Erro 403: Acesso negado para CoinGecko. URL: {0}", url);
            throw new RuntimeException("Acesso negado à CoinGecko. Verifique sua assinatura.");
        } else if (statusCode >= 500) {
            logger.log(Level.SEVERE, "Erro de servidor CoinGecko {0}. Resposta: {1}", new Object[]{statusCode, responseBody});
            throw new RuntimeException("Erro interno do servidor da CoinGecko. Tente novamente mais tarde.");
        } else {
            logger.log(Level.WARNING, "Erro inesperado CoinGecko {0}. Resposta: {1}", new Object[]{statusCode, responseBody});
            throw new RuntimeException("Erro ao obter taxa de câmbio da CoinGecko: " + responseBody);
        }
    }
}
