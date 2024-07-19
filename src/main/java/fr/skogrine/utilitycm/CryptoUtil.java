package fr.skogrine.utilitycm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * CryptoUtil provides utility methods for encryption, decryption, and secure hash functions using modern algorithms.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * SecretKey key = CryptoUtil.generateKey();
 * String encrypted = CryptoUtil.encrypt("Hello, World!", key);
 * String decrypted = CryptoUtil.decrypt(encrypted, key);
 * String hash = CryptoUtil.sha256("Hello, World!");
 * System.out.println("Encrypted: " + encrypted);
 * System.out.println("Decrypted: " + decrypted);
 * System.out.println("SHA-256 Hash: " + hash);
 * }</pre>
 */
public class CryptoUtil {

    private static final String ALGORITHM = "AES";

    /**
     * Generates a new secret key for AES encryption.
     *
     * @return the generated secret key
     * @throws NoSuchAlgorithmException if the algorithm is not available
     */
    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(128);
        return keyGen.generateKey();
    }

    /**
     * Encrypts a string using the specified key.
     *
     * @param data the data to encrypt
     * @param key the secret key
     * @return the base64-encoded encrypted data
     * @throws Exception if an error occurs during encryption
     */
    public static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts a base64-encoded string using the specified key.
     *
     * @param encryptedData the base64-encoded encrypted data
     * @param key the secret key
     * @return the decrypted data
     * @throws Exception if an error occurs during decryption
     */
    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    /**
     * Computes the SHA-256 hash of a string.
     *
     * @param data the data to hash
     * @return the hexadecimal SHA-256 hash
     * @throws NoSuchAlgorithmException if the algorithm is not available
     */
    public static String sha256(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
