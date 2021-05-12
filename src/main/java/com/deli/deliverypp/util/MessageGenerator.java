package com.deli.deliverypp.util;

import com.deli.deliverypp.model.ResponseMessage;

public class MessageGenerator {

    public void init(){}


    public static  <T> ResponseMessage<T> makeMsg(String msg, T data, String code) {
        return new ResponseMessage<T>(msg, data, code);
    }

    public static  <T> ResponseMessage<T> makeMsg(String msg, T data){
        if (data == null) {
            return makeResultMsg(null);
        }
        return new ResponseMessage<T>(msg, data, null);
    }

    public static  <T> ResponseMessage<T> makeResultMsg(T data) {
        return new ResponseMessage<T>(data == null ? "failed" : "success", data);
    }

    public static <T> ResponseMessage<T> makeErrorMsg(String msg, String code) {
        return new ResponseMessage<>(msg, null, code);
    }
}
