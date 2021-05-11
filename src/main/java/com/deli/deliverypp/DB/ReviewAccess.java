package com.deli.deliverypp.DB;

import com.deli.deliverypp.model.Review;
import com.deli.deliverypp.util.DBUtil;
import com.deli.deliverypp.util.annotaions.FindColumn;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.deli.deliverypp.DB.ConnHandler.close;
import static com.deli.deliverypp.DB.ConnHandler.getConn;
import static com.deli.deliverypp.util.DBUtil.*;

public class ReviewAccess {

    private static Connection conn;

    public Review insertReview (Review review) {
        String sql = "INSERT INTO REVIEW (STORE_ID, REVIEW_ID, REVIEW_RATING, REVIEW_IMAGE, USER_EMAIL, REVIEW_CONTENT) VALUES (?,?,?,?,?,?)";
        conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, review.getStoreId());
            prst.setString(2, review.getReviewId());
            prst.setInt(3, review.getReviewRating());
            prst.setString(4, review.getUserEmail());
            prst.setString(5, review.getReviewContent());
            prst.setString(6, review.getReviewDate());
            if (prst.executeUpdate() > 0) {
                conn.commit();
                return review;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close(conn);
        }
        return null;
    }

    public Review updateReview (Review review) {

        return null;
    }

    public boolean deleteReview (String reviewId) {

        return false;
    }

    public List<Review> getReviewsByKey (@FindColumn(target = Review.class) String key, String value) {
        try {
            Field f = Review.class.getField(key);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        List<Review> list = new ArrayList<>();
        String dbKey = convertToDbNameConvention(key);
        String sql = "SELECT * FROM REVIEW WHERE " + dbKey+"=?";
        conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, value);

            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
                list.add(setPOJO(Review.class, rs));
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Review getReviewByKey (@FindColumn(target = Review.class) String key, String value) {
        List<Review> list = getReviewsByKey(key, value);
        if (list !=null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public List<Review> getReviewsByStore (String storeId) {
        return null;
    }



    public Review getReview (String reviewId) {

        return null;
    }




}
