package primeiro.desafio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Random;

class FirstChallengeTest {

    @Test void appHasAGreeting() {
        Assertions.assertDoesNotThrow(() -> FirstChallenge.evenAndOddSequencePrinter(new ArrayList<>(
            new Random().ints(new Random().nextInt(2, (int) (1e4 + 1)))
                .filter(possiblePositiveInt -> possiblePositiveInt > 0)
                .boxed()
                .toList()))
        );
    }
}
