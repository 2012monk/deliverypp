package com.deli.deliverypp.controller;

import com.deli.deliverypp.service.OrderService;
import com.deli.deliverypp.util.ControlUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "OrderController", value = "/order/*")
public class OrderController extends HttpServlet {


    private static final OrderService service = new OrderService();


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
        service.startKaKaoPayment(ControlUtil.getJson(request));
    }





}
