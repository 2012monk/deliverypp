package com.deli.deliverypp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseUtil {

//    private static String imgRoute = "https://deli.alconn.co/static/image/";
    private static String domain = "http://112.169.196.76:47788";
    private static String imgRoute = "/static/image/";

    public static String parseImgPath (String name) {
        Pattern p = Pattern.compile(imgRoute);
        if (name != null) {
            Matcher m = p.matcher(name);
            if (m.find()){
                return m.group().replaceAll(imgRoute,"");
            }
            return imgRoute + name;
        }
        return null;
    }

//    public static String parseImgPath (String name) {
//        return name;
//    }

}
