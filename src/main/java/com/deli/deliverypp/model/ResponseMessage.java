package com.deli.deliverypp.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

public class ResponseMessage implements Serializable {

    private String message;
    @JsonRawValue
    private String data;

    public ResponseMessage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setData(Object object) {
        try {
            this.data = new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            this.data = "null";
            e.printStackTrace();
        }
    }
}
