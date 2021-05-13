package com.deli.deliverypp.controller;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.model.Store;
import com.deli.deliverypp.service.StoreService;
import com.deli.deliverypp.util.ControlUtil;
import com.deli.deliverypp.util.HttpConnectionHandler;
import com.deli.deliverypp.util.annotaions.ProtectedResource;
import com.deli.deliverypp.util.annotaions.RequiredModel;
import com.deli.deliverypp.util.exp.AuthorityChecker;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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


//        System.out.println(uri);
        switch (uri) {
            case "list":
                sendStoreList(request,response);
                break;
            case "by":
                sendStore(request, response);
                break;
            case "check-name":
                checkStoreName(request, response);
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



    @ProtectedResource(role = DeliUser.UserRole.SELLER, uri = "/stores")
    // CREATE
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String json = ControlUtil.getJson(request);
        ControlUtil.responseMsg(response, service.insertStoreService(json));
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
                ControlUtil.responseMsg(resp, service.updateStore(json));
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
                ControlUtil.responseMsg(resp, service.deleteStore(ControlUtil.getRequestUri(req, 1)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            ControlUtil.sendUnAuthorizeMsg(resp);
        }
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
//        System.out.println(storeId+"from con");
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

    // TODO url encoding 되서 들어옴
    // TODO url decoding 으로 해결했지만  issue 해결방안 필요
    public void checkStoreName (HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        String r = ControlUtil.getRequestUri(request, 2);
        String name = java.net.URLDecoder.decode(r, "UTF-8");

        ControlUtil.sendResponseData(response, name, service.checkStoreName(name) ? "overlap" : "free");
    }




}
