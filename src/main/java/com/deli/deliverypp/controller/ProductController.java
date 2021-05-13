package com.deli.deliverypp.controller;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.model.Product;
import com.deli.deliverypp.service.StoreService;
import com.deli.deliverypp.util.ControlUtil;
import com.deli.deliverypp.util.annotaions.ProtectedResource;
import com.deli.deliverypp.util.annotaions.RequiredModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ProductController", value = "/products/*")
public class ProductController extends HttpServlet {



    private static final StoreService service = new StoreService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        switch (ControlUtil.getRequestUri(request)) {
            case "list":
                sendProductListByStoreId(request,response);
                break;
            default:
                sendByProductId(request,response);
                break;

        }
    }



    @ProtectedResource(uri = "/products", role = DeliUser.UserRole.SELLER)
    // CREATE product
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        switch (ControlUtil.getRequestUri(request)) {
            case "list":
                insertProductList(request,response);
                break;
            default:
                insertProduct(request,response);
                break;

        }


    }


    @RequiredModel(target = Product.class)
    @ProtectedResource(uri = "/products", id = true)
    // UPDATE product
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp){
        ControlUtil.responseMsg(resp, service.updateProduct(ControlUtil.getJson(req)));
    }

    @RequiredModel(target = Product.class)
    @ProtectedResource(uri = "/products", id = true)
    // DELETE Product
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp){
        System.out.println(ControlUtil.getRequestUri(req, 1));
        ControlUtil.responseMsg(resp, service.deleteProduct(ControlUtil.getRequestUri(req, 1)));
    }




    public void insertProduct (HttpServletRequest request, HttpServletResponse response) throws IOException {
        ControlUtil.responseMsg(response,
                service.insertProductService(
                        ControlUtil.getJson(request)));
    }

    public void insertProductList (HttpServletRequest request, HttpServletResponse response) throws IOException {
        ControlUtil.responseMsg(response,
                service.insertProductListService(
                        ControlUtil.getJson(request)
                ));
    }


    public void sendByProductId (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productId = ControlUtil.getRequestUri(request, 1);
        String json = "null";
        if (productId != null) {
            json = service.getProductByProductId(productId);
        }

//        response.getWriter().write(json);
        ControlUtil.sendResponseData(response, json);
    }

    public void sendProductListByStoreId (HttpServletRequest request, HttpServletResponse response) throws IOException{
        String storeId = ControlUtil.getRequestUri(request, 2);
        String json = "null";

        if (storeId != null) {
            json = service.getProductListByStoreId(storeId);
        }


        ControlUtil.sendResponseData(response, json, json.equals("") ? "FAILED" : "SUCCESS");
    }
}
