package vn.edu.fpt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.config.DbContext;
import vn.edu.fpt.model.OrderDetail;

public class OrderDetailDao extends DbContext{

    /**
     * Create a new order detail in the database
     * @param orderDetail The order detail object to create
     * @return True if successful, false otherwise
     */
    public boolean createOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO [order_detail] (order_id, product_id, quantity, unit_price, note, " +
                "create_at, update_at, product_option_id, is_feedback) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderDetail.getOrderId());
            ps.setInt(2, orderDetail.getProductId());
            ps.setInt(3, orderDetail.getQuantity());
            ps.setInt(4, orderDetail.getUnitPrice());
            ps.setString(5, orderDetail.getNote());
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

            // Handle potentially null product_option_id
            if (orderDetail.getProductOptionId() != 0) {
                ps.setInt(8, orderDetail.getProductOptionId());
            } else {
                ps.setNull(8, java.sql.Types.INTEGER);
            }

            ps.setInt(9, 0); // Default is_feedback to 0 (not feedback)

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error creating order detail: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Create multiple order details in batch
     * @param orderDetails List of order details to create
     * @return True if all items were created successfully, false otherwise
     */
    public boolean createOrderDetails(List<OrderDetail> orderDetails) {
        String sql = "INSERT INTO [order_detail] (order_id, product_id, quantity, unit_price, note, " +
                "create_at, update_at, product_option_id, is_feedback) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (OrderDetail orderDetail : orderDetails) {
                ps.setInt(1, orderDetail.getOrderId());
                ps.setInt(2, orderDetail.getProductId());
                ps.setInt(3, orderDetail.getQuantity());
                ps.setInt(4, orderDetail.getUnitPrice());
                ps.setString(5, orderDetail.getNote());
                ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

                // Handle potentially null product_option_id
                if (orderDetail.getProductOptionId() != 0) {
                    ps.setInt(8, orderDetail.getProductOptionId());
                } else {
                    ps.setNull(8, java.sql.Types.INTEGER);
                }

                ps.setInt(9, 0); // Default is_feedback to 0 (not feedback)

                ps.addBatch();
            }

            int[] results = ps.executeBatch();
            conn.commit();

            // Check if all items were created successfully
            for (int result : results) {
                if (result <= 0) {
                    return false;
                }
            }

            return true;
        } catch (SQLException e) {
            System.out.println("Error creating order details: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get all order details for a specific order
     * @param orderId The order ID
     * @return List of order details
     */
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT * FROM [order_detail] WHERE order_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderDetailId(rs.getInt("order_detail_id"));
                    orderDetail.setOrderId(rs.getInt("order_id"));
                    orderDetail.setProductId(rs.getInt("product_id"));
                    orderDetail.setQuantity(rs.getInt("quantity"));
                    orderDetail.setUnitPrice(rs.getInt("unit_price"));
                    orderDetail.setNote(rs.getString("note"));
                    orderDetail.setCreateAt(rs.getTimestamp("create_at"));
                    orderDetail.setUpdateAt(rs.getTimestamp("update_at"));
                    orderDetail.setProductOptionId(rs.getInt("product_option_id"));
                    orderDetail.setIsFeedback(rs.getInt("is_feedback"));

                    orderDetails.add(orderDetail);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting order details: " + e.getMessage());
            e.printStackTrace();
        }

        return orderDetails;
    }
}