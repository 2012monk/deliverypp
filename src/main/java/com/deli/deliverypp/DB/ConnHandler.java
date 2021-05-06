package com.deli.deliverypp.DB;

import com.deli.deliverypp.util.ConfigLoader;
import com.deli.deliverypp.util.LoadConfig;

import java.sql.*;

/**
 *  간단한 커넥션 핸들러 입니다
 *  static import로 간편하게 사용해주시면 되고
 *
 *  close 명령어로 꼭 닫아주세요
 */
@LoadConfig(path = "config.properties")
public class ConnHandler {

    private static String DB_URL;
    private static String DB_ID;
    private static String DB_PW;
    private static String DRIVER;


    public static void init() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }
    }

    public static void main(String[] args) {
        getConn();
    }



    public static Connection getConn() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
            conn.setAutoCommit(false);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn){
        try {
            if (conn != null) conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void close (PreparedStatement prst) {
        try {
            if (prst != null) prst.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void close (ResultSet rs) {
        try {
            if (rs != null) rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
