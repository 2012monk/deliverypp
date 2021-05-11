package com.deli.deliverypp.controller;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.auth.AuthProvider;
import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.service.UserLoginService;
import com.deli.deliverypp.util.ControlUtil;
import com.deli.deliverypp.util.MessageGenerator;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.deli.deliverypp.util.MessageGenerator.makeMsg;

@WebServlet(name = "UserController", value = "/user/*")
public class UserController extends HttpServlet {


    private static final UserLoginService service = new UserLoginService();
    private static final AuthProvider provider = new AuthProvider();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (ControlUtil.getRequestUri(request)){
            case "check-id":
                checkId(request, response);
                break;
            default:
            getUserInfo(request,response);
            break;

        }
    }


    // for signup
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        register(request, response);
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
//    private void registerAsSeller (HttpServletRequest request, HttpServletResponse response) {
//        ControlUtil.responseMsg(response, service.signUpSeller(ControlUtil.getJson(request)));
//    }

    // 유저 등록
    private void registerAsUser (HttpServletRequest request, HttpServletResponse response){
        try {
            String json = ControlUtil.getJson(request);
            ControlUtil.responseMsg(
                    response,service.signUpUser(json));
        } catch (Exception e) {
            ControlUtil.sendResponseData(response, "123","failed" );
            e.printStackTrace();
        }
    }


    // 분기점
    private void register(HttpServletRequest request, HttpServletResponse response) {
        if (ControlUtil.getRequestUri(request).equals("signup")){
            if (ControlUtil.getRequestUri(request,2).equals("seller")) {
                System.out.println(provider.checkUserStatusValid(request));
                System.out.println("seller");

                if (provider.checkUserStatusValid(request)) {
                    ControlUtil.responseMsg(response, service.signUpSeller(ControlUtil.getJson(request)));
                }
                else {
                    ResponseMessage<String> msg = new ResponseMessage<>();
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
            else if(ControlUtil.getRequestUri(request, 2).equals("")) {
                registerAsUser(request, response);
            }else{
                try {
                    response.sendError(400);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateUserInfo (HttpServletRequest request, HttpServletResponse response) {
        ControlUtil.responseMsg(response, service.updateUser(ControlUtil.getJson(request)));
    }

    private void unregisterUser (HttpServletRequest request, HttpServletResponse response) {
        ControlUtil.responseMsg(response, service.deleteUser(ControlUtil.getRequestUri(request)));
    }

    private void getUserInfo (HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<DeliUser> msg = MessageGenerator.makeResultMsg(service.getUserInfo(ControlUtil.getRequestUri(request)));
        ControlUtil.sendResponseData(response, msg);
    }



    private void checkId (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = ControlUtil.getRequestUri(request, 2);
        ResponseMessage<?> msg = new ResponseMessage<>();
        msg.setMessage(service.checkUserIdOverlap(id) ? "overlap" : "free");
        msg.setData(id);
        ControlUtil.sendResponseData(response, msg);
    }

}
