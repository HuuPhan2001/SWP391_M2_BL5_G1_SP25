/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.dao;

import java.util.ArrayList;
import java.util.List;
import vn.edu.fpt.model.CategoryType;
import vn.edu.fpt.config.DbContext;
import java.sql.*;

/**
 *
 * @author MTTTT
 */
public class CategoryTypeDao {
    public List<CategoryType> getAllCategoryTypes() {
        List<CategoryType> list = new ArrayList<>();
        String query = "SELECT * FROM category_type WHERE status = 1";

        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CategoryType ct = new CategoryType();
                ct.setCategoryTypeId(rs.getInt("category_type_id"));
                ct.setCategoryTypeName(rs.getString("category_type_name"));
                ct.setCategoryTypeDesc(rs.getString("category_type_desc"));
                ct.setStatus(rs.getInt("status"));
                ct.setCreateAt(rs.getTimestamp("create_at"));
                ct.setUpdateAt(rs.getTimestamp("update_at"));
                list.add(ct);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
