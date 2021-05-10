package com.deli.deliverypp.controller;

import com.deli.deliverypp.service.UserLoginService;
import com.deli.deliverypp.util.ControlUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SignUpController", value = "/signup/*")
public class SignUpController extends HttpServlet {


    private static final UserLoginService service = new UserLoginService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    // for signup
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        switch (ControlUtil.getRequestUri(request)) {
            case "google":
                break;
            default:
                handleSignUp(request, response);
                break;
        }
    }


    private void handleSignUp (HttpServletRequest request, HttpServletResponse response)
            throws IOException{

        String json = ControlUtil.getJson(request);
        System.out.println(json);
        ControlUtil.responseMsg(response, service.signUpUser(json));
    }




}
