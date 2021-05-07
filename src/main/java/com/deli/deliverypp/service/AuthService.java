package com.deli.deliverypp.service;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.DB.UserAccess;
import com.deli.deliverypp.auth.google.GoogleAuthentication;
import com.deli.deliverypp.auth.jwt.JwtTokenProvider;
import com.deli.deliverypp.model.AuthInfo;
import com.deli.deliverypp.model.GoogleUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class AuthService {

    private static final JwtTokenProvider provider = new JwtTokenProvider();
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

    private static final UserAccess access = new UserAccess();

    public boolean signUpUser (String json) throws JsonProcessingException {
        return access.registerUser(parseUser(json));
    }

    public boolean userLogin(String json) throws JsonProcessingException {
        return access.loginUser(parseUser(json));
    }

    public boolean checkUserIdOverlap (String userEmail) {
        return access.isUserEmailOverlap(userEmail);
    }

    public DeliUser getUserInfo (String json) {
        return access.getUserInfo(json);
    }

    private DeliUser parseUser (String json) throws JsonProcessingException {
        if (json == null || json.equals("")) return null;
        return mapper.readValue(json, DeliUser.class);
    }

    /**
     *
     * @param json user information
     * @return authorization information
     *         - user email
     *         - user role
     *         - user type
     *         - access token
     *         - auth type
     *         -
     * @throws JsonProcessingException json parse exception
     */
    public String loginUser (String json) throws JsonProcessingException {
        DeliUser user = access.signInUser(parseUser(json));

        if (user != null) {
//            return user.getUserEmail();
            return setAccess(user);
        }
        return null;
    }



    public void googleAuth(String json) throws IOException {
        GoogleUser user = GoogleAuthentication.accessGoogleInfo(json);

    }


    public boolean getAuthentication(String jws) {
        try {
            if (jws != null){
                provider.validateToken(jws);
            }else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void getAuthorized (String jws) {

    }




    /**
     *
     * @param user user information
     * @return json auth information
     */
    public String setAccess (DeliUser user) throws JsonProcessingException {
        String token = provider.generateToken(user.getUserEmail(),
                user.getUserRole().name(), user.getUserType().name());

        AuthInfo auth = new AuthInfo();
        auth.setAuth_type("Bearer");
        auth.setAccess_token(token);
        auth.setUser(user);

        return mapper.writeValueAsString(auth);
    }


    public DeliUser parseUserFromToken(String jws) {
        Map<String, Object> map = provider.getTokenBody(jws);

        String userEmail = (String) map.get("userEmail");
        String userRole = (String) map.get("userRole");
        String userType = (String) map.get("userType");

        DeliUser user = new DeliUser();

        user.setUserRole(DeliUser.UserRole.valueOf(userRole));
        user.setUserType(DeliUser.UserType.valueOf(userType));
        user.setUserEmail(userEmail);
        return user;
    }

}
