package quarto.desafio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FourthChallengeTest {

    @Test
    void checkingIfReverseEncryptorIsCorrectlyImplemented() {

        Assertions.assertEquals("SUPERA", FourthChallenge.reverseEncryptor("arepus"));

    }
}
