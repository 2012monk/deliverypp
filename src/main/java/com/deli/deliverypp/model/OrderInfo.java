package com.deli.deliverypp.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.api.client.util.Value;

import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderInfo {

    private String orderId;
    private String userEmail;
    private String address;
    private String telephone;
    private String orderRequirement;
    private String orderState;

    private String storeId;
    private String storeName;
    private String quantity;
    private String totalPrice;
//    @JsonRawValue
    private List<Product> orderList;
    private String paymentType;
    @JsonIgnoreProperties
    private Payment payment;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void makeNewOrder() {
        this.orderId = UUID.randomUUID().toString();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getOrderRequirement() {
        return orderRequirement;
    }

    public void setOrderRequirement(String orderRequirement) {
        this.orderRequirement = orderRequirement;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }



    public List<Product> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Product> orderList) {
        this.orderList = orderList;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "orderId='" + orderId + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", orderRequirement='" + orderRequirement + '\'' +
                ", orderState='" + orderState + '\'' +
                ", storeId='" + storeId + '\'' +
                ", storeName='" + storeName + '\'' +
                ", quantity='" + quantity + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", orderList=" + orderList +
                ", paymentType='" + paymentType + '\'' +
                ", payment=" + payment +
                '}';
    }
}
