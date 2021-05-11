package com.deli.deliverypp.push;


import com.deli.deliverypp.model.OrderInfo;
import com.google.firebase.messaging.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;


public class OrderAlertService {

    private final Logger log = LogManager.getLogger(OrderAlertService.class);

    

    private void sendOrderToSeller(Map<String, String > data, Map<String, String > notification, String token) {

        sendFCMMessage(data, notification, token, "주문 요청 입니다~");
    }

    public void sendStateToClient(Map<String, String> data, Map<String, String> notification, String token) {
        sendFCMMessage(data, notification, token, "주문 완료 메세지");
    }

    private void sendFCMMessage(Map<String, String > data, Map<String, String > notification, String token, String condition) {
        Notification n = Notification.builder()
                .setTitle(notification.get("title"))
                .setBody(notification.get("body"))
                .build();

        Message msg = Message.builder()
                .setNotification(n)
                .putAllData(data)
                .setToken(token)
                .setCondition("주문 요청!")
                .build();
        try{
            String res = FirebaseMessaging.getInstance().send(msg);
            log.info(res);
            log.info("success");
        }catch (FirebaseMessagingException e) {
            log.info("failed");
            e.printStackTrace();
        }
    }

    
}
