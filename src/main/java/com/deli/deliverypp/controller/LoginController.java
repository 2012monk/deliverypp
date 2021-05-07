package com.deli.deliverypp.controller;

import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.service.AuthService;
import com.deli.deliverypp.util.ControlUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;

@WebServlet(name = "LoginController", value = "/login/*")
public class LoginController extends HttpServlet {

    //TODO implements refresh token
    //TODO check the token

    private static final AuthService service = new AuthService();



    // GET ACCESS TOKEN
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        invalidateAuth(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println(request.getRequestURL().toString());

        switch (ControlUtil.getRequestUri(request)) {
            case "id-check":
                checkId(request, response);
                break;
            case "google":
                googleLogin(request, response);
                break;
            default:
                handleLogin(request, response);
                break;
        }
    }



    private void handleLogin (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authInfo = service.loginUser(ControlUtil.getJson(request));
        ResponseMessage msg = new ResponseMessage();
        if (authInfo != null) {
            setAuthCookie(response, authInfo);

            msg.setMessage("Login Success");
            msg.setData(authInfo);

        }else {
            msg.setMessage("login failed");
            msg.setData("error");
        }
        ControlUtil.sendResponseData(response, msg);
    }


    private void checkId (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = ControlUtil.getRequestUri(request, 2);
        ResponseMessage msg = new ResponseMessage();
        msg.setMessage(service.checkUserIdOverlap(id) ? "overlap" : "free");
        msg.setData(id);
        ControlUtil.sendResponseData(response, msg);
    }


    private void setAuthCookie(HttpServletResponse response, String email) {
        Cookie authCookie = new Cookie("SID", "refreshtoken!");
        authCookie.setPath("/");
        authCookie.setHttpOnly(true);
        authCookie.setMaxAge(60 * 60 * 24);

        response.addCookie(authCookie);
    }

    private void invalidateAuth (HttpServletRequest request,HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie c:cookies) {
            if (c.getName().equals("SID")) {
                c.setMaxAge(0);
                c.setValue(null);
                response.addCookie(c);
            }
        }
    }

    public void googleLogin (HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(ControlUtil.getJson(request));
        service.googleAuth(ControlUtil.getJson(request));
    }



}

