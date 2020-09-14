package by.mishota.graduation.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Md5Util {
    private Md5Util() {
    }

    public static String generateHashMd5(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");

        byte[] passwordDigest = digest.digest(password.getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : passwordDigest) {
            stringBuilder.append(Integer.toHexString(0xff & b));

        }
        return stringBuilder.toString();
    }
}
