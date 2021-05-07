package com.deli.deliverypp.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil {

    public static ObjectMapper getMapperWithFeature(DeserializationFeature feature, boolean t) {
        return new ObjectMapper().configure(feature, t);
    }

    public static ObjectMapper getMapper() {
        return getMapperWithFeature(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}

