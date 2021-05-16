package com.deli.deliverypp.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class PathSerializer extends JsonSerializer<String> {
//    private final String domain = "http://112.169.196.76:47788/";
    private final String domain = "https://deli.alconn.co/";
//    private final String domain = "http://localhost:47788/";
    private final String subPath = "static/image/";

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null || value.equals("")){
            gen.writeObject("");
        }else {
            gen.writeObject(domain+ subPath + value);
        }
    }
}
