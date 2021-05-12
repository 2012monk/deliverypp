package com.deli.deliverypp.controller;

import com.deli.deliverypp.filter.CORSFilter;
import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.service.OrderService;
import com.deli.deliverypp.util.ControlUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


@WebServlet(name = "PaymentController", value = "/payment/*")
public class PaymentController extends HttpServlet {

    private final Logger log = LogManager.getLogger(PaymentController.class);
    private static final OrderService service = new OrderService();



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (ControlUtil.getRequestUri(request)) {
            case "kakao":
                switch (ControlUtil.getRequestUri(request, 2)){
                    case "success":
                        onKaKaoSuccess(request, response);
                        break;
                }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("post");
        switch (ControlUtil.getRequestUri(request)) {
            case "kakao":
                onKaKaoSuccess(request, response);
                break;
            default:
                break;
        }


    }

    // send success redirect uri
    public void onSuccess(HttpServletRequest request, HttpServletResponse response) {
        String json = ControlUtil.getJson(request);

    }

    public void onKaKaoSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getParameter("pg_token");
        String tid = (String) request.getSession().getAttribute("tid");
        try {
            ResponseMessage res = service.sendKaKaoDone(token, tid);
            log.info(new ObjectMapper().writeValueAsString(res));
            try {
                ControlUtil.sendResponseData(response, res);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info(res.getData());
            System.out.println(res.getData());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("failed");
        }
//        ControlUtil.sendResponseData(response,, "payment success");

    }


}
