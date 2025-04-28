/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.service;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import vn.edu.fpt.common.Common;
import vn.edu.fpt.common.Constant;
import vn.edu.fpt.common.PaginatedResult;
import vn.edu.fpt.common.Pagination;
import vn.edu.fpt.dao.CategoryDao;
import vn.edu.fpt.dao.CategoryTypeDao;
import vn.edu.fpt.dao.ProductDao;
import vn.edu.fpt.dto.CategoryDto;
import vn.edu.fpt.dto.ProductCategoryDto;
import vn.edu.fpt.dto.ProductDto;
import vn.edu.fpt.model.Category;
import vn.edu.fpt.model.CategoryType;
import vn.edu.fpt.model.Product;
import vn.edu.fpt.model.ProductImage;

/**
 *
 * @author MTTTT
 */
public class ProductService {

    private ProductDao productDao = new ProductDao();
    private CategoryDao categoryDao = new CategoryDao();
    private CategoryTypeDao categoryTypeDao = new CategoryTypeDao();

    public void listAllProductPaging(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String text = request.getParameter("text");
        String statusParam = request.getParameter("status");
        String minPriceParam = request.getParameter("minPrice");
        String maxPriceParam = request.getParameter("maxPrice");
        String[] categoryIdsParam = request.getParameterValues("categoryIds");
        String sortBy = request.getParameter("sortBy");
        String sortDirection = request.getParameter("sortDirection");
        String pageParam = request.getParameter("page");
        String sizeParam = request.getParameter("size");

        Pagination pagination = new Pagination();
        if (sortBy != null) {
            pagination.setSortBy(sortBy);
        }
        if (sortDirection != null) {
            pagination.setSortDirection(sortDirection);
        }
        if (pageParam != null) {
            pagination.setPage(Integer.parseInt(pageParam));
        }
        if (sizeParam != null) {
            pagination.setSize(Integer.parseInt(sizeParam));
        }

        Integer status = statusParam != null && !statusParam.isBlank() ? Integer.parseInt(statusParam) : null;
        Integer minPrice = minPriceParam != null && !minPriceParam.isBlank() ? Integer.parseInt(minPriceParam) : null;
        Integer maxPrice = maxPriceParam != null && !maxPriceParam.isBlank() ? Integer.parseInt(maxPriceParam) : null;

        List<Integer> categoryIds = new ArrayList<>();
        if (categoryIdsParam != null) {
            for (String idStr : categoryIdsParam) {
                try {
                    categoryIds.add(Integer.parseInt(idStr));
                } catch (NumberFormatException ignored) {
                    throw new RuntimeException(Constant.DATA_INVALID);
                }
            }
        }

        try {
            Set<String> validColumns = new HashSet<>(Arrays.asList(
                    "product_id", "product_name", "status", "create_at"
            ));
            pagination.setSortBy(Common.getValidSortColumn(request.getParameter("sortBy"), validColumns, "product_id"));
            pagination.setSortDirection(request.getParameter("sortDirection"));

            PaginatedResult<ProductDto> result = productDao.listAllProductPaging(pagination, status, text, minPrice, maxPrice, categoryIds);

            request.setAttribute("products", result.getContent());
            request.setAttribute("pagination", result.getPagination());
            request.setAttribute("text", text);
            request.setAttribute("status", status);
            request.setAttribute("minPrice", minPrice);
            request.setAttribute("maxPrice", maxPrice);
            request.setAttribute("categoryIds", categoryIds);

            List<CategoryType> categoryTypes = categoryTypeDao.checkNameExist("Product");
            List<Category> categories = categoryDao.getCategoryByType(categoryTypes.get(0).getCategoryTypeId());

            request.setAttribute("categories", categories);

            request.getRequestDispatcher("./product/ListProduct.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "Error loading product list");
        }
    }

    public void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<CategoryType> categoryTypes = categoryTypeDao.checkNameExist("Product");
        List<Category> categories = categoryDao.getCategoryByType(categoryTypes.get(0).getCategoryTypeId());
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("./product/FormProduct.jsp").forward(request, response);
    }

