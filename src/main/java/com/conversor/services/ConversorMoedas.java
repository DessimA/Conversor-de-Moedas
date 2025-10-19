package com.conversor.services;

import com.conversor.models.ConversaoResultado;
import com.conversor.models.HistoricoConversao;
import com.conversor.models.Moeda;
import com.conversor.models.TaxaCambio;
import com.conversor.utils.LoggerUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConversorMoedas {
    private static final Logger logger = LoggerUtil.getLogger(ConversorMoedas.class.getName());
    private final ApiTaxaCambio apiTaxaCambio;
    private final HistoricoConversao historicoConversao;

    public ConversorMoedas(ApiTaxaCambio apiTaxaCambio, HistoricoConversao historicoConversao) {
        this.apiTaxaCambio = apiTaxaCambio;
        this.historicoConversao = historicoConversao;
    }

    public ConversaoResultado converter(double valor, Moeda origem, Moeda destino) throws IOException, InterruptedException, RuntimeException {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor a ser convertido deve ser maior que zero.");
        }
        if (origem.equals(destino)) {
            // If currencies are the same, no conversion needed, rate is 1.0
            BigDecimal valorConvertido = BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_UP);
            ConversaoResultado resultado = new ConversaoResultado(valor, origem, valorConvertido.doubleValue(), destino, 1.0, LocalDateTime.now());
            historicoConversao.adicionarConversao(resultado);
            logger.log(Level.INFO, "Conversão de {0} {1} para {2} {3} (mesma moeda). Resultado: {4}",
                    new Object[]{valor, origem.getCodigo(), valorConvertido, destino.getCodigo(), resultado.valorConvertido()});
            return resultado;
        }

        TaxaCambio taxaCambio = apiTaxaCambio.obterTaxaCambio(origem, destino);

        BigDecimal valorOriginalBd = BigDecimal.valueOf(valor);
        BigDecimal taxaBd = BigDecimal.valueOf(taxaCambio.taxa());
        BigDecimal valorConvertidoBd = valorOriginalBd.multiply(taxaBd).setScale(2, RoundingMode.HALF_UP);

        ConversaoResultado resultado = new ConversaoResultado(
                valor,
                origem,
                valorConvertidoBd.doubleValue(),
                destino,
                taxaCambio.taxa(),
                LocalDateTime.now()
        );

        historicoConversao.adicionarConversao(resultado);
        logger.log(Level.INFO, "Conversão de {0} {1} para {2} {3}. Taxa: {4}. Resultado: {5}",
                new Object[]{valor, origem.getCodigo(), valorConvertidoBd, destino.getCodigo(), taxaCambio.taxa(), resultado.valorConvertido()});

        return resultado;
    }
}
