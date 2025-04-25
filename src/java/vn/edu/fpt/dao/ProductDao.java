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
import vn.edu.fpt.model.Product;
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

        StringBuilder countSqlBuilder = new StringBuilder("SELECT COUNT(*) AS total FROM product WHERE 1=1 AND status <> 2 ");
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM product WHERE 1=1 AND status <> 2 ");

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

        params.add(pagination.getOffset());
        params.add(pagination.getSize());

        try (Connection conn = getConnection(); PreparedStatement pstmt = Common.prepareStatement(conn, sqlBuilder.toString(), params)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ProductDto dto = new ProductDto();
                    dto.setProductId(rs.getInt("product_id"));
                    dto.setProductName(rs.getString("product_name"));
                    dto.setProductAvatar(rs.getString("product_avatar"));
                    dto.setProductDesc(rs.getString("product_desc"));
                    dto.setProductPrice(rs.getBigDecimal("product_price"));
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

    public List<ProductCategoryDto> getCategoryListByProductId(int product_id) {
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

    public List<ProductImage> getImageListByProductId(int product_id) {
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

    public void createProduct(Product product, List<Integer> categoryIds, List<String> imageUrls) throws SQLException {
        String insertProductSql = "INSERT INTO product (product_name, product_avatar, product_desc, product_price, product_quantity, status, create_at, update_at, weight, length, width, height) VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?, ?)";
        String insertProductCategorySql = "INSERT INTO product_category (product_id, category_id) VALUES (?, ?)";
        String insertProductImageSql = "INSERT INTO product_image (product_id, product_image, create_at, update_at) VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(insertProductSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, product.getProductName());
                ps.setString(2, product.getProductAvatar());
                ps.setString(3, product.getProductDesc());
                ps.setObject(4, product.getProductPrice());
                ps.setObject(5, product.getProductQuantity());
                ps.setInt(6, product.getStatus());
                ps.setBigDecimal(7, product.getWeight());
                ps.setBigDecimal(8, product.getLength());
                ps.setBigDecimal(9, product.getWidth());
                ps.setBigDecimal(10, product.getHeight());

                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int generatedProductId = rs.getInt(1);

                    try (PreparedStatement psCat = conn.prepareStatement(insertProductCategorySql)) {
                        for (Integer catId : categoryIds) {
                            psCat.setInt(1, generatedProductId);
                            psCat.setInt(2, catId);
                            psCat.addBatch();
                        }
                        psCat.executeBatch();
                    }

                    try (PreparedStatement psImg = conn.prepareStatement(insertProductImageSql)) {
                        for (String url : imageUrls) {
                            psImg.setInt(1, generatedProductId);
                            psImg.setString(2, url);
                            psImg.addBatch();
                        }
                        psImg.executeBatch();
                    }
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void updateProduct(Product product, List<Integer> categoryIds, List<String> imageUrls) throws SQLException {
        String updateProductSql = "UPDATE product SET product_name = ?, product_avatar = ?, product_desc = ?, product_price = ?, product_quantity = ?, status = ?, update_at = CURRENT_TIMESTAMP, weight = ?, length = ?, width = ?, height = ? WHERE product_id = ?";
        String deleteOldCategorySql = "DELETE FROM product_category WHERE product_id = ?";
        String insertProductCategorySql = "INSERT INTO product_category (product_id, category_id) VALUES (?, ?)";
        String deleteOldImageSql = "DELETE FROM product_image WHERE product_id = ?";
        String insertProductImageSql = "INSERT INTO product_image (product_id, product_image, create_at, update_at) VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(updateProductSql)) {
                ps.setString(1, product.getProductName());
                ps.setString(2, product.getProductAvatar());
                ps.setString(3, product.getProductDesc());
                ps.setObject(4, product.getProductPrice());
                ps.setObject(5, product.getProductQuantity());
                ps.setInt(6, product.getStatus());
                ps.setBigDecimal(7, product.getWeight());
                ps.setBigDecimal(8, product.getLength());
                ps.setBigDecimal(9, product.getWidth());
                ps.setBigDecimal(10, product.getHeight());
                ps.setInt(11, product.getProductId());

                ps.executeUpdate();
            }

            if (categoryIds != null && !categoryIds.isEmpty()) {
                try (PreparedStatement psDel = conn.prepareStatement(deleteOldCategorySql)) {
                    psDel.setInt(1, product.getProductId());
                    psDel.executeUpdate();
                }

                try (PreparedStatement psIns = conn.prepareStatement(insertProductCategorySql)) {
                    for (Integer catId : categoryIds) {
                        psIns.setInt(1, product.getProductId());
                        psIns.setInt(2, catId);
                        psIns.addBatch();
                    }
                    psIns.executeBatch();
                }
            }

            if (imageUrls != null && !imageUrls.isEmpty()) {
                try (PreparedStatement psDelImg = conn.prepareStatement(deleteOldImageSql)) {
                    psDelImg.setInt(1, product.getProductId());
                    psDelImg.executeUpdate();
                }

                try (PreparedStatement psInsImg = conn.prepareStatement(insertProductImageSql)) {
                    for (String url : imageUrls) {
                        psInsImg.setInt(1, product.getProductId());
                        psInsImg.setString(2, url);
                        psInsImg.addBatch();
                    }
                    psInsImg.executeBatch();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void softDeleteProduct(int productId) throws SQLException {
        String sql = "UPDATE product SET status = 2, update_at = CURRENT_TIMESTAMP WHERE product_id = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.executeUpdate();
        }
    }

    public Product getProductById(int productId) {
        Product pro = new Product();

        String query = "SELECT * FROM product WHERE product_id = ? AND status <> 2";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pro.setProductId(rs.getInt("product_id"));
                pro.setProductName(rs.getString("product_name"));
                pro.setProductAvatar(rs.getString("product_avatar"));
                pro.setProductDesc(rs.getString("product_desc"));
                pro.setProductPrice(rs.getBigDecimal("product_price"));
                pro.setProductQuantity((Integer) rs.getObject("product_quantity"));
                pro.setStatus(rs.getInt("status"));
                pro.setCreateAt(rs.getTimestamp("create_at"));
                pro.setUpdateAt(rs.getTimestamp("update_at"));
                pro.setWeight(rs.getBigDecimal("weight"));
                pro.setLength(rs.getBigDecimal("length"));
                pro.setWidth(rs.getBigDecimal("width"));
                pro.setHeight(rs.getBigDecimal("height"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pro;
    }

}
