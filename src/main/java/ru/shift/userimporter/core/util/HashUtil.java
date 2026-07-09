package ru.shift.userimporter.core.util;

import lombok.experimental.UtilityClass;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@UtilityClass
public class HashUtil {

    private static final String ALGORITHM = "SHA-1";

    public String generateHash(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            byte[] hashBytes = digest.digest(data);
            return bytesToHex(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate hash", e);
        }
    }

    public String generateHash(String data) {
        return generateHash(data.getBytes());
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public boolean verifyHash(byte[] data, String expectedHash) {
        String actualHash = generateHash(data);
        return actualHash.equals(expectedHash);
    }
}