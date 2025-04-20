/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.dao;

import vn.edu.fpt.config.DbContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import vn.edu.fpt.common.Common;
import vn.edu.fpt.common.PaginatedResult;
import vn.edu.fpt.common.Pagination;
import vn.edu.fpt.dto.CategoryDto;
import vn.edu.fpt.model.Category;

/**
 *
 * @author MTTTT
 */
public class CategoryDao extends DbContext {

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM category";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setCategoryTypeId(rs.getInt("category_type_id"));
                c.setCategoryName(rs.getString("category_name"));
                c.setCategoryDesc(rs.getString("category_desc"));
                c.setParent(rs.getInt("parent"));
                c.setCategoryBanner(rs.getString("category_banner"));
                c.setStatus(rs.getInt("status"));
                c.setCreateAt(rs.getTimestamp("create_at"));
                c.setUpdateAt(rs.getTimestamp("update_at"));
                categories.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public Category getCategoryById(int id) {
        Category c = new Category();

        String query = "SELECT * FROM category WHERE category_id = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                c.setCategoryId(rs.getInt("category_id"));
                c.setCategoryTypeId(rs.getInt("category_type_id"));
                c.setCategoryName(rs.getString("category_name"));
                c.setCategoryDesc(rs.getString("category_desc"));
                c.setParent(rs.getInt("parent"));
                c.setCategoryBanner(rs.getString("category_banner"));
                c.setStatus(rs.getInt("status"));
                c.setCreateAt(rs.getTimestamp("create_at"));
                c.setUpdateAt(rs.getTimestamp("update_at"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }

    public PaginatedResult<CategoryDto> listAllCatePaging(Pagination pagination, Integer typeId, Integer status, String text)
            throws SQLException {
        PaginatedResult<CategoryDto> result = new PaginatedResult<>();
        List<CategoryDto> categories = new ArrayList<>();

        StringBuilder countSqlBuilder = new StringBuilder("SELECT COUNT(*) AS total FROM category c JOIN category_type ct ON c.category_type_id = ct.category_type_id WHERE 1=1 AND ct.status = 1 AND c.status <> 2 ");
        StringBuilder sqlBuilder = new StringBuilder("SELECT c.category_id, c.category_type_id, c.category_name, c.category_desc, c.parent, c.category_banner, c.status, c.create_at, c.update_at, ct.category_type_name FROM category c JOIN category_type ct ON c.category_type_id = ct.category_type_id WHERE 1=1 AND ct.status = 1 AND c.status <> 2 ");

        List<Object> params = new ArrayList<>();

        if (text != null && !text.isBlank()) {
            countSqlBuilder.append(" AND LOWER(category_name) LIKE ?");
            sqlBuilder.append(" AND LOWER(category_name) LIKE ?");
            params.add("%" + text.toLowerCase() + "%");
        }

        if (typeId != null && typeId > 0) {
            countSqlBuilder.append(" AND c.category_type_id = ?");
            sqlBuilder.append(" AND c.category_type_id = ?");
            params.add(typeId);
        }

        if (status != null) {
            countSqlBuilder.append(" AND c.status = ?");
            sqlBuilder.append(" AND c.status = ?");
            params.add(status);
        }

        // Sorting
        String validSortColumns = "category_id,category_type_id,category_name,status,create_at";
        String sortColumn = validSortColumns.contains(pagination.getSortBy())
                ? pagination.getSortBy()
                : "category_id";
        String sortDirection = "ASC".equalsIgnoreCase(pagination.getSortDirection()) ? "ASC" : "DESC";

        sqlBuilder.append(" ORDER BY ").append(sortColumn).append(" ").append(sortDirection);
        sqlBuilder.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (Connection conn = getConnection(); PreparedStatement countStmt = Common.prepareStatement(conn, countSqlBuilder.toString(), params)) {

            try (ResultSet countRs = countStmt.executeQuery()) {
                if (countRs.next()) {
                    long totalElements = countRs.getLong("total");
                    pagination.setTotalElements(totalElements);

                }
            }
        }

        params.add(pagination.getOffset());
        params.add(pagination.getSize());

        try (Connection conn = getConnection(); PreparedStatement pstmt = Common.prepareStatement(conn, sqlBuilder.toString(), params)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CategoryDto categoryDto = mapResultSetToCategoryDto(rs);
                    categories.add(categoryDto);
                }
            }
        }

        result.setContent(categories);
        result.setPagination(pagination);
        return result;

    }

