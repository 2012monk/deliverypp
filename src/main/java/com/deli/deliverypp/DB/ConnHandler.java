package com.deli.deliverypp.DB;

import com.deli.deliverypp.util.Config;

import java.sql.*;

/**
 *  간단한 커넥션 핸들러 입니다
 *  static import로 간편하게 사용해주시면 되고
 *
 *  close 명령어로 꼭 닫아주세요
 */
public class ConnHandler {
    private static final String URL = Config.DB_URL;
    private static final String USR = Config.DB_ID;
    private static final String PW = Config.DB_PW;
    private static final String DRIVER = Config.DRIVER;

    static {
        System.out.println(URL+USR+PW);
        try {
            Class.forName(DRIVER);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }



    public static Connection getConn() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USR, PW);
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
