package vn.edu.fpt.dao;

import vn.edu.fpt.config.DbContext;
import vn.edu.fpt.model.Voucher;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoucherDao extends DbContext {

    public void insertVoucher(Voucher voucher) throws SQLException {
        String sql = "INSERT INTO voucher (code, description, discount_amount, min_order_amount, start_date, end_date, quantity, product_id, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, voucher.getCode());
            statement.setString(2, voucher.getDescription());
            if (voucher.getDiscountAmount() != null) {
                statement.setInt(3, voucher.getDiscountAmount());
            } else {
                statement.setNull(3, Types.INTEGER);
            }
            if (voucher.getMinOrderAmount() != null) {
                statement.setInt(4, voucher.getMinOrderAmount());
            } else {
                statement.setNull(4, Types.INTEGER);
            }
            statement.setTimestamp(5, voucher.getStartDate());
            statement.setTimestamp(6, voucher.getEndDate());
            if (voucher.getQuantity() != null) {
                statement.setInt(7, voucher.getQuantity());
            } else {
                statement.setNull(7, Types.INTEGER);
            }
            if (voucher.getProductId() != null) {
                statement.setInt(8, voucher.getProductId());
            } else {
                statement.setNull(8, Types.INTEGER);
            }
            statement.setInt(9, voucher.getStatus());
            statement.executeUpdate();
        }
    }

    public Voucher getVoucherById(int voucherId) throws SQLException {
        String sql = "SELECT * FROM voucher WHERE voucher_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, voucherId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Voucher voucher = new Voucher();
                voucher.setVoucherId(rs.getInt("voucher_id"));
                voucher.setCode(rs.getString("code"));
                voucher.setDescription(rs.getString("description"));
                voucher.setDiscountAmount(rs.getObject("discount_amount") != null ? rs.getInt("discount_amount") : null);
                voucher.setMinOrderAmount(rs.getObject("min_order_amount") != null ? rs.getInt("min_order_amount") : null);
                voucher.setStartDate(rs.getTimestamp("start_date"));
                voucher.setEndDate(rs.getTimestamp("end_date"));
                voucher.setQuantity(rs.getObject("quantity") != null ? rs.getInt("quantity") : null);
                voucher.setProductId(rs.getObject("product_id") != null ? rs.getInt("product_id") : null);
                voucher.setStatus(rs.getObject("status") != null ? rs.getInt("status") : null);
                voucher.setCreatedAt(rs.getTimestamp("created_at"));
                voucher.setUpdatedAt(rs.getTimestamp("updated_at"));
                return voucher;
            }
        }
        return null;
    }

    public List<Voucher> getAllVouchers(String text, Integer status, Integer minDiscount, Integer maxDiscount, int page, int pageSize) throws SQLException {
        List<Voucher> vouchers = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM voucher WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (text != null && !text.trim().isEmpty()) {
            sql.append(" AND code LIKE ?");
            params.add("%" + text + "%");
        }
        if (status != null) {
            sql.append(" AND status = ?");
            params.add(status);
        }
        if (minDiscount != null) {
            sql.append(" AND (discount_amount >= ? OR discount_amount IS NULL)");
            params.add(minDiscount);
        }
        if (maxDiscount != null) {
            sql.append(" AND (discount_amount <= ? OR discount_amount IS NULL)");
            params.add(maxDiscount);
        }

        sql.append(" ORDER BY voucher_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        params.add((page - 1) * pageSize);
        params.add(pageSize);

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Voucher voucher = new Voucher();
                voucher.setVoucherId(rs.getInt("voucher_id"));
                voucher.setCode(rs.getString("code"));
                voucher.setDescription(rs.getString("description"));
                voucher.setDiscountAmount(rs.getObject("discount_amount") != null ? rs.getInt("discount_amount") : null);
                voucher.setMinOrderAmount(rs.getObject("min_order_amount") != null ? rs.getInt("min_order_amount") : null);
                voucher.setStartDate(rs.getTimestamp("start_date"));
                voucher.setEndDate(rs.getTimestamp("end_date"));
                voucher.setQuantity(rs.getObject("quantity") != null ? rs.getInt("quantity") : null);
                voucher.setProductId(rs.getObject("product_id") != null ? rs.getInt("product_id") : null);
                voucher.setStatus(rs.getObject("status") != null ? rs.getInt("status") : null);
                voucher.setCreatedAt(rs.getTimestamp("created_at"));
                voucher.setUpdatedAt(rs.getTimestamp("updated_at"));
                vouchers.add(voucher);
            }
        }
        return vouchers;
    }

    public List<Voucher> getAvailableVouchers() throws SQLException {
        List<Voucher> vouchers = new ArrayList<>();
        String sql = "SELECT * FROM voucher WHERE status = 1 AND GETDATE() BETWEEN start_date AND end_date";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Voucher voucher = new Voucher();
                voucher.setVoucherId(rs.getInt("voucher_id"));
                voucher.setCode(rs.getString("code"));
                voucher.setDescription(rs.getString("description"));
                voucher.setDiscountAmount(rs.getObject("discount_amount") != null ? rs.getInt("discount_amount") : null);
                voucher.setMinOrderAmount(rs.getObject("min_order_amount") != null ? rs.getInt("min_order_amount") : null);
                voucher.setStartDate(rs.getTimestamp("start_date"));
                voucher.setEndDate(rs.getTimestamp("end_date"));
                voucher.setQuantity(rs.getObject("quantity") != null ? rs.getInt("quantity") : null);
                voucher.setProductId(rs.getObject("product_id") != null ? rs.getInt("product_id") : null);
                voucher.setStatus(rs.getObject("status") != null ? rs.getInt("status") : null);
                voucher.setCreatedAt(rs.getTimestamp("created_at"));
                voucher.setUpdatedAt(rs.getTimestamp("updated_at"));
                vouchers.add(voucher);
            }
        }
        return vouchers;
    }

    public void updateVoucher(Voucher voucher) throws SQLException {
        String sql = "UPDATE voucher SET code = ?, description = ?, discount_amount = ?, min_order_amount = ?, start_date = ?, end_date = ?, quantity = ?, product_id = ?, status = ?, updated_at = GETDATE() WHERE voucher_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, voucher.getCode());
            statement.setString(2, voucher.getDescription());
            if (voucher.getDiscountAmount() != null) {
                statement.setInt(3, voucher.getDiscountAmount());
            } else {
                statement.setNull(3, Types.INTEGER);
            }
            if (voucher.getMinOrderAmount() != null) {
                statement.setInt(4, voucher.getMinOrderAmount());
            } else {
                statement.setNull(4, Types.INTEGER);
            }
            statement.setTimestamp(5, voucher.getStartDate());
            statement.setTimestamp(6, voucher.getEndDate());
            if (voucher.getQuantity() != null) {
                statement.setInt(7, voucher.getQuantity());
            } else {
                statement.setNull(7, Types.INTEGER);
            }
            if (voucher.getProductId() != null) {
                statement.setInt(8, voucher.getProductId());
            } else {
                statement.setNull(8, Types.INTEGER);
            }
            statement.setInt(9, voucher.getStatus());
            statement.setInt(10, voucher.getVoucherId());
            statement.executeUpdate();
        }
    }

    public void deleteVoucher(int voucherId) throws SQLException {
        String sql = "DELETE FROM voucher WHERE voucher_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, voucherId);
            statement.executeUpdate();
        }
    }

    public int getTotalVouchers(String text, Integer status, Integer minDiscount, Integer maxDiscount) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM voucher WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (text != null && !text.trim().isEmpty()) {
            sql.append(" AND code LIKE ?");
            params.add("%" + text + "%");
        }
        if (status != null) {
            sql.append(" AND status = ?");
            params.add(status);
        }
        if (minDiscount != null) {
            sql.append(" AND (discount_amount >= ? OR discount_amount IS NULL)");
            params.add(minDiscount);
        }
        if (maxDiscount != null) {
            sql.append(" AND (discount_amount <= ? OR discount_amount IS NULL)");
            params.add(maxDiscount);
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
}