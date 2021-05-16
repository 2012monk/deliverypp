package com.deli.deliverypp.controller;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.auth.provider.AuthProvider;
import com.deli.deliverypp.model.Store;
import com.deli.deliverypp.service.StoreService;
import com.deli.deliverypp.service.UserLoginService;
import com.deli.deliverypp.util.ControlUtil;
import com.deli.deliverypp.util.MessageGenerator;
import com.deli.deliverypp.util.annotaions.ProtectedResource;
import com.deli.deliverypp.util.annotaions.RequiredModel;
import com.deli.deliverypp.auth.provider.AuthorityChecker;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "StoreController", value = "/stores/*")
public class StoreController extends HttpServlet {


    private static final Logger log = LogManager.getLogger(StoreController.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final StoreService service = new StoreService();
    private AuthProvider provider = new AuthProvider();
    private UserLoginService userLoginService = new UserLoginService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uri = ControlUtil.getRequestUri(request);


//        System.out.println(uri);
        switch (uri) {
            case "list":
                sendStoreList(request,response);
                break;
            case "user":
                sendStoreByUser(request, response);
                break;
            case "name":
                sendStoreByName(request, response);
                break;
            case "check-name":
                checkStoreName(request, response);
                break;
            default:
                sendStoreById(request, response);
                break;

        }

    }

    @ProtectedResource(role = DeliUser.UserRole.SELLER, uri = "/stores")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DeliUser user = provider.getUserFromHeader(request);
        if (user != null){
            log.info(user);
            try {
                if (DeliUser.UserRole.SELLER.isHigher(user.getUserRole())){
                    String json = ControlUtil.getJson(request);
                    ControlUtil.sendResponseData(response, service.insertStoreService(json, user.getUserEmail()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            ControlUtil.sendUnAuthorizeMsg(response);
        }
    }




    // UPDATE Store
    @RequiredModel(target = Store.class)
    @ProtectedResource(uri = "/stores", id = true, method = "put")
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String json = ControlUtil.getJson(req);
        if (AuthorityChecker.checkUserEmailFromJson(req, Store.class, "storeId", json)){
            try{
                ControlUtil.sendResponseData(resp, service.updateStore(json));
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            ControlUtil.sendUnAuthorizeMsg(resp);
        }
    }


    // DELETE Store
    @RequiredModel(target = Store.class)
    @ProtectedResource(uri = "/stores", id = true, method = "delete")
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp){
        String id = ControlUtil.getRequestUri(req);
        if (AuthorityChecker.checkUserEmail(req, Store.class, "storeId", id)){
            try {
                ControlUtil.sendResponseData(resp, service.deleteStore(ControlUtil.getRequestUri(req)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            ControlUtil.sendUnAuthorizeMsg(resp);
        }
    }



    public void sendStoreList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ControlUtil.sendResponseData(response, service.getStoreList());
    }

    public void sendStoreById (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String storeId = ControlUtil.getRequestUri(request);
        ControlUtil.sendResponseData(response, service.getStoreById(storeId));
    }



    public void sendStoreByName (HttpServletRequest request, HttpServletResponse response)  {
        String storeName = ControlUtil.getRequestUri(request, 2);
        ControlUtil.sendResponseData(response, service.getStoreByName(storeName));
    }



    public void sendStoreByUser(HttpServletRequest request, HttpServletResponse response)  {
        String userEmail = provider.getUserEmailFromHeader(request);
        if (userEmail == null) {
            ControlUtil.sendResponseData(response, MessageGenerator.makeErrorMsg("failed", "인증 정보가 없습니다"));
        }
        else {
            ControlUtil.sendResponseData(response, service.getStoreListByUser(userEmail));
        }


    }

    // TODO url encoding 되서 들어옴
    // TODO url decoding 으로 해결했지만  issue 해결방안 필요
    public void checkStoreName (HttpServletRequest request, HttpServletResponse response)  {
        try {
            request.setCharacterEncoding("utf-8");
            String r = ControlUtil.getRequestUri(request, 2);
            String name = java.net.URLDecoder.decode(r, "UTF-8");
            ControlUtil.sendResponseData(response, name, service.checkStoreName(name) ? "overlap" : "free");
        } catch (Exception e) {
            e.printStackTrace();
            ControlUtil.sendResponseData(response, MessageGenerator.makeErrorMsg("failed", "encoding_error"));
        }

    }




}
