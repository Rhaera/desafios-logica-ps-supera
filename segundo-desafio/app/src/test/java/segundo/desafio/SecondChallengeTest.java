package segundo.desafio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Random;

class SecondChallengeTest {

    @Test void checkingIfMoneyPerValueOfBillAndCoinSelectorIsCorrectlyImplemented() {
        assertEquals(HashMap.class, SecondChallenge.getSingleton().moneyPerValueOfBillAndCoinSelector(BigDecimal.valueOf(new Random().nextDouble(0d, 1e6 + 1))).getClass());
        assertFalse(SecondChallenge.getSingleton()
                .moneyPerValueOfBillAndCoinSelector(BigDecimal.valueOf(new Random().nextDouble(1e-2, 1e6 + 1)))
                .values()
                .stream()
                .allMatch(probablePositiveDouble -> probablePositiveDouble == 0d));
        assertInstanceOf(SecondChallenge.class, SecondChallenge.getSingleton());
    }
}
