package com.deli.deliverypp.controller;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.auth.AuthProvider;
import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.service.UserLoginService;
import com.deli.deliverypp.util.ControlUtil;
import com.deli.deliverypp.util.MessageGenerator;
import com.deli.deliverypp.util.annotaions.ProtectedResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.deli.deliverypp.util.JSONUtil.getMapper;
import static com.deli.deliverypp.util.MessageGenerator.makeMsg;

@WebServlet(name = "UserController", value = "/user/*")
public class UserController extends HttpServlet {


    private static final UserLoginService service = new UserLoginService();
    private static final AuthProvider provider = new AuthProvider();
    private static final Logger log = LogManager.getLogger(UserLoginService.class);
    private static final ObjectMapper mapper = getMapper();


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
        String json = ControlUtil.getJson(req);
        DeliUser user = mapper.readValue(json, DeliUser.class);
        if (provider.checkId(user.getUserEmail(), req)){
            ControlUtil.responseMsg(resp, service.updateUser(json));
        }else {
            ControlUtil.sendUnAuthorizeMsg(resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = ControlUtil.getRequestUri(req, 1);
        if (provider.checkId(email, req)) {
            ControlUtil.responseMsg(resp, service.deleteUser(email));
        }else{
            ControlUtil.sendUnAuthorizeMsg(resp);
        }
    }


//    private void handleSignUp (HttpServletRequest request, HttpServletResponse response){
//
//        String json = ControlUtil.getJson(request);
//        log.debug(json);
//        try {
//            ControlUtil.responseMsg(response, service.signUpUser(json));
//        } catch (Exception e) {
//            e.printStackTrace();
//            ControlUtil.responseMsg(response, false);
//        }
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

                if (provider.checkUserStatusValid(request)) {
                    try {
                        DeliUser user = provider.getUserFromHeader(request);
                        log.info(user);
                        ControlUtil.responseMsg(response, service.signUpSeller(mapper.writeValueAsString(user)));

                    } catch (Exception e) {

                    }
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

    private void getUserInfo (HttpServletRequest request, HttpServletResponse response) {
//        String email = ControlUtil.getRequestUri(request);
        DeliUser user = provider.getUserFromHeader(request);
        try {
            String email = user.getUserEmail();
            ResponseMessage<DeliUser> msg = MessageGenerator.makeResultMsg(service.getUserInfo(email));
            ControlUtil.sendResponseData(response, msg);
        } catch (Exception e) {
            ControlUtil.sendUnAuthorizeMsg(response);
            e.printStackTrace();

        }
//        if (provider.checkId(email, request)){
//        }else {
//        }
    }



    private void checkId (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = ControlUtil.getRequestUri(request, 2);
        ResponseMessage<?> msg = new ResponseMessage<>();
        msg.setMessage(service.checkUserIdOverlap(id) ? "overlap" : "free");
        msg.setData(id);
        ControlUtil.sendResponseData(response, msg);
    }

}
