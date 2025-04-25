/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.fpt.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import vn.edu.fpt.config.DbContext;
import vn.edu.fpt.model.User;

/**
 *
 * @author laptop Tsuki
 */
public class AccountDAO extends DbContext {

    public User getAccount(String username, String password) {
    String sql = "SELECT *" +
                   "FROM [user] u " +
                   "WHERE u.user_name = ? AND u.user_password = ?";
    try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            
            ResultSet rs = st.executeQuery();
            if(rs.next()){
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
//    try{ 
//        (PreparedStatement st = connection.prepareStatement(sql);
//        
//        ps.setString(1, username);
//        ps.setString(2, password);
//        ResultSet rs = st.executeQuery();
//            if (rs.next()) {
//                User user = new User();
//                user.setUserId(rs.getInt("user_id"));
//                user.setUserName(rs.getString("user_name"));
//                user.setUserEmail(rs.getString("user_email"));
//                user.setUserPassword(rs.getString("user_password"));
//                user.setUserFullName(rs.getString("user_full_name"));
//                user.setUserAvatar(rs.getString("user_avatar"));
//                user.setPhone(rs.getString("phone"));
//                user.setIdentificationNumber(rs.getString("identification_number"));
//                user.setAddress(rs.getString("address"));
//                user.setRoleId(rs.getInt("role_id"));
//                user.setStatus(rs.getInt("status"));
//                return user;
//            
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//
//    return null;
}

    public void addAccount(String username, String password) {
        String out = "Dang ky thanh cong";
        String sql = "INSERT INTO dbo.account VALUES (N'" + username + "', N'" + password + "', 'user')";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateAccount(String username, String newpass) {
        String sql = "UPDATE dbo.account SET password = ? WHERE username = ? ";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, newpass);
            st.setString(2, username);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
