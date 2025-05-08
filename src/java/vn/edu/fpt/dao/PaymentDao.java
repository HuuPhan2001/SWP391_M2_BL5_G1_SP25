package vn.edu.fpt.dao;

import vn.edu.fpt.model.Payment;
import vn.edu.fpt.model.PaymentMethod;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import vn.edu.fpt.config.DbContext;

public class PaymentDao {
    // Lấy danh sách phương thức thanh toán
    public List<PaymentMethod> getAllPaymentMethods() {
        List<PaymentMethod> methods = new ArrayList<>();
        String sql = "SELECT payment_method_id, payment_method_name FROM payment_method";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PaymentMethod method = new PaymentMethod();
                method.setPaymentMethodId(rs.getInt("payment_method_id"));
                method.setPaymentMethodName(rs.getString("payment_method_name"));
                methods.add(method);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return methods;
    }

    // Tạo mới payment
    public boolean createPayment(Payment payment) {
        String sql = "INSERT INTO payment (payment_method_id, status, amount, order_id, create_at, update_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, payment.getPaymentMethodId());
            ps.setString(2, payment.getStatus());
            ps.setInt(3, payment.getAmount());
            ps.setInt(4, payment.getOrderId());
            ps.setTimestamp(5, payment.getCreateAt());
            ps.setTimestamp(6, payment.getUpdateAt());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
} 