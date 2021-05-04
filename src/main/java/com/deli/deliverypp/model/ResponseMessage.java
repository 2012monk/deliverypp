package com.deli.deliverypp.model;

import com.fasterxml.jackson.annotation.JsonRawValue;

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
}
