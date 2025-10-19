package com.conversor;

import com.conversor.models.ConversaoResultado;
import com.conversor.models.HistoricoConversao;
import com.conversor.models.Moeda;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Estatisticas {
    private final HistoricoConversao historicoConversao;
    private final Scanner scanner;

    public Estatisticas(HistoricoConversao historicoConversao, Scanner scanner) {
        this.historicoConversao = historicoConversao;
        this.scanner = scanner;
    }

    public void exibirEstatisticas() {
        System.out.println("\n========================================");
        System.out.println("          ESTATÍSTICAS DE USO         ");
        System.out.println("========================================");

        List<ConversaoResultado> todasConversoes = historicoConversao.getTodasConversoes();

        if (todasConversoes.isEmpty()) {
            System.out.println("Nenhuma conversão realizada ainda para gerar estatísticas.");
            System.out.println("========================================");
            System.out.print("Pressione ENTER para voltar ao menu...");
            scanner.nextLine(); // Wait for user input
            return;
        }

        System.out.printf("Total de Conversões Realizadas: %d%n", getTotalConversoes(todasConversoes));
        System.out.println("----------------------------------------");

        Moeda moedaMaisConvertida = getMoedaMaisConvertida(todasConversoes);
        if (moedaMaisConvertida != null) {
            System.out.printf("Moeda Mais Convertida (Origem/Destino): %s (%s)%n", moedaMaisConvertida.getCodigo(), moedaMaisConvertida.getNomeCompleto());
        }
        System.out.println("----------------------------------------");

        System.out.println("Valor Total Convertido por Moeda:");
        getValorTotalConvertidoPorMoeda(todasConversoes).forEach((moeda, total) ->
                System.out.printf("  %s: %.2f%n", moeda.getCodigo(), total)
        );
        System.out.println("----------------------------------------");

        System.out.printf("Média de Valores Convertidos: %.2f%n", getMediaValoresConvertidos(todasConversoes));
        System.out.println("----------------------------------------");

        System.out.println("Frequência de Uso das Moedas (Gráfico ASCII):");
        gerarGraficoFrequenciaMoedas(todasConversoes);
        System.out.println("========================================");
        System.out.print("Pressione ENTER para voltar ao menu...");
        scanner.nextLine(); // Wait for user input
    }

    private int getTotalConversoes(List<ConversaoResultado> conversoes) {
        return conversoes.size();
    }

    private Moeda getMoedaMaisConvertida(List<ConversaoResultado> conversoes) {
        Map<Moeda, Long> frequencia = new HashMap<>();
        conversoes.forEach(c -> {
            frequencia.merge(c.moedaOrigem(), 1L, Long::sum);
            frequencia.merge(c.moedaDestino(), 1L, Long::sum);
        });

        return frequencia.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private Map<Moeda, Double> getValorTotalConvertidoPorMoeda(List<ConversaoResultado> conversoes) {
        Map<Moeda, Double> totais = new HashMap<>();
        conversoes.forEach(c -> {
            totais.merge(c.moedaOrigem(), c.valorOriginal(), Double::sum);
            totais.merge(c.moedaDestino(), c.valorConvertido(), Double::sum);
        });
        return totais;
    }

    private double getMediaValoresConvertidos(List<ConversaoResultado> conversoes) {
        return conversoes.stream()
                .mapToDouble(ConversaoResultado::valorConvertido)
                .average()
                .orElse(0.0);
    }

    private void gerarGraficoFrequenciaMoedas(List<ConversaoResultado> conversoes) {
        Map<Moeda, Long> frequencia = new HashMap<>();
        conversoes.forEach(c -> {
            frequencia.merge(c.moedaOrigem(), 1L, Long::sum);
            frequencia.merge(c.moedaDestino(), 1L, Long::sum);
        });

        if (frequencia.isEmpty()) {
            System.out.println("  Nenhuma moeda utilizada para gerar o gráfico.");
            return;
        }

        long maxCount = frequencia.values().stream().max(Comparator.naturalOrder()).orElse(0L);
        int barLength = 30;

        frequencia.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(Moeda::getCodigo)))
                .forEach(entry -> {
                    Moeda moeda = entry.getKey();
                    long count = entry.getValue();
                    int numHashes = (int) ((double) count / maxCount * barLength);
                    String bar = "#".repeat(numHashes);
                    System.out.printf("  %s | %-30s %d%n", moeda.getCodigo(), bar, count);
                });
    }
}
