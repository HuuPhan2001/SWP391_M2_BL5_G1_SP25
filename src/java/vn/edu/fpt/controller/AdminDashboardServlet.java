/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package vn.edu.fpt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.fpt.dao.AdminDAO;

/**
 *
 * @author ADMIN
 */
@WebServlet("/adminDashboard")
public class AdminDashboardServlet extends HttpServlet {
    private AdminDAO adminDAO;

    @Override
    public void init() {
        adminDAO = new AdminDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userCount = adminDAO.getUserCount();
        int orderCount = adminDAO.getOrderCount();
        int productCount = adminDAO.getProductCount();
        double totalRevenue = adminDAO.getTotalRevenue();
        int activeVoucherCount = adminDAO.getActiveVoucherCount();        
        
        request.setAttribute("userCount", userCount);
        request.setAttribute("orderCount", orderCount);
        request.setAttribute("productCount", productCount);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("activeVoucherCount", activeVoucherCount);

        request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
    }
}
