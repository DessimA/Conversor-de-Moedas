package com.conversor.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("Sorry, unable to find config.properties. Please create it in the project root.");
                // Fallback to environment variable if config.properties is not found
                apiKey = System.getenv("EXCHANGERATE_API_KEY");
                if (apiKey == null) {
                    throw new RuntimeException("API Key not found in config.properties or as environment variable EXCHANGERATE_API_KEY.");
                }
                openExchangeRatesAppId = System.getenv("OPENEXCHANGERATES_APP_ID"); // Fallback for OpenExchangeRates
            } else {
                prop.load(input);
                apiKey = prop.getProperty("api.key");
                if (apiKey == null || apiKey.trim().isEmpty()) {
                    throw new RuntimeException("API Key not found in config.properties. Please ensure 'api.key' is set.");
                }
                openExchangeRatesAppId = prop.getProperty("openexchangerates.app_id");
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error loading API key from config.properties", ex);
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
