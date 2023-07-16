package segundo.desafio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SecondChallenge {

    private final Map<BigDecimal, Integer> moneyPerValueOfNoteAndCoinMap = new HashMap<>();

    public SecondChallenge() {
        moneyPerValueOfNoteAndCoinMap.putAll(Map.of(
                BigDecimal.valueOf(10000L, 2), 0,
                BigDecimal.valueOf(5000L, 2), 0,
                BigDecimal.valueOf(2000L, 2), 0,
                BigDecimal.valueOf(1000L, 2), 0,
                BigDecimal.valueOf(500L, 2), 0,
                BigDecimal.valueOf(200L, 2), 0)
        );
        moneyPerValueOfNoteAndCoinMap.putAll(Map.of(
                BigDecimal.valueOf(100L, 2), 0,
                BigDecimal.valueOf(100L / 2, 2), 0,
                BigDecimal.valueOf(100L / 4, 2), 0,
                BigDecimal.valueOf(100L / 10, 2), 0,
                BigDecimal.valueOf(100L / 20, 2), 0,
                BigDecimal.valueOf(100L / 100, 2), 0)
        );
    }

    public static void main(String[] args) {
        SecondChallenge secondChallengeInstance = new SecondChallenge();
        Scanner userInputScanner = new Scanner(System.in);
        System.out.println();
        long testRhR = userInputScanner.nextLong();
        System.out.println(secondChallengeInstance.moneyPerValueOfNoteAndCoinSelector(BigDecimal.valueOf(testRhR)));
    }

    public Map<BigDecimal, Integer> moneyPerValueOfNoteAndCoinSelector(BigDecimal decimalValueSelected) {
        return decimalValueSelected.compareTo(BigDecimal.ZERO) == 0 ?
            this.moneyPerValueOfNoteAndCoinMap :
            moneyPerValueOfNoteAndCoinSelection(decimalValueSelected, new HashMap<>(moneyPerValueOfNoteAndCoinMap));
    }

    private Map<BigDecimal, Integer> moneyPerValueOfNoteAndCoinSelection(BigDecimal decimalValueSelected, Map<BigDecimal, Integer> copyOfClassMap) {
        BigDecimal copyOfSelectedValue = decimalValueSelected;
        for (BigDecimal moneyValueType:
                copyOfClassMap.keySet()
                        .stream()
                        .sorted(Collections.reverseOrder())
                        .toList()) {
            int integerPartOfDividedResult = numberOfTimesIntCanBeDividedByOther(copyOfSelectedValue, moneyValueType);
            copyOfClassMap.put(moneyValueType, integerPartOfDividedResult);
            copyOfSelectedValue = copyOfSelectedValue.subtract(moneyValueType.multiply(BigDecimal.valueOf(integerPartOfDividedResult)));
        }
        return copyOfClassMap;
    }

    private int numberOfTimesIntCanBeDividedByOther(BigDecimal dividedNumber, BigDecimal divisorNumber) {
        return dividedNumber.divide(divisorNumber, 0, RoundingMode.DOWN)
                .intValue();
    }
}
