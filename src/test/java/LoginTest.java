import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.service.UserLoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginTest {

    static UserLoginService s = new UserLoginService();
    public static void main(String[] args) throws JsonProcessingException {
        String json = "{\"userType\":\"DELI\",\"userRole\":\"CLIENT\",\"userEmail\":\"test@test.com\",\"userPw\":\"abcd1234\",\"userName\":\"kims\",\"userTel\":\"010-2345-2345\"}";

        DeliUser u = new ObjectMapper().readValue(json, DeliUser.class);

//        String j = s.setAccess(u);
//        System.out.println(j);
//        String i = s.generateAuthInfo(json);
//        System/.out.println(i);
    }
}
