package com.deli.deliverypp.controller.login;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LogOutController", value = "/logout")
public class LogOutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        for (Cookie c:cookies) {
            if (c.getName().equals("SID")) {
                c.setMaxAge(-1);
                c.setValue(null);
                response.addCookie(c);
            }
        }

    }
}
