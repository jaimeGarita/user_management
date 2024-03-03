package com.iftc.user_management_ms.infraestructure.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.util.Base64Utils;

public class AESEncryptDecrypt {

    private final static String SECRET_PASSWORD = "C U N A S I F T C J A I M E";
    private static String APPEND = "Salted__";
    private static String HASH_CIPHER = "AES/CBC/PKCS5Padding";
    private static String AES = "AES";
    private static String CHARSET_TYPE = "UTF-8";
    private static String KDF_DIGEST = "MD5";

    public static String encrypt(String plainText) {
        try {
            final int ivSize = 128;
            final int keySize = 256;
            byte[] saltBytes = generateSalt(8);
            byte[] key = new byte[keySize / 8];
            byte[] iv = new byte[ivSize / 8];
            
            EvpKDF(SECRET_PASSWORD.getBytes(CHARSET_TYPE), keySize, ivSize, saltBytes, key, iv);
            Cipher cipher = Cipher.getInstance(HASH_CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
            byte[] cipherText = cipher.doFinal(plainText.getBytes(CHARSET_TYPE));

            byte[] sBytes = APPEND.getBytes(CHARSET_TYPE);
            byte[] b = new byte[sBytes.length + saltBytes.length + cipherText.length];
            System.arraycopy(sBytes, 0, b, 0, sBytes.length);
            System.arraycopy(saltBytes, 0, b, sBytes.length, saltBytes.length);
            System.arraycopy(cipherText, 0, b, sBytes.length + saltBytes.length, cipherText.length);

            byte[] bEncode = Base64Utils.encode(b);

            return new String(bEncode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * @param password   passphrase
     * @param cipherText encrypted string
     */
    public static String decrypt(String cipherText) {
        try {
            final int ivSize = 128;
            final int keySize = 256;
            byte[] ctBytes = Base64Utils.decode(cipherText.getBytes(CHARSET_TYPE));
            byte[] saltBytes = Arrays.copyOfRange(ctBytes, 8, 16);
            byte[] ciphertextBytes = Arrays.copyOfRange(ctBytes, 16, ctBytes.length);
            byte[] key = new byte[keySize / 8];
            byte[] iv = new byte[ivSize / 8];

            EvpKDF(SECRET_PASSWORD.getBytes("UTF-8"), keySize, ivSize, saltBytes, key, iv);
            Cipher cipher = Cipher.getInstance(HASH_CIPHER);
            
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
            byte[] recoveredPlaintextBytes = cipher.doFinal(ciphertextBytes);

            return new String(recoveredPlaintextBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] EvpKDF(byte[] password, int keySize, int ivSize, byte[] salt, byte[] resultKey,
            byte[] resultIv) throws NoSuchAlgorithmException {
        return EvpKDF(password, keySize, ivSize, salt, 1, KDF_DIGEST, resultKey, resultIv);
    }

    private static byte[] generateSalt(int length) {
        Random r = new SecureRandom();
        byte[] salt = new byte[length];
        r.nextBytes(salt);
        return salt;
    }

    private static byte[] EvpKDF(byte[] password, int keySize, int ivSize, byte[] salt, int iterations,
            String hashAlgorithm, byte[] resultKey, byte[] resultIv) throws NoSuchAlgorithmException {
        keySize = keySize / 32;
        ivSize = ivSize / 32;
        int targetKeySize = keySize + ivSize;
        byte[] derivedBytes = new byte[targetKeySize * 4];
        int numberOfDerivedWords = 0;
        byte[] block = null;
        MessageDigest hasher = MessageDigest.getInstance(hashAlgorithm);
        while (numberOfDerivedWords < targetKeySize) {
            if (block != null) {
                hasher.update(block);
            }
            hasher.update(password);
            block = hasher.digest(salt);
            hasher.reset();

            // Iterations
            for (int i = 1; i < iterations; i++) {
                block = hasher.digest(block);
                hasher.reset();
            }

            System.arraycopy(block, 0, derivedBytes, numberOfDerivedWords * 4,
                    Math.min(block.length, (targetKeySize - numberOfDerivedWords) * 4));

            numberOfDerivedWords += block.length / 4;
        }

        System.arraycopy(derivedBytes, 0, resultKey, 0, keySize * 4);
        System.arraycopy(derivedBytes, keySize * 4, resultIv, 0, ivSize * 4);

        return derivedBytes; // key + iv
    }

}