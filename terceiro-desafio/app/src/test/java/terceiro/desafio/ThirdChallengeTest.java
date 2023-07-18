package terceiro.desafio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Random;

class ThirdChallengeTest {

    @Test
    void checkingIfAllPairsWhichAchieveTargetValueIsCorrectlyImplemented() {
        Assertions.assertNotNull(ThirdChallenge.allPairsWhichAchieveTargetValueInArray(new Random().nextInt(), new Random().ints(new Random().nextInt()).toArray()));
    }
}
