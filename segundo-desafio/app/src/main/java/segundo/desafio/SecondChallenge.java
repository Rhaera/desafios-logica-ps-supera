package segundo.desafio;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SecondChallenge {

    private final Map<BigDecimal, Integer> moneyPerValueOfNoteAndCoinMap = new HashMap<>();

    public SecondChallenge() {
        moneyPerValueOfNoteAndCoinMap.putAll(Map.of(
                BigDecimal.valueOf(100L, 2), 0,
                BigDecimal.valueOf(50L, 2), 0,
                BigDecimal.valueOf(20L, 2), 0,
                BigDecimal.valueOf(10L, 2), 0,
                BigDecimal.valueOf(5L, 2), 0,
                BigDecimal.valueOf(2L, 2), 0)
        );
        moneyPerValueOfNoteAndCoinMap.putAll(Map.of(
                BigDecimal.valueOf(1L, 2), 0,
                BigDecimal.valueOf(1L / 2, 2), 0,
                BigDecimal.valueOf(1L / 4, 2), 0,
                BigDecimal.valueOf(1L / 10, 2), 0,
                BigDecimal.valueOf(1L / 20, 2), 0,
                BigDecimal.valueOf(1L / 100, 2), 0)
        );
    }

    public static void main(String[] args) {
        SecondChallenge secondChallengeInstance = new SecondChallenge();
        Scanner userInputScanner = new Scanner(System.in);
        System.out.println();
    }
}
