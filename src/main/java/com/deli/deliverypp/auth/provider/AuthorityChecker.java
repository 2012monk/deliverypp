package com.deli.deliverypp.auth.provider;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.service.UserLoginService;
import com.deli.deliverypp.util.ControlUtil;
import com.deli.deliverypp.util.DBUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


import static com.deli.deliverypp.DB.ConnHandler.close;
import static com.deli.deliverypp.DB.ConnHandler.getConn;
import static com.deli.deliverypp.util.DBUtil.setPOJO;
import static com.deli.deliverypp.util.JSONUtil.getMapper;

public class AuthorityChecker {

    private static final ObjectMapper mapper = getMapper();
    private static final UserLoginService userLoginService = new UserLoginService();
    private static final AuthProvider authProvider = new AuthProvider();
    private static final Logger log = LogManager.getLogger(AuthorityChecker.class);




    public static  <T> boolean selfCheck(String userEmail, String key, String value, Class<T> model) {
        T target = getModelFromKey(model, key, value);

        try {
            String email = (String) target.getClass().getDeclaredField("userEmail").get(target);
            assert email != null;
            if (userEmail.equals(email)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    public static String checkLogin(HttpServletRequest request) {
        String token = authProvider.parseHeader(request);
        if (token != null) {
            DeliUser user = userLoginService.parseUserFromToken(token);
            if (user != null) {
                return  user.getUserEmail();
            }

        }

        return null;
    }





    public static  <T> T getModelFromKey (Class<T> tClass, String key, String value) {
        String table = DBUtil.convertClassNameToDbName(tClass.getSimpleName());
        String field = DBUtil.convertToDbNameConvention(key);

        String sql = "SELECT * FROM "+table+" WHERE "+key;

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM").append(" ")
                .append(table).append(" ")
                .append("WHERE").append(" ")
                .append(field).append("=")
                .append("'").append(value).append("'");

        log.info(sb.toString());
        Connection conn = getConn();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sb.toString());

            if (rs.next()) {
                try {
                    return setPOJO(tClass, rs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close(conn);
        }

        return null;
    }


    public static <T> boolean checkUserEmail(HttpServletRequest request, Class<T> targetClass, String key, String value) {
        T model = getModelFromKey(targetClass, key, value);
        DeliUser user = authProvider.getUserFromHeader(request);

        if (user != null && model != null) {
            try {
                String email = user.getUserEmail();
                Field field = model.getClass().getDeclaredField("userEmail");
                field.setAccessible(true);

                log.info(email);
                log.info(field.getName());
                String tEmail = (String) field.get(model);
                log.info(tEmail);

                return email.equals(tEmail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static <T> T parseModelFromRequest (HttpServletRequest request, Class<T> tClass){
        try {
            return mapper.readValue(ControlUtil.getJson(request), tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> boolean checkUserEmailFromJson(HttpServletRequest request, Class<T> targetClass, String key, String json) {
        try {
            T keyModel = mapper.readValue(json, targetClass);
            Field f = keyModel.getClass().getDeclaredField(key);
            f.setAccessible(true);
            String id = (String) f.get(keyModel);

            // id 로 db 조회
            T targetModel = getModelFromKey(targetClass, key, id);
//            DeliUser user = authProvider.getUserFromHeader(request);
            String userEmail = authProvider.getUserEmailFromHeader(request);
            if (targetModel != null && userEmail != null){
                try {
                    Field field = targetModel.getClass().getDeclaredField("userEmail");
                    field.setAccessible(true);

                    String tEmail = (String) field.get(targetModel);

                    return userEmail.equals(tEmail);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }




}
