package com.deli.deliverypp.push;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class FCMInitializer {

    private static final String keyPath ="test-c5caf-firebase-adminsdk-wi8s2-87418faf77.json";
    private static final Logger log = LogManager.getLogger(FCMInitializer.class);

    static {
        URL url = FCMInitializer.class.getClassLoader().getResource(keyPath);
        if (url != null) {
            try{
                File file = new File(url.toURI());
                FileInputStream fis = new FileInputStream(file);

                FirebaseOptions options = FirebaseOptions.builder().
                        setCredentials(GoogleCredentials.fromStream(fis)).build();

                if (FirebaseApp.getApps().isEmpty()){
                    FirebaseApp.initializeApp(options);
                    log.info("FCM APP load complete");
                }else{
                    log.info("calling after init");
                }

            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
