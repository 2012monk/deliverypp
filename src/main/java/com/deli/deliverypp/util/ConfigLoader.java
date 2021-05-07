package com.deli.deliverypp.util;

import io.jsonwebtoken.SignatureAlgorithm;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.security.interfaces.RSAPrivateKey;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

public class ConfigLoader {

    private static final Logger log = Logger.getGlobal();
    private final HashMap<String, Properties> cache = new HashMap<>();

    static {
        Reflections reflections = new Reflections("com.deli.deliverypp", new FieldAnnotationsScanner(), new TypeAnnotationsScanner(), new SubTypesScanner());
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(LoadConfig.class);
        Set<Field> keyLoad = reflections.getFieldsAnnotatedWith(KeyLoad.class);
        System.out.println(keyLoad.size());
        for (Field c: keyLoad) {
            System.out.println(c.getName());
            c.setAccessible(true);
            loadKey(c.getDeclaredAnnotation(KeyLoad.class).path(), c);
        }
        for (Class<?> c: annotated) {
            try {
                load(c.getDeclaredAnnotation(LoadConfig.class).path(), c);
            } catch (ClassNotFoundException e) {
                log.warning("load failed");
                e.printStackTrace();
            }
        }
        System.out.println(annotated.size()+"hoo");

    }

    static public void loadKey(String path, Field field) {
        URL keyPath = ConfigLoader.class.getClassLoader().getResource(path);
        System.out.println(keyPath);
        try {

            URI uri = keyPath.toURI();
            File file = new File(uri);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine() ) != null) {
                if (!line.equals("-----BEGIN RSA PRIVATE KEY-----") && !line.equals("-----END RSA PRIVATE KEY-----")){
                    sb.append(line);

                }
            }

            byte[] keyBytes = DatatypeConverter.parseBase64Binary(sb.toString());
            SecretKey secretKey = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
//            System.out.println(Arrays.toString(secretKey.getEncoded()));
            if (field.getType() == String.class) {
                field.set(String.class, sb.toString());
            }
            field.set(SecretKey.class, secretKey);
            log.info("key load complete from  "+ "[" + field.getDeclaringClass().getName() + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    static public void parsePrivate(String key) {
//        RSAPrivateKey k = (SecretKey)
//    }

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
