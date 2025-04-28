<<<<<<< Updated upstream
package vn.edu.fpt.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.fpt.dao.ProductDao;
import vn.edu.fpt.model.CartItem;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/update-cart")
public class UpdateCartServlet extends HttpServlet {
    private ProductDao productDao = new ProductDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            HttpSession session = request.getSession();
            List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");

            if (cartItems != null) {
                boolean itemUpdated = false;

                for (CartItem item : cartItems) {
                    if (item.getProductId() == productId) {
                        item.setQuantity(quantity);
                        item.setUpdateAt(new Timestamp(System.currentTimeMillis()));
                        itemUpdated = true;
                        break;
                    }
                }

                if (itemUpdated) {
                    // Update cart totals
                    int totalItems = productDao.calculateTotalItems(cartItems);
                    BigDecimal totalPrice = productDao.calculateTotalPrice(cartItems);

                    session.setAttribute("totalItems", totalItems);
                    session.setAttribute("totalPrice", totalPrice);

                    // Return success response with updated cart info
                    response.setContentType("application/json");
                    response.getWriter().write(String.format(
                            "{\"success\": true, \"totalItems\": %d, \"totalPrice\": %.2f}",
                            totalItems, totalPrice
                    ));
                } else {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"success\": false, \"error\": \"Item not found in cart\"}");
                }
            } else {
                response.setContentType("application/json");
                response.getWriter().write("{\"success\": false, \"error\": \"Cart is empty\"}");
            }
        } catch (Exception e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
        }
    }
=======
package vn.edu.fpt.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.fpt.dao.ProductDao;
import vn.edu.fpt.model.CartItem;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/update-cart")
public class UpdateCartServlet extends HttpServlet {
    private ProductDao productDao = new ProductDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            HttpSession session = request.getSession();
            List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");

            if (cartItems != null) {
                boolean itemUpdated = false;

                for (CartItem item : cartItems) {
                    if (item.getProductId() == productId) {
                        item.setQuantity(quantity);
                        item.setUpdateAt(new Timestamp(System.currentTimeMillis()));
                        itemUpdated = true;
                        break;
                    }
                }

                if (itemUpdated) {
                    // Update cart totals
                    int totalItems = productDao.calculateTotalItems(cartItems);
                    BigDecimal totalPrice = productDao.calculateTotalPrice(cartItems);

                    session.setAttribute("totalItems", totalItems);
                    session.setAttribute("totalPrice", totalPrice);

                    // Return success response with updated cart info
                    response.setContentType("application/json");
                    response.getWriter().write(String.format(
                            "{\"success\": true, \"totalItems\": %d, \"totalPrice\": %.2f}",
                            totalItems, totalPrice
                    ));
                } else {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"success\": false, \"error\": \"Item not found in cart\"}");
                }
            } else {
                response.setContentType("application/json");
                response.getWriter().write("{\"success\": false, \"error\": \"Cart is empty\"}");
            }
        } catch (Exception e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
        }
    }
>>>>>>> Stashed changes
}