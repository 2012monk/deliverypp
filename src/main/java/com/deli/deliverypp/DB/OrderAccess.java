package com.deli.deliverypp.DB;

import com.deli.deliverypp.model.OrderInfo;

public class OrderAccess {


    public boolean makeOrderReady(OrderInfo info) {

        return false;
    }

    public boolean makeOrderSuccess(OrderInfo info) {
        return false;
    }

    public boolean makeOrderFailed (OrderInfo info) {
        return false;
    }

    public boolean makeOrderDone (OrderInfo info) {
        return false;
    }


    public OrderInfo getOrderInfo (String key) {
        return null;
    }

    public OrderInfo getOrderInfoByTid (String tid) {
        return null;
    }

    public boolean makeOrderFailed (String key) {
        return false;
    }


}
