package vn.edu.fpt.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.fpt.dao.OrderDao;
import vn.edu.fpt.model.Order;

import java.io.IOException;

@WebServlet("/order-confirmation")
public class OrderConfirmationServlet extends HttpServlet {
    private OrderDao orderDao = new OrderDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderIdRaw = request.getParameter("orderId");
        if (orderIdRaw == null) {
            response.sendRedirect("home");
            return;
        }
        try {
            int orderId = Integer.parseInt(orderIdRaw);
            Order order = orderDao.getOrderById(orderId);
            if (order == null) {
                response.sendRedirect("home");
                return;
            }
            request.setAttribute("order", order);
            request.getRequestDispatcher("/order-confirmation.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("home");
        }
    }
} 