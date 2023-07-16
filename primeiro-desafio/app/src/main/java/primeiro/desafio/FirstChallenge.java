package primeiro.desafio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FirstChallenge {

    public static void main(String[] args) {
        int chosenNumberCounter = 1;
        int amountOfIntegersToBeChosen = 0;
        List<Integer> selectedIntegers = new ArrayList<>();
        Scanner userInputScanner = new Scanner(System.in);
        System.out.println("Olá, bem vindo, vamos começar o desafio!");
        System.out.println("Para começar, por favor, escolha quantos números INTEIROS serão ordenados (sendo pelo menos 2 e no máximo 10.000 números):\n");
        while (true) {
            if (amountOfIntegersToBeChosen != 0)
                System.out.printf("Por favor, digite o " + chosenNumberCounter + "º número ou %s para sair:\n", 's');
            String userInput = userInputScanner.nextLine();
            if (userInput.equals("s"))
                break;
            if (amountOfIntegersToBeChosen != 0 && chosenNumberCounter == amountOfIntegersToBeChosen) {
                evenAndOddSequencePrinter(selectedIntegers);
                break;
            }
            if (amountOfIntegersToBeChosen != 0 && isInteger(userInput)) {
                selectedIntegers.add(Integer.parseInt(userInput));
                chosenNumberCounter++;
                continue;
            }
            if (isInteger(userInput) && Integer.parseInt(userInput) <= 1e5 && Integer.parseInt(userInput) > 1) {
                amountOfIntegersToBeChosen = Integer.parseInt(userInput);
                continue;
            }
            if (amountOfIntegersToBeChosen == 0 && (Integer.parseInt(userInput) > 1e5 || Integer.parseInt(userInput) < 2)) {
                System.out.println("Desculpe, valor inválido!\n");
                System.out.printf("Por favor, digite um valor apropriado para prosseguirmos ou digite %s para sair.\n", 's');
            }
        }
        System.out.println("Obrigado por participar do desafio #1! Volte sempre.");
        userInputScanner.close();
    }

    public static void evenAndOddSequencePrinter(List<Integer> integerList) {
        evenSequencePrinter(integerList);
        oddSequencePrinter(integerList);
    }

    private static boolean isInteger(String maybeInteger) {
        try {
            Integer.parseInt(maybeInteger);
            return true;
        } catch (NumberFormatException failedConversionToInteger) {
            System.out.println("Desculpe, valor inválido!\n");
            System.out.printf("Por favor, digite um valor apropriado para prosseguirmos ou digite %s para sair.\n", 's');
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
