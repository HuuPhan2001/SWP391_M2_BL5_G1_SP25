/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package vn.edu.fpt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import vn.edu.fpt.model.User;
import vn.edu.fpt.service.ProductService;

/**
 *
 * @author MTTTT
 */
@MultipartConfig
public class ProductServlet extends HttpServlet {

    private ProductService productService;

    public void init() {
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println(action);
        User user = (User) request.getSession().getAttribute("acc");
        if (user == null) {
            request.getRequestDispatcher("./Login.jsp").forward(request, response);
            return;
        }
        try {
            if (null == user.getRoleId()) {
                request.getRequestDispatcher("./Login.jsp").forward(request, response);
            } else {
                Integer roleId = user.getRoleId();
                switch (roleId) {
                    case 3:
                        switch (action) {
                            case "/all-product":
                                productService.listAllProductPagingCustomer(request, response);
                                break;
                            case "/product-detail":
                                productService.listAllProductPagingCustomer(request, response);
                                break;
                            default:
                                productService.listAllProductPagingCustomer(request, response);
                        }
                        break;
                    case 1:
                        switch (action) {
                            case "/new-product":
                                productService.showNewForm(request, response);
                                break;
                            case "/edit-product":
                                productService.showEditForm(request, response);
                                break;
                            case "/view-product":
                                productService.showEditForm(request, response);
                                break;
                            case "/delete-product":
                                productService.deleteProduct(request, response);
                                break;
                            case "/list-product":
                                productService.listAllProductPaging(request, response);
                                break;
                            case "/create-product":
                                productService.createProduct(request, response);
                                break;
                            case "/update-product":
                                productService.updateProduct(request, response);
                                break;
//                    case "/update-product-status":
//                        productService.updateProductStatus(request, response);
//                        break;
                            default:
                                productService.listAllProductPaging(request, response);
                        }
                        break;
                    default:
                        request.getRequestDispatcher("./Login.jsp").forward(request, response);
                        break;
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
