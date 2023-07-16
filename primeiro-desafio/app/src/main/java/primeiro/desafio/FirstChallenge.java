package primeiro.desafio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FirstChallenge {

    private static final String NOT_PARSABLE_STRING = "intentionalNotNumericString";

    public static void main(String[] args) {
        int chosenNumberCounter = 1;
        int amountOfIntegersToBeChosen = 0;
        List<Integer> selectedIntegers = new ArrayList<>();
        Scanner userInputScanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Olá, bem vindo, vamos começar o desafio!");
        System.out.println("Para começar, por favor, escolha quantos números INTEIROS serão ordenados (sendo pelo menos 2 e no máximo 10.000 números):");
        while (true) {
            if (amountOfIntegersToBeChosen != 0 && selectedIntegers.size() == amountOfIntegersToBeChosen) {
                System.out.println("Pronto, segue a sequência dos pares (em ordem crescente) e dos ímpares (em ordem decrescente):");
                evenAndOddSequencePrinter(selectedIntegers);
                break;
            }
            if (amountOfIntegersToBeChosen != 0)
                System.out.printf("Por favor, digite o " + chosenNumberCounter + "º número ou %s para sair:\n", "'s'");
            System.out.println();
            String userInput = userInputScanner.nextLine();
            System.out.println();
            if (userInput.trim().equalsIgnoreCase("s"))
                break;
            boolean isValidIntegerAnswer = isInteger(userInput);
            if (isValidIntegerAnswer && Integer.parseInt(userInput) < 0 && !isInteger(NOT_PARSABLE_STRING))
                continue;
            if (amountOfIntegersToBeChosen != 0 && isValidIntegerAnswer) {
                selectedIntegers.add(Integer.parseInt(userInput));
                chosenNumberCounter++;
                System.out.println();
                continue;
            }
            if (amountOfIntegersToBeChosen == 0 && isValidIntegerAnswer)
                amountOfIntegersToBeChosen = Integer.parseInt(userInput) == 0 ? 1 : Integer.parseInt(userInput);
            if (amountOfIntegersToBeChosen != 0 &&
                (amountOfIntegersToBeChosen > 1e5 || amountOfIntegersToBeChosen <= 1) &&
                !isInteger(NOT_PARSABLE_STRING))
                amountOfIntegersToBeChosen = 0;
        }
        System.out.println("Obrigado por participar do desafio #1! Volte sempre.");
        System.out.println();
        userInputScanner.close();
    }

    public static void evenAndOddSequencePrinter(List<Integer> integerList) {
        evenSequencePrinter(integerList);
        oddSequencePrinter(integerList);
        System.out.println();
    }

    private static boolean isInteger(String maybeInteger) {
        try {
            Integer.parseInt(maybeInteger);
            return true;
        } catch (NumberFormatException failedConversionToInteger) {
            System.out.println();
            System.out.println("Desculpe, valor inválido!");
            System.out.printf("Por favor, digite um valor apropriado para prosseguirmos ou digite %s para sair. ", "'s'");
            return false;
        }
    }

    private static void evenSequencePrinter(List<Integer> integerList) {
        if (hasAtLeastOneEven(integerList)) {
            integerList.sort(Integer::compareTo);
            integerList.stream()
                .filter(possibleEvenNumber -> possibleEvenNumber % 2 == 0)
                .forEach(System.out::println);
        }
    }
    
    private static void oddSequencePrinter(List<Integer> integerList) {
        if (hasAtLeastOneOdd(integerList)) {
            integerList.sort(Collections.reverseOrder());
            integerList.stream()
                .filter(possibleOddNumber -> possibleOddNumber % 2 != 0)
                .forEach(System.out::println);
        }
    }

    private static boolean hasAtLeastOneEven(List<Integer> listToVerifyIfThereIsOneOrMoreEven) {
        return listToVerifyIfThereIsOneOrMoreEven.stream()
                .anyMatch(possibleEvenNumber -> possibleEvenNumber % 2 == 0);
    }

    private static boolean hasAtLeastOneOdd(List<Integer> listToVerifyIfThereIsOneOrMoreOdd) {
        return listToVerifyIfThereIsOneOrMoreOdd.stream()
                .anyMatch(possibleOddNumber -> possibleOddNumber % 2 != 0);
    }
}
