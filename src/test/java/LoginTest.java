import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.model.AuthInfo;
import com.deli.deliverypp.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginTest {

    static AuthService s = new AuthService();
    public static void main(String[] args) throws JsonProcessingException {
        String json = "{\"userType\":\"DELI\",\"userRole\":\"CLIENT\",\"userEmail\":\"test@test.com\",\"userPw\":\"abcd1234\",\"userName\":\"kims\",\"userTel\":\"010-2345-2345\"}";

        DeliUser u = new ObjectMapper().readValue(json, DeliUser.class);

        String j = s.setAccess(u);
        System.out.println(j);
        String i = s.loginUser(json);
        System.out.println(i);
    }
}
