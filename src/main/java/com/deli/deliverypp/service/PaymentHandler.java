package com.deli.deliverypp.service;

import com.deli.deliverypp.model.KaKaoPayment;
import com.deli.deliverypp.model.OrderInfo;
import com.deli.deliverypp.model.Product;
import com.deli.deliverypp.util.HttpConnectionHandler;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.util.stream.Collectors;

public class PaymentHandler {

    private final Logger log = LogManager.getLogger(PaymentHandler.class);
    /**
     *
     * @param orderInfo
     * @return redirectUrl
     */

    public KaKaoPayment kakaoPaymentReadyStage(OrderInfo orderInfo) throws IOException {
        String reqUri = "https://kapi.kakao.com/v1/payment/ready";
        Map<String, String > params = new HashMap<>();
        Map<String, String > header = new HashMap<>();
        String products = orderInfo.getOrderList().stream().map(Product::getProductName).collect(Collectors.joining(","));
        // TODO 가맹점 아이디 검색후 cid 가져오기
        log.info((int) orderInfo.getTotalAmount());
        log.info(orderInfo.getTotalPrice());
        params.put("cid", "TC0ONETIME");
        params.put("partner_order_id", "partner_order_id");
        params.put("partner_user_id","partner_user_id");
        params.put("item_name", products);
        params.put("quantity", String.valueOf((int) orderInfo.getTotalAmount()));
        params.put("total_amount", String.valueOf((int) orderInfo.getTotalPrice()));
        params.put("tax_free_amount", "0");
//        params.put("approval_url", "http://localhost:47788/payment/kakao/success");
        params.put("approval_url", "http://localhost:47788/kakao.redirect.html");
        params.put("cancel_url", "http://localhost:47788/kakao.cancel.html");
        params.put("fail_url", "http://localhost:47788/kakao.failed.html");

        header.put("Authorization", "KakaoAK 896429d8b61fe0fcb3795728adba6047");
        header.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        String json = HttpConnectionHandler.POSTHttpRequest(reqUri, params, header);

        System.out.println(json);

        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
                .readValue(json, KaKaoPayment.class
                );
    }


    public KaKaoPayment kakaoPaymentDoneStage (KaKaoPayment payment) throws IOException {
        String reqUri = "https://kapi.kakao.com/v1/payment/approve";
        Map<String ,String > params = new HashMap<String ,String>(){{
            put("cid", "TC0ONETIME");
            put("partner_order_id", "partner_order_id");
            put("partner_user_id","partner_user_id");
            put("pg_token", payment.getPg_token());
            put("tid", payment.getTid());
        }};
        Map<String, String> header = new HashMap<String,String>(){{
            put("Authorization", "KakaoAK 896429d8b61fe0fcb3795728adba6047");
            put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        }};

        String json = HttpConnectionHandler.POSTHttpRequest(reqUri, params, header);
        log.info(json);
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
                .readValue(json, KaKaoPayment.class
                );
    }




}
