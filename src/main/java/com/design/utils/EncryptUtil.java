package com.design.utils;

import org.apache.commons.lang3.StringUtils;

public class EncryptUtil {

    public static String name(String name) {
        if(StringUtils.isBlank(name)){
            return "";
        }
        if(name.length() == 1){
            return name;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(name.substring(0, 1));
        sb.append("*");
        if(name.length() == 2){
            return sb.toString();
        }
        sb.append(name.substring(2));
        return sb.toString();
    }

    public static String phoneNumber(String phoneNumber) {
        if(StringUtils.isBlank(phoneNumber)){
            return "";
        }
        if(phoneNumber.length() <= 2){
            return phoneNumber;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(phoneNumber.substring(0, 2));
        sb.append("***");
        if(phoneNumber.length() <= 5){
            return sb.toString();
        }
        sb.append(phoneNumber.substring(5));
        return sb.toString();
    }

    public static String email(String email) {
        if(StringUtils.isBlank(email)){
            return "";
        }
        if(email.length() <= 2){
            return email;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(email.substring(0, 2));
        sb.append("***");
        if(email.length() <= 5){
            return sb.toString();
        }
        sb.append(email.substring(5));
        return sb.toString();
    }

    public static String address(String address) {
        if(StringUtils.isBlank(address)){
            return "";
        }
        if(address.length() <= 2){
            return address;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(address.substring(0, 2));
        sb.append("***");
        if(address.length() <= 5){
            return sb.toString();
        }
        sb.append(address.substring(5));
        return sb.toString();
    }

}
