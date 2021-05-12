package com.deli.deliverypp.model;

import com.deli.deliverypp.util.ParseUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties
public class Store implements Serializable {

    private String storeId;
    private String storeName;
    private String storeDesc;
    // img 경로 수정 Method
    private String storeImage;
    private List<Product> productList;
    private String storeAddr;
    private String storeTelephone;

    public Store() {
    }

    public String getStoreTelephone() {
        return storeTelephone;
    }

    public void setStoreTelephone(String storeTelephone) {
        this.storeTelephone = storeTelephone;
    }

    public void generateStoreId() {
        this.storeId = UUID.randomUUID().toString();
    }

    public String getStoreAddr() {
        return storeAddr;
    }

    public void setStoreAddr(String storeAddr) {
        this.storeAddr = storeAddr;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
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

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = ParseUtil.parseImgPath(storeImage);
    }


    @Override
    public String toString() {
        return "Store{" +
                "storeId='" + storeId + '\'' +
                ", storeName='" + storeName + '\'' +
                ", storeDesc='" + storeDesc + '\'' +
                ", storeImage='" + storeImage + '\'' +
                ", productList=" + productList +
                ", storeAddr='" + storeAddr + '\'' +
                '}';
    }
}
