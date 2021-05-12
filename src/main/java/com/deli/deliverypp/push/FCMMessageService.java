package com.deli.deliverypp.push;


import com.deli.deliverypp.DB.FCMTokenHandler;
import com.deli.deliverypp.model.DeliveryInfo;
import com.deli.deliverypp.model.OrderInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.*;
import com.sun.prism.impl.FactoryResetException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public class FCMMessageService {

    private final Logger log = LogManager.getLogger(FCMMessageService.class);
    private static final FCMTokenHandler handler = new FCMTokenHandler();
    private static final ObjectMapper mapper = new ObjectMapper();

    

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


    public void sendOrderMsgToSeller (OrderInfo info){
        String token = handler.getStoreToken(info.getStoreId());
        if (token != null) {
            Map<String, String> body = parseOrderInfo(info);
            if (body != null) {

                Map<String ,String > header = new HashMap<>();
                header.put("title", "주문 요청입니다~");
                header.put("body", info.getOrderId());

                sendFCMMessage(body, header, token,"새로운 주문");
            }
        }
    }

    public void sendDeliveryInfoToClient (DeliveryInfo info) throws JsonProcessingException {
        String token = handler.getTokenFromOrderId(info.getOrderId());
        if (token != null) {

            Map<String, String> data = new HashMap<String ,String >(){{
                put("message", "order confirm call");
                put("delivery_info", mapper.writeValueAsString(info));
            }};

            Map<String,String> notification = new HashMap<String ,String>(){{
                put("title", "주문이 점수되었습니다");
                put("body", "order_id : " +info.getOrderId());
            }};

            sendFCMMessage(data, notification, token, "주문 완료 메세지");
        }
    }

    private Map<String ,String> parseOrderInfo (OrderInfo info) {
        Map<String, String> data = new HashMap<>();
        data.put("message", "order call");
        try {
            data.put("order_info" , mapper.writeValueAsString(info));
            return data;
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    
}
