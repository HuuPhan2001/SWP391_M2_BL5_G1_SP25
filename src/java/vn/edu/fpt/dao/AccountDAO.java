/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.fpt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import vn.edu.fpt.config.DbContext;
import static vn.edu.fpt.config.DbContext.getConnection;
import vn.edu.fpt.model.User;

/**
 *
 * @author laptop Tsuki
 */
public class AccountDAO extends DbContext {

    public User getAccount(String username, String password) {
        String sql = "SELECT *"
                + "FROM [user] u "
                + "WHERE u.user_name = ? AND u.user_password = ?";
        try (Connection conn = getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, username);
            st.setString(2, password);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setUserEmail(rs.getString("user_email"));
                user.setUserPassword(rs.getString("user_password"));
                user.setUserFullName(rs.getString("user_full_name"));
                user.setUserAvatar(rs.getString("user_avatar"));
                user.setPhone(rs.getString("phone"));
                user.setIdentificationNumber(rs.getString("identification_number"));
                user.setAddress(rs.getString("address"));
                user.setRoleId(rs.getInt("role_id"));
                user.setStatus(rs.getInt("status"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean addAccount(User user) {
        String sql = "INSERT INTO [user] (user_name, user_email, user_password, phone, status, role_id, create_at, update_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getUserEmail());
            ps.setString(3, user.getUserPassword());
            ps.setString(4, user.getPhone());
            ps.setInt(5, user.getStatus());
            ps.setObject(6, user.getRoleId());
            ps.setTimestamp(7, user.getCreateAt());
            ps.setTimestamp(8, user.getUpdateAt());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT user_id FROM [user] WHERE user_email = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUsernameExists(String username) {
        String sql = "SELECT user_id FROM [user] WHERE user_name = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserAccount(int userId) {
        String sql = "SELECT * FROM [user] WHERE user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setUserEmail(rs.getString("user_email"));
                user.setUserPassword(rs.getString("user_password"));
                user.setUserFullName(rs.getString("user_full_name"));
                user.setUserAvatar(rs.getString("user_avatar"));
                user.setPhone(rs.getString("phone"));
                user.setIdentificationNumber(rs.getString("identification_number"));
                user.setForgotPasswordCode(rs.getString("forgot_password_code"));
                user.setAddress(rs.getString("address"));
                user.setRoleId(rs.getInt("role_id"));
                user.setStatus(rs.getInt("status"));
                user.setCreateAt(rs.getTimestamp("create_at"));
                user.setUpdateAt(rs.getTimestamp("update_at"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE [user] SET user_full_name = ?, user_email = ?, phone = ?,"
                + " address = ?, user_avatar = ?, identification_number = ?, update_at = ? WHERE user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUserFullName());
            ps.setString(2, user.getUserEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getAddress());
            ps.setString(5, user.getUserAvatar());
            ps.setString(6, user.getIdentificationNumber());
            ps.setTimestamp(7, user.getUpdateAt());
            ps.setInt(8, user.getUserId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePassword(int userId, String newPassword) {
        String sql = "UPDATE [user] SET user_password = ?, update_at = GETDATE() WHERE user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkOldPassword(int userId, String oldPassword) {
        String sql = "SELECT user_id FROM [user] WHERE user_id = ? AND user_password = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, oldPassword);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Trả về true nếu tìm thấy bản ghi khớp
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
