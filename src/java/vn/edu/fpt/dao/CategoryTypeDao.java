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
import vn.edu.fpt.common.Common;

/**
 *
 * @author MTTTT
 */
public class CategoryTypeDao extends DbContext {

    public List<CategoryType> getAllCategoryTypes() {
        List<CategoryType> list = new ArrayList<>();
        String query = "SELECT * FROM category_type WHERE status = 1";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CategoryType ct = mapResultSetToCategoryType(rs);
                list.add(ct);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<CategoryType> getAllCategoryTypesNonCheck(Integer status, String text) throws SQLException {
        List<CategoryType> list = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM category_type WHERE 1=1 AND status <> 2 ");
        List<Object> params = new ArrayList<>();

        if (text != null && !text.isBlank()) {
            sqlBuilder.append(" AND LOWER(category_type_name) LIKE ?");
            params.add("%" + text.toLowerCase() + "%");
        }

        if (status != null) {
            sqlBuilder.append(" AND status = ?");
            params.add(status);
        }
        try (Connection conn = getConnection(); PreparedStatement ps = Common.prepareStatement(conn, sqlBuilder.toString(), params)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CategoryType ct = mapResultSetToCategoryType(rs);
                    list.add(ct);
                }
            }
        }

        return list;
    }

    public boolean createCategoryType(CategoryType categoryType) throws SQLException {
        String sql = "INSERT INTO category_type (category_type_name, category_type_desc, status) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, categoryType.getCategoryTypeName());
            ps.setString(2, categoryType.getCategoryTypeDesc());
            ps.setInt(3, categoryType.getStatus());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateCategoryType(CategoryType categoryType) throws SQLException {
        String sql = "UPDATE category_type SET category_type_name = ?, category_type_desc = ?,status = ?, update_at = ? WHERE category_type_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, categoryType.getCategoryTypeName());
            ps.setString(2, categoryType.getCategoryTypeDesc());
            ps.setInt(3, categoryType.getStatus());
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            ps.setInt(5, categoryType.getCategoryTypeId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateCategoryTypeStatus(int typeId, int status) throws SQLException {
        String sql = "UPDATE category_type SET status = ?, update_at = ? WHERE category_type_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setInt(3, typeId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean softDeleteCategoryType(int typeId) throws SQLException {
        String sql = "UPDATE category_type SET status = 2, update_at = ? WHERE category_type_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            ps.setInt(2, typeId);
            return ps.executeUpdate() > 0;
        }
    }

    public CategoryType getById(int typeId) throws SQLException {
        String sql = "SELECT * FROM category_type WHERE category_type_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, typeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCategoryType(rs);
                }
            }
        }
        return null;
    }

    public CategoryType getByName(String typeName) throws SQLException {
        String sql = "SELECT * FROM category_type WHERE LOWER(category_type_name) = ? AND status = 1";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, typeName.toLowerCase());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCategoryType(rs);
                }
            }
        }
        return null;
    }

    private CategoryType mapResultSetToCategoryType(ResultSet rs) throws SQLException {
        CategoryType type = new CategoryType();
        type.setCategoryTypeId(rs.getInt("category_type_id"));
        type.setCategoryTypeName(rs.getString("category_type_name"));
        type.setCategoryTypeDesc(rs.getString("category_type_desc"));
        type.setStatus(rs.getInt("status"));
        type.setCreateAt(rs.getTimestamp("create_at"));
        type.setUpdateAt(rs.getTimestamp("update_at"));
        return type;
    }

    public List<CategoryType> checkNameExist(String name) {
        System.out.println(name);
        List<CategoryType> list = new ArrayList<>();

        String query = "SELECT * FROM category_type WHERE category_type_name LIKE ? AND status = 1";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CategoryType category = mapResultSetToCategoryType(rs);
                    list.add(category);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
