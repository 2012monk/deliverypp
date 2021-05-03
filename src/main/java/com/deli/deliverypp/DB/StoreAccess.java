package com.deli.deliverypp.DB;

import com.deli.deliverypp.model.Store;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class StoreAccess {

    private static  Connection conn;

    public List<Store> getStoreList () {
        List<Store> list = new ArrayList<>();
        return list;
    }

    public Store getStoreById(String storeId) {

        return null;
    }


    public boolean insertStore (Store store) {
        return false;
    }

    public boolean updateStore (Store store) {
        return false;
    }

    public boolean deleteStore (String storeId) {
        return false;
    }



}
