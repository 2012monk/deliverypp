package com.deli.deliverypp.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.deli.deliverypp.DB.ConnHandler.close;
import static com.deli.deliverypp.DB.ConnHandler.getConn;

public class FCMTokenHandler {


    private Connection conn;

    public String getTokenFromOrderId (String orderId) {
        String sql = "SELECT FCM_TOKEN FROM USER JOIN ORDER_INFO OI on USER.USER_EMAIL = OI.USER_EMAIL WHERE ORDER_ID=?";
        conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, orderId);

            ResultSet rs = prst.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(conn);
        }
        return null;
    }
    public String getToken(String userEmail){
        String sql = "SELECT FCM_TOKEN FROM USER WHERE USER_EMAIL=?";
        conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, userEmail);
            ResultSet rs = prst.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(conn);
        }
        return null;
    }

    public String getStoreToken(String storeId) {
        if (storeId == null) {
            return null;
        }
        String sql = "SELECT FCM_TOKEN FROM USER JOIN STORE S on USER.USER_EMAIL = S.USER_EMAIL WHERE STORE_ID=?";
        conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, storeId);

            ResultSet rs = prst.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setToken(String userEmail, String token){
        String sql = "UPDATE USER SET FCM_TOKEN=? WHERE USER_EMAIL=?";
        conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, token);
            prst.setString(2, userEmail);

            if (prst.executeUpdate() > 0) {
                conn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(conn);
        }

        return false;
    }

    public boolean changeToken (String userEmail, String token) {
        return setToken(userEmail, token);
    }

    public boolean deleteToken (String userEmail){
        return changeToken(userEmail, "");
    }
}
