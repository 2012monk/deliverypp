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

    /**
     *
     * @param response send obj
     * @param success if true send success else failed
     */
    public static void responseMsg(HttpServletResponse response, boolean success) {
        response.setContentType("text/html");
        try {
            response.getWriter().write(success ? "SUCCESS" : "FAILED");
        } catch (Exception e) {
            e.printStackTrace();
            try{
                response.getWriter().write("failed server error");

            }catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static String getRequestUri(HttpServletRequest rq, int num) {
        if (rq.getRequestURI() == null || rq.getRequestURI().equals("/")){
            return "";
        }else{

            try{
//                return java.net.URLDecoder.decode(rq.getRequestURI().substring(1).split("/")[num], "UTF-8");
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
        sendResponseData(response, data, "success");
    }

    public static void sendResponseData(HttpServletResponse response, String data, String message){
        response.setContentType("application/json");
        try {
            response.getWriter().write(formatJson(message, data));
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().write("failed server error");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }



    // NOTE application/json 으로 설정안하면 한글깨짐
    public static void sendResponseData(HttpServletResponse response, ResponseMessage<?> msg) {
        response.setContentType("application/json");
        try {
            response.getWriter().write(mapper.writeValueAsString(msg));
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().write("failed server error");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


    // TODO browser 요청시 stack over flow 발생
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
}
