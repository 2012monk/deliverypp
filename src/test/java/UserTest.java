
import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.service.UserLoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserTest {

    static UserLoginService s = new UserLoginService();
    static ObjectMapper mapper = new ObjectMapper();
    public static void main(String[] args) throws JsonProcessingException {
        DeliUser user = new DeliUser();
        String json = "  {\n" +
                "    \"userType\" : \"DELI\",\n" +
                "    \"userRole\" : \"CLIENT\",\n" +
                "    \"userEmail\" : \"email\",\n" +
                "    \"userPw\" : \"pw\"\n" +
                "  }";
        user = mapper.readValue(json, DeliUser.class);
        user = s.getUserInfo(json);
        System.out.println(user);
    }
}

