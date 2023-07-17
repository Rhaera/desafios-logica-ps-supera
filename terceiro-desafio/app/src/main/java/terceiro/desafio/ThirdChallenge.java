package terceiro.desafio;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ThirdChallenge {

    private ThirdChallenge() {
        throw new AssertionError();
    }

    public static void main(String[] args) {
        System.out.println(IntStream.of(12, 1, 15, 89, 4, 86).distinct().count());
        int[] testArray = new int[] {12, 1, 12, 86, 4, 86};
        System.out.println(allPairsWhichAchieveTargetValueInArray(0, testArray));
    }

    private static String allPairsWhichAchieveTargetValueInArray(int targetValue, int[] chosenIntegerArray) {
        if (targetValue == 0)
            return howManyDuplicateValuesInsideArray(chosenIntegerArray) == 0 ?
            targetValue + "." :
            targetValue + ": " + IntStream.of(chosenIntegerArray)
                .filter(possibleDuplicateValue -> chosenIntegerArray.length - IntStream.of(chosenIntegerArray).filter(comparableExample -> comparableExample != possibleDuplicateValue).count() > 1)
                .distinct()
                .mapToObj(numberFiltered -> numberFiltered + " - " + numberFiltered + " = " + targetValue + ", ")
                .collect(Collectors.joining())
                .trim();
        StringBuilder answerBuffer = new StringBuilder(targetValue + ": ");
        for (int index = 0; index < chosenIntegerArray.length; index++)
            answerBuffer.append(whichPairsCanAchieveTargetValueByIndex(targetValue, index, chosenIntegerArray));
        return answerBuffer.replace(answerBuffer.length() - 2, answerBuffer.length() - 1, ".").toString();
    }

    public static String whichPairsCanAchieveTargetValueByIndex(int targetValue, int beginIndex, int[] chosenIntegerArray) {
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
