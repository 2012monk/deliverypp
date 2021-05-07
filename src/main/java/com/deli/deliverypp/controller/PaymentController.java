package com.deli.deliverypp.controller;

import com.deli.deliverypp.filter.CORSFilter;
import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.service.OrderService;
import com.deli.deliverypp.util.ControlUtil;
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
        String tid = request.getParameter("tid");

        ResponseMessage res = service.sendKaKaoDone(token, tid);
//        ControlUtil.sendResponseData(response,, "payment success");

        ControlUtil.sendResponseData(response, res);
        System.out.println(res.getData());
    }


}
