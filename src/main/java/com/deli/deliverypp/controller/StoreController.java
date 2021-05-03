package com.deli.deliverypp.controller;

import com.deli.deliverypp.model.Store;
import com.deli.deliverypp.util.ControlUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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

@WebServlet(name = "StoreController", value = "/store/*")
public class StoreController extends HttpServlet {


    private static HashMap<String , Store> list = new HashMap<>();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static List<Store> storeList = new ArrayList<>();
    private static String json;

    public void init(){
        try{
            URL url = StoreController.class.getClassLoader().getResource("/test.json");
            assert url != null;
            File file = new File(url.toURI());
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();

            String current = null;
            while(( current = br.readLine() ) != null) {
                sb.append(current);
            }

            json = new String(sb);
            System.out.println(json);


            storeList = mapper.readValue(file, new TypeReference<List<Store>>() {
            });

            for (Store s: storeList) {
                list.put(s.getStoreId(), s);
            }

            System.out.println(list);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

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
                response.getWriter().write(json);
                break;
            case "info":
                String id = ControlUtil.getRequestUri(request,2);
                Store s = list.get(id);
                if (s == null) {
                    response.getWriter().write("null");
                }else{
                    response.getWriter().write(mapper.writeValueAsString(s));
                }
                break;

        }

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


}
