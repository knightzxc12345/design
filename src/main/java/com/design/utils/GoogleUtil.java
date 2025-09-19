package com.design.utils;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

public class GoogleUtil {

    public static String extractKey(){
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey secretKey = gAuth.createCredentials();
        return secretKey.getKey();
    }

    public static String extractQrCode(String issuer, String name, String key){
        GoogleAuthenticatorKey secretKey = new GoogleAuthenticatorKey.Builder(key).build();
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL(issuer, name, secretKey);
    }

    public static boolean isCodeValid(String key, int verificationCode){
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        return gAuth.authorize(key, verificationCode);
    }

}