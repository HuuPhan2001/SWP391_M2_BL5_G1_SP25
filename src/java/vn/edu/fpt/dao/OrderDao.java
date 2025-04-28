package vn.edu.fpt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import vn.edu.fpt.config.DbContext;
import vn.edu.fpt.model.Order;
import vn.edu.fpt.model.OrderDetail;

public class OrderDao extends DbContext {


    public int createOrder(Order order) {
        String sql = "INSERT INTO [order] (user_id, shop_id, order_total, order_status, payment_status, " +
                "address, district, ward, phone_receiver, name_receiver, ship_cost, payment_method_id, " +
                "create_at, update_at, province, order_code, discount) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Generate a unique order code
            String orderCode = generateOrderCode();

            // Set order parameters
            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getShopId());
            ps.setInt(3, order.getOrderTotal());
            ps.setString(4, order.getOrderStatus());
            ps.setInt(5, order.getPaymentStatus());
            ps.setString(6, order.getAddress());
            ps.setString(7, order.getDistrict());
            ps.setString(8, order.getWard());
            ps.setString(9, order.getPhoneReceiver());
            ps.setString(10, order.getNameReceiver());
            ps.setInt(11, order.getShipCost());
            ps.setInt(12, order.getPaymentMethodId());
            ps.setTimestamp(13, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(14, new Timestamp(System.currentTimeMillis()));
            ps.setString(15, order.getProvince());
            ps.setString(16, orderCode);
            ps.setInt(17, order.getDiscount());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error creating order: " + e.getMessage());
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Generate a unique order code
     * @return A unique order code
     */
    private String generateOrderCode() {
        // Generate a unique ID using UUID and timestamp
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
        return "ORD-" + uuid.toUpperCase();
    }


    /**
     * Update an order's payment status
     * @param orderId The order ID
     * @param paymentStatus The new payment status
     * @return True if successful, false otherwise
     */
    public boolean updatePaymentStatus(int orderId, int paymentStatus) {
        String sql = "UPDATE [order] SET payment_status = ?, update_at = ? WHERE order_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, paymentStatus);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setInt(3, orderId);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating payment status: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public Order getOrderById(int orderId) throws SQLException {
        String sql = "SELECT o.*, u.user_name FROM [order] o LEFT JOIN [user] u ON o.user_id = u.user_id WHERE o.order_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getObject("user_id") != null ? rs.getInt("user_id") : null);
                order.setShopId(rs.getObject("shop_id") != null ? rs.getInt("shop_id") : null);
                order.setOrderTotal(rs.getObject("order_total") != null ? rs.getInt("order_total") : null);
                order.setOrderStatus(rs.getString("order_status"));
                order.setPaymentStatus(rs.getInt("payment_status"));
                order.setAddress(rs.getString("address"));
                order.setDistrict(rs.getString("district"));
                order.setWard(rs.getString("ward"));
                order.setPhoneReceiver(rs.getString("phone_receiver"));
                order.setNameReceiver(rs.getString("name_receiver"));
                order.setShipCost(rs.getInt("ship_cost"));
                order.setPaymentMethodId(rs.getInt("payment_method_id"));
                order.setCreateAt(rs.getTimestamp("create_at"));
                order.setUpdateAt(rs.getTimestamp("update_at"));
                order.setProvince(rs.getString("province"));
                order.setOrderCode(rs.getString("order_code"));
                order.setDiscount(rs.getObject("discount") != null ? rs.getInt("discount") : null);
                order.setUserName(rs.getString("user_name"));
                order.setOrderDetails(getOrderDetailsByOrderId(orderId));
                return order;
            }
        }
        return null;
    }

    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) throws SQLException {
        List<OrderDetail> details = new ArrayList<>();
        String sql = "SELECT od.*, p.product_name, p.product_avatar FROM order_detail od LEFT JOIN product p ON od.product_id = p.product_id WHERE od.order_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                OrderDetail detail = new OrderDetail();
                detail.setOrderDetailId(rs.getInt("order_detail_id"));
                detail.setOrderId(rs.getObject("order_id") != null ? rs.getInt("order_id") : null);
                detail.setProductId(rs.getObject("product_id") != null ? rs.getInt("product_id") : null);
                detail.setQuantity(rs.getObject("quantity") != null ? rs.getInt("quantity") : null);
                detail.setUnitPrice(rs.getObject("unit_price") != null ? rs.getInt("unit_price") : null);
                detail.setNote(rs.getString("note"));
                detail.setCreateAt(rs.getTimestamp("create_at"));
                detail.setUpdateAt(rs.getTimestamp("update_at"));
                detail.setProductOptionId(rs.getObject("product_option_id") != null ? rs.getInt("product_option_id") : null);
                detail.setIsFeedback(rs.getObject("is_feedback") != null ? rs.getInt("is_feedback") : null);
                detail.setProductName(rs.getString("product_name"));
                detail.setProductAvatar(rs.getString("product_avatar"));
                details.add(detail);
            }
        }
        return details;
    }

    public List<Order> getAllOrders(String text, String status, Integer userId, int page, int pageSize) throws SQLException {
        List<Order> orders = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT o.*, u.user_name FROM [order] o LEFT JOIN [user] u ON o.user_id = u.user_id WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (text != null && !text.trim().isEmpty()) {
            sql.append(" AND o.order_code LIKE ?");
            params.add("%" + text + "%");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND o.order_status = ?");
            params.add(status);
        }
        if (userId != null) {
            sql.append(" AND o.user_id = ?");
            params.add(userId);
        }

        sql.append(" ORDER BY o.order_id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        params.add((page - 1) * pageSize);
        params.add(pageSize);

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getObject("user_id") != null ? rs.getInt("user_id") : null);
                order.setShopId(rs.getObject("shop_id") != null ? rs.getInt("shop_id") : null);
                order.setOrderTotal(rs.getObject("order_total") != null ? rs.getInt("order_total") : null);
                order.setOrderStatus(rs.getString("order_status"));
                order.setPaymentStatus(rs.getInt("payment_status"));
                order.setAddress(rs.getString("address"));
                order.setDistrict(rs.getString("district"));
                order.setWard(rs.getString("ward"));
                order.setPhoneReceiver(rs.getString("phone_receiver"));
                order.setNameReceiver(rs.getString("name_receiver"));
                order.setShipCost(rs.getInt("ship_cost"));
                order.setPaymentMethodId(rs.getInt("payment_method_id"));
                order.setCreateAt(rs.getTimestamp("create_at"));
                order.setUpdateAt(rs.getTimestamp("update_at"));
                order.setProvince(rs.getString("province"));
                order.setOrderCode(rs.getString("order_code"));
                order.setDiscount(rs.getObject("discount") != null ? rs.getInt("discount") : null);
                order.setUserName(rs.getString("user_name"));
                orders.add(order);
            }
        }
        return orders;
    }

    public int getTotalOrders(String text, String status, Integer userId) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM [order] o WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (text != null && !text.trim().isEmpty()) {
            sql.append(" AND o.order_code LIKE ?");
            params.add("%" + text + "%");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND o.order_status = ?");
            params.add(status);
        }
        if (userId != null) {
            sql.append(" AND o.user_id = ?");
            params.add(userId);
        }

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public void updateOrderStatus(int orderId, String status) throws SQLException {
        String sql = "UPDATE [order] SET order_status = ?, update_at = GETDATE() WHERE order_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, orderId);
            statement.executeUpdate();
        }
    }
}