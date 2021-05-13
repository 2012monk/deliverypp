package com.deli.deliverypp.controller;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.DB.FCMTokenHandler;
import com.deli.deliverypp.util.ControlUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.deli.deliverypp.util.JSONUtil.getMapper;

@WebServlet(name = "PushController", value = "/push/*")
public class PushController extends HttpServlet {


    private static final Logger log = LogManager.getLogger(PushController.class);
    private static final FCMTokenHandler handler = new FCMTokenHandler();
    private static final ObjectMapper mapper = getMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String json = ControlUtil.getJson(request);
        log.info(json);
        try {
            DeliUser user = mapper.readValue(json, DeliUser.class);
            handler.changeToken(user.getUserEmail(), user.getFcmToken());

        } catch (Exception e) {

        }
        ControlUtil.sendResponseData(response, "success");


    }
}
