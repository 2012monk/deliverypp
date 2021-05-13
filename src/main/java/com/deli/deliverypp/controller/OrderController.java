package com.deli.deliverypp.controller;

import com.deli.deliverypp.DB.OrderAccess;
import com.deli.deliverypp.model.OrderInfo;
import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.service.OrderService;
import com.deli.deliverypp.util.ControlUtil;
import com.deli.deliverypp.util.MessageGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderController", value = "/order/*")
public class OrderController extends HttpServlet {


    private static final OrderService service = new OrderService();
    private final Logger log = LogManager.getLogger(OrderService.class);
    private final OrderAccess access = new OrderAccess();


    // order/:order-id
    // order/store/:store-id
    // order/user/:user-id
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (ControlUtil.getRequestUri(request)) {
            case "store":
                String storeId = ControlUtil.getRequestUri(request, 2);
                ResponseMessage<List<OrderInfo>> msg = MessageGenerator.makeResultMsg(access.getOrderListByStore(storeId));
                ControlUtil.sendResponseData(response, msg);
                break;
            case "user":
                String userEmail = ControlUtil.getRequestUri(request, 2);
                ControlUtil.sendResponseData(response, MessageGenerator.makeResultMsg(access.getOrderListByUser(userEmail)));
                break;
            case "tid":
                ControlUtil.sendResponseData(response,
                        MessageGenerator.makeResultMsg(
                                access.getOrderInfoByTid(ControlUtil.getRequestUri(request, 2))));
                break;
            default:
                ControlUtil.sendResponseData(response, MessageGenerator.makeResultMsg(access.getOrderInfoByOrderId(ControlUtil.getRequestUri(request))));
                break;
        }
    }


    // TODO check quantity of each product
    // MAKE order
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        initiateOrder(request, response);
    }

    public void initiateOrder(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        String json = ControlUtil.getJson(request);
        ResponseMessage<?> msg = null;
        try {
            msg = service.startKaKaoPayment(json);
            if (msg.getMessage() != null){
                log.info(msg.getMessage());
                request.getSession().setAttribute("tid", msg.getMessage());
                log.info(msg.getMessage());

                msg.setMessage("payment proceed");
            }
        } catch (IllegalArgumentException | IllegalStateException | JsonProcessingException e) {
            msg = new ResponseMessage<>();
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
