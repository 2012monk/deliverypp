package com.deli.deliverypp.controller;

import com.deli.deliverypp.auth.google.GoogleAuthentication;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;

@WebServlet(name = "LoginController", value = {"/login/*", "/signup"})
public class LoginController extends HttpServlet {

    //TODO implements refresh token
    //TODO check the token



    // GET ACCESS TOKEN
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GoogleAuthentication.googleLogin(request, response);


    }




}

