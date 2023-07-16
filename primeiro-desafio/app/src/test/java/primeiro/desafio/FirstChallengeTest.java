package primeiro.desafio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Random;

class FirstChallengeTest {

    @Test void checkingIfEvenAndOddSequencePrinterIsCorrectlyImplemented() {
        Assertions.assertDoesNotThrow(() -> FirstChallenge.evenAndOddSequencePrinter(new ArrayList<>(
            new Random().ints(new Random().nextInt(2, (int) (1e5 + 1)))
                .filter(possibleNotNegativeInt -> possibleNotNegativeInt >= 0)
                .boxed()
                .toList()))
        );
    }
}
