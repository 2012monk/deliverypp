package com.deli.deliverypp.service;

import com.deli.deliverypp.DB.ProductAccess;
import com.deli.deliverypp.DB.StoreAccess;
import com.deli.deliverypp.model.Product;
import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.model.Store;
import com.deli.deliverypp.util.MessageGenerator;
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


    public ResponseMessage<List<Store>> getStoreListByUser (String userEmail) {
        return MessageGenerator.makeMsg("success", storeAccess.getStoreListByUser(userEmail));
    }


    public ResponseMessage<Store> insertStoreService (String json, String userEmail) {
        Store store = jsonToStoreObject(json);

        if (store == null) {
            return MessageGenerator.makeErrorMsg("failed", "데이터가 올바르지 않습니다");
        }
        store.generateStoreId();
        store.setUserEmail(userEmail);
        Store res = storeAccess.insertStore(store);
        if (res == null) {
            return MessageGenerator.makeErrorMsg("failed", "db_error");
        }

        return MessageGenerator.makeMsg("create_success", res);
    }

    public ResponseMessage<List<Product>> insertProductListService (String json) {
        return MessageGenerator.makeErrorMsg("failed", "완성이 안되었습니다");
    }



    public ResponseMessage<Product> insertProductService (String json) {
        Product product = null;
        log.info(json);
        try {
            product = mapper.readValue(json, Product.class);
            product.generateId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (product == null) {
            return MessageGenerator.makeErrorMsg("failed", "데이터가 올바르지 않습니다");
        }
        Product res = productAccess.insertProduct(product);

        return MessageGenerator.makeResultMsg(res);
    }


    public ResponseMessage<Store> getStoreById(String storeId) {
        Store store = storeAccess.getStoreById(storeId);
        store.setProductList(productAccess.getProductListByStoreId(storeId));

        return MessageGenerator.makeResultMsg(store);
    }

    public ResponseMessage<List<Store>> getStoreList () {
        List<Store> list = storeAccess.getStoreList();

        for (Store s:list) {
            s.setProductList(productAccess.getProductListByStoreId(s.getStoreId()));
        }

        return MessageGenerator.makeResultMsg(list);
    }


    public ResponseMessage<List<Product>> getProductListByStoreId (String storeId) {
        List<Product> list = productAccess.getProductListByStoreId(storeId);
        if (list == null) {
            return MessageGenerator.makeErrorMsg("failed", "존재하지 않는 아이디");
        }

        return MessageGenerator.makeMsg("success", list);
    }


    public ResponseMessage<Product> getProductByProductId (String productId) {
        Product product = productAccess.getProductById(productId);

        if (product == null) {
            return MessageGenerator.makeErrorMsg("failed", "id error");
        }

        return MessageGenerator.makeMsg("success", product);
    }


//    public ResponseMessage<Product> getProductById (String productId) {
//        Product
//        try {
//            return productAccess.getProductById(productId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    public ResponseMessage<String> deleteStore(String storeId) {
        if (storeAccess.deleteStore(storeId)) {
            return MessageGenerator.makeMsg("success", storeId, "delete_success");
        }
        return MessageGenerator.makeErrorMsg("failed", "stored_id_error");
    }



    public ResponseMessage<Store> updateStore(String json){
        Store store;
        try {
            store = mapper.readValue(json, Store.class);
            store = storeAccess.updateStore(store);
        } catch (Exception e) {
            e.printStackTrace();
            return MessageGenerator.makeErrorMsg("failed", "올바르지 않은 형식");
        }

        return MessageGenerator.makeMsg("success", store, "update_success");
    }

    public ResponseMessage<Store> getStoreByName(String storeName) {
        Store store = storeAccess.getStoreByName(storeName);
        if (store == null) {
            return MessageGenerator.makeErrorMsg("failed", "invalid_id");
        }
        return MessageGenerator.makeMsg("success", store, "store_update_success");
    }

    public ResponseMessage<String> deleteProduct(String productId) {
        if (productAccess.deleteProduct(productId)) {
            return MessageGenerator.makeMsg("success", productId, "delete_success");
        }
        return MessageGenerator.makeMsg("failed", productId, "invalid_product_id");
    }


    public ResponseMessage<Product> updateProduct (String json) {
        try {
            Product product = mapper.readValue(json, Product.class);
            if (product != null) {
                return MessageGenerator.makeMsg("success", productAccess.updateProduct(product), "update_success");
            }
            return MessageGenerator.makeErrorMsg("failed", "invalid_product_id");
        } catch (Exception e) {
            return MessageGenerator.makeErrorMsg("failed", "invalid_data_format");
        }

    }

}
