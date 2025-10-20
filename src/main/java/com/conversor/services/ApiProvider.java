package com.conversor.services;

public enum ApiProvider {
    EXCHANGERATE_API("ExchangeRate-API"),
    OPENEXCHANGERATES("OpenExchangeRates");

    private final String name;

    ApiProvider(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ApiProvider fromString(String text) {
        for (ApiProvider b : ApiProvider.values()) {
            if (b.name.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return EXCHANGERATE_API; // Default
    }
}
