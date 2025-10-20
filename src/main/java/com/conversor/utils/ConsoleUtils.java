package com.conversor.utils;

import java.util.Scanner;
import java.util.function.Predicate;

public class ConsoleUtils {

    public static void clearScreen() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }

    public static void waitForEnter(Scanner scanner) {
        System.out.print("Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    public static double solicitarDouble(Scanner scanner, String prompt, String errorMessage, java.util.function.Predicate<Double> validator) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                if (validator.test(value)) {
                    return value;
                } else {
                    System.out.println(errorMessage);
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número válido.");
                // logger.log(Level.WARNING, "Entrada inválida: {0}", scanner.nextLine()); // Logger not available here
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }
}
