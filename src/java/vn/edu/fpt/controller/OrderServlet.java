package vn.edu.fpt.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.fpt.model.User;
import vn.edu.fpt.service.OrderService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "OrderServlet", urlPatterns = {"/list-order", "/edit-order", "/update-order-status", "/cancel-order"})
public class OrderServlet extends HttpServlet {
    private OrderService orderService;

    public void init() {
        orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        User user = (User) request.getSession().getAttribute("acc");

        if (user == null) {
            request.getRequestDispatcher("./Login.jsp").forward(request, response);
            return;
        }

        try {
            if (user.getRoleId() == null) {
                request.getRequestDispatcher("./Login.jsp").forward(request, response);
            } else {
                Integer roleId = user.getRoleId();
                switch (roleId) {
                    case 1: // Admin
                        switch (action) {
                            case "/edit-order":
                                orderService.showEditForm(request, response);
                                break;
                            case "/list-order":
                            default:
                                orderService.listAllOrdersPaging(request, response);
                                break;
                        }
                        break;
                    case 3: // Customer
                        switch (action) {
                            case "/view-order":
                                orderService.showEditForm(request, response);
                                break;
                            case "/list-order":
                            default:
                                orderService.listAllOrdersPaging(request, response);
                                break;
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
        String action = request.getServletPath();
        User user = (User) request.getSession().getAttribute("acc");

        if (user == null || user.getRoleId() == null || user.getRoleId() != 1) {
            request.getRequestDispatcher("./Login.jsp").forward(request, response);
            return;
        }

        try {
            switch (action) {
                case "/update-order-status":
                    orderService.updateOrderStatus(request, response);
                    break;
                case "/cancel-order":
                    orderService.cancelOrder(request, response);
                    break;
                default:
                    orderService.listAllOrdersPaging(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}