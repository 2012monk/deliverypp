package com.deli.deliverypp.model;

public class DeliveryInfo {

    private String orderId;
    private String eta;
    private String deliveryStatus;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
