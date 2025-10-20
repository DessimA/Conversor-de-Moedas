package com.conversor.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JsonParser {

    private final Gson gson;

    public double parseConversionRate(String jsonResponse, String expectedBaseCode, String expectedTargetCode) throws JsonSyntaxException, IllegalArgumentException {
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

        if (jsonObject == null) {
            throw new JsonSyntaxException("Resposta JSON vazia ou inválida.");
        }

        String result = jsonObject.has("result") ? jsonObject.get("result").getAsString() : null;
        String baseCode = jsonObject.has("base_code") ? jsonObject.get("base_code").getAsString() : null;
        String targetCode = jsonObject.has("target_code") ? jsonObject.get("target_code").getAsString() : null;
        Double conversionRate = jsonObject.has("conversion_rate") ? jsonObject.get("conversion_rate").getAsDouble() : null;

        if (!"success".equalsIgnoreCase(result)) {
            String errorType = jsonObject.has("error_type") ? jsonObject.get("error_type").getAsString() : "unknown_error";
            throw new IllegalArgumentException("Erro na resposta da API: " + errorType);
        }

        if (conversionRate == null) {
            throw new IllegalArgumentException("Taxa de conversão não encontrada na resposta JSON.");
        }

        if (baseCode == null || !baseCode.equalsIgnoreCase(expectedBaseCode)) {
            throw new IllegalArgumentException("Código da moeda base inesperado na resposta JSON. Esperado: " + expectedBaseCode + ", Recebido: " + baseCode);
        }

        if (targetCode == null || !targetCode.equalsIgnoreCase(expectedTargetCode)) {
            throw new IllegalArgumentException("Código da moeda alvo inesperado na resposta JSON. Esperado: " + expectedTargetCode + ", Recebido: " + targetCode);
        }

        return conversionRate;
    }
}
