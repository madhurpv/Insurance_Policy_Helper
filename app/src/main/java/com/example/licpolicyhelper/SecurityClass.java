package com.example.licpolicyhelper;

import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SecurityClass {

    private static final String masterPwd = "Hello83EDAI";

    private static final String ALGORITHM = "AES";

    // Encrypt or Decrypt based on the mode
    public static String encryptDecrypt(String input, String password, boolean isEncrypt) {
        try {
            // Create a SecretKey from the password
            SecretKeySpec secretKey = createSecretKey(password);

            // Get Cipher instance for AES
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            // Initialize Cipher for encryption or decryption
            if (isEncrypt) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] encrypted = cipher.doFinal(input.getBytes());
                return Base64.getEncoder().encodeToString(encrypted); // Return encrypted string as base64
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                byte[] decoded = Base64.getDecoder().decode(input); // Decode from base64
                byte[] decrypted = cipher.doFinal(decoded);
                return new String(decrypted); // Return decrypted string
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Create a SecretKey from the password
    private static SecretKeySpec createSecretKey(String password) {
        try {
            // Use SHA-256 to create a 256-bit key
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] key = sha.digest(password.getBytes());
            return new SecretKeySpec(key, ALGORITHM);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static boolean comparePWDs(String givenPWD, String origHash){
        String newHash = getSHA512Hash(givenPWD);
        if(newHash.equals(origHash)){
            return true;
        }
        return false;
    }

    public static String getSHA512Hash(String input) {
        try {
            // Get an instance of the SHA-512 MessageDigest
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

            // Perform the hash computation on the input string
            byte[] hashBytes = messageDigest.digest(input.getBytes());

            // Convert the byte array into a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                // Convert each byte to a 2-digit hexadecimal value
                hexString.append(String.format("%02x", b));
            }

            // Return the final hexadecimal string
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encrypt_main_Password(String password){
        return encryptDecrypt(password, masterPwd, true);
    }
}
