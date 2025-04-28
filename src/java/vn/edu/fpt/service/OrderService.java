package vn.edu.fpt.service;

import vn.edu.fpt.dao.OrderDao;
import vn.edu.fpt.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private OrderDao orderDao;

    public OrderService() {
        orderDao = new OrderDao();
    }

    public void listAllOrdersPaging(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int page = 1;
        int pageSize = 10;
        String text = request.getParameter("text");
        String status = request.getParameter("status");

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        if (request.getParameter("size") != null) {
            pageSize = Integer.parseInt(request.getParameter("size"));
        }

        // For customers, filter by their userId
        Integer userId = null;
        if (request.getSession().getAttribute("acc") != null) {
            vn.edu.fpt.model.User user = (vn.edu.fpt.model.User) request.getSession().getAttribute("acc");
            if (user.getRoleId() == 3) {
                userId = user.getUserId();
            }
        }

        List<Order> orders = orderDao.getAllOrders(text, status, userId, page, pageSize);
        int totalOrders = orderDao.getTotalOrders(text, status, userId);
        int totalPages = (int) Math.ceil((double) totalOrders / pageSize);

        request.setAttribute("orders", orders);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("text", text);
        request.setAttribute("status", status);
        request.getRequestDispatcher("./order/orderList.jsp").forward(request, response);
    }

    public void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Order order = orderDao.getOrderById(orderId);
        request.setAttribute("order", order);
        request.getRequestDispatcher("./order/formOrder.jsp").forward(request, response);
    }

    public void updateOrderStatus(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String status = request.getParameter("order_status");
        orderDao.updateOrderStatus(orderId, status);
        request.getSession().setAttribute("successMessage", "Order status updated successfully.");
        response.sendRedirect(request.getContextPath() + "/list-order");
    }

    public void cancelOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        orderDao.updateOrderStatus(orderId, "Cancelled");
        request.getSession().setAttribute("successMessage", "Order cancelled successfully.");
        response.sendRedirect(request.getContextPath() + "/list-order");
    }
}