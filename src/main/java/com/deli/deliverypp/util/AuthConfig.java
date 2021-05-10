package com.deli.deliverypp.util;

import com.deli.deliverypp.util.annotaions.LoadConfig;

@LoadConfig(path = "application.oauth.properties")
public class AuthConfig  {

    public static String auth;
    public static String test;


    public static void main(String[] args) {
        ConfigLoader.init();
        System.out.println(test);
        System.out.println(auth);
    }
}
