package vn.edu.fpt.controller.cart;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.fpt.dao.ProductDao;
import vn.edu.fpt.model.Cart;
import vn.edu.fpt.model.CartItem;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
    private ProductDao productDao = new ProductDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            HttpSession session = request.getSession();

            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                cart.setCreateAt(new Timestamp(System.currentTimeMillis()));
                session.setAttribute("cart", cart);
            }

            List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
            if (cartItems == null) {
                cartItems = new ArrayList<>();
                session.setAttribute("cartItems", cartItems);
            }

            boolean itemExists = false;
            for (CartItem item : cartItems) {
                if (item.getProductId() == productId) {
                    item.setQuantity(item.getQuantity() + quantity);
                    item.setUpdateAt(new Timestamp(System.currentTimeMillis()));
                    itemExists = true;
                    break;
                }
            }

            if (!itemExists) {
                CartItem newItem = new CartItem();
                newItem.setCartId(cart.getCartId());
                newItem.setProductId(productId);
                newItem.setQuantity(quantity);
                newItem.setCreateAt(new Timestamp(System.currentTimeMillis()));
                cartItems.add(newItem);
            }

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
        } catch (Exception e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
        }
    }
}