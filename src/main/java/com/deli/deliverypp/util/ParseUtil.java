package com.deli.deliverypp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseUtil {

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
//
//    public static void main(String[] args) {
//        String[] list = new String[]{
//                "/static/image/ste.td", "sldkfjsd.ddtd", "slkdfjs9834kjn--dfdf.kkldf"
//        };
//
//        for (String s:list) {
//            System.out.println(parseImgPath(s));
//        }
//    }
}
