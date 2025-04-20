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
import java.sql.SQLException;
import vn.edu.fpt.service.CategoryTypeService;

/**
 *
 * @author MTTTT
 */
public class CategoryTypeServlet extends HttpServlet {

    private CategoryTypeService categoryTypeService;

    public void init() {
        categoryTypeService = new CategoryTypeService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println(action);
        try {
            if (action == null) {
                categoryTypeService.listAllCategoryTypes(request, response);
            } else {
                switch (action) {
                    case "/new-category-type":
                        categoryTypeService.showNewForm(request, response);
                        break;
                    case "/edit-category-type":
                        categoryTypeService.showEditForm(request, response);
                        break;
                    case "/view-category-type":
                        categoryTypeService.showEditForm(request, response);
                        break;
                    case "/delete-category-type":
                        categoryTypeService.deleteCategoryType(request, response);
                        break;
                    case "/list-category-type":
                        categoryTypeService.listAllCategoryTypes(request, response);
                        break;
                    case "/create-category-type":
                        categoryTypeService.createCategoryType(request, response);
                        break;
                    case "/update-category-type":
                        categoryTypeService.updateCategoryType(request, response);
                        break;
                    case "/update-category-type-status":
                        categoryTypeService.updateCategoryTypeStatus(request, response);
                        break;
                    default:
                        categoryTypeService.listAllCategoryTypes(request, response);
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
