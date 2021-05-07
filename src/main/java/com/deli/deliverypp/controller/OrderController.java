package com.deli.deliverypp.controller;

import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.service.OrderService;
import com.deli.deliverypp.util.ControlUtil;
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

    public void initiateOrder(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String json = ControlUtil.getJson(request);
        ResponseMessage msg = service.startKaKaoPayment(json);

        if (msg != null) {
            if (msg.getMessage() != null){
                request.setAttribute("tid", msg.getMessage());
//                System.out.println(msg.getMessage());
                log.info(msg.getMessage());

                msg.setMessage("payment proceed");
            }
        }
        ControlUtil.sendResponseData(response, msg);
    }





}
