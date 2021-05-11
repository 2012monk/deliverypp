package com.deli.deliverypp.util;

public class ParseUtil {

    private static String imgRoute = "/static/image/";

    public static String parseImgPath (String name) {
        return imgRoute + name;
    }
}
