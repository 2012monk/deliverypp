package com.deli.deliverypp.service;

import com.deli.deliverypp.DB.OrderAccess;
import com.deli.deliverypp.model.KaKaoPayment;
import com.deli.deliverypp.model.OrderInfo;
import com.deli.deliverypp.model.ResponseMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import static com.deli.deliverypp.util.JSONUtil.getMapper;

public class OrderService {

    private static final ObjectMapper mapper = getMapper();
    private static final PaymentHandler paymentHandler = new PaymentHandler();
    private static final OrderAccess access = new OrderAccess();

    public ResponseMessage startKaKaoPayment(String json) throws IOException {

        OrderInfo orderInfo = new OrderInfo();

        try {

            orderInfo = mapper.readValue(json, OrderInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO order 검증 validate

        System.out.println(orderInfo);
        assert  orderInfo != null;
        orderInfo.makeNewOrder();
        if (orderInfo.getPaymentType() == null) {
            return null;
        }

        if (orderInfo.getPaymentType().equals("kakao")) {
            KaKaoPayment paymentInfo = paymentHandler.kakaoPaymentReadyStage(orderInfo);


            access.makeOrderReady(orderInfo);
            ResponseMessage msg = new ResponseMessage();
            if (paymentInfo != null) {
                OrderInfo finalOrderInfo = orderInfo;
                Map<String, String> data = new HashMap<String ,String >(){{
                    put("redirect_url", paymentInfo.getNext_redirect_pc_url());
                    put("tid", paymentInfo.getTid());
                    put("orderId", finalOrderInfo.getOrderId());
                }};
                msg.setData(mapper.writeValueAsString(data));
                msg.setMessage(paymentInfo.getTid());

            }
            else {
                msg.setMessage("failed");
                msg.setData("error");
            }
            return msg;
        }

        return null;
    }



    public ResponseMessage sendKaKaoDone(String token, String tid) throws IOException {
//        KaKaoPayment payment = mapper.readValue(json, KaKaoPayment.class);
        OrderInfo info = access.getOrderInfoByTid(tid);
        KaKaoPayment payment = new KaKaoPayment();
        payment.setPg_token(token);
        payment.setTid(tid);

        System.out.println(tid + token);
        KaKaoPayment done = paymentHandler.kakaoPaymentDoneStage(payment);

        ResponseMessage msg = new ResponseMessage();
        if (done.getCode() != null) {
            access.makeOrderFailed(tid);
            msg.setMessage("failed");
            msg.setData(done.getCode());
        }else {
            msg.setMessage("success");
            msg.setData(mapper.writeValueAsString(
                    new HashMap<String,Object>(){{
                        put("orderInfo", info);
                        put("paymentInfo", done);
                    }})
            );
//            return mapper.writeValueAsString(access.getOrderInfo(done.getTid()));
//            return msg;
        }

        return msg;
    }


}
