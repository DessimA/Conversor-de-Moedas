package com.conversor.models;

import lombok.Getter;

@Getter
public enum Moeda {
    ARS("ARS", "Peso Argentino", "$", MoedaType.FIAT),
    BOB("BOB", "Boliviano Boliviano", "Bs.", MoedaType.FIAT),
    BRL("BRL", "Real Brasileiro", "R$", MoedaType.FIAT),
    CLP("CLP", "Peso Chileno", "$", MoedaType.FIAT),
    COP("COP", "Peso Colombiano", "$", MoedaType.FIAT),
    USD("USD", "Dólar Americano", "$", MoedaType.FIAT),
    EUR("EUR", "Euro", "€", MoedaType.FIAT),
    GBP("GBP", "Libra Esterlina", "£", MoedaType.FIAT),
    JPY("JPY", "Iene Japonês", "¥", MoedaType.FIAT),
    AUD("AUD", "Dólar Australiano", "A$", MoedaType.FIAT),
    CAD("CAD", "Dólar Canadense", "C$", MoedaType.FIAT),
    CHF("CHF", "Franco Suíço", "CHF", MoedaType.FIAT),
    CNY("CNY", "Yuan Chinês", "¥", MoedaType.FIAT),
    MXN("MXN", "Peso Mexicano", "$", MoedaType.FIAT),
    INR("INR", "Rúpia Indiana", "₹", MoedaType.FIAT),

    // Cryptocurrencies
    BTC("BTC", "Bitcoin", "₿", MoedaType.CRYPTO),
    ETH("ETH", "Ethereum", "Ξ", MoedaType.CRYPTO),
    USDT("USDT", "Tether", "$", MoedaType.CRYPTO),
    BNB("BNB", "Binance Coin", "BNB", MoedaType.CRYPTO),
    SOL("SOL", "Solana", "SOL", MoedaType.CRYPTO),
    XRP("XRP", "XRP", "XRP", MoedaType.CRYPTO),
    USDC("USDC", "USD Coin", "$", MoedaType.CRYPTO),
    ADA("ADA", "Cardano", "ADA", MoedaType.CRYPTO),
    AVAX("AVAX", "Avalanche", "AVAX", MoedaType.CRYPTO),
    DOGE("DOGE", "Dogecoin", "DOGE", MoedaType.CRYPTO);

    private final String codigo;
    private final String nomeCompleto;
    private final String simbolo;
    private final MoedaType tipo;

    Moeda(String codigo, String nomeCompleto, String simbolo, MoedaType tipo) {
        this.codigo = codigo;
        this.nomeCompleto = nomeCompleto;
        this.simbolo = simbolo;
        this.tipo = tipo;
    }

    public boolean isCrypto() {
        return this.tipo == MoedaType.CRYPTO;
    }

    public boolean isFiat() {
        return this.tipo == MoedaType.FIAT;
    }

    public static Moeda fromCodigo(String codigo) {
        for (Moeda moeda : Moeda.values()) {
            if (moeda.getCodigo().equalsIgnoreCase(codigo)) {
                return moeda;
            }
        }
        throw new IllegalArgumentException("Código de moeda inválido: " + codigo);
    }

    public enum MoedaType {
        FIAT,
        CRYPTO
    }
}