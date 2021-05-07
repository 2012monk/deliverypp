package com.deli.deliverypp.service;

import com.deli.deliverypp.DB.OrderAccess;
import com.deli.deliverypp.model.KaKaoPayment;
import com.deli.deliverypp.model.OrderInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;

public class OrderService {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final PaymentHandler paymentHandler = new PaymentHandler();
    private static final OrderAccess access = new OrderAccess();

    public String startKaKaoPayment(String json) throws IOException {

        OrderInfo orderInfo = mapper.readValue(json, OrderInfo.class);

        orderInfo.makeNewOrder();

        if (orderInfo.getPaymentType().equals("kakao")) {
            KaKaoPayment paymentInfo = paymentHandler.kakaoPaymentReadyStage(orderInfo);

            access.makeOrderReady(orderInfo);
            return paymentInfo.getRedirectUri();
        }

        return "/";
    }

    public String sendKaKaoDone(String json) throws IOException {
        KaKaoPayment payment = mapper.readValue(json, KaKaoPayment.class);

        KaKaoPayment done = paymentHandler.kakaoPaymentDoneStage(payment);

        if (done.getCode() != null) {
            access.makeOrderFailed(done.getTid());
        }else {
            return mapper.writeValueAsString(access.getOrderInfo(done.getTid()));
        }

        return mapper.writeValueAsString(done);
    }


}
