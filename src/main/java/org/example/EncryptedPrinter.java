package org.example;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EncryptedPrinter extends PrinterDecorator {
    private static final String PUBLIC_KEY_STRING = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0xkF2F1bA5bGKdL4z57" +
            "6h8Vw+j5LKgqY38IR4/DQ7smD9zyYAp4w9mR9y5KOE5H5A5Gx0uOZV5x5ro6Kz" +
            "2DPUkU09FdFJ6+fJKF9S3R0BkzYz7xO/YuqU6cP7duWoZ8UO8tySG+fNjmAqO9" +
            "Rz1U4wIuIHUksXZ2sPQIzvOe0d1ZQ7J6kRE5tWwS9jZxJAs4nFwl/Z8MkjhW0F" +
            "kVQKoA5X6T2GhHo8ypfHZgfJ7C2Iq1rhbUL0xErFw2jPoGpE1rZrHII1NIDu81b" +
            "jWEBCiDwOQ2ThPvKsq72bmzHb99OoBexyH9U8WwD3lSP6RO3MxX6Up7yZmgxub" +
            "ofJcl+m4p6Rm8xW57CMOaSskA4LkCkOXLQIDAQAB";

    private static final String PRIVATE_KEY_STRING = "MIIEpAIBAAKCAQEA0xkF2F1bA5bGKdL4z57" +
            "6h8Vw+j5LKgqY38IR4/DQ7smD9zyYAp4w9mR9y5KOE5H5A5Gx0uOZV5x5ro6Kz" +
            "2DPUkU09FdFJ6+fJKF9S3R0BkzYz7xO/YuqU6cP7duWoZ8UO8tySG+fNjmAqO9" +
            "Rz1U4wIuIHUksXZ2sPQIzvOe0d1ZQ7J6kRE5tWwS9jZxJAs4nFwl/Z8MkjhW0F" +
            "kVQKoA5X6T2GhHo8ypfHZgfJ7C2Iq1rhbUL0xErFw2jPoGpE1rZrHII1NIDu81b" +
            "jWEBCiDwOQ2ThPvKsq72bmzHb99OoBexyH9U8WwD3lSP6RO3MxX6Up7yZmgxub" +
            "ofJcl+m4p6Rm8xW57CMOaSskA4LkCkOXLQIDAQAB";

    private static PublicKey getPublicKey(String publicKeyString) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    private static PrivateKey getPrivateKey(String privateKeyString) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    public static String encrypt(String message, String publicKeyString) throws Exception {
        PublicKey publicKey = getPublicKey(publicKeyString);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedMessage, String privateKeyString) throws Exception {
        PrivateKey privateKey = getPrivateKey(privateKeyString);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
        return new String(decryptedBytes);
    }

    public EncryptedPrinter(Printer printer) {
        super(printer);
    }

    @Override
    public void print(String message) throws Exception {
        String encryptedMessage = encrypt(message, PUBLIC_KEY_STRING);
        super.print(encryptedMessage);
    }
}