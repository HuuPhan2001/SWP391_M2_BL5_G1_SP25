/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import vn.edu.fpt.common.Common;
import vn.edu.fpt.common.Constant;
import vn.edu.fpt.common.PaginatedResult;
import vn.edu.fpt.common.Pagination;
import vn.edu.fpt.dao.ProductDao;
import vn.edu.fpt.dto.CategoryDto;
import vn.edu.fpt.dto.ProductDto;
import vn.edu.fpt.model.CategoryType;

/**
 *
 * @author MTTTT
 */
public class ProductService {

    private ProductDao productDao = new ProductDao();

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

            request.setAttribute("result", result.getContent());
            request.setAttribute("pagination", result.getPagination());
            request.setAttribute("text", text);
            request.setAttribute("status", status);
            request.setAttribute("minPrice", minPrice);
            request.setAttribute("maxPrice", maxPrice);
            request.setAttribute("categoryIds", categoryIds);

            request.getRequestDispatcher("./product/ListProduct.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "Error loading product list");
        }
    }
}
