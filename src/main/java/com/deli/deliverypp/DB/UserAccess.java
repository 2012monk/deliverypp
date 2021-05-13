package com.deli.deliverypp.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.deli.deliverypp.DB.ConnHandler.close;
import static com.deli.deliverypp.DB.ConnHandler.getConn;
import static com.deli.deliverypp.util.DBUtil.setPOJO;

public class UserAccess {

//    private static Connection conn;

    public boolean registerUser(DeliUser user) {
        String sql = "INSERT INTO USER (USER_EMAIL, USER_PW, USER_ROLE, USER_TYPE, USER_ADDR,USER_TELEPHONE) VALUES (?,?,?,?,?,?)";
        Connection conn = getConn();

        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, user.getUserEmail());
            prst.setString(2, user.getUserPw());
            prst.setString(3, user.getUserRole().name());
            prst.setString(4, user.getUserType().name());
            prst.setString(5, user.getUserAddr());
            prst.setString(6, user.getUserTelephone());

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

    public DeliUser registerUser(DeliUser user, boolean getInfo) {
        if (getInfo && registerUser(user)){
            return user;
        }
        return null;
    }


    public DeliUser loginUser (DeliUser user) {
        DeliUser savedUser = getUserInfo(user.getUserEmail());

        if (user.getUserType() == DeliUser.UserType.DELI) {

            if (user.getUserEmail().equals(savedUser.getUserEmail()) &&
                    user.getUserPw().equals(savedUser.getUserPw())) {
                return savedUser;
            }
        }
        return savedUser;
    }

    public boolean isUserEmailOverlap(String email) {
        String sql = "SELECT COUNT(*) FROM USER WHERE USER_EMAIL=?";
        Connection conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, email);
            ResultSet rs = prst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public DeliUser getUserInfo (String userEmail) {
        String sql = "SELECT * FROM USER WHERE USER_EMAIL=?";
        DeliUser user = new DeliUser();
        Connection conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, userEmail);

            ResultSet rs = prst.executeQuery();

            if (rs.next()) {
//                user.setUserEmail(rs.getString("USER_EMAIL"));
//                user.setUserPw(rs.getString("USER_PW"));
//                user.setUserType(DeliUser.UserType.valueOf(rs.getString("USER_TYPE")));
//                user.setUserRole(DeliUser.UserRole.valueOf(rs.getString("USER_ROLE")));
                user = setPOJO(DeliUser.class, rs);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close(conn);
        }

        return null;
    }

//    public DeliUser signInUser (DeliUser user) {
//        if (loginUser(user) != null) {
//            return user;
//        }
//        return null;
//    }

    public boolean updateUser (DeliUser user) {
        String sql = "UPDATE USER SET USER_ADDR=?,USER_ROLE=?,USER_TELEPHONE=?, USER_PW=? WHERE USER_EMAIL=?";
        System.out.println(user);
        Connection conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, user.getUserAddr());
            prst.setString(2, user.getUserRole().name());
            prst.setString(3, user.getUserTelephone());
            prst.setString(4, user.getUserPw());
            prst.setString(5, user.getUserEmail());

            if (prst.executeUpdate() > 0) {
                conn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close(conn);
        }
        return false;
    }

    public boolean deleteUser (String userEmail) {
        String sql = "DELETE FROM USER WHERE USER_EMAIL=?";
        Connection conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, userEmail);

            if (prst.executeUpdate() > 0) {
                conn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close(conn);
        }
        return false;
    }


}
