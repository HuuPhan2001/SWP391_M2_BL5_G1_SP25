/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import vn.edu.fpt.dao.CategoryDao;
import vn.edu.fpt.dao.CategoryTypeDao;
import vn.edu.fpt.model.Category;
import vn.edu.fpt.model.CategoryType;

/**
 *
 * @author MTTTT
 */
public class HeaderService {

    private CategoryDao categoryDao = new CategoryDao();
    private CategoryTypeDao categoryTypeDao = new CategoryTypeDao();

    public void listAllCateHeader(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            // Get categories for the header
            List<CategoryType> categoryTypes = categoryTypeDao.checkNameExist("Product");
            if (!categoryTypes.isEmpty()) {
                List<Category> categories = categoryDao.getCategoryByType(categoryTypes.get(0).getCategoryTypeId());
                request.setAttribute("headerCategories", categories);
            }

            request.getRequestDispatcher("/header.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Error loading header categories");
        }

    }
}
