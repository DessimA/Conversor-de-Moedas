package com.conversor.services;

import com.conversor.models.Moeda;
import com.conversor.models.TaxaCambio;

import java.io.IOException;

public interface ApiTaxaCambio {
    TaxaCambio obterTaxaCambio(Moeda origem, Moeda destino) throws IOException, InterruptedException, RuntimeException;
}