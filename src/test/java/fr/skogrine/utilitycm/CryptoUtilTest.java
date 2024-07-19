package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;
import javax.crypto.SecretKey;
import static org.junit.jupiter.api.Assertions.*;

class CryptoUtilTest {

    @Test
    void testCryptoUtil() throws Exception {
        SecretKey key = CryptoUtil.generateKey();
        String originalText = "Hello, World!";
        String encryptedText = CryptoUtil.encrypt(originalText, key);
        String decryptedText = CryptoUtil.decrypt(encryptedText, key);

        assertEquals(originalText, decryptedText);
        assertNotNull(CryptoUtil.sha256(originalText));
    }
}
