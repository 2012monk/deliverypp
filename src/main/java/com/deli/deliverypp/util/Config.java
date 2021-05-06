package com.deli.deliverypp.util;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;


/**
 * define config
 * load config from external properties
 */
public class Config extends ConfigLoader{

    public static String HOME_URL;
    public static String DB_URL;
    public static String DB_ID;
    public static String DB_PW;
    public static String OUT_URL;
    public static String DRIVER;


    static {
//        try {
////            load("./config.properties");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    // load
//    static {
//        Properties p = new Properties();
//        try {
//            System.out.println(Config.class.getClassLoader().getResource("/config.properties"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        URL path = Config.class.getClassLoader().getResource("/config.properties");
//
//        try {
//            File file = null;
//            URI uri = null;
//            if (path == null){
//                file = new File("./src/main/resources/config.properties");
//            }else{
//                uri = path.toURI();
//                file = new File(uri);
//            }
//
//            FileReader fr = new FileReader(file);
//
//            p.load(fr);
//            Field[] fields = Config.class.getDeclaredFields();
//
//            for (Field f: fields){
//                f.setAccessible(false);
//                if (f.getModifiers() != 25){
//                    f.set(String.class, p.getProperty(f.getName()));
//
//                }
//
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }


}
