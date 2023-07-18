package terceiro.desafio;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ThirdChallenge {

    private static final String NOT_PARSABLE_STRING = "intentionalNotNumericString";

    private ThirdChallenge() {
        throw new AssertionError();
    }

    public static void main(String[] args) {
        Optional<Integer> target;
        int chosenNumberCounter = 1;
        int amountOfIntegersToBeChosen = 0;
        int[] selectedIntegers = {};
        Scanner userInputScanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Olá, bem vindo, vamos começar o desafio!");
        System.out.println("Para iniciarmos, por favor, selecione o valor alvo (K) para o desafio #3 (sendo \"K\" um número inteiro):");
        target = choosingTargetValueInsideLoop(userInputScanner);
        if (target.isEmpty())
            byeAndThanksFinalizer();
        System.out.println("Muito bem, então o valor alvo " + '"' + 'K' + '"' + " é igual a: " + target.orElseThrow() + ".");
        System.out.println("Agora, por favor, escolha quantos números INTEIROS serão inseridos no array (sendo pelo menos 2 INTEIROS para formar um par):");
        while (true) {
            if (amountOfIntegersToBeChosen != 0 && chosenNumberCounter == amountOfIntegersToBeChosen + 1)
                break;
            if (amountOfIntegersToBeChosen != 0)
                System.out.printf("Por favor, digite o " + chosenNumberCounter + "º número ou %s para sair:\n", "'s'");
            System.out.println();
            String userInput = userInputScanner.nextLine();
            System.out.println();
            if (userInput.trim().equalsIgnoreCase("s"))
                break;
            boolean isValidIntegerAnswer = isInteger(userInput.trim());
            if (amountOfIntegersToBeChosen != 0 && isValidIntegerAnswer) {
                selectedIntegers = Arrays.copyOf(selectedIntegers, chosenNumberCounter);
                selectedIntegers[chosenNumberCounter - 1] = Integer.parseInt(userInput.trim());
                chosenNumberCounter++;
                System.out.println();
                continue;
            }
            if (amountOfIntegersToBeChosen == 0 && isValidIntegerAnswer)
                amountOfIntegersToBeChosen = Integer.parseInt(userInput.trim()) == 0 ? 1 : Integer.parseInt(userInput.trim());
            if (amountOfIntegersToBeChosen != 0 &&
                amountOfIntegersToBeChosen <= 1 &&
                !isInteger(NOT_PARSABLE_STRING))
                amountOfIntegersToBeChosen = 0;
        }
        if (!(amountOfIntegersToBeChosen != 0 && chosenNumberCounter == amountOfIntegersToBeChosen + 1)) {
            byeAndThanksFinalizer();
        }
        String finalAnswer = allPairsWhichAchieveTargetValueInArray(target.get(), selectedIntegers);
        if (target.get() == 0 && finalAnswer.contains(":"))
            finalAnswer = finalAnswer.substring(0, finalAnswer.length() - 1).concat(".");
        System.out.println("Existe" + (finalAnswer.contains(",") || !finalAnswer.contains(":") ?
                "m " :
                " ") + (finalAnswer.contains(":") && finalAnswer.substring(finalAnswer.indexOf(":")).length() > 2 ?
                finalAnswer.split(",").length :
                0) + " par" + (finalAnswer.contains(",") || !finalAnswer.contains(":") ? "es" : "") + " cuja diferença é igual ao valor alvo " + finalAnswer);
        byeAndThanksFinalizer();
        userInputScanner.close();
    }

    private static void byeAndThanksFinalizer() {
        System.out.println();
        System.out.println("Obrigado por participar do desafio #3! Volte sempre.");
        System.out.println();
        System.exit(0);
    }

    private static Optional<Integer> choosingTargetValueInsideLoop(Scanner inputScanner) {
        int target = 0;
        int exit = 0;
        while (true) {
            System.out.println();
            String userInput = inputScanner.nextLine();
            System.out.println();
            if (userInput.trim().equalsIgnoreCase("s")) {
                exit = 1;
                break;
            }
            boolean isValidIntegerAnswer = isInteger(userInput.trim());
            if (isValidIntegerAnswer) {
                target = Integer.parseInt(userInput.trim());
                break;
            }
        }
        if (exit > 0) {
            return Optional.empty();
        }
        return Optional.of(target);
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

    public static String allPairsWhichAchieveTargetValueInArray(int targetValue, int[] chosenIntegerArray) {
        return targetValue == 0 ? allPairsMatchedForZeroTargetValue(chosenIntegerArray) : allPairsMatchedForNonZeroTargetValue(targetValue, chosenIntegerArray);
    }

    private static String allPairsMatchedForNonZeroTargetValue(int nonZeroTargetValue, int[] chosenIntegerArrayWithoutZeroAsTarget) {
        Set<Integer> duplicateEraser = new HashSet<>();
        StringBuilder answerBuffer = new StringBuilder(nonZeroTargetValue + ": ");
        Arrays.sort(chosenIntegerArrayWithoutZeroAsTarget);
        for (int index = 0; index < chosenIntegerArrayWithoutZeroAsTarget.length; index++)
            if (duplicateEraser.add(chosenIntegerArrayWithoutZeroAsTarget[index]))
                answerBuffer.append(whichPairsCanAchieveTargetValueByIndex(nonZeroTargetValue, index, chosenIntegerArrayWithoutZeroAsTarget));
        return answerBuffer.replace(answerBuffer.length() - 2, answerBuffer.length() - 1, ".").toString();
    }

    private static String allPairsMatchedForZeroTargetValue(int[] chosenIntegerArrayWithZeroAsTarget) {
        return howManyDuplicateValuesInsideArray(chosenIntegerArrayWithZeroAsTarget) == 0 ?
            0 + "." :
            0 + ": " + IntStream.of(chosenIntegerArrayWithZeroAsTarget)
                    .filter(possibleDuplicateValue -> chosenIntegerArrayWithZeroAsTarget.length - IntStream.of(chosenIntegerArrayWithZeroAsTarget).filter(comparableExample -> comparableExample != possibleDuplicateValue).count() > 1)
                    .distinct()
                    .mapToObj(numberFiltered -> numberFiltered + " - " + numberFiltered + " = " + 0 + ", ")
                    .collect(Collectors.joining())
                    .trim();
    }

    private static String whichPairsCanAchieveTargetValueByIndex(int targetValue, int beginIndex, int[] chosenIntegerArray) {
        return Arrays.stream(chosenIntegerArray, beginIndex, chosenIntegerArray.length)
            .filter(numberToSubtract -> chosenIntegerArray[beginIndex] - numberToSubtract == targetValue || numberToSubtract - chosenIntegerArray[beginIndex] == targetValue)
            .mapToObj(numberFiltered ->
                chosenIntegerArray[beginIndex] - numberFiltered == targetValue ?
                chosenIntegerArray[beginIndex] + " - " + numberFiltered + " = " + targetValue + ", " :
                numberFiltered + " - " + chosenIntegerArray[beginIndex] + " = " + targetValue + ", ")
            .collect(Collectors.joining());
    }

    private static int howManyDuplicateValuesInsideArray(int[] possibleArrayWithDuplicateInts) {
        return (int) (possibleArrayWithDuplicateInts.length - IntStream.of(possibleArrayWithDuplicateInts).distinct().count());
    }
}
