package com.deli.deliverypp.auth.google;

import com.deli.deliverypp.model.GoogleToken;
import com.deli.deliverypp.model.GoogleUser;
import com.deli.deliverypp.util.ControlUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GoogleAuthentication {

    private static String client_id;
    private static String client_secret;
    private static String google_redirect_url;

    private static final Logger log = LogManager.getLogger(LogManager.class);
    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    /**
     *
     * @param googleToken google access information
     * @throws IOException http connection exception
     */
    public static GoogleUser accessGoogleInfo(GoogleToken googleToken) throws IOException, JsonProcessingException {


//        GoogleToken googleToken = null;
//        googleToken = mapper.readValue(json, GoogleToken.class);
        log.info(googleToken);

//        String tokenUri = "https://oauth2.googleapis.com/token";

        GoogleToken token = getGoogleRefreshToken(googleToken);
        return getGoogleProfile(token);
    }


    private static GoogleUser getGoogleProfile (GoogleToken resToken) throws IOException {
//        GoogleToken initToken = mapper.readValue(json, GoogleToken.class);

//        GoogleToken resToken = getGoogleRefreshToken(initToken);

        String url = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + resToken.getAccess_token();
        URL uri = new URL(url);
        log.info(url);

        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setRequestMethod("GET");

        String res = parseInputStream(conn);
        GoogleUser user = mapper.readValue(res, GoogleUser.class);
//        if (resToken.getRefresh_token() != null) {
//            user.setDeliUser(false);
//            user.setRefreshToken(resToken.getRefresh_token());
//        }

        return user;
    }

    private static GoogleToken getGoogleRefreshToken(GoogleToken token) throws IOException {
        String requestUri = "https://oauth2.googleapis.com/token";
        String clientId = "866373809054-qjks61l0i75u3sk5oimmedo24ifjcf8j.apps.googleusercontent.com";
        String secretKey = "hVnQou4vOicfoN9Q6LQIn5d9";
        String redirectUri = "http%3A//localhost:47788/redirect.html";
        Map<String, String> map = new HashMap<String, String>() {{
            put("code", token.getCode());
            put("client_id", clientId);
            put("client_secret", secretKey);
            put("grant_type", "authorization_code");
            put("redirect_uri", redirectUri);
        }};

        String param = map.keySet().stream()
                .map(k -> k + "=" + map.get(k))
                .collect(Collectors.joining("&"));

        log.info(param);
        URL url = new URL(requestUri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);
        try (OutputStream stream = conn.getOutputStream()) {
            try (BufferedWriter wd = new BufferedWriter(new OutputStreamWriter(stream))) {
                wd.write(param);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        int responseCode = conn.getResponseCode();
        log.info(responseCode);

        String res = parseInputStream(conn);

        System.out.println(res);
        return mapper.readValue(res, GoogleToken.class);
    }

    private static String parseInputStream(HttpURLConnection conn) {
        StringBuilder sb = new StringBuilder();
        try(InputStreamReader in = new InputStreamReader(conn.getInputStream())) {
            try(BufferedReader bis = new BufferedReader(in)){
                String line;
                while ((line = bis.readLine()) != null) {
                    sb.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
