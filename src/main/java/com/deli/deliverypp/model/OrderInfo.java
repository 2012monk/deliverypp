
package com.deli.deliverypp.model;

import com.fasterxml.jackson.annotation.*;

import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderInfo {

    public enum OrderState{
        BEFORE_PAYMENT,
        PAYMENT_SUCCESS,
        PAYMENT_FAILED,
        DONE
    }

    private String orderId;
    private String tid;
    private String userEmail;
    private String userAddr;
    private String userTelephone;
    private String orderRequirement;
//    private String orderState;

    private String storeId;
    private String storeName;
    private int totalAmount;
    private int totalPrice;
//    @JsonRawValue
    private List<Product> orderList;
    private String paymentType;
    @JsonIgnoreProperties
    private Payment payment;
    private OrderState orderState;
    private String orderInitDate;


    public String getOrderInitDate() {
        return orderInitDate;
    }

    public void setOrderInitDate(String orderInitDate) {
        this.orderInitDate = orderInitDate;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void generateOrderId() {
        this.orderId = UUID.randomUUID().toString();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

//    public String getOrderState() {
//        return orderState;
//    }
//
//    public void setOrderState(String orderState) {
//        this.orderState = orderState;
//    }


    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public String getUserTelephone() {
        return userTelephone;
    }

    public void setUserTelephone(String userTelephone) {
        this.userTelephone = userTelephone;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
    // parse string

    public String getQuantityString() {
        return String.valueOf(totalAmount);
    }

    public void setQuantity(String quantity) {
        this.totalAmount = Integer.parseInt(quantity);
    }

    public String  getTotalPriceString() {
        return String.valueOf(totalPrice);
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = Integer.parseInt(totalPrice);
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
                ", tid='" + tid + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userAddr='" + userAddr + '\'' +
                ", userTelephone='" + userTelephone + '\'' +
                ", orderRequirement='" + orderRequirement + '\'' +
                ", storeId='" + storeId + '\'' +
                ", storeName='" + storeName + '\'' +
                ", totalAmount=" + totalAmount +
                ", totalPrice=" + totalPrice +
                ", orderList=" + orderList +
                ", paymentType='" + paymentType + '\'' +
                ", payment=" + payment +
                ", orderState=" + orderState +
                ", orderInitDate='" + orderInitDate + '\'' +
                '}';
    }
}