    private CategoryDto mapResultSetToCategoryDto(ResultSet rs) throws SQLException {
        CategoryDto c = new CategoryDto();
        c.setCategoryId(rs.getInt("category_id"));
        c.setCategoryTypeId(rs.getInt("category_type_id"));
        c.setCategoryTypeName(rs.getString("category_type_name"));
        c.setCategoryName(rs.getString("category_name"));
        c.setCategoryDesc(rs.getString("category_desc"));
        c.setParent(rs.getInt("parent"));
        c.setCategoryBanner(rs.getString("category_banner"));
        c.setStatus(rs.getInt("status"));
        c.setCreateAt(rs.getTimestamp("create_at"));
        c.setUpdateAt(rs.getTimestamp("update_at"));

        return c;
    }
    
    private Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        Category c = new Category();
        c.setCategoryId(rs.getInt("category_id"));
        c.setCategoryTypeId(rs.getInt("category_type_id"));
        c.setCategoryName(rs.getString("category_name"));
        c.setCategoryDesc(rs.getString("category_desc"));
        c.setParent(rs.getInt("parent"));
        c.setCategoryBanner(rs.getString("category_banner"));
        c.setStatus(rs.getInt("status"));
        c.setCreateAt(rs.getTimestamp("create_at"));
        c.setUpdateAt(rs.getTimestamp("update_at"));

        return c;
    }

    public boolean createCategory(Category category) throws SQLException {
        String sql = "INSERT INTO category (category_type_id, category_name, category_desc, parent, category_banner, status, create_at, update_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, category.getCategoryTypeId());
            pstmt.setString(2, category.getCategoryName());
            pstmt.setString(3, category.getCategoryDesc());
            if (category.getParent() != null) {
                pstmt.setInt(4, category.getParent());
            } else {
                pstmt.setNull(4, java.sql.Types.INTEGER);
            }
            pstmt.setString(5, category.getCategoryBanner());
            pstmt.setInt(6, category.getStatus());
            pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean updateCategory(Category category) throws SQLException {
        String sql = "UPDATE category SET category_type_id = ?, category_name = ?, category_desc = ?, parent = ?, "
                + "category_banner = ?, status = ?, update_at = ? WHERE category_id = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, category.getCategoryTypeId());
            pstmt.setString(2, category.getCategoryName());
            pstmt.setString(3, category.getCategoryDesc());
            if (category.getParent() != null) {
                pstmt.setInt(4, category.getParent());
            } else {
                pstmt.setNull(4, java.sql.Types.INTEGER);
            }
            pstmt.setString(5, category.getCategoryBanner());
            pstmt.setInt(6, category.getStatus());
            pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(8, category.getCategoryId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteCategory(int categoryId) throws SQLException {
        String sql = "UPDATE category SET status = 2 WHERE category_id = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryId);
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean updateCategoryStatus(int categoryId, int status) throws SQLException {
        String sql = "UPDATE category SET status = ?, update_at = ? WHERE category_id = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, status);
            pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(3, categoryId);
            return pstmt.executeUpdate() > 0;
        }
    }
    
    public List<Category> checkNameExist(String name) {
        System.out.println(name);
        List<Category> list = new ArrayList<>();

        String query = "SELECT * FROM category WHERE category_name LIKE ? AND status = 1";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category category = mapResultSetToCategory(rs);
                    list.add(category);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public Category getCategoryByType(int type) {
        Category c = new Category();

        String query = "SELECT * FROM category WHERE category_type_id = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                c.setCategoryId(rs.getInt("category_id"));
                c.setCategoryTypeId(rs.getInt("category_type_id"));
                c.setCategoryName(rs.getString("category_name"));
                c.setCategoryDesc(rs.getString("category_desc"));
                c.setParent(rs.getInt("parent"));
                c.setCategoryBanner(rs.getString("category_banner"));
                c.setStatus(rs.getInt("status"));
                c.setCreateAt(rs.getTimestamp("create_at"));
                c.setUpdateAt(rs.getTimestamp("update_at"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }

}
