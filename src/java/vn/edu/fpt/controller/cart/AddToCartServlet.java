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
import vn.edu.fpt.model.Product;
import vn.edu.fpt.model.User;

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
        response.setContentType("application/json");
        try {
            // Validate user session
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("acc");
            if (user == null) {
                response.getWriter().write("{\"success\": false, \"error\": \"Please login to add items to cart\"}");
                return;
            }

            // Validate input parameters
            int productId;
            int quantity;
            try {
                productId = Integer.parseInt(request.getParameter("productId"));
                quantity = Integer.parseInt(request.getParameter("quantity"));
                if (quantity <= 0) {
                    response.getWriter().write("{\"success\": false, \"error\": \"Invalid quantity\"}");
                    return;
                }
            } catch (NumberFormatException e) {
                response.getWriter().write("{\"success\": false, \"error\": \"Invalid product or quantity\"}");
                return;
            }

            // Check product existence and stock
            Product product = productDao.getProductById(productId);
            if (product == null) {
                response.getWriter().write("{\"success\": false, \"error\": \"Product not found\"}");
                return;
            }
            if (product.getStatus() != 1) {
                response.getWriter().write("{\"success\": false, \"error\": \"Product is not available\"}");
                return;
            }
            if (product.getProductQuantity() < quantity) {
                response.getWriter().write("{\"success\": false, \"error\": \"Not enough stock available\"}");
                return;
            }

            // Get or create cart
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                cart.setUserId(user.getUserId());
                cart.setCreateAt(new Timestamp(System.currentTimeMillis()));
                session.setAttribute("cart", cart);
            }

            // Get or create cart items
            List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
            if (cartItems == null) {
                cartItems = new ArrayList<>();
                session.setAttribute("cartItems", cartItems);
            }

            // Check if item exists in cart
            boolean itemExists = false;
            for (CartItem item : cartItems) {
                if (item.getProductId() == productId) {
                    // Check if new total quantity exceeds stock
                    int newQuantity = item.getQuantity() + quantity;
                    if (newQuantity > product.getProductQuantity()) {
                        response.getWriter().write("{\"success\": false, \"error\": \"Not enough stock available\"}");
                        return;
                    }
                    item.setQuantity(newQuantity);
                    item.setUpdateAt(new Timestamp(System.currentTimeMillis()));
                    itemExists = true;
                    break;
                }
            }

            // Add new item if not exists
            if (!itemExists) {
                CartItem newItem = new CartItem();
                newItem.setCartId(cart.getCartId());
                newItem.setProductId(productId);
                newItem.setQuantity(quantity);
                newItem.setCreateAt(new Timestamp(System.currentTimeMillis()));
                newItem.setProductName(product.getProductName());
                newItem.setProductPrice(product.getProductPrice());
                newItem.setProductAvatar(product.getProductAvatar());
                cartItems.add(newItem);
            }

            // Update cart totals
            int totalItems = productDao.calculateTotalItems(cartItems);
            BigDecimal totalPrice = productDao.calculateTotalPrice(cartItems);

            session.setAttribute("totalItems", totalItems);
            session.setAttribute("totalPrice", totalPrice);

            // Return success response
            response.getWriter().write(String.format(
                    "{\"success\": true, \"totalItems\": %d, \"totalPrice\": %.2f, \"message\": \"Product added to cart successfully\"}",
                    totalItems, totalPrice
            ));

        } catch (Exception e) {
            response.getWriter().write("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
        }
    }
}