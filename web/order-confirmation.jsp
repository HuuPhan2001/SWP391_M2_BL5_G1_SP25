<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Order Confirmation</title>
    <jsp:include page="Header.jsp"/>
    <style>
        .confirmation-container { max-width: 600px; margin: 40px auto; background: #fff; border-radius: 8px; box-shadow: 0 0 10px #eee; padding: 30px; }
        .confirmation-title { color: #28a745; text-align: center; margin-bottom: 20px; }
        .order-info { margin-bottom: 20px; }
        .order-info strong { display: inline-block; width: 140px; }
        .order-detail-table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        .order-detail-table th, .order-detail-table td { padding: 10px; border-bottom: 1px solid #e0e0e0; }
        .order-detail-table th { background: #f8f9fa; }
        .btn { padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; font-weight: 500; background: #28a745; color: white; margin-top: 20px; display: block; width: 100%; }
        .btn:hover { background: #218838; }
    </style>
</head>
<body>
<div class="confirmation-container">
    <h2 class="confirmation-title">Thank you for your order!</h2>
    <div class="order-info">
        <p><strong>Order Code:</strong> ${order.orderCode}</p>
        <p><strong>Order Date:</strong> ${order.createAt}</p>
        <p><strong>Receiver:</strong> ${order.nameReceiver}</p>
        <p><strong>Phone:</strong> ${order.phoneReceiver}</p>
        <p><strong>Address:</strong> ${order.address}, ${order.ward}, ${order.district}, ${order.province}</p>
        <p><strong>Total:</strong> $${order.orderTotal}</p>
    </div>
    <h4>Order Details</h4>
    <table class="order-detail-table">
        <thead>
        <tr>
            <th>Product</th>
            <th>Quantity</th>
            <th>Unit Price</th>
            <th>Total</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="detail" items="${order.orderDetails}">
            <tr>
                <td>${detail.productName}</td>
                <td>${detail.quantity}</td>
                <td>$${detail.unitPrice}</td>
                <td>$${detail.unitPrice * detail.quantity}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="home" class="btn">Back to Home</a>
</div>
</body>
</html> 