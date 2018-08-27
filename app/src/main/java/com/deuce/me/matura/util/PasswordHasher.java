package com.deuce.me.matura.util;

import android.provider.Settings;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Created by ingli on 21.06.2018.
 */

//https://nelenkov.blogspot.com/2012/04/using-password-based-encryption-on.html

public class PasswordHasher {

    private static final int ITERATIONS = 1000;
    private static final int saltlength = 32;


    public PasswordHasher() {}

    public byte[] generateSalt() {

        byte[] salt = new byte[saltlength];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

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
        byte[] saltBytes = hexStringToByteArray(salt);
        String data = byteArrayToHexString(hashPassword(password, saltBytes));
        return data;
    }

    //https://stackoverflow.com/questions/332079/in-java-how-do-i-convert-a-byte-array-to-a-string-of-hex-digits-while-keeping-l/2197650#2197650
    //https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
    public String convertBytesToHex(byte[] bytes) {

        final char[] hexArray = "0123456789ABCDEF".toCharArray(); //Characters needed for HexValue
        char[] hexChars = new char[bytes.length * 2];

        for(int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xff;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 0xf];
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


    //http://texnological.blogspot.com/2014/01/convert-hex-string-to-byte-array-and.html
    public byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }

        return data;
    }

    public String byteArrayToHexString(byte[] b) {
        int len = b.length;
        String data = new String();

        for (int i = 0; i < len; i++){
            data += Integer.toHexString((b[i] >>> 4) & 0xf);
            data += Integer.toHexString(b[i] & 0xf);
        }
        return data;
    }

}
