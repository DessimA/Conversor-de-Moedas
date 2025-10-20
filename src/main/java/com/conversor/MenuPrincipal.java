package com.conversor;

import com.conversor.models.Moeda;
import com.conversor.services.ConversorMoedas;
import com.conversor.models.HistoricoConversao;
import com.conversor.services.ApiTaxaCambioService;
import com.conversor.utils.ApiConfig;
import com.conversor.utils.LoggerUtil;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuPrincipal {
    private static final Logger logger = LoggerUtil.getLogger(MenuPrincipal.class.getName());
    private final Scanner scanner;
    private ConversorMoedas conversorMoedas; // Changed to non-final
    private final HistoricoConversao historicoConversao;
    private InterfaceConversao interfaceConversao; // Changed to non-final
    private final Estatisticas estatisticas;
    private String preferredApi; // New field for preferred API

    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
        this.historicoConversao = new HistoricoConversao(); // Will be loaded from file later
        this.estatisticas = new Estatisticas(historicoConversao, scanner);
        this.preferredApi = System.getProperty("api.preferred", "EXCHANGERATE_API"); // Default from system property or hardcoded
        inicializarServicos(); // Initialize services with default/preferred API
    }

    private void inicializarServicos() {
        ApiConfig apiConfig = ApiConfig.getInstance();
        ApiTaxaCambioService apiTaxaCambioService = new ApiTaxaCambioService(apiConfig, preferredApi);
        this.conversorMoedas = new ConversorMoedas(apiTaxaCambioService, historicoConversao);
        this.interfaceConversao = new InterfaceConversao(); // Re-initialize if needed, or ensure it's stateless
    }

    public void exibirMenu() {
        System.out.println("\n========================================");
        System.out.println("          CONVERSOR DE MOEDAS         ");
        System.out.println("========================================");
        System.out.println("1) Converter moeda");
        System.out.println("2) Ver histórico de conversões");
        System.out.println("3) Limpar histórico");
        System.out.println("4) Ver taxas disponíveis");
        System.out.println("5) Ver Estatísticas");
        System.out.println("6) Selecionar API de Câmbio (Atual: " + preferredApi + ")");
        System.out.println("7) Sair");
        System.out.println("========================================");
        System.out.print("Escolha uma opção: ");
    }

    public void executar() {
        int opcao = 0;
        while (opcao != 7) { // Change loop termination condition to option 7 (Sair)
            clearScreen(); // Clear screen before displaying menu
            exibirMenu();
            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (opcao) {
                    case 1:
                        realizarConversao();
                        break;
                    case 2:
                        verHistorico();
                        break;
                    case 3:
                        limparHistorico();
                        break;
                    case 4:
                        verTaxasDisponiveis();
                        break;
                    case 5:
                        verEstatisticas();
                        break;
                    case 6:
                        selecionarApi();
                        break;
                    case 7:
                        System.out.println("Saindo do Conversor de Moedas. Até logo!");
                        logger.log(Level.INFO, "Aplicação encerrada.");
                        break;
                    default:
                        System.out.println("Opção inválida. Por favor, escolha uma opção entre 1 e 7.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                logger.log(Level.WARNING, "Entrada inválida no menu: {0}", scanner.nextLine());
                scanner.nextLine(); // Consume the invalid input
            } catch (IOException | InterruptedException | RuntimeException e) {
                System.err.println("Ocorreu um erro: " + e.getMessage());
                logger.log(Level.SEVERE, "Erro durante a execução: {0}", e.getMessage());
            }
        }
        scanner.close();
    }

    private void selecionarApi() {
        clearScreen();
        System.out.println("\n--- Selecionar API de Câmbio ---");
        System.out.println("1) ExchangeRate-API (Padrão)");
        System.out.println("2) OpenExchangeRates");
        System.out.print("Escolha a API preferencial: ");
        try {
            int apiChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (apiChoice) {
                case 1:
                    preferredApi = "EXCHANGERATE_API";
                    System.out.println("API selecionada: ExchangeRate-API");
                    break;
                case 2:
                    preferredApi = "OPENEXCHANGERATES";
                    System.out.println("API selecionada: OpenExchangeRates");
                    break;
                default:
                    System.out.println("Opção inválida. Usando ExchangeRate-API como padrão.");
                    preferredApi = "EXCHANGERATE_API";
            }
            inicializarServicos(); // Re-initialize services with new API
            System.out.print("Pressione ENTER para voltar ao menu...");
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            logger.log(Level.WARNING, "Entrada inválida na seleção de API: {0}", scanner.nextLine());
            scanner.nextLine(); // Consume the invalid input
        }
    }

    private void realizarConversao() throws IOException, InterruptedException {
        clearScreen(); // Clear screen before displaying option content
        interfaceConversao.realizarConversao(scanner, conversorMoedas);
    }

    private void verHistorico() {
        clearScreen(); // Clear screen before displaying option content
        interfaceConversao.exibirHistorico(scanner, historicoConversao);
    }

    private void limparHistorico() {
        clearScreen(); // Clear screen before displaying option content
        interfaceConversao.limparHistorico(scanner, historicoConversao);
    }

    private void verTaxasDisponiveis() {
        clearScreen(); // Clear screen before displaying option content
        System.out.println("\n--- Taxas Disponíveis ---");
        System.out.println("Moedas suportadas:");
        for (Moeda moeda : Moeda.values()) {
            System.out.printf("- %s (%s) - %s%n", moeda.getCodigo(), moeda.getNomeCompleto(), moeda.getSimbolo());
        }
        System.out.print("\nPressione ENTER para voltar ao menu...");
        scanner.nextLine(); // Wait for user input
    }

    private void verEstatisticas() {
        clearScreen(); // Clear screen before displaying option content
        estatisticas.exibirEstatisticas();
    }

    public static void main(String[] args) {
        logger.log(Level.INFO, "Aplicação iniciada.");
        MenuPrincipal menu = new MenuPrincipal();
        menu.executar();
    }

    private void clearScreen() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }
}
