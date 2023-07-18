package quarto.desafio;

import java.util.*;
import java.util.stream.Collectors;

public class FourthChallenge {

    private static final String NOT_PARSABLE_STRING = "intentionalNotNumericString";

    public static void main(String[] args) {
        int chosenLineCounter = 1;
        int amountOfIntegersToBeChosen = 0;
        List<String> selectedLines = new ArrayList<>();
        Scanner userInputScanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Olá, bem vindo, vamos começar o desafio!");
        System.out.println("Para começar, por favor, escolha quantas frases serão decifradas (escreva pelo menos uma frase para prosseguir com o teste):");
        while (true) {
            if (amountOfIntegersToBeChosen != 0 && selectedLines.size() == amountOfIntegersToBeChosen) {
                System.out.println("Pronto, seguem as frases de teste devidamente decifradas por ordem de inserção:");
                System.out.println();
                encryptLinesPrinter(selectedLines);
                break;
            }
            if (amountOfIntegersToBeChosen != 0)
                System.out.printf("Por favor, digite a " + chosenLineCounter + "º frase ou %s para sair:\n", "'s'");
            System.out.println();
            String userInput = userInputScanner.nextLine();
            System.out.println();
            if (userInput.trim().equalsIgnoreCase("s"))
                break;
            boolean isValidIntegerOrLineAnswer = amountOfIntegersToBeChosen == 0 ? isInteger(userInput.trim()) : isValidLine(userInput);
            if (amountOfIntegersToBeChosen != 0 && !isValidIntegerOrLineAnswer) {
                System.out.println("Ops! Frase incorreta, por favor, digite uma frase com pelo menos 2 e no máximo 100 letras + espaços ou tecle \"s\" para encerrar a sessão.");
                System.out.println();
                continue;
            }
            if (amountOfIntegersToBeChosen != 0) {
                selectedLines.add(userInput.trim());
                chosenLineCounter++;
                System.out.println();
                continue;
            }
            if (isValidIntegerOrLineAnswer)
                amountOfIntegersToBeChosen = Integer.parseInt(userInput.trim()) == 0 ? -1 : Integer.parseInt(userInput.trim());
            if (amountOfIntegersToBeChosen < 0 && !isInteger(NOT_PARSABLE_STRING))
                amountOfIntegersToBeChosen = 0;
        }
        System.out.println();
        System.out.println("Obrigado por participar do desafio #4! Volte sempre.");
        System.out.println();
        userInputScanner.close();
    }

    private static boolean isValidLine(String lineToBeValidated) {
        return lineToBeValidated.trim().length() > 0 && lineToBeValidated.trim().length() % 2 == 0 && lineToBeValidated.trim().length() <= 100;
    }

    private static boolean isInteger(String maybeInteger) {
        try {
            Integer.parseInt(maybeInteger);
            return true;
        } catch (NumberFormatException failedConversionToInteger) {
            System.out.println();
            System.out.println("Desculpe, valor inválido!");
            System.out.printf("Por favor, digite um valor apropriado para prosseguirmos ou digite %s para sair. \n", "'s'");
            return false;
        }
    }

    private static void encryptLinesPrinter(List<String> encryptedLines) {
        encryptedLines.forEach(encryptedLine ->
            System.out.println(
                reverseEncryptor(encryptedLine.substring(0, encryptedLine.length() / 2)).concat(reverseEncryptor(encryptedLine.substring(encryptedLine.length() / 2)))
            )
        );
    }

    public static String reverseEncryptor(String decryptedLine) {
        List<String> toEncodeList = Arrays.stream(decryptedLine.split("")).collect(Collectors.toList());
        Collections.reverse(toEncodeList);
        return String.join("", toEncodeList).toUpperCase();
    }
}
