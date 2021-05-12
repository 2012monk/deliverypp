package com.deli.deliverypp.service;

import com.deli.deliverypp.DB.ProductAccess;
import com.deli.deliverypp.DB.StoreAccess;
import com.deli.deliverypp.model.Product;
import com.deli.deliverypp.model.Store;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.deli.deliverypp.util.JSONUtil.getMapper;


public class StoreService {

    private static final Logger log = LogManager.getLogger(StoreService.class);
    private static final ObjectMapper mapper = getMapper();
    private static final StoreAccess storeAccess = new StoreAccess();
    private static final ProductAccess productAccess = new ProductAccess();

    public boolean checkStoreName (String name) {
        return storeAccess.isNameOverlap(name);
    }

    public Store jsonToStoreObject (String json) {
        Store store = null;
        try {
            store = mapper.readValue(json, Store.class);
//            return store;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return store;
    }

    public boolean insertStoreService (String json) {
        Store store = jsonToStoreObject(json);

        if (store == null) return false;
        store.generateStoreId();

        return storeAccess.insertStore(store);
    }

    public boolean insertProductListService (String json) {
        return false;
    }



    public boolean insertProductService (String json) {
        Product product = null;
        log.info(json);
        try {
            product = mapper.readValue(json, Product.class);
            product.generateId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (product == null) return false;

        return productAccess.insertProduct(product);
    }

    public Store getStoreById(String storeId) {
        Store store = storeAccess.getStoreById(storeId);
        store.setProductList(productAccess.getProductListByStoreId(storeId));

        return store;
    }

    public String getStoreList () throws JsonProcessingException {
        List<Store> list = storeAccess.getStoreList();

        for (Store s:list) {

            s.setProductList(productAccess.getProductListByStoreId(s.getStoreId()));
        }

        return mapper.writeValueAsString(list);
    }

    public String getProductListByStoreId (String storeId) {
        try {
            return mapper.writeValueAsString(productAccess.getProductListByStoreId(storeId));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getProductByProductId (String productId) {
        try {
            return mapper.writeValueAsString(productAccess.getProductById(productId));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }


    public boolean deleteStore(String storeId) {
        return storeAccess.deleteStore(storeId);
    }

    public boolean updateStore(String json){
        try {
            return storeAccess.updateStore(mapper.readValue(json, Store.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getStoreByName(String storeName) {
        try {
            return mapper.writeValueAsString(storeAccess.getStoreByName(storeName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public boolean deleteProduct(String productId) {
        return productAccess.deleteProduct(productId);
    }

    public boolean updateProduct (String json) {
        try {
            return productAccess.updateProduct(mapper.readValue(json, Product.class));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
