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
import vn.edu.fpt.dao.PaymentDao;
import vn.edu.fpt.model.*;
import vn.edu.fpt.util.OrderCodeGenerator;

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
    private PaymentDao paymentDao = new PaymentDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
<<<<<<< Updated upstream
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
                   if (item.getQuantity() > 100) {
        session.setAttribute("errorMessage", "Không thể thanh toán: Số lượng sản phẩm \"" + item.getProductName() + "\" vượt quá 100.");
        response.sendRedirect(request.getContextPath() + "/checkout");
        return;
    }
            }

            request.setAttribute("cartItems", cartItemsWithDetails);
            request.setAttribute("totalPrice", session.getAttribute("totalPrice"));
        } else {
            request.setAttribute("cartItems", new ArrayList<CartItem>());
            request.setAttribute("totalPrice", 0);
=======
        User user = (User) session.getAttribute("acc");
        
        // Check if user is logged in
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login?redirect=checkout");
            return;
>>>>>>> Stashed changes
        }

        // Get cart items
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        if (cartItems == null || cartItems.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart?error=empty_cart");
            return;
        }

        // Get payment methods
        List<PaymentMethod> paymentMethods = paymentDao.getAllPaymentMethods();
        request.setAttribute("paymentMethods", paymentMethods);

        // Calculate totals
        BigDecimal totalPrice = (BigDecimal) session.getAttribute("totalPrice");
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("user", user);

        request.getRequestDispatcher("/checkout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("acc");

        // Validate user session
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login?redirect=checkout");
            return;
        }

        try {
            // Get cart items and validate
            List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
            BigDecimal totalPrice = (BigDecimal) session.getAttribute("totalPrice");

            if (cartItems == null || cartItems.isEmpty() || totalPrice == null) {
                response.sendRedirect(request.getContextPath() + "/cart?error=empty_cart");
                return;
            }

            // Validate stock for all items
            for (CartItem item : cartItems) {
                Product product = productDao.getProductById(item.getProductId());
                if (product == null || product.getStatus() != 1 || product.getProductQuantity() < item.getQuantity()) {
                    response.sendRedirect(request.getContextPath() + "/cart?error=stock_unavailable");
                    return;
                }
            }

            // Get shipping information
            String nameReceiver = request.getParameter("nameReceiver");
            String phoneReceiver = request.getParameter("phoneReceiver");
            String address = request.getParameter("address");
            String province = request.getParameter("province");
            String district = request.getParameter("district");
            String ward = request.getParameter("ward");
            int paymentMethodId = Integer.parseInt(request.getParameter("paymentMethodId"));
            int shipCost = Integer.parseInt(request.getParameter("shipCost"));
            int discount = Integer.parseInt(request.getParameter("discount"));

            // Validate required fields
            if (nameReceiver == null || phoneReceiver == null || address == null || 
                province == null || district == null || ward == null) {
                response.sendRedirect(request.getContextPath() + "/checkout?error=missing_info");
                return;
            }

            // Create order
            Order order = new Order();
            order.setUserId(user.getUserId());
            order.setShopId(1); // Default shop ID
            order.setOrderTotal(totalPrice.intValue());
            order.setOrderStatus("pending");
            order.setPaymentStatus(0);
            order.setAddress(address);
            order.setProvince(province);
            order.setDistrict(district);
            order.setWard(ward);
            order.setPhoneReceiver(phoneReceiver);
            order.setNameReceiver(nameReceiver);
            order.setShipCost(shipCost);
            order.setPaymentMethodId(paymentMethodId);
            order.setDiscount(discount);
            order.setOrderCode(OrderCodeGenerator.generateOrderCode());
            order.setCreateAt(new Timestamp(System.currentTimeMillis()));
            order.setUpdateAt(new Timestamp(System.currentTimeMillis()));

            // Save order to database
            int orderId = orderDao.createOrder(order);
            if (orderId <= 0) {
                response.sendRedirect(request.getContextPath() + "/checkout?error=order_failed");
                return;
            }

            // Create order details
            List<OrderDetail> orderDetails = new ArrayList<>();
            for (CartItem item : cartItems) {
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
                    orderDetail.setProductOptionId(0);
                    orderDetail.setIsFeedback(0);
                    orderDetails.add(orderDetail);

                    // Update product quantity
                    product.setProductQuantity(product.getProductQuantity() - item.getQuantity());
                    productDao.updateProduct(product);
                }
            }

            // Save order details
            boolean orderDetailsCreated = orderDetailDao.createOrderDetails(orderDetails);
            if (!orderDetailsCreated) {
                // Rollback order creation
                orderDao.deleteOrder(orderId);
                response.sendRedirect(request.getContextPath() + "/checkout?error=order_details_failed");
                return;
            }

            // Create payment record
            Payment payment = new Payment();
            payment.setPaymentMethodId(paymentMethodId);
            payment.setStatus("pending");
            payment.setAmount(totalPrice.intValue());
            payment.setOrderId(orderId);
            payment.setCreateAt(new Timestamp(System.currentTimeMillis()));
            payment.setUpdateAt(new Timestamp(System.currentTimeMillis()));
            paymentDao.createPayment(payment);

            // Clear cart
            session.removeAttribute("cart");
            session.removeAttribute("cartItems");
            session.removeAttribute("totalItems");
            session.removeAttribute("totalPrice");

            // Redirect to order confirmation
            response.sendRedirect(request.getContextPath() + "/order-confirmation?orderId=" + orderId);

        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/checkout?error=system_error");
        }
    }
}