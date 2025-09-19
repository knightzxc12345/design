package com.design.utils;

import java.security.SecureRandom;

public class PasswordUtil {

    public static String extract(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        char uppercaseLetter = (char) (random.nextInt(26) + 'A');
        password.append(uppercaseLetter);
        char lowercaseLetter = (char) (random.nextInt(26) + 'a');
        password.append(lowercaseLetter);
        int digit = random.nextInt(10);
        password.append(digit);
        while (password.length() < length) {
            int randomChar = random.nextInt(62);
            if (randomChar < 26) {
                password.append((char) (randomChar + 'A'));
            } else if (randomChar < 52) {
                password.append((char) (randomChar - 26 + 'a'));
            } else {
                password.append((char) (randomChar - 52 + '0'));
            }
        }
        return password.toString();
    }

}