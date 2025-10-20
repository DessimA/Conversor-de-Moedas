package com.conversor.services;

import com.conversor.utils.ApiConfig;

public class ApiTaxaCambioFactory {

    public static ApiTaxaCambio getApiImplementation(ApiProvider provider, ApiConfig apiConfig) {
        switch (provider) {
            case EXCHANGERATE_API:
                return new ExchangeRateApiImpl(apiConfig);
            case OPENEXCHANGERATES:
                return new OpenExchangeRatesApiImpl(apiConfig.getOpenExchangeRatesAppId());
            default:
                throw new IllegalArgumentException("Provedor de API desconhecido: " + provider);
        }
    }
}
