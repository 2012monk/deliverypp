package com.deli.deliverypp.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpConnectionHandler {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     *
     * @param url
     * @param params
     * @param header
     * @return response
     */
    public static String POSTHttpRequest (String url, Map<String ,String > params, Map<String,String> header) throws IOException {
        URL uri = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setDoOutput(true);
        String parsed = params.entrySet()
                .stream()
                .map(c -> c.getKey()+"="+c.getValue())
                .collect(Collectors.joining("&"));
        if (header != null) {
            header.forEach(conn::setRequestProperty);
        }

        conn.getOutputStream().write(parsed.getBytes());

        BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                        conn.getInputStream()));

        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

    public String POSTHttpRequest (String url, Map<String , String > params) throws IOException {
        return POSTHttpRequest(url, params, null);
    }
}
