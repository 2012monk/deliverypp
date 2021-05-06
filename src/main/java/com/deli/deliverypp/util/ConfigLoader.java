package com.deli.deliverypp.util;

import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

public class ConfigLoader {

    private static final Logger log = Logger.getGlobal();
    private final HashMap<String, Properties> cache = new HashMap<>();

    static {
        Reflections reflections = new Reflections("com.deli.deliverypp");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(LoadConfig.class);
        for (Class<?> c: annotated) {
            try {
                load(c.getDeclaredAnnotation(LoadConfig.class).path(), c);
            } catch (ClassNotFoundException e) {
                log.warning("load failed");
                e.printStackTrace();
            }
        }
    }

    static public void load(String path, Class<?> aClass) throws ClassNotFoundException {
        Properties p = new Properties();

        URL configPath = ConfigLoader.class.getClassLoader().getResource(path);
        try {
            File file = null;
            URI uri = null;
            if (configPath == null){
//                file = new File("./src/main/resources/"+path);
            }else{
                uri = configPath.toURI();
                file = new File(uri);

            }


            assert file != null;
            FileReader fr = new FileReader(file);
            p.load(fr);

            for (Field f: aClass.getDeclaredFields()){
                f.setAccessible(true);
                // Load Db driver
                if (f.getName().equals("DRIVER")){
                    try {
                        Class.forName(p.getProperty(f.getName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                f.set(String.class, p.getProperty(f.getName()));
            }

            log.info( "load Complete "  +"\n"+"[" + Thread.currentThread().getName() + Thread.currentThread().getId()+ "]"+ aClass.getName() );

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Class<?> call() throws ClassNotFoundException {
        return Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
    }

    public static void init() {

    }


}
