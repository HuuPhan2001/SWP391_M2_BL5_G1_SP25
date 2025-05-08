package vn.edu.fpt.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.fpt.model.CartItem;
import vn.edu.fpt.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("acc");
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        Integer totalItems = (Integer) session.getAttribute("totalItems");
        Object totalPrice = session.getAttribute("totalPrice");

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("totalItems", totalItems);
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }
} 