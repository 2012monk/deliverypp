package com.deli.deliverypp.DB;

import com.deli.deliverypp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAccess {


    public List<Product> getProductListByStoreId (String storeId) {
        List<Product> list =  new ArrayList<>();

        return list;
    }

    public Product getProductById (String productId) {


        return null;
    }

    public boolean insertProduct(Product product) {
        return false;
    }

    public boolean updateProduct(Product product) {
        return false;
    }


    public boolean deleteProduct(String productId) {
        return false;
    }

}
