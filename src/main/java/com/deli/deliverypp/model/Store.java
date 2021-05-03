package com.deli.deliverypp.model;

import java.io.Serializable;

public class Store implements Serializable {

    private String storeName;
    private String storeDesc;
    private String storeId;

    public Store() {
    }

    public Store(String storeName, String storeDesc, String storeId) {
        this.storeName = storeName;
        this.storeDesc = storeDesc;
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreDesc() {
        return storeDesc;
    }

    public void setStoreDesc(String storeDesc) {
        this.storeDesc = storeDesc;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
