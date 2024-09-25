package org.example;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EncryptedPrinter extends PrinterDecorator {

    private String publicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAn4pp9Iqmz/WZtd/nQJIocOsvMc0l4C+H7ex9bfyYN9pLjaXohgZub6meVawI8KNWVrRfx9psSF2V22DM88IBWO8Fw2BSxXSKcCGffuGhycY48p2lpKdBKNVp0EFZNIf2wZjS9sF9Jz0WfepcDTCoxkxl1p24vNHqIReEGJyVeo6i9bLjdnNgry7u7vMY+ogLeLhytdnO2lJP5aRRr8DzPvBLwf67oC3VcgE3KZ6EMYzaAcqR36Aytci/YVt9RnQEQ65Uo8GwzGYo2kAnAEmMHBEKX1iJwmMUuGFAMif9LdykOvhlKC808rkJgThLEHHcCtJLNWKWrJIUYsbtrYiFuQIDAQAB";
    private String privateKey="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCfimn0iqbP9Zm13+dAkihw6y8xzSXgL4ft7H1t/Jg32kuNpeiGBm5vqZ5VrAjwo1ZWtF/H2mxIXZXbYMzzwgFY7wXDYFLFdIpwIZ9+4aHJxjjynaWkp0Eo1WnQQVk0h/bBmNL2wX0nPRZ96lwNMKjGTGXWnbi80eohF4QYnJV6jqL1suN2c2CvLu7u8xj6iAt4uHK12c7aUk/lpFGvwPM+8EvB/rugLdVyATcpnoQxjNoBypHfoDK1yL9hW31GdARDrlSjwbDMZijaQCcASYwcEQpfWInCYxS4YUAyJ/0t3KQ6+GUoLzTyuQmBOEsQcdwK0ks1YpaskhRixu2tiIW5AgMBAAECggEAEzobpdiAh7HPdKnyFTKSxB+xKt0l55GNiKtxEaxuQyuVjRHFvEKR1Ew1Tu7c5En9sZUAXAtjSbSu8ZGGHF8GSYuuKUsg/oyafdlXfvOTS3K4/JLqL7Xmdj+DW+acPmafuC+CRocFXbwQy1ukp1mfZXKMmA5JrBvXjbsR9ONeEmG7JOUAA5bzLE+wI9AmXFMEXM+AjRN2jvfjVWW6RrlSMKrD4it+OL/2AAsdHyTgF1kTjZJD3/HTc2IZ7Vglck7vFGmizOJI1pFE8N3Qfw3Qt28AiTorCe+7XoGzohYkZnMB6VSFAM8rwNgfpAqL8WePJE0zdvFKQyNiDgQKzCgPTwKBgQC+pRZbKoASdZ/oVOdoKCpyEOkOES0o/tA+OCCXZRzKNJsyHkrmg/K/NqbuXACUTkHOD6yRGpi+C8sWSEwqU6HygAPYK3axz+lG6ux+2ImIop0hbx6+G6gDzBDIzliecNskTZRLSZbRj4MA0UZ+dT1he6gW4lOsJVcO0/o2+I1iWwKBgQDWO6LGWQDH2vu3stvhxV9EkNUoAI7nrGXrdEAWetzUwztBP+9k49k9yS5ZY9w0bukLhme/sG4IvVLCsd3aXUc3rmQS4qAF9FJfiN4jgA8Qgy4l8TaP1JoaeMAXPUogtV1o/Wey59quRVuQEyBZgV9BpxRjMv50ZySNtGqut1oMewKBgDzyG8aDPLQqBDfKxLuIc6FJhKepmA1OAJaTbN/ZRC3kSWTpSDqPHhBA0XbL1KeUqPbODfXJUeEXdhImhKrXV6Nlh1UY9/X6KHIyce5PHRCgI6lnk6Vkw/6KwybeyHfGTlg5sNmsqdlOjqu5O1b79eZvGJpQOj2DJmSoIYpnRROXAoGAYYb2nEtqYpFbZI5lMUvUCffRQgu3Atrl7yGWB2XZYHacZCECD7D2df0/P2yJk8kmCJwgYRCllw7xPTcR41XxlPSsFDjdVriaQ2mgjxK+SOsfOCCukR3dJc3wzOOW+nr2UlSCP9zzHcDvZRB/+p89yTqRunM9iapm5qfKpU1NDj8CgYA405QJoHxFHrpMfqCZjtFHVs1ja3+VyqdXJi7DEMnuoJfa5CMk+7AxaS7ELzU9dxoSxDUcE3UzslX5MOa1FRUNeUaIjDJ4pKZ2j2hIKWwIXHvg7DyglXRTPMDl3+tvroOoPFlzD7tu7fDV+eU3UlqwSBdN4QjBBOQMs1sQ8JFIGQ==";

    // Original message
    private String originalMessage = "Hello, this is a secret message!";
    private String encryptedMessage = encrypt(originalMessage, publicKey);

    public EncryptedPrinter(Printer printer) throws Exception {
        super(printer);
    }


public static String encrypt(String message, String  publicKeyString) throws Exception {
    byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString.getBytes());
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
    PublicKey publicKey =  keyFactory.generatePublic(publicKeySpec);

    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);

    return Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes(StandardCharsets.UTF_8)));
}

public static String decrypt(String encryptedMessage, String privateKeyString) throws Exception {
    byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString.getBytes());
    PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");

    PrivateKey privateKey= keyFactory.generatePrivate(privateKeySpec);

    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    cipher.init(Cipher.DECRYPT_MODE, privateKey);
    return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedMessage)), StandardCharsets.UTF_8);
}



    @Override
    public void print(String message) throws Exception {
        String encryptedMessage = encrypt(message, publicKey);
        super.print(encryptedMessage);
    }
}