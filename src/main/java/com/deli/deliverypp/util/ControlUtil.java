package com.deli.deliverypp.util;

import com.deli.deliverypp.model.ResponseMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.core.util.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControlUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void responseMsg(HttpServletResponse response, boolean success) throws IOException {
        response.getWriter().write(success ? "SUCCESS" : "FAILED");
    }

    public static String getRequestUri(HttpServletRequest rq, int num) {
        if (rq.getRequestURI() == null || rq.getRequestURI().equals("/")){
            return "";
        }else{

            try{
                return rq.getRequestURI().substring(1).split("/")[num];
            }catch (Exception e){
//                e.printStackTrace();
                return "";
            }
        }
    }

    public static String getRequestUri(HttpServletRequest rq) {
        return getRequestUri(rq, 1);
    }

    public static String getJson(HttpServletRequest rq) {
        try {
            return IOUtils.toString(rq.getReader());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void sendResponseData(HttpServletResponse response, String data) throws IOException {
        sendResponseData(response, data, "null");
    }

    public static void sendResponseData(HttpServletResponse response, String data, String message)
            throws IOException {
        response.getWriter().write(formatJson(message, data));
    }

    private static String formatJson (String message, String data) {
        ResponseMessage msg = new ResponseMessage();

        msg.setMessage(message);
        msg.setData(data);
        try {
            return mapper.writeValueAsString(msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "null";
    }


    public static void sendResponseData(HttpServletResponse response, ResponseMessage msg) throws IOException {
        response.getWriter().write(mapper.writeValueAsString(msg));
    }
}
