package com.deli.deliverypp.DB;

import com.deli.deliverypp.model.Reply;

import javax.servlet.annotation.WebServlet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.deli.deliverypp.DB.ConnHandler.close;
import static com.deli.deliverypp.DB.ConnHandler.getConn;
import static com.deli.deliverypp.util.DBUtil.setPOJO;

public class ReplyAccess {

//    private static Connection conn;

    public Reply getReplyById(String replyId) {
        String sql = "SELECT * FROM REPLY WHERE REPLY_ID=?";
        Connection conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, replyId);

            ResultSet rs = prst.executeQuery();
            if (rs.next()) {
                try {
                    return setPOJO(Reply.class, rs);
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

    public List<Reply> getRepliesBiReview(String reviewId) {
        String sql = "SELECT * FROM REPLY WHERE REVIEW_ID=?";
        List<Reply> replies = new ArrayList<>();
        Connection conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, reviewId);
            ResultSet rs = prst.executeQuery();

            while (rs.next()) {
                try {
                    replies.add(setPOJO(Reply.class, rs));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return replies;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close(conn);
        }
        return null;
    }

    public Reply insertReply(Reply reply) {
        String sql = "INSERT INTO REPLY (REVIEW_ID, REPLY_ID, REPLY_CONTENT, USER_EMAIL) VALUES (?,?,?,?)";
        Connection conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, reply.getReviewId());
            prst.setString(2, reply.getReplyId());
            prst.setString(3, reply.getReplyContent());
            prst.setString(4, reply.getUserEmail());

            if (prst.executeUpdate() > 0) {
                conn.commit();
                return reply;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close(conn);
        }
        return null;
    }

    public Reply updateReply(Reply reply) {
        String sql = "UPDATE REPLY SET REPLY_CONTENT=? WHERE REPLY_ID=?";
        Connection conn = getConn();

        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, reply.getReplyContent());
            prst.setString(2, reply.getReplyId());

            if (prst.executeUpdate() > 0) {
                conn.commit();
                return reply;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close(conn);
        }
        return null;
    }

    public boolean deleteReply(String replyId) {
        String sql = "DELETE FROM REPLY WHERE REPLY_ID=?";
        Connection conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);

            prst.setString(1, replyId);

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
