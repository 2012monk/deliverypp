package com.deli.deliverypp.model;

import java.util.List;

public class Order {

    private String userId;
    private String address;
    private String telephone;
    private String orderRequirement;

    private String storeId;
    private String quantity;
    private String totalPrice;
    private DeliveryInfo deliveryInfo;
    private List<Product> orderList;
    private Payment payment;



}
