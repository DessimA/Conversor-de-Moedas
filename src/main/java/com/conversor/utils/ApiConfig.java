package com.conversor.utils;



public class ApiConfig {
    private static ApiConfig instance;
    private String apiKey;
    private String openExchangeRatesAppId;
    private final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    // Supported currencies as constants
    public static final String ARS = "ARS";
    public static final String BOB = "BOB";
    public static final String BRL = "BRL";
    public static final String CLP = "CLP";
    public static final String COP = "COP";
    public static final String USD = "USD";
    public static final String EUR = "EUR";
    public static final String GBP = "GBP";
    public static final String JPY = "JPY";
    public static final String AUD = "AUD";
    public static final String CAD = "CAD";
    public static final String CHF = "CHF";
    public static final String CNY = "CNY";
    public static final String MXN = "MXN";
    public static final String INR = "INR";

    private ApiConfig() {
        loadApiKey();
    }

    public static synchronized ApiConfig getInstance() {
        if (instance == null) {
            instance = new ApiConfig();
        }
        return instance;
    }

    private void loadApiKey() {
        apiKey = System.getenv("EXCHANGERATE_API_KEY");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new RuntimeException("API Key for ExchangeRate-API not found. Please set the environment variable EXCHANGERATE_API_KEY.");
        }

        openExchangeRatesAppId = System.getenv("OPENEXCHANGERATES_APP_ID");
        if (openExchangeRatesAppId == null || openExchangeRatesAppId.trim().isEmpty()) {
            System.err.println("Warning: App ID for OpenExchangeRates not found. Please set the environment variable OPENEXCHANGERATES_APP_ID if you intend to use it as a fallback.");
            // Not throwing an exception here, as OpenExchangeRates might be a fallback and not strictly required if primary works.
        }
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getOpenExchangeRatesAppId() {
        return openExchangeRatesAppId;
    }

    public String buildPairConversionUrl(String baseCurrency, String targetCurrency) {
        return BASE_URL + apiKey + "/pair/" + baseCurrency + "/" + targetCurrency;
    }
}
