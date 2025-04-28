package vn.edu.fpt.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.fpt.dao.OrderDao;
import vn.edu.fpt.dao.OrderDetailDao;
import vn.edu.fpt.dao.ProductDao;
import vn.edu.fpt.model.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private OrderDao orderDao = new OrderDao();
    private OrderDetailDao orderDetailDao = new OrderDetailDao();
    private ProductDao productDao = new ProductDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        if (cartItems != null && !cartItems.isEmpty()) {
            List<CartItem> cartItemsWithDetails = new ArrayList<>();

            for (CartItem item : cartItems) {
                Product product = productDao.getProductById(item.getProductId());
                if (product != null) {
                    item.setProductName(product.getProductName());
                    item.setProductPrice(product.getProductPrice());
                    item.setProductAvatar(product.getProductAvatar());
                    cartItemsWithDetails.add(item);
                }
            }

            request.setAttribute("cartItems", cartItemsWithDetails);
            request.setAttribute("totalPrice", session.getAttribute("totalPrice"));
        } else {
            request.setAttribute("cartItems", new ArrayList<CartItem>());
            request.setAttribute("totalPrice", 0);
        }

        request.getRequestDispatcher("/checkout.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();

            // Get cart items
            List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
            BigDecimal totalPrice = (BigDecimal) session.getAttribute("totalPrice");

            if (cartItems == null || cartItems.isEmpty() || totalPrice == null) {
                response.sendRedirect(request.getContextPath() + "/checkout");
                return;
            }
            User user = (User) session.getAttribute("acc");
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/login?redirect=checkout");
                return;
            }
            String nameReceiver = user.getUserName();
            String phoneReceiver = user.getPhone();
            String address = user.getAddress();
            String province = "";
            String district = "";
            String ward = "";
            int paymentMethodId = 1;
            Order order = new Order();
            order.setUserId(user.getUserId());
            order.setShopId(1);
            order.setOrderTotal(totalPrice.intValue());
            order.setOrderStatus("pending");
            order.setPaymentStatus(0);
            order.setAddress(address);
            order.setProvince(province);
            order.setDistrict(district);
            order.setWard(ward);
            order.setPhoneReceiver(phoneReceiver);
            order.setNameReceiver(nameReceiver);
            order.setShipCost(0);
            order.setPaymentMethodId(paymentMethodId);
            order.setDiscount(0);
            int orderId = orderDao.createOrder(order);
            if (orderId > 0) {
                List<OrderDetail> orderDetails = new ArrayList<>();
                for (CartItem item : cartItems) {
                    // Get product details
                    Product product = productDao.getProductById(item.getProductId());
                    if (product != null) {
                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setOrderId(orderId);
                        orderDetail.setProductId(item.getProductId());
                        orderDetail.setQuantity(item.getQuantity());
                        orderDetail.setUnitPrice(product.getProductPrice().intValue());
                        orderDetail.setNote(item.getNote());
                        orderDetail.setCreateAt(new Timestamp(System.currentTimeMillis()));
                        orderDetail.setUpdateAt(new Timestamp(System.currentTimeMillis()));
                        orderDetail.setProductOptionId(0); // Assuming no product options
                        orderDetail.setIsFeedback(0); // Default no feedback
                        orderDetails.add(orderDetail);
                    }
                }

                // Insert order details into database
                boolean orderDetailsCreated = orderDetailDao.createOrderDetails(orderDetails);

                if (orderDetailsCreated) {
                    // Clear cart after successful order
                    session.removeAttribute("cart");
                    session.removeAttribute("cartItems");
                    session.removeAttribute("totalItems");
                    session.removeAttribute("totalPrice");

                    // Redirect to order confirmation page or success page
                    response.sendRedirect(request.getContextPath() + "/product?orderId=" + orderId);
                } else {
                    // Rollback order if order details creation failed
                    // Note: You might want to implement actual database rollback
                    response.sendRedirect(request.getContextPath() + "/checkout?error=order_failed");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/checkout?error=order_creation_failed");
            }
        } catch (Exception e) {

        }
    }
}