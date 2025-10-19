package com.conversor.services;

import com.conversor.models.Moeda;
import com.conversor.models.TaxaCambio;
import com.conversor.utils.ApiConfig;
import com.conversor.utils.LoggerUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApiTaxaCambioService implements ApiTaxaCambio {
    private static final Logger logger = LoggerUtil.getLogger(ApiTaxaCambioService.class.getName());
    private final ApiTaxaCambio primaryApi;
    private final ApiTaxaCambio fallbackApi;
    private final Map<String, TaxaCambio> cacheTaxas;
    private final String preferredApiName;

    public ApiTaxaCambioService(ApiConfig apiConfig, String preferredApiName) {
        this.cacheTaxas = new ConcurrentHashMap<>();
        this.preferredApiName = preferredApiName;

        // Initialize API implementations
        ApiTaxaCambio exchangeRateApi = new ExchangeRateApiImpl(apiConfig);
        ApiTaxaCambio openExchangeRatesApi = new OpenExchangeRatesApiImpl(apiConfig.getOpenExchangeRatesAppId());

        if ("EXCHANGERATE_API".equalsIgnoreCase(preferredApiName)) {
            this.primaryApi = exchangeRateApi;
            this.fallbackApi = openExchangeRatesApi;
        } else if ("OPENEXCHANGERATES".equalsIgnoreCase(preferredApiName)) {
            this.primaryApi = openExchangeRatesApi;
            this.fallbackApi = exchangeRateApi;
        } else {
            logger.log(Level.WARNING, "API preferencial '{0}' não reconhecida. Usando ExchangeRate-API como primária e OpenExchangeRates como fallback.", preferredApiName);
            this.primaryApi = exchangeRateApi;
            this.fallbackApi = openExchangeRatesApi;
        }
        logger.log(Level.INFO, "API Primária: {0}, API Fallback: {1}", new Object[]{primaryApi.getClass().getSimpleName(), fallbackApi.getClass().getSimpleName()});
    }

    @Override
    public TaxaCambio obterTaxaCambio(Moeda origem, Moeda destino) throws IOException, InterruptedException, RuntimeException {
        String cacheKey = origem.getCodigo() + "_" + destino.getCodigo();

        // 1. Check cache
        TaxaCambio cachedRate = cacheTaxas.get(cacheKey);
        if (cachedRate != null && isCacheValid(cachedRate)) {
            logger.log(Level.INFO, "Taxa de câmbio para {0} -> {1} encontrada no cache e válida.", new Object[]{origem.getCodigo(), destino.getCodigo()});
            return cachedRate;
        }

        // 2. Try primary API
        try {
            logger.log(Level.INFO, "Tentando obter taxa de câmbio da API primária: {0}", primaryApi.getClass().getSimpleName());
            TaxaCambio newRate = primaryApi.obterTaxaCambio(origem, destino);
            cacheTaxas.put(cacheKey, newRate); // Store in cache
            logger.log(Level.INFO, "Taxa de câmbio para {0} -> {1} obtida da {2} e armazenada no cache.", new Object[]{origem.getCodigo(), destino.getCodigo(), primaryApi.getClass().getSimpleName()});
            return newRate;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Falha na API primária ({0}): {1}. Tentando API de fallback...", new Object[]{primaryApi.getClass().getSimpleName(), e.getMessage()});
            // 3. Try fallback API
            try {
                TaxaCambio newRate = fallbackApi.obterTaxaCambio(origem, destino);
                cacheTaxas.put(cacheKey, newRate); // Store in cache
                logger.log(Level.INFO, "Taxa de câmbio para {0} -> {1} obtida da {2} e armazenada no cache.", new Object[]{origem.getCodigo(), destino.getCodigo(), fallbackApi.getClass().getSimpleName()});
                return newRate;
            } catch (Exception fallbackE) {
                logger.log(Level.SEVERE, "Falha em ambas as APIs para {0} -> {1}. Primária ({2}): {3}. Fallback ({4}): {5}",
                        new Object[]{origem.getCodigo(), destino.getCodigo(), primaryApi.getClass().getSimpleName(), e.getMessage(), fallbackApi.getClass().getSimpleName(), fallbackE.getMessage()});
                throw new RuntimeException("Não foi possível obter a taxa de câmbio de nenhuma API disponível.", fallbackE);
            }
        }
    }

    private boolean isCacheValid(TaxaCambio cachedRate) {
        // Cache is valid for 1 hour
        return cachedRate.timestamp().plusHours(1).isAfter(LocalDateTime.now());
    }

    // Optional: Method to clear expired cache (can be called periodically if needed)
    public void limparCacheExpirado() {
        cacheTaxas.entrySet().removeIf(entry -> !isCacheValid(entry.getValue()));
        logger.log(Level.INFO, "Cache de taxas expiradas limpo.");
    }
}
