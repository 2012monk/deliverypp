package com.deli.deliverypp.controller;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.auth.AuthProvider;
import com.deli.deliverypp.model.AuthInfo;
import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.service.UserLoginService;
import com.deli.deliverypp.util.ControlUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

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

        System.out.println(request.getRequestURL().toString());

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
    private void handleLogin (HttpServletRequest request, HttpServletResponse response) throws IOException {
        AuthInfo authInfo = service.generateAuthInfo(ControlUtil.getJson(request));
        String token = service.getRefreshToken(authInfo.getUser());
        setRefreshToken(response, token);
        ControlUtil.sendResponseData(response, authInfoMsg(authInfo));
    }



    private void checkId (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = ControlUtil.getRequestUri(request, 2);
        ResponseMessage msg = new ResponseMessage();
        msg.setMessage(service.checkUserIdOverlap(id) ? "overlap" : "free");
        msg.setData(id);
        ControlUtil.sendResponseData(response, msg);
    }



    // TODO set exp time with options
    private void setRefreshToken(HttpServletResponse response, String token) {
//        String refreshToken = service
        Cookie authCookie = new Cookie("SID", token);
        authCookie.setPath("/");
        authCookie.setHttpOnly(true);
        authCookie.setMaxAge(60 * 60 * 24 * 7); // 7days
        response.addCookie(authCookie);
    }


    private void invalidateAuth (HttpServletRequest request,HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie c:cookies) {
            if (c.getName().equals("SID")) {
                c.setMaxAge(-1);
                c.setValue(null);
                response.addCookie(c);
            }
        }
    }

    // NOTE sign up and login integration
    public void proceedWithGoogle(HttpServletRequest request, HttpServletResponse response)  {

        // NOTE DB query to check if user exist
        ControlUtil.sendResponseData(response, service.googleAuth(ControlUtil.getJson(request)));

    }

    public void exchangeToken (HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie refreshCookie = Arrays
                .stream(request.getCookies())
                .filter(c -> c.getName().equals("SID"))
                .collect(Collectors.toList()).get(0);


        if (refreshCookie != null && provider.checkRefreshToken(refreshCookie.getValue())) {
            String token = refreshCookie.getValue();
            DeliUser user = service.parseUserFromRefreshToken(token);

            AuthInfo info = service.generateAuthInfo(user);

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

