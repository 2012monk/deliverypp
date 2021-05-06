package com.deli.deliverypp.util;

import static com.deli.deliverypp.util.ConfigLoader.load;

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
