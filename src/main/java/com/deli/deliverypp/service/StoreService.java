package com.deli.deliverypp.service;

import com.deli.deliverypp.DB.ProductAccess;
import com.deli.deliverypp.DB.StoreAccess;
import com.deli.deliverypp.model.Product;
import com.deli.deliverypp.model.Store;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class StoreService {

    private static final Logger log = LogManager.getLogger(StoreService.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final StoreAccess storeAccess = new StoreAccess();
    private static final ProductAccess productAccess = new ProductAccess();


    public Store jsonToStoreObject (String json) {
        Store store = null;
        try {
            store = mapper.readValue(json, Store.class);
            return store;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return store;
    }

    public boolean insertStoreService (String json) {
        log.info(json, "hi");
        Store store = jsonToStoreObject(json);

        if (store == null) return false;
        return storeAccess.insertStore(store);
    }


    public boolean insertProductService (String json) {
        Product product = null;
        try {
            product = mapper.readValue(json, Product.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (product == null) return false;
        return productAccess.insertProduct(product);
    }

    public Store getStoreById(String storeId) {
        return null;
    }


}
