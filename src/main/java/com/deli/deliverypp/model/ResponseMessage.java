package com.deli.deliverypp.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

public class ResponseMessage<T> implements Serializable {

    private String message;
    @JsonRawValue
    private String data;
    private String code;

    public ResponseMessage() {
    }

    public ResponseMessage(String message, T data, String code) {
        this.message = message;
        setData(data);
        this.code = code;
    }

    public ResponseMessage(String message, T data) {
        this.message = message;
        setData(data);
    }

    public ResponseMessage(String message, String data, String code) {
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public ResponseMessage(String  msg) {
        this.message = msg;
    }

    public ResponseMessage(String message, String data) {
        this.message = message;
        this.data = data;
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

    public void setData(T object) {
        try {
            this.data = new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            this.data = "null";
            e.printStackTrace();
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
