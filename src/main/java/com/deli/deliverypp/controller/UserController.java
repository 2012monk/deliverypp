package com.deli.deliverypp.controller;

import com.deli.deliverypp.auth.AuthProvider;
import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.service.UserLoginService;
import com.deli.deliverypp.util.ControlUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.ResponseCache;

@WebServlet(name = "UserController", value = "/user/*")
public class UserController extends HttpServlet {


    private static final UserLoginService service = new UserLoginService();
    private static final AuthProvider provider = new AuthProvider();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getUserInfo(request,response);
    }


    // for signup
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        switch (ControlUtil.getRequestUri(request)) {
            case "signup":
                register(request, response);
                break;
            default:
//                handleSignUp(request, response);
                break;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        updateUserInfo(req,resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        unregisterUser(req,resp);
    }


    private void handleSignUp (HttpServletRequest request, HttpServletResponse response){

        String json = ControlUtil.getJson(request);
        System.out.println(json);
        try {
            ControlUtil.responseMsg(response, service.signUpUser(json));
        } catch (Exception e) {
            e.printStackTrace();
            ControlUtil.responseMsg(response, false);
        }
    }

    // TODO implement me register seller
    private void registerAsSeller (HttpServletRequest request, HttpServletResponse response) {
        ControlUtil.responseMsg(response, service.signUpSeller(ControlUtil.getJson(request)));
    }

    private void registerAsUser (HttpServletRequest request, HttpServletResponse response){
        try {
            ControlUtil.responseMsg(
                    response,
                    service.signUpUser(
                            ControlUtil.getJson(request)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void register(HttpServletRequest request, HttpServletResponse response) {
        if (ControlUtil.getRequestUri(request,2).equals("seller")) {

            if (provider.checkUserStatus(request)) {
                registerAsSeller(request, response);
            }

            else {
                ResponseMessage msg = new ResponseMessage();
                msg.setMessage("need login!");
                msg.setCode("register");
                try {
                    ControlUtil.sendResponseData(response, msg);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        else {
            registerAsUser(request, response);
        }
    }

    private void updateUserInfo (HttpServletRequest request, HttpServletResponse response) {
        ControlUtil.responseMsg(response, service.updateUser(ControlUtil.getJson(request)));
    }

    private void unregisterUser (HttpServletRequest request, HttpServletResponse response) {
        ControlUtil.responseMsg(response, service.deleteUser(ControlUtil.getJson(request)));
    }

    private void getUserInfo (HttpServletRequest request, HttpServletResponse response) {
        service.getUserInfo(ControlUtil.getRequestUri(request, 2));
    }


}
