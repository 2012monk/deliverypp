package com.deli.deliverypp.controller;

import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.service.OrderService;
import com.deli.deliverypp.util.ControlUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "OrderController", value = "/order/*")
public class OrderController extends HttpServlet {


    private static final OrderService service = new OrderService();
    private final Logger log = LogManager.getLogger(OrderService.class);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


    }


    // TODO check quantity of each product
    // MAKE order
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        initiateOrder(request, response);
        System.out.println(ControlUtil.getJson(request));
    }

    public void initiateOrder(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        String json = ControlUtil.getJson(request);
        ResponseMessage msg = null;
        try {
            msg = service.startKaKaoPayment(json);
            if (msg.getMessage() != null){
                log.info(msg.getMessage());
                request.getSession().setAttribute("tid", msg.getMessage());
                log.info(msg.getMessage());

                msg.setMessage("payment proceed");
            }
        } catch (IllegalArgumentException | IllegalStateException | JsonProcessingException e) {
            msg = new ResponseMessage();
            msg.setMessage("failed");
            msg.setData(e.getMessage());
        }

        log.info(msg.getData());

        try {
            // FIXED return message 한글 깨짐 해결 필요!
            ControlUtil.sendResponseData(response, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
