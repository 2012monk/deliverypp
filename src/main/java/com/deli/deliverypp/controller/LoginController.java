package com.deli.deliverypp.controller;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.auth.provider.AuthProvider;
import com.deli.deliverypp.model.AuthInfo;
import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.service.UserLoginService;
import com.deli.deliverypp.util.ControlUtil;
import com.deli.deliverypp.util.MessageGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;

@WebServlet(name = "LoginController", value = "/login/*")
public class LoginController extends HttpServlet {

    //TODO implements refresh token
    //TODO check the token

    private static final UserLoginService service = new UserLoginService();
    private static final AuthProvider provider = new AuthProvider();
    private final Logger log = LogManager.getLogger(LoginController.class);



    // GET ACCESS TOKEN
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {



        log.info(ControlUtil.getRequestUri(request));
        switch (ControlUtil.getRequestUri(request)) {
            case "google":
                proceedWithGoogle(request, response);
                break;
            case "exchange":
                exchangeToken(request, response);
                break;
            default:
                handleLogin(request, response);
                break;
        }
    }



    // send access token
    // set refresh token
    private void handleLogin (HttpServletRequest request, HttpServletResponse response){
        AuthInfo authInfo = null;
        try {
            authInfo = service.generateAuthInfo(ControlUtil.getJson(request));
            if (authInfo != null){
                String token = service.getRefreshToken(authInfo.getUser());
                setRefreshToken(response, token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ControlUtil.sendResponseData(response, authInfoMsg(authInfo));
    }






    // TODO set exp time with options
    private void setRefreshToken(HttpServletResponse response, String token) {
        Cookie authCookie = new Cookie("SID", token);
        authCookie.setPath("/");
        authCookie.setHttpOnly(true);
        authCookie.setSecure(true);
        authCookie.setMaxAge(60 * 60 * 24 * 7); // 7days
        response.setHeader("Set-Cookie", "SID="+token+";SameSite=None; HttpOnly; Secure; path=/;max-age="+(60 * 60 * 24 * 7));
        log.info("cookie generated");
    }


    // NOTE sign up and login integration
    public void proceedWithGoogle(HttpServletRequest request, HttpServletResponse response)  {

        // NOTE DB query to check if user exist
        ControlUtil.sendResponseData(response, service.googleAuth(ControlUtil.getJson(request)));

    }

    public void exchangeToken (HttpServletRequest request, HttpServletResponse response)  {
//        log.warn("1234");
//        Cookie refreshCookie = null;
//        try {
//            log.warn("exchange");
//        refreshCookie = Arrays
//                    .stream(request.getCookies())
//                    .filter(c -> c.getName().equals("SID"))
//                    .collect(Collectors.toList()).get(0);
//
//        } catch (Exception e) {
//            ControlUtil.sendResponseData(response,
//                MessageGenerator.makeMsg("refresh failed", "refresh_token doesn't exist", "111"));
//            e.printStackTrace();
//        }
        String token = new AuthProvider().parseHeader(request);

        if (token != null) {
//            String token = refreshCookie.getValue();
            DeliUser user = service.parseUserFromToken(token);
            System.out.println(user);
            UserLoginService s = new UserLoginService();
            DeliUser u = service.getUserInfo(user.getUserEmail());
            System.out.println(u);
            AuthInfo info = null;
            try {
                info = service.generateAuthInfo(u);
            } catch (Exception e) {
                ControlUtil.sendResponseData(response,
                        MessageGenerator.makeErrorMsg("refresh failed", "invalid_token"));
                e.printStackTrace();
            }
            log.warn(u);
            ResponseMessage<AuthInfo> msg = authInfoMsg(info);
            msg.setMessage("exchange success");
            ControlUtil.sendResponseData(response, msg);
        }
    }

    private ResponseMessage<AuthInfo> authInfoMsg(AuthInfo authInfo) {
        ResponseMessage<AuthInfo> msg = new ResponseMessage<>();
        if (authInfo != null) {
            msg.setMessage("Login Success");
            msg.setData(authInfo);

        }else {
            msg.setMessage("login failed");
            msg.setData("error");
        }
        log.info(authInfo);
        return msg;

    }



}

