package com.deli.deliverypp.controller;

import com.deli.deliverypp.model.Store;
import com.deli.deliverypp.service.StoreService;
import com.deli.deliverypp.util.ControlUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "StoreController", value = "/stores/*")
public class StoreController extends HttpServlet {


    private static final Logger log = LogManager.getLogger(StoreController.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final StoreService service = new StoreService();


//    private static HashMap<String , Store> list = new HashMap<>();
//    private static List<Store> storeList = new ArrayList<>();
//    private static String json;

//    public void init(){
//        try{
//            URL url = StoreController.class.getClassLoader().getResource("/test.json");
//            assert url != null;
//            File file = new File(url.toURI());
//            FileReader fr = new FileReader(file);
//            BufferedReader br = new BufferedReader(fr);
//            StringBuffer sb = new StringBuffer();
//
//            String current = null;
//            while(( current = br.readLine() ) != null) {
//                sb.append(current);
//            }
//
//            json = new String(sb);
//            storeList = mapper.readValue(file, new TypeReference<List<Store>>() {
//            });
//
//
//            for (Store s: storeList) {
//                list.put(s.getStoreId(), s);
//            }
//
//
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * @UriSchema
     * /store/info/id
     * /store/list
     */
    // for test
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uri = ControlUtil.getRequestUri(request);


        System.out.println(uri);
        switch (uri) {
            case "list":
                sendStoreList(request,response);
                break;
            case "by":
                sendStore(request, response);
                break;
            default:
                sendStoreById(request, response);
                break;

        }

    }


    // URI ROUTING

    // Create store

    /**
     *
     *  RECEIVE JSON Format
     *  {
     *      storeName : ,
     *      storeId : ,
     *      storeDesc : ,
     *      storeImage : ,
     *      productList : [{},{}]
     *  }
     */



    // CREATE
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String json = ControlUtil.getJson(request);
        ControlUtil.responseMsg(response, service.insertStoreService(json));
    }




    // UPDATE Store
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    }


    // DELETE Store
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    }



    public void sendStoreList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = "";
        try {
            json = service.getStoreList();
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//        response.getWriter().write(json);
        ControlUtil.sendResponseData(response, json);

    }

    public void sendStoreById (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String storeId = ControlUtil.getRequestUri(request);
        System.out.println(storeId+"from con");
        String json = "null";
        if (storeId != null) {
            try {
                json = mapper.writeValueAsString(service.getStoreById(storeId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ControlUtil.sendResponseData(response, json);
    }



    public void sendStoreByName (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String storeName = ControlUtil.getRequestUri(request, 2);
        String json = "null";
    }



    public void sendStore (HttpServletRequest request, HttpServletResponse response) throws IOException {

    }


}
