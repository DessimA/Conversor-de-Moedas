# Conversor-de-Moedas

Conversor de moedas em Java 17 que interage com 15 moedas fiduciarias e diversas criptomoedas via APIs externas (ExchangeRate-API, OpenExchangeRates, CoinGecko). Utiliza Gson para serializacao JSON, Lombok para reducao de boilerplate e HttpClient nativo para requisicoes HTTP. Implementa cache de taxas, fallback automatico entre APIs, historico persistido em JSON e estatisticas de uso.

**Stack:** Java 17, Gson, Lombok, REST APIs