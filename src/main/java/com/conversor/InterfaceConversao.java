package com.conversor;

import com.conversor.models.ConversaoResultado;
import com.conversor.models.Moeda;
import com.conversor.models.HistoricoConversao;
import com.conversor.services.ConversorMoedas;
import com.conversor.utils.LoggerUtil;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterfaceConversao {
    private static final Logger logger = LoggerUtil.getLogger(InterfaceConversao.class.getName());

    public void exibirMenuSelecaoMoeda() {
        System.out.println("\n--- Seleção de Moeda ---");
        Moeda[] moedas = Moeda.values();
        int index = 1;
        for (Moeda moeda : moedas) {
            if (moeda.isFiat()) {
                System.out.printf("%d) %s - %s (%s)%n", index++, moeda.getCodigo(), moeda.getNomeCompleto(), moeda.getSimbolo());
            }
        }
        System.out.print("------------------------\n");
    }

    public void exibirMenuSelecaoCripto() {
        System.out.println("\n--- Seleção de Criptomoeda ---");
        Moeda[] moedas = Moeda.values();
        int index = 1;
        for (Moeda moeda : moedas) {
            if (moeda.isCrypto()) {
                System.out.printf("%d) %s - %s (%s)%n", index++, moeda.getCodigo(), moeda.getNomeCompleto(), moeda.getSimbolo());
            }
        }
        System.out.print("------------------------\n");
    }

    public Moeda selecionarCripto(Scanner scanner, String prompt) {
        Moeda[] moedas = Moeda.values();
        while (true) {
            System.out.print(prompt);
            try {
                int escolha = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                int cryptoIndex = 0;
                for (int i = 0; i < moedas.length; i++) {
                    if (moedas[i].isCrypto()) {
                        cryptoIndex++;
                        if (cryptoIndex == escolha) {
                            return moedas[i];
                        }
                    }
                }
                System.out.println("Opção inválida. Por favor, escolha um número da lista.");
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                logger.log(Level.WARNING, "Entrada inválida na seleção de moeda: {0}", scanner.nextLine());
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    public Moeda selecionarMoeda(Scanner scanner, String prompt) {
        Moeda[] moedas = Moeda.values();
        while (true) {
            System.out.print(prompt);
            try {
                int escolha = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                int fiatIndex = 0;
                for (int i = 0; i < moedas.length; i++) {
                    if (moedas[i].isFiat()) {
                        fiatIndex++;
                        if (fiatIndex == escolha) {
                            return moedas[i];
                        }
                    }
                }
                System.out.println("Opção inválida. Por favor, escolha um número da lista.");
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                logger.log(Level.WARNING, "Entrada inválida na seleção de moeda: {0}", scanner.nextLine());
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    public double solicitarValor(Scanner scanner) {
        while (true) {
            System.out.print("Digite o valor a ser convertido: ");
            try {
                double valor = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                if (valor > 0) {
                    return valor;
                }
 else {
                    System.out.println("O valor deve ser maior que zero.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número válido.");
                logger.log(Level.WARNING, "Entrada inválida no valor de conversão: {0}", scanner.nextLine());
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    public void realizarConversao(Scanner scanner, ConversorMoedas conversorMoedas) throws IOException, InterruptedException {
        boolean continuar = true;
        while (continuar) {
            try {
                exibirMenuSelecaoMoeda();
                Moeda origem = selecionarMoeda(scanner, "Selecione a moeda de origem: ");
                Moeda destino = selecionarMoeda(scanner, "Selecione a moeda de destino: ");
                double valor = solicitarValor(scanner);

                ConversaoResultado resultado = conversorMoedas.converter(valor, origem, destino);

                System.out.printf("\n%s %.2f %s = %s %.2f %s (Taxa: %.4f)%n",
                        origem.getCodigo(), resultado.valorOriginal(), origem.getSimbolo(),
                        destino.getCodigo(), resultado.valorConvertido(), destino.getSimbolo(),
                        resultado.taxaUtilizada());

            } catch (InputMismatchException e) {
                System.out.println("Erro de entrada: Por favor, digite um valor válido.");
                logger.log(Level.WARNING, "Erro de InputMismatchException durante conversão: {0}", e.getMessage());
            } catch (RuntimeException e) { // IllegalArgumentException is a subclass of RuntimeException
                System.out.println("Erro: " + e.getMessage());
                logger.log(Level.WARNING, "Erro durante conversão: {0}", e.getMessage());
            } catch (IOException | InterruptedException e) {
                System.err.println("Erro de comunicação com a API: " + e.getMessage());
                logger.log(Level.SEVERE, "Erro de API durante conversão: {0}", e.getMessage());
                throw e; // Re-throw to be handled by MenuPrincipal
            }

            System.out.print("\nDeseja fazer outra conversão? (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (!resposta.equals("s")) {
                continuar = false;
            }
        }
    }

    public void realizarConversaoCripto(Scanner scanner, ConversorMoedas conversorMoedas) throws IOException, InterruptedException {
        boolean continuar = true;
        while (continuar) {
            try {
                exibirMenuSelecaoCripto();
                Moeda origem = selecionarCripto(scanner, "Selecione a criptomoeda de origem: ");
                exibirMenuSelecaoMoeda();
                Moeda destino = selecionarMoeda(scanner, "Selecione a moeda de destino: ");
                double valor = solicitarValor(scanner);

                ConversaoResultado resultado = conversorMoedas.converter(valor, origem, destino);

                System.out.printf("\n%s %.2f %s = %s %.2f %s (Taxa: %.4f)%n",
                        origem.getCodigo(), resultado.valorOriginal(), origem.getSimbolo(),
                        destino.getCodigo(), resultado.valorConvertido(), destino.getSimbolo(),
                        resultado.taxaUtilizada());

            } catch (InputMismatchException e) {
                System.out.println("Erro de entrada: Por favor, digite um valor válido.");
                logger.log(Level.WARNING, "Erro de InputMismatchException durante conversão: {0}", e.getMessage());
            } catch (RuntimeException e) { // IllegalArgumentException is a subclass of RuntimeException
                System.out.println("Erro: " + e.getMessage());
                logger.log(Level.WARNING, "Erro durante conversão: {0}", e.getMessage());
            } catch (IOException | InterruptedException e) {
                System.err.println("Erro de comunicação com a API: " + e.getMessage());
                logger.log(Level.SEVERE, "Erro de API durante conversão: {0}", e.getMessage());
                throw e; // Re-throw to be handled by MenuPrincipal
            }

            System.out.print("\nDeseja fazer outra conversão? (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (!resposta.equals("s")) {
                continuar = false;
            }
        }
    }

    public void realizarConversaoCriptoParaCripto(Scanner scanner, ConversorMoedas conversorMoedas) throws IOException, InterruptedException {
        boolean continuar = true;
        while (continuar) {
            try {
                exibirMenuSelecaoCripto();
                Moeda origem = selecionarCripto(scanner, "Selecione a criptomoeda de origem: ");
                exibirMenuSelecaoCripto();
                Moeda destino = selecionarCripto(scanner, "Selecione a criptomoeda de destino: ");
                double valor = solicitarValor(scanner);

                ConversaoResultado resultado = conversorMoedas.converter(valor, origem, destino);

                System.out.printf("\n%s %.2f %s = %s %.2f %s (Taxa: %.4f)%n",
                        origem.getCodigo(), resultado.valorOriginal(), origem.getSimbolo(),
                        destino.getCodigo(), resultado.valorConvertido(), destino.getSimbolo(),
                        resultado.taxaUtilizada());

            } catch (InputMismatchException e) {
                System.out.println("Erro de entrada: Por favor, digite um valor válido.");
                logger.log(Level.WARNING, "Erro de InputMismatchException durante conversão: {0}", e.getMessage());
            } catch (RuntimeException e) { // IllegalArgumentException is a subclass of RuntimeException
                System.out.println("Erro: " + e.getMessage());
                logger.log(Level.WARNING, "Erro durante conversão: {0}", e.getMessage());
            } catch (IOException | InterruptedException e) {
                System.err.println("Erro de comunicação com a API: " + e.getMessage());
                logger.log(Level.SEVERE, "Erro de API durante conversão: {0}", e.getMessage());
                throw e; // Re-throw to be handled by MenuPrincipal
            }

            System.out.print("\nDeseja fazer outra conversão? (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (!resposta.equals("s")) {
                continuar = false;
            }
        }
    }

    public void exibirHistorico(Scanner scanner, HistoricoConversao historicoConversao) {
        System.out.println("\n--- Histórico de Conversões ---");
        if (historicoConversao.estaVazio()) {
            System.out.println("O histórico de conversões está vazio.");
            System.out.print("\nPressione ENTER para voltar ao menu...");
            scanner.nextLine(); // Wait for user input
            return;
        }

        List<ConversaoResultado> ultimasConversoes = historicoConversao.getUltimasConversoes(10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        System.out.println("Data/Hora          | Origem  | Destino | Valor      | Resultado  | Taxa");
        System.out.println("-------------------|---------|---------|------------|------------|-------");
        for (ConversaoResultado resultado : ultimasConversoes) {
            System.out.printf("%s | %-7s | %-7s | %-10.2f | %-10.2f | %.4f%n",
                    resultado.dataHora().format(formatter),
                    resultado.moedaOrigem().getCodigo(),
                    resultado.moedaDestino().getCodigo(),
                    resultado.valorOriginal(),
                    resultado.valorConvertido(),
                    resultado.taxaUtilizada());
        }
        System.out.println("-------------------|---------|---------|------------|------------|-------");

        System.out.print("\nDeseja exportar o histórico para um arquivo TXT? (s/n): ");
        String resposta = scanner.nextLine().trim().toLowerCase();
        if (resposta.equals("s")) {
            exportarHistoricoParaTxt(historicoConversao.getTodasConversoes());
        }
    }

    public void limparHistorico(Scanner scanner, HistoricoConversao historicoConversao) {
        System.out.println("\n--- Limpar Histórico ---");
        if (historicoConversao.estaVazio()) {
            System.out.println("O histórico de conversões já está vazio.");
            return;
        }

        System.out.print("Tem certeza que deseja limpar todo o histórico de conversões? (s/n): ");
        String confirmacao = scanner.nextLine().trim().toLowerCase();
        if (confirmacao.equals("s")) {
            historicoConversao.limparHistorico();
            System.out.println("Histórico de conversões limpo com sucesso.");
            logger.log(Level.INFO, "Histórico de conversões limpo pelo usuário.");
        } else {
            System.out.println("Operação de limpeza de histórico cancelada.");
        }
    }

    private void exportarHistoricoParaTxt(List<ConversaoResultado> historico) {
        String fileName = "historico_conversoes_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
        File logDir = new File("logs");
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        File file = new File(logDir, fileName);

        try (PrintWriter writer = new PrintWriter(file)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            writer.println("Histórico de Conversões - " + LocalDateTime.now().format(formatter));
            writer.println("----------------------------------------------------------------------------------------------------");
            writer.println("Data/Hora            | Origem  | Destino | Valor      | Resultado  | Taxa");
            writer.println("---------------------|---------|---------|------------|------------|-------");
            for (ConversaoResultado resultado : historico) {
                writer.printf("%s | %-7s | %-7s | %-10.2f | %-10.2f | %.4f%n",
                        resultado.dataHora().format(formatter),
                        resultado.moedaOrigem().getCodigo(),
                        resultado.moedaDestino().getCodigo(),
                        resultado.valorOriginal(),
                        resultado.valorConvertido(),
                        resultado.taxaUtilizada());
            }
            writer.println("----------------------------------------------------------------------------------------------------");
            System.out.println("Histórico exportado com sucesso para: " + file.getAbsolutePath());
            logger.log(Level.INFO, "Histórico exportado para: {0}", file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Erro ao exportar histórico para TXT: " + e.getMessage());
            logger.log(Level.SEVERE, "Erro ao exportar histórico para TXT: {0}", e.getMessage());
        }
    }
}
