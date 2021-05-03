package com.deli.deliverypp.util;

import org.apache.logging.log4j.core.util.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControlUtil {

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
}
