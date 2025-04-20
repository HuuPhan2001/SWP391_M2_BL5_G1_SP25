/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.dao;

import vn.edu.fpt.common.Pagination;
import vn.edu.fpt.config.DbContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import vn.edu.fpt.common.Common;
import vn.edu.fpt.common.PaginatedResult;
import vn.edu.fpt.dto.ProductCategoryDto;
import vn.edu.fpt.dto.ProductDto;
import vn.edu.fpt.model.ProductImage;

/**
 *
 * @author MTTTT
 */
public class ProductDao extends DbContext {

    public PaginatedResult<ProductDto> listAllProductPaging(Pagination pagination, Integer status, String text, Integer minPrice, Integer maxPrice, List<Integer> category_ids)
            throws SQLException {
        PaginatedResult<ProductDto> result = new PaginatedResult<>();
        List<ProductDto> products = new ArrayList<>();

        StringBuilder countSqlBuilder = new StringBuilder("SELECT COUNT(*) AS total FROM product WHERE 1=1 ");
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM product WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (text != null && !text.isBlank()) {
            countSqlBuilder.append(" AND LOWER(product_name) LIKE ?");
            sqlBuilder.append(" AND LOWER(product_name) LIKE ?");
            params.add("%" + text.toLowerCase() + "%");
        }

        if (status != null) {
            countSqlBuilder.append(" AND status = ?");
            sqlBuilder.append(" AND status = ?");
            params.add(status);
        }

        if (minPrice != null) {
            countSqlBuilder.append(" AND product_price >= ?");
            sqlBuilder.append(" AND product_price >= ?");
            params.add(minPrice);
        }

        if (maxPrice != null) {
            countSqlBuilder.append(" AND product_price <= ?");
            sqlBuilder.append(" AND product_price <= ?");
            params.add(maxPrice);
        }

        if (!category_ids.isEmpty()) {
            String inClause = category_ids.stream()
                    .map(id -> "?")
                    .collect(Collectors.joining(", "));
            String condition = " AND product_id IN (SELECT product_id FROM product_category WHERE category_id IN (" + inClause + "))";
            countSqlBuilder.append(condition);
            sqlBuilder.append(condition);
            params.addAll(category_ids);
        }
        
        // Sorting
        String validSortColumns = "product_id,product_name,status,create_at,update_at";
        String sortColumn = validSortColumns.contains(pagination.getSortBy()) ? pagination.getSortBy() : "product_id";
        String sortDirection = "ASC".equalsIgnoreCase(pagination.getSortDirection()) ? "ASC" : "DESC";

        sqlBuilder.append(" ORDER BY ").append(sortColumn).append(" ").append(sortDirection);
        sqlBuilder.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        // Total count
        try (Connection conn = getConnection(); PreparedStatement countStmt = Common.prepareStatement(conn, countSqlBuilder.toString(), params)) {

            try (ResultSet countRs = countStmt.executeQuery()) {
                if (countRs.next()) {
                    long totalElements = countRs.getLong("total");
                    pagination.setTotalElements(totalElements);
                }
            }
        }

        params.add(pagination.getOffset() - 1);
        params.add(pagination.getSize());

        try (Connection conn = getConnection(); PreparedStatement pstmt = Common.prepareStatement(conn, sqlBuilder.toString(), params)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ProductDto dto = new ProductDto();
                    dto.setProductId(rs.getInt("product_id"));
                    dto.setProductName(rs.getString("product_name"));
                    dto.setProductAvatar(rs.getString("product_avatar"));
                    dto.setProductDesc(rs.getString("product_desc"));
                    dto.setProductPrice((Integer) rs.getObject("product_price"));
                    dto.setProductQuantity((Integer) rs.getObject("product_quantity"));
                    dto.setStatus(rs.getInt("status"));
                    dto.setCreateAt(rs.getTimestamp("create_at"));
                    dto.setUpdateAt(rs.getTimestamp("update_at"));
                    dto.setWeight(rs.getBigDecimal("weight"));
                    dto.setLength(rs.getBigDecimal("length"));
                    dto.setWidth(rs.getBigDecimal("width"));
                    dto.setHeight(rs.getBigDecimal("height"));

                    dto.setCategoryList(getCategoryListByProductId(dto.getProductId()));
                    dto.setImages(getImageListByProductId(dto.getProductId()));

                    products.add(dto);
                }
            }
        }

        result.setContent(products);
        result.setPagination(pagination);
        return result;
    }

    private List<ProductCategoryDto> getCategoryListByProductId(int product_id) {
        List<ProductCategoryDto> list = new ArrayList<>();
        String sql = "SELECT pc.product_id, p.product_name, pc.product_category_id, pc.category_id, c.category_name\n"
                + "    FROM product_category pc\n"
                + "    JOIN product p ON p.product_id = pc.product_id\n"
                + "    JOIN category c ON c.category_id = pc.category_id\n"
                + "    WHERE pc.product_id = ?\n";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, product_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductCategoryDto dto = new ProductCategoryDto();
                dto.setProductId(rs.getInt("product_id"));
                dto.setProductName(rs.getString("product_name"));
                dto.setProductCategoryId(rs.getInt("product_category_id"));
                dto.setCategoryId(rs.getInt("category_id"));
                dto.setCategoryName(rs.getString("category_name"));
                list.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private List<ProductImage> getImageListByProductId(int product_id) {
        List<ProductImage> list = new ArrayList<>();
        String sql = "SELECT * FROM product_image WHERE product_id = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, product_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductImage img = new ProductImage();
                img.setProductImageId(rs.getInt("product_image_id"));
                img.setProductId(rs.getInt("product_id"));
                img.setProductImage(rs.getString("product_image"));
                img.setCreateAt(rs.getTimestamp("create_at"));
                img.setUpdateAt(rs.getTimestamp("update_at"));
                list.add(img);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
