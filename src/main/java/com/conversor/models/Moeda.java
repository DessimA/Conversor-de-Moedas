package com.conversor.models;

public enum Moeda {
    ARS("ARS", "Peso Argentino", "$"),
    BOB("BOB", "Boliviano Boliviano", "Bs."),
    BRL("BRL", "Real Brasileiro", "R$"),
    CLP("CLP", "Peso Chileno", "$"),
    COP("COP", "Peso Colombiano", "$"),
    USD("USD", "Dólar Americano", "$"),
    EUR("EUR", "Euro", "€"),
    GBP("GBP", "Libra Esterlina", "£"),
    JPY("JPY", "Iene Japonês", "¥"),
    AUD("AUD", "Dólar Australiano", "A$"),
    CAD("CAD", "Dólar Canadense", "C$"),
    CHF("CHF", "Franco Suíço", "CHF"),
    CNY("CNY", "Yuan Chinês", "¥"),
    MXN("MXN", "Peso Mexicano", "$"),
    INR("INR", "Rúpia Indiana", "₹");

    private final String codigo;
    private final String nomeCompleto;
    private final String simbolo;

    Moeda(String codigo, String nomeCompleto, String simbolo) {
        this.codigo = codigo;
        this.nomeCompleto = nomeCompleto;
        this.simbolo = simbolo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public static Moeda fromCodigo(String codigo) {
        for (Moeda moeda : Moeda.values()) {
            if (moeda.getCodigo().equalsIgnoreCase(codigo)) {
                return moeda;
            }
        }
        throw new IllegalArgumentException("Código de moeda inválido: " + codigo);
    }
}