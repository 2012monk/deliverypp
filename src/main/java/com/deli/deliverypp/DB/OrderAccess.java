package com.deli.deliverypp.DB;

import com.deli.deliverypp.model.OrderInfo;
import com.deli.deliverypp.model.OrderList;
import com.deli.deliverypp.model.Product;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.deli.deliverypp.DB.ConnHandler.close;
import static com.deli.deliverypp.DB.ConnHandler.getConn;
import static com.deli.deliverypp.util.DBUtil.*;

// NOTE 어떻게 반복적인 코드를 줄일까? Connection preparedStatement
public class OrderAccess {

    public List<OrderInfo> getOrderListByUser (String userEmail) {
        return getOrderList("userEmail", userEmail);
    }

    public List<OrderInfo> getOrderListByStore (String storeId) {
        return getOrderList("storeId", storeId);
    }


    private List<OrderInfo> getOrderList (String key, String value) {
        List<OrderInfo> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        Connection conn = getConn();
        sql.append("SELECT * FROM").append(" ")
                .append(convertClassNameToDbName(OrderInfo.class.getSimpleName()))
                .append(" ")
                .append("WHERE")
                .append(" ")
                .append(convertToDbNameConvention(key))
                .append("=")
                .append("'")
                .append(value)
                .append("'");
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql.toString());
            while (rs.next()){
                OrderInfo info = setPOJO(OrderInfo.class, rs);
                System.out.println(info);
                List<Product> orderList = getOrderList(info.getOrderId());
                info.setOrderList(orderList);
                list.add(info);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(conn);
        }
        return null;
    }


    public boolean makeNewOrder(OrderInfo info) {
        String sql = "INSERT INTO ORDER_INFO (ORDER_ID, TID, PAYMENT_TYPE, STORE_ID, STORE_NAME, TOTAL_AMOUNT, TOTAL_PRICE) " +
                "VALUES (?,?,?,?,?,?,?)";

        System.out.println(info+" from access 26");
        ArrayList<String> val = new ArrayList<String>(){{
            add(info.getOrderId());
            add(info.getTid());
            add(info.getPaymentType());
            add(info.getStoreId());
            add(info.getStoreName());
            add(info.getQuantityString());
            add(info.getTotalPriceString());
        }};
        Connection conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            for (int i=1; i<=val.size();i++) {
                prst.setString(i, val.get(i-1));
            }
            if (prst.executeUpdate() > 0) {
                conn.commit();
                close(conn);
                for (Product p: info.getOrderList()) {
                    insertToOrderList(info.getOrderId(), p.getProductId(), p.getQuantity());
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(conn);
        }
        return false;
    }


    public boolean insertToOrderList (String orderId, String productId, int quantity) {
        String sql = "INSERT INTO ORDER_LIST (ORDER_ID, PRODUCT_ID, QUANTITY) VALUES (?,?,?)";
        OrderList orderList = new OrderList(orderId,productId,quantity);
        Connection conn = getConn();
        try {
            PreparedStatement prst =(PreparedStatement) makeInsertStatement(orderList, conn);
            if (prst.executeUpdate() > 0){
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

    public List<Product> getOrderList (String orderId) {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT PRODUCT.*, QUANTITY, ORDER_ID FROM PRODUCT JOIN ORDER_LIST OL ON PRODUCT.PRODUCT_ID=OL.PRODUCT_ID WHERE ORDER_ID=?";
        Connection conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, orderId);
            ResultSet rs = prst.executeQuery();

            while (rs.next()) {
                list.add(setPOJO(Product.class, rs));
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(conn);
        }
        return null;
    }


    private OrderInfo getOrderInfo (String key, String subSql) {
        String sql = "SELECT * FROM ORDER_INFO WHERE ";
        sql += subSql;

        Connection conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, key);
            ResultSet rs = prst.executeQuery();

            if (rs.next()){
                OrderInfo orderInfo = setPOJO(OrderInfo.class, rs);
                System.out.println(orderInfo);
                try {
                    List<Product> list = getOrderList(orderInfo.getOrderId());
                    orderInfo.setOrderList(list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return orderInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close(conn);
        }
        return null;
    }

    public OrderInfo getOrderInfoByOrderId (String id) {
        return getOrderInfo(id, "ORDER_ID=?");
    }


    public OrderInfo getOrderInfoByTid (String tid) {
        return getOrderInfo(tid, "TID=?");
    }





    private boolean updateOrderState(String state, String subSql, String key) {
        String sql = "UPDATE ORDER_INFO SET ORDER_STATE=? WHERE ";
        sql += subSql;

        Connection conn = getConn();
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, state);
            prst.setString(2, key);

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


    // NOTE UPDATE orderState

    public boolean makeOrderSuccessByTid(String tid) {
        return updateOrderState(OrderInfo.OrderState.PAYMENT_SUCCESS.name(), "TID=?", tid);
    }

    public boolean makeOrderSuccessByOrderId(String orderId) {
        return updateOrderState(OrderInfo.OrderState.PAYMENT_SUCCESS.name(), "ORDER_ID=?", orderId);
    }

    public boolean makeOrderDoneByOrderID (String orderId) {
        return updateOrderState(OrderInfo.OrderState.DONE.name(), "ORDER_ID=?", orderId);
    }

    public boolean makeOrderDoneByTid (String tid) {
        return updateOrderState(OrderInfo.OrderState.DONE.name(), "TID=?", tid);
    }

    public boolean makeOrderFailedByOrderId (String orderId) {
        return updateOrderState(OrderInfo.OrderState.PAYMENT_FAILED.name(), "ORDER_ID", orderId);
    }

    public boolean makeOrderFailedByTid (String tid) {
        return updateOrderState(OrderInfo.OrderState.PAYMENT_FAILED.name(), "TID=?", tid);
    }



    public boolean makeOrderFailed (OrderInfo info) {
//        return updateOrderState(OrderInfo.OrderState.PAYMENT_FAILED.name(), )
        return false;
    }

    public boolean makeOrderDone (OrderInfo info) {
        return false;
    }




}
