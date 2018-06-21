package com.deuce.me.matura;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * Created by ingli on 21.06.2018.
 */

public class passwordHasher {

    public passwordHasher() {}

    public byte[] generateSalt(int bytes) {
        byte[] salt = new byte[bytes];

        try {

            SecureRandom random = new SecureRandom().getInstance("SHA1PRNG", "SUN");
            random.nextBytes(salt);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        return salt;
    }

    //https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
    public byte[] hashPassword(String password, byte[] salt) {
        try {
            MessageDigest digester = MessageDigest.getInstance("SHA-256");
            digester.update(salt);
            byte[] hash = digester.digest(password.getBytes());

            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String hashPassword(String password, String salt) {
        byte[] saltBytes = convertHexToBytes(salt);
        String data = convertBytesToHex(hashPassword(password, saltBytes));
        return data;
    }

    //https://stackoverflow.com/questions/332079/in-java-how-do-i-convert-a-byte-array-to-a-string-of-hex-digits-while-keeping-l/2197650#2197650
    public String convertBytesToHex(byte[] bytes) {

        final char[] hexArray = "0123456789ABCDEF".toCharArray(); //Characters needed for HexValue
        char[] hexChars = new char[bytes.length * 2];

        for(int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xff;
            hexChars[i * 2] = hexArray[v/16];
            hexChars[i * 2 + 1] = hexArray[v%16];
        }

        return new String(hexChars);
    }

    //https://stackoverflow.com/questions/140131/convert-a-string-representation-of-a-hex-dump-to-a-byte-array-using-java
    public byte[] convertHexToBytes(String hex) {
        byte[] output = new byte[hex.length() / 2];
        for(int i = 0; i < hex.length(); i+= 2) {
            output[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i+1), 16));
        }
        return output;
    }

}
