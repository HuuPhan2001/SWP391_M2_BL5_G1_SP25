/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package vn.edu.fpt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.fpt.service.CategoryService;
import java.sql.SQLException;
import vn.edu.fpt.model.User;

/**
 *
 * @author MTTTT
 */
public class CategoryServlet extends HttpServlet {

    private CategoryService categoryService;

    public void init() {
        categoryService = new CategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println(action);
        User user = (User) request.getSession().getAttribute("acc");
        if (user == null || user.getRoleId() != 1) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            request.getRequestDispatcher("./Login.jsp").forward(request, response);
            return;
        }
        try {
            if (action == null) {
                categoryService.listAllCatePaging(request, response);
            } else {
                switch (action) {
                    case "/new-category":
                        categoryService.showNewForm(request, response);
                        break;
                    case "/edit-category":
                        categoryService.showEditForm(request, response);
                        break;
                    case "/view-category":
                        categoryService.showEditForm(request, response);
                        break;
                    case "/delete-category":
                        categoryService.deleteCategory(request, response);
                        break;
                    case "/list-category":
                        categoryService.listAllCatePaging(request, response);
                        break;
                    case "/create-category":
                        categoryService.createCategory(request, response);
                        break;
                    case "/update-category":
                        categoryService.updateCategory(request, response);
                        break;
                    case "/update-category-status":
                        categoryService.updateCategoryStatus(request, response);
                        break;
                    default:
                        categoryService.listAllCatePaging(request, response);
                }
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
