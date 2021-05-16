package com.deli.deliverypp.service;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.DB.OrderAccess;
import com.deli.deliverypp.model.KaKaoPayment;
import com.deli.deliverypp.model.OrderInfo;
import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.push.FCMMessageService;
import com.deli.deliverypp.util.MessageGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import static com.deli.deliverypp.util.JSONUtil.getMapper;

public class OrderService {

    private static final ObjectMapper mapper = getMapper();
    private static final PaymentHandler paymentHandler = new PaymentHandler();
    private static final OrderAccess access = new OrderAccess();
    private final Logger log = LogManager.getLogger(OrderService.class);

    private static final FCMMessageService alertService = new FCMMessageService();

    public ResponseMessage<OrderInfo> startKaKaoPayment(String json, DeliUser user) throws JsonProcessingException {

        OrderInfo orderInfo;

        try {
            orderInfo = mapper.readValue(json, OrderInfo.class);

            if (orderInfo.getUserEmail() == null) {
                orderInfo.setUserEmail(user.getUserEmail());
            }
            log.info(orderInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return MessageGenerator.makeErrorMsg("failed", "주문정보 양식이 올바르지 않습니다");
        }

        // TODO order 검증 validate

        log.debug(orderInfo);
        assert  orderInfo != null;

        // 주문 번호 부여
        orderInfo.initOrder();

        // TODO 주문별로 라우팅 필요
        if (!validateOrderInfo(orderInfo)){
            return MessageGenerator.makeErrorMsg("failed", "주문 사항 누락");
        }

        if (orderInfo.getPaymentType().equals("kakao")) {
            KaKaoPayment paymentInfo;
            try {
                paymentInfo = paymentHandler.kakaoPaymentReadyStage(orderInfo);
            }catch (Exception e) {
                e.printStackTrace();
                return MessageGenerator.makeErrorMsg("failed", "결제 요청 실패!");
            }

            ResponseMessage<OrderInfo> msg = new ResponseMessage<>();
            if (paymentInfo != null) {
                // add order list
                orderInfo.setTid(paymentInfo.getTid());
                access.makeNewOrder(orderInfo);

                Map<String, String> data = new HashMap<String ,String >(){{
                    put("redirect_url", paymentInfo.getNext_redirect_pc_url());
                    put("tid", paymentInfo.getTid());
                    put("orderId", orderInfo.getOrderId());
                }};

                msg.setData(mapper.writeValueAsString(data));
                msg.setMessage(paymentInfo.getTid());
            }
            else {
                return MessageGenerator.makeErrorMsg("failed", "주문 실패");
            }
            return msg;
        }

        return null;
    }



    public ResponseMessage<OrderInfo> sendKaKaoDone(String token, String tid) throws IOException {

        KaKaoPayment payment = new KaKaoPayment();
        payment.setPg_token(token);
        payment.setTid(tid);

        KaKaoPayment doneState = paymentHandler.kakaoPaymentDoneStage(payment);

        ResponseMessage<OrderInfo> msg = new ResponseMessage<>();
        // validate if failed
        if (doneState.getCode() != null) {

//            access.makeOrderFailed(tid);
            access.makeOrderFailedByTid(tid);
            msg.setMessage("failed");
            msg.setData(doneState.getCode());
        }else {
            access.makeOrderSuccessByTid(tid);
            OrderInfo info = access.getOrderInfoByTid(tid);
            msg = MessageGenerator.makeMsg("order success", info);

            alertService.sendOrderMsgToSeller(info);
        }

        return msg;
    }


    // TODO 주문 data 검증
    public boolean validateOrderInfo (OrderInfo info) {
        return info.getPaymentType() != null ||
                info.getUserAddr() != null ||
                info.getOrderList() != null ||
                info.getStoreId() != null ||
                info.getTotalAmount() != 0 ||
                info.getOrderId() != null;
//        return false;
    }


}
