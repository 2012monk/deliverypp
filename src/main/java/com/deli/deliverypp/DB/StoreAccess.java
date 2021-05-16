package com.deli.deliverypp.DB;

import com.deli.deliverypp.model.Store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.deli.deliverypp.DB.ConnHandler.close;
import static com.deli.deliverypp.DB.ConnHandler.getConn;
import static com.deli.deliverypp.util.DBUtil.setPOJO;

public class StoreAccess {

    public static void main(String[] args) {
        System.out.println(new StoreAccess().getStoreById("stid3"));
    }


    public List<Store> getStoreListByUser (String userEmail) {
        List<Store> list = new ArrayList<>();
        String sql = "SELECT * FROM STORE WHERE USER_EMAIL=?";
        Connection conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, userEmail);
            ResultSet rs = prst.executeQuery();

            while (rs.next()) {
                list.add(setPOJO(Store.class, rs));
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(conn);
        }
        return list;
    }

    public List<Store> getStoreList () {
        List<Store> list = new ArrayList<>();
        String sql = "SELECT * FROM STORE";
        Connection conn = getConn();
        try {
            ResultSet rs = conn.prepareStatement(sql).executeQuery();

            while (rs.next()) {
//                Store store = rollUpStore(rs, new Store());
//                list.add(store);
                list.add(setPOJO(Store.class, rs));
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(conn);
        }
        return list;
    }

    public Store getStoreByName(String storeName) {
        String sql = "SELECT * FROM STORE WHERE STORE_NAME=?";
        Connection conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, storeName);
            ResultSet rs = prst.executeQuery();

            if (rs.next()) {
//                store = rollUpStore(rs, store);
                return setPOJO(Store.class, rs);
            }
//            return store;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(conn);
        }
        return null;
    }

    public Store getStoreById(String storeId) {
        Store store = new Store();
        String sql = "SELECT * FROM STORE WHERE STORE_ID=?";
        Connection conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, storeId);
            ResultSet rs = prst.executeQuery();

            if (rs.next()) {
                store = setPOJO(Store.class, rs);
            }
            return store;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(conn);
        }
        return null;
    }


    public Store insertStore (Store store) {
        String sql = "INSERT INTO STORE (STORE_ID, STORE_NAME, STORE_DESC, STORE_IMAGE, STORE_ADDR, USER_EMAIL,STORE_TELEPHONE) VALUES (?,?,?,?,?,?,?)";
        Connection conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, store.getStoreId());
            prst.setString(2, store.getStoreName());
            prst.setString(3, store.getStoreDesc());
            prst.setString(4, store.getStoreImage());
            prst.setString(5, store.getStoreAddr());
            prst.setString(6, store.getUserEmail());
            prst.setString(7, store.getStoreTelephone());

            if (prst.executeUpdate() > 0) {
                conn.commit();
                return store;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(conn);
        }

        return null;
    }

    public Store updateStore (Store store) {
        String sql = "UPDATE STORE SET STORE_IMAGE=?,STORE_TELEPHONE=?,STORE_DESC=?,STORE_NAME=?,STORE_ADDR=? WHERE STORE_ID=?";
        Connection conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, store.getStoreImage());
            prst.setString(2, store.getStoreTelephone());
            prst.setString(3, store.getStoreDesc());
            prst.setString(4, store.getStoreName());
            prst.setString(5, store.getStoreAddr());
            prst.setString(6, store.getStoreId());

            if (prst.executeUpdate() > 0) {
                conn.commit();
                return store;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close(conn);
        }
        return null;
    }

    public boolean deleteStore (String storeId) {
        String sql = "DELETE FROM STORE WHERE STORE_ID=?";
        Connection conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, storeId);

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


    public boolean isNameOverlap(String name) {
        String sql = "SELECT COUNT(*) FROM STORE WHERE STORE_NAME=?";
        Connection conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, name);

            ResultSet rs = prst.executeQuery();
            if (rs.next()) {
                int l = rs.getInt(1) ;
                return l >= 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(conn);
        }
        return false;
    }

}
