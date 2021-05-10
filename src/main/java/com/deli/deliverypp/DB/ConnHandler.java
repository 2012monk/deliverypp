package com.deli.deliverypp.DB;

import com.deli.deliverypp.util.ConfigLoader;
import com.deli.deliverypp.util.annotaions.LoadConfig;

import java.sql.*;
import java.util.logging.Logger;

/**
 *  간단한 커넥션 핸들러 입니다
 *  static import로 간편하게 사용해주시면 되고
 *
 *  close 명령어로 꼭 닫아주세요
 */
@LoadConfig()
public class ConnHandler {

    private static String DB_URL;
    private static String DB_ID;
    private static String DB_PW;
    private static String DRIVER;
    private static final Logger log = Logger.getGlobal();


    public static void init() {
        try {
            if (DB_URL == null) {
                Class.forName("com.deli.deliverypp.util.ConfigLoader");
            }
            Class.forName(DRIVER);
            log.info("DB Handler Load Completed");
        } catch (ClassNotFoundException e) {
            log.info("DB Handler load failed");
            e.printStackTrace();

        }
    }

    public static void main(String[] args) {
        getConn();
    }



    public static Connection getConn() {
        Connection conn = null;
        try {
            if (DB_URL == null) {
                init();
            }
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
