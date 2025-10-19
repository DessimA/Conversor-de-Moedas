package com.conversor.models;

import java.time.LocalDateTime;

public record TaxaCambio(
    Moeda origem,
    Moeda destino,
    double taxa,
    LocalDateTime timestamp
) {
}