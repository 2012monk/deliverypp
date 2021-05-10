import com.deli.deliverypp.model.KaKaoPayment;
import com.deli.deliverypp.model.OrderInfo;
import com.deli.deliverypp.service.OrderService;
import com.deli.deliverypp.service.PaymentHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class OrderTest {

    static ObjectMapper m = new ObjectMapper();
    public static void main(String[] args) throws IOException {
        String json = "{\n" +
                "    \"address\" : \"address\",\n" +
                "    \"telephone\" : \"telephone\",\n" +
                "    \"orderRequirement\" : \"orderRequirement\",\n" +
                "\t\t\"paymentType\" : \"kakao\",\n" +
                "    \"storeId\": \"storeId\",\n" +
                "    \"storeName\": \"storeName\",\n" +
                "    \"quantity\" : \"quantity\",\n" +
                "    \"totalPrice\": \"totalPrice\",\n" +
                "    \"orderList\": [\n" +
                "      {\n" +
                "        \"productId\": \"pid2\",\n" +
                "        \"productName\": \"아이템3421\",\n" +
                "        \"productImage\": null,\n" +
                "        \"storeId\": \"stid2\",\n" +
                "        \"productPrice\": \"15000\",\n" +
                "        \"productDesc\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"productId\": \"pid4\",\n" +
                "        \"productName\": \"섬띵\",\n" +
                "        \"productImage\": null,\n" +
                "        \"storeId\": \"stid2\",\n" +
                "        \"productPrice\": \"80000\",\n" +
                "        \"productDesc\": null\n" +
                "      }\n" +
                "    ]\n" +
                "\n" +
                "  }";

        OrderInfo info = m.readValue("{\"storeId\":\"stid2\",\"storeName\":\"포명청천\",\"quantity\":\"13\",\"totalPrice\":\"715000\",\"orderList\":[{\"productId\":\"pid2\",\"productName\":\"아이템3421\",\"productImage\":null,\"storeId\":\"stid2\",\"productPrice\":\"15000\",\"productDesc\":null,\"entity\":5},{\"productId\":\"pid4\",\"productName\":\"섬띵\",\"productImage\":null,\"storeId\":\"stid2\",\"productPrice\":\"80000\",\"productDesc\":null,\"entity\":8}],\"address\":\"555\",\"telephone\":\"666\",\"orderRequirement\":\"777\",\"paymentType\":\"kakao\"}", OrderInfo.class);
//        OrderInfo o = m.readValue(json, OrderInfo.class);

        OrderService s = new OrderService();
//        System.out.println(new PaymentHandler().kakaoPaymentReadyStage(info));
        System.out.println(s.startKaKaoPayment(m.writeValueAsString(info)));




    }
}
