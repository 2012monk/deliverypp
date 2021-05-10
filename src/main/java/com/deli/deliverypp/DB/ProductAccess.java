package com.deli.deliverypp.DB;

import com.deli.deliverypp.model.Product;
import com.deli.deliverypp.model.Store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.deli.deliverypp.DB.ConnHandler.close;
import static com.deli.deliverypp.DB.ConnHandler.getConn;

public class ProductAccess {

    private static Connection conn;

    public List<Product> getProductListByStoreId (String storeId) {
        List<Product> list =  new ArrayList<>();
        String sql = "SELECT * FROM PRODUCT WHERE STORE_ID=?";
        conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, storeId);

            ResultSet rs = prst.executeQuery();

            while (rs.next()) {
                try {
                    list.add(setProduct(rs, new Product()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(conn);
        }
        return list;
    }

    private Product setProduct (ResultSet rs, Product product) throws SQLException {
//        if (rs.next()){
        product.setProductId(rs.getString("PRODUCT_ID"));
        product.setStoreId(rs.getString("STORE_ID"));
        product.setProductName(rs.getString("PRODUCT_NAME"));
        product.setProductImage(rs.getString("PRODUCT_IMAGE"));
        product.setProductPrice(rs.getString("PRODUCT_PRICE"));
        product.setProductDesc(rs.getString("PRODUCT_DESC"));
//        }
        return product;
    }

    public Product getProductById (String productId) {
        Product product;
        String sql = "SELECT * FROM PRODUCT WHERE PRODUCT_ID=?";
        conn = getConn();

        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, productId);

            return setProduct(prst.executeQuery(), new Product());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertProduct(Product product) {
        String sql = "INSERT INTO PRODUCT " +
                "(PRODUCT_ID, STORE_ID, PRODUCT_NAME, PRODUCT_IMAGE, PRODUCT_PRICE,PRODUCT_DESC) " +
                "VALUES (?,?,?,?,?,?)";
        conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, product.getProductId());
            prst.setString(2, product.getStoreId());
            prst.setString(3, product.getProductName());
            prst.setString(4, product.getProductImage());
            prst.setString(5, product.getProductPrice());
            prst.setString(6, product.getProductDesc());

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

    public boolean updateProduct(Product product) {
        return false;
    }


    public boolean deleteProduct(String productId) {
        return false;
    }

}