    public void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            request.getSession().setAttribute("errorMessage", Constant.DATA_INVALID);
            response.sendRedirect("product");
            return;
        }

        int productId = Integer.parseInt(idParam);
        Product product = productDao.getProductById(productId);
        if (product == null) {
            request.getSession().setAttribute("errorMessage", Constant.ADD_FAILED + ": " + Constant.DATA_INVALID);
            response.sendRedirect("product");
        }

        List<ProductCategoryDto> listCate = productDao.getCategoryListByProductId(productId);
        List<Integer> listCateId = listCate.stream().map(it -> it.getCategoryId()).collect(Collectors.toList());

        List<ProductImage> listImg = productDao.getImageListByProductId(productId);

        List<CategoryType> categoryTypes = categoryTypeDao.checkNameExist("Product");
        List<Category> categories = categoryDao.getCategoryByType(categoryTypes.get(0).getCategoryTypeId());
        request.setAttribute("categories", categories);
        request.setAttribute("product", product);
        request.setAttribute("selectedCategoryIds", listCateId);
        request.setAttribute("productImages", listImg);
        request.getRequestDispatcher("./product/FormProduct.jsp").forward(request, response);
    }

    public void createProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        try {
            String name = request.getParameter("name");
            String desc = request.getParameter("desc");
            String priceRaw = request.getParameter("price");
            String quantityRaw = request.getParameter("quantity");
            String weightRaw = request.getParameter("weight");
            String lengthRaw = request.getParameter("length");
            String widthRaw = request.getParameter("width");
            String heightRaw = request.getParameter("height");

            Product product = new Product();
            product.setProductName(name != null ? name.trim() : "");
            product.setProductDesc(desc != null ? desc.trim() : "");

            System.out.println(product.toString());

            if (product.getProductName().isEmpty() || product.getProductName().length() >= 255 || product.getProductName().startsWith(" ")) {
                request.getSession().setAttribute("errorMessage", Constant.ADD_FAILED + ": " + Constant.DATA_INVALID);
                request.getSession().setAttribute("formData", product);
                response.sendRedirect("new-product");
                return;
            }

            if (product.getProductDesc().isEmpty() || product.getProductDesc().trim().startsWith(" ")) {
                request.getSession().setAttribute("errorMessage", Constant.ADD_FAILED + ": " + Constant.DATA_INVALID);
                request.getSession().setAttribute("formData", product);
                response.sendRedirect("new-product");
                return;
            }

            try {
                product.setProductPrice(priceRaw != null && !priceRaw.trim().isEmpty()
                        ? new BigDecimal(priceRaw.trim())
                        : BigDecimal.ZERO);
                product.setProductQuantity(quantityRaw != null && !quantityRaw.trim().isEmpty()
                        ? Integer.parseInt(quantityRaw.trim())
                        : 0);
                product.setWeight(weightRaw != null && !weightRaw.trim().isEmpty()
                        ? new BigDecimal(weightRaw.trim())
                        : BigDecimal.ZERO);
                product.setWidth(widthRaw != null && !widthRaw.trim().isEmpty()
                        ? new BigDecimal(widthRaw.trim())
                        : BigDecimal.ZERO);
                product.setHeight(heightRaw != null && !heightRaw.trim().isEmpty()
                        ? new BigDecimal(heightRaw.trim())
                        : BigDecimal.ZERO);
                product.setLength(lengthRaw != null && !lengthRaw.trim().isEmpty()
                        ? new BigDecimal(lengthRaw.trim())
                        : BigDecimal.ZERO);
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("errorMessage", Constant.ADD_FAILED + ": " + Constant.DATA_INVALID);
                request.getSession().setAttribute("formData", product);
                response.sendRedirect("new-product");
                return;
            }

            String[] categoryIdsRaw = request.getParameterValues("categoryIds");
            List<Integer> categoryIds = new ArrayList<>();
            if (categoryIdsRaw != null) {
                for (String id : categoryIdsRaw) {
                    categoryIds.add(Integer.parseInt(id));
                }
            }

            Part avatarPart = request.getPart("avatar");
            if (avatarPart != null && avatarPart.getSize() > 0) {
                try (InputStream fileContent = avatarPart.getInputStream()) {
                    String fileName = avatarPart.getSubmittedFileName();
                    if (fileName == null || fileName.trim().isEmpty()) {
                        fileName = "avatar_" + System.currentTimeMillis();
                    }
                    String uploadedUrl = Common.uploadImage(fileContent, fileName, "products");
                    product.setProductAvatar(uploadedUrl);
                }
            }

            List<Part> imageParts = request.getParts().stream()
                    .filter(p -> p.getName().equals("images") && p.getSize() > 0)
                    .collect(Collectors.toList());

            List<String> imageUrls = new ArrayList<>();
            for (Part part : imageParts) {
                try (InputStream is = part.getInputStream()) {
                    String fileName = part.getSubmittedFileName();
                    if (fileName == null || fileName.trim().isEmpty()) {
                        fileName = "image_" + System.currentTimeMillis();
                    }
                    String url = Common.uploadImage(is, fileName, "products");
                    imageUrls.add(url);
                }
            }

//            if (!imageUrls.isEmpty()) {
//                product.setProductAvatar(imageUrls.get(0));
//            }
            product.setStatus(1);
            productDao.createProduct(product, categoryIds, imageUrls);

            request.getSession().setAttribute("successMessage", Constant.ADD_SUCCESS);
            response.sendRedirect("product");
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Lỗi: " + e.getMessage());
            response.sendRedirect("new-product");
        }
    }

    public void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            System.out.println("id");
            System.out.println(productId);
            Product product = productDao.getProductById(productId);
            if (product == null) {
                System.out.println("error id get");
                request.getSession().setAttribute("errorMessage", "Sản phẩm không tồn tại.");
                response.sendRedirect("edit-product?id=" + productId);
                return;
            }

            String name = request.getParameter("name");
            String desc = request.getParameter("desc");
            String priceRaw = request.getParameter("price");
            String quantityRaw = request.getParameter("quantity");
            String weightRaw = request.getParameter("weight");
            String lengthRaw = request.getParameter("length");
            String widthRaw = request.getParameter("width");
            String heightRaw = request.getParameter("height");
            String statusRaw = request.getParameter("status");

            product.setProductName(name != null ? name.trim() : "");
            product.setProductDesc(desc != null ? desc.trim() : "");
            product.setStatus((statusRaw != null) ? 1 : 0);
            product.setUpdateAt(new Timestamp(System.currentTimeMillis()));

            System.out.println(product.toString());

            if (product.getProductName().isEmpty() || product.getProductName().length() >= 255 || product.getProductName().startsWith(" ")) {
                System.out.println("invalid name " + product.getProductName());
                request.getSession().setAttribute("errorMessage", Constant.UPDATE_FAILED + ": " + Constant.DATA_INVALID);
                request.getSession().setAttribute("formData", product);
                response.sendRedirect("edit-product?id=" + productId);
                return;
            }

            if (desc != null && (desc.length() >= 255 || desc.startsWith(" "))) {
                request.getSession().setAttribute("errorMessage", Constant.UPDATE_FAILED + ": " + Constant.DATA_INVALID);
                request.getSession().setAttribute("formData", product);
                response.sendRedirect("edit-product?id=" + productId);
                return;
            }

            try {
                product.setProductPrice(priceRaw != null && !priceRaw.trim().isEmpty()
                        ? new BigDecimal(priceRaw.trim())
                        : BigDecimal.ZERO);

                product.setProductQuantity(quantityRaw != null && !quantityRaw.trim().isEmpty()
                        ? Integer.parseInt(quantityRaw.trim())
                        : 0);

                product.setWeight(weightRaw != null && !weightRaw.trim().isEmpty()
                        ? new BigDecimal(weightRaw.trim())
                        : BigDecimal.ZERO);

                product.setWidth(widthRaw != null && !widthRaw.trim().isEmpty()
                        ? new BigDecimal(widthRaw.trim())
                        : BigDecimal.ZERO);

                product.setHeight(heightRaw != null && !heightRaw.trim().isEmpty()
                        ? new BigDecimal(heightRaw.trim())
                        : BigDecimal.ZERO);

                product.setLength(lengthRaw != null && !lengthRaw.trim().isEmpty()
                        ? new BigDecimal(lengthRaw.trim())
                        : BigDecimal.ZERO);
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("errorMessage", "Invalid numeric value provided");
                response.sendRedirect("edit-product?id=" + productId);
                return;
            }

            String[] categoryIdsRaw = request.getParameterValues("categoryIds");
            List<Integer> categoryIds = new ArrayList<>();
            if (categoryIdsRaw != null) {
                for (String catId : categoryIdsRaw) {
                    if (catId != null && !catId.trim().isEmpty()) {
                        try {
                            categoryIds.add(Integer.parseInt(catId.trim()));
                        } catch (NumberFormatException e) {
                            System.out.println("Error categoryIds");
                            continue;
                        }
                    }
                }
            }

            Part avatarPart = request.getPart("avatar");
            if (avatarPart != null && avatarPart.getSize() > 0) {
                try (InputStream fileContent = avatarPart.getInputStream()) {
                    String fileName = avatarPart.getSubmittedFileName();
                    if (fileName == null || fileName.trim().isEmpty()) {
                        fileName = "avatar_" + System.currentTimeMillis();
                    }
                    String uploadedUrl = Common.uploadImage(fileContent, fileName, "products");
                    product.setProductAvatar(uploadedUrl);
                }
            }

            List<String> imageUrls = new ArrayList<>();
            List<Part> imageParts = request.getParts().stream()
                    .filter(p -> p.getName().equals("images") && p.getSize() > 0)
                    .collect(Collectors.toList());

            if (!imageParts.isEmpty()) {
                for (Part part : imageParts) {
                    try (InputStream is = part.getInputStream()) {
                        String fileName = part.getSubmittedFileName();
                        if (fileName == null || fileName.trim().isEmpty()) {
                            fileName = "image_" + System.currentTimeMillis();
                        }
                        String url = Common.uploadImage(is, fileName, "products");
                        imageUrls.add(url);
                    }
                }
            } else {
                List<ProductImage> existingImages = productDao.getImageListByProductId(productId);
                imageUrls = existingImages.stream()
                        .map(ProductImage::getProductImage)
                        .collect(Collectors.toList());
            }

            productDao.updateProduct(product, categoryIds, imageUrls);

            request.getSession().setAttribute("successMessage", Constant.UPDATE_SUCCESS);
            response.sendRedirect("product");
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Lỗi cập nhật: " + e.getMessage());
            response.sendRedirect("edit-product?id=" + request.getParameter("id"));
        }
    }

    public void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            System.out.println(productId);
            productDao.softDeleteProduct(productId);
            request.getSession().setAttribute("successMessage", Constant.DELETE_SUCCESS);
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", Constant.DELETE_FAILED + " " + e.getMessage());
        }
        response.sendRedirect("product");
    }

    public void listAllProductPagingCustomer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String text = request.getParameter("text");
        String statusParam = request.getParameter("status");
        String minPriceParam = request.getParameter("minPrice");
        String maxPriceParam = request.getParameter("maxPrice");
        String[] categoryIdsParam = request.getParameterValues("categoryIds");
        String sortBy = request.getParameter("sortBy");
        String sortDirection = request.getParameter("sortDirection");
        String pageParam = request.getParameter("page");
        String sizeParam = request.getParameter("size");

        Pagination pagination = new Pagination();
        if (sortBy != null) {
            pagination.setSortBy(sortBy);
        }
        if (sortDirection != null) {
            pagination.setSortDirection(sortDirection);
        }
        if (pageParam != null) {
            pagination.setPage(Integer.parseInt(pageParam));
        }
        if (sizeParam != null) {
            pagination.setSize(Integer.parseInt(sizeParam));
        }

        Integer status = statusParam != null && !statusParam.isBlank() ? Integer.parseInt(statusParam) : null;
        Integer minPrice = minPriceParam != null && !minPriceParam.isBlank() ? Integer.parseInt(minPriceParam) : null;
        Integer maxPrice = maxPriceParam != null && !maxPriceParam.isBlank() ? Integer.parseInt(maxPriceParam) : null;

        List<Integer> categoryIds = new ArrayList<>();
        if (categoryIdsParam != null) {
            for (String idStr : categoryIdsParam) {
                try {
                    categoryIds.add(Integer.parseInt(idStr));
                } catch (NumberFormatException ignored) {
                    throw new RuntimeException(Constant.DATA_INVALID);
                }
            }
        }

        try {
            Set<String> validColumns = new HashSet<>(Arrays.asList(
                    "product_id", "product_name", "status", "create_at"
            ));
            pagination.setSortBy(Common.getValidSortColumn(request.getParameter("sortBy"), validColumns, "product_id"));
            pagination.setSortDirection(request.getParameter("sortDirection"));

            PaginatedResult<ProductDto> result = productDao.listAllProductPaging(pagination, status, text, minPrice, maxPrice, categoryIds);

            request.setAttribute("products", result.getContent());
            request.setAttribute("pagination", result.getPagination());
            request.setAttribute("text", text);
            request.setAttribute("status", status);
            request.setAttribute("minPrice", minPrice);
            request.setAttribute("maxPrice", maxPrice);
            request.setAttribute("categoryIds", categoryIds);

            List<CategoryType> categoryTypes = categoryTypeDao.checkNameExist("Product");
            List<Category> categories = categoryDao.getCategoryByType(categoryTypes.get(0).getCategoryTypeId());

            request.setAttribute("categories", categories);

            request.getRequestDispatcher("ListProduct.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "Error loading product list");
        }
    }

    public void productDetail(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            request.getSession().setAttribute("errorMessage", Constant.DATA_INVALID);
            response.sendRedirect("all-product");
            return;
        }

        int productId = Integer.parseInt(idParam);
        Product product = productDao.getProductById(productId);
        if (product == null) {
            request.getSession().setAttribute("errorMessage", Constant.ADD_FAILED + ": " + Constant.DATA_INVALID);
            response.sendRedirect("all-product");
        }

        List<ProductCategoryDto> listCate = productDao.getCategoryListByProductId(productId);
        List<Integer> listCateId = listCate.stream().map(it -> it.getCategoryId()).collect(Collectors.toList());

        List<ProductImage> listImg = productDao.getImageListByProductId(productId);

        List<CategoryType> categoryTypes = categoryTypeDao.checkNameExist("Product");
        List<Category> categories = categoryDao.getCategoryByType(categoryTypes.get(0).getCategoryTypeId());
        request.setAttribute("categories", categories);
        request.setAttribute("product", product);
        request.setAttribute("selectedCategoryIds", listCateId);
        request.setAttribute("productImages", listImg);

        List<Category> sizeCategories = getSizeCategories(listCate);
        List<Category> colorCategories = getColorCategories(listCate);
        request.setAttribute("sizeCategories", sizeCategories);
        request.setAttribute("colorCategories", colorCategories);

        request.getRequestDispatcher("ProductDetail.jsp").forward(request, response);
    }

    private List<Category> getSizeCategories(List<ProductCategoryDto> categories) {
        return categories.stream()
                .filter(cat -> cat.getCategoryName().toLowerCase().contains("size"))
                .map(cat -> {
                    Category category = new Category();
                    category.setCategoryId(cat.getCategoryId());
                    category.setCategoryName(cat.getCategoryName());
                    return category;
                })
                .collect(Collectors.toList());
    }

    private List<Category> getColorCategories(List<ProductCategoryDto> categories) {
        return categories.stream()
                .filter(cat -> cat.getCategoryName().toLowerCase().contains("color"))
                .map(cat -> {
                    Category category = new Category();
                    category.setCategoryId(cat.getCategoryId());
                    category.setCategoryName(cat.getCategoryName());
                    return category;
                })
                .collect(Collectors.toList());
    }
}
