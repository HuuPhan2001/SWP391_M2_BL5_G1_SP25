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
import java.util.Iterator;
import java.util.List;

@WebServlet("/remove-from-cart")
public class RemoveFromCartServlet extends HttpServlet {
    private ProductDao productDao = new ProductDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));

            HttpSession session = request.getSession();
            List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");

            if (cartItems != null) {
                boolean itemRemoved = false;

                for (Iterator<CartItem> iterator = cartItems.iterator(); iterator.hasNext();) {
                    CartItem item = iterator.next();
                    if (item.getProductId() == productId) {
                        iterator.remove();
                        itemRemoved = true;
                        break;
                    }
                }

                if (itemRemoved) {
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
}