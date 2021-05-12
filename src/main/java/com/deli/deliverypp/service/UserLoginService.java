package com.deli.deliverypp.service;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.DB.UserAccess;
import com.deli.deliverypp.auth.google.GoogleAuthentication;
import com.deli.deliverypp.auth.jwt.JwtTokenProvider;
import com.deli.deliverypp.model.AuthInfo;
import com.deli.deliverypp.model.GoogleToken;
import com.deli.deliverypp.model.GoogleUser;
import com.deli.deliverypp.model.ResponseMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;

public class UserLoginService {

    private static final Logger log = LogManager.getLogger(UserLoginService.class);
    private static final JwtTokenProvider provider = new JwtTokenProvider();
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

    private static final UserAccess access = new UserAccess();

    public boolean signUpUser (String json) throws JsonProcessingException {
        DeliUser user = parseUser(json);
        user.setUserRole(DeliUser.UserRole.CLIENT);
        return access.registerUser(user);
    }

    public boolean signUpSeller (String json) {
        DeliUser user = null;
        try {
            user = parseUser(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user != null){
            user.setUserRole(DeliUser.UserRole.SELLER);
            return access.registerUser(user);
        }

        return false;
    }

    public boolean userLogin(String json) throws JsonProcessingException {
        return access.loginUser(parseUser(json));
    }

    public boolean checkUserIdOverlap (String userEmail) {
        return access.isUserEmailOverlap(userEmail);
    }

    public DeliUser getUserInfo (String email) {
        return access.getUserInfo(email);
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
    public AuthInfo generateAuthInfo (String json) throws JsonProcessingException {
        DeliUser user = access.signInUser(parseUser(json));
        return generateAuthInfo(user);
    }

    public AuthInfo generateAuthInfo (DeliUser user) throws JsonProcessingException {
        if (user != null) {
            return setAccess(user);
        }
        return null;
    }



    public ResponseMessage<AuthInfo> googleAuth(String json) {
        GoogleUser user = null;
        ResponseMessage<AuthInfo> msg = new ResponseMessage<>();
        AuthInfo info = null;
        try {
            GoogleToken googleToken = mapper.readValue(json, GoogleToken.class);
            user = GoogleAuthentication.accessGoogleInfo(googleToken);
            log.info(user);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        DeliUser regUser = access.getUserInfo(user.getEmail());

        // sign up process
        if (regUser == null) {
            registerGoogleUser(user);
            msg.setMessage("register_info_required");
            msg.setCode("877");
        }
        else {
            try {
                info = generateAuthInfo(regUser);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                msg.setMessage("generate_auth_failed");
                msg.setCode("999");
                return msg;
            }
        }

        msg.setMessage("login success");
        msg.setData(info);

        return msg;
    }

    public boolean registerGoogleUser (GoogleUser googleUser) {
        DeliUser user = new DeliUser();
        user.setUserEmail(googleUser.getEmail());
        user.setUserType(DeliUser.UserType.GOOGLE);
        return access.registerUser(user);
    }




    public boolean getAuthentication(String jws) {
        if (jws != null) {
            try {
                provider.validateToken(jws);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public void getAuthorized (String jws) {

    }

    private boolean validateUser (DeliUser user) {
        return user.getUserEmail() != null ;
    }

    /**
     *
     * @param user user information
     * @return json auth information
     */
    private AuthInfo setAccess (DeliUser user) throws JsonProcessingException {
        String token = provider.generateToken(user.getUserEmail(),
                user.getUserRole().name(), user.getUserType().name());

        AuthInfo auth = new AuthInfo();
        auth.setAuth_type("Bearer");
        auth.setAccess_token(token);
        auth.setUser(user);
        auth.setExp(Long.parseLong(String.valueOf(provider.getTokenBody(token).get("exp"))));
        return auth;
    }


    public DeliUser parseUserFromToken(String jws) {
        return parse(provider.getTokenBody(jws));
    }


    public DeliUser parseUserFromRefreshToken(String jws) {
        return parse(provider.getRefreshTokenBody(jws));
    }

    public String getRefreshToken(DeliUser user) {
        return provider.generateRefreshToken(user);
    }

    private DeliUser parse(Map<String, Object> map) {
        String userEmail = (String) map.get("userEmail");
        String userRole = (String) map.get("userRole");
        String userType = (String) map.get("userType");

        DeliUser user = new DeliUser();

        try {
            user.setUserRole(DeliUser.UserRole.valueOf(userRole));
            user.setUserType(DeliUser.UserType.valueOf(userType));
            user.setUserEmail(userEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean updateUser(String json) {
        try {
            return access.updateUser(mapper.readValue(json, DeliUser.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(String userEmail) {
        return access.deleteUser(userEmail);
    }
}
