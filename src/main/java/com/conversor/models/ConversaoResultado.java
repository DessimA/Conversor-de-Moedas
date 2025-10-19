package com.conversor.models;

import java.time.LocalDateTime;

public record ConversaoResultado(
    double valorOriginal,
    Moeda moedaOrigem,
    double valorConvertido,
    Moeda moedaDestino,
    double taxaUtilizada,
    LocalDateTime dataHora
) {
}