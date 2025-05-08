<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Your Cart</title>
    <jsp:include page="Header.jsp"/>
    <style>
        .cart-container { padding: 30px; }
        .cart-table { width: 100%; border-collapse: collapse; margin-bottom: 30px; }
        .cart-table th, .cart-table td { padding: 12px; border-bottom: 1px solid #e0e0e0; }
        .cart-table th { background: #f8f9fa; }
        .cart-summary { background: #f8f9fa; padding: 20px; border-radius: 8px; max-width: 400px; margin-left: auto; }
        .cart-actions { margin-top: 20px; display: flex; justify-content: flex-end; gap: 10px; }
        .btn { padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; font-weight: 500; }
        .btn-primary { background: #28a745; color: white; }
        .btn-primary:hover { background: #218838; }
        .btn-secondary { background: #f0f0f0; color: #333; }
        .btn-secondary:hover { background: #e0e0e0; }
        .btn-danger { background: #dc3545; color: white; }
        .btn-danger:hover { background: #b52a37; }
        .empty-cart { text-align: center; padding: 50px 0; }
        .qty-input { width: 50px; text-align: center; }
        .error-message { color: #dc3545; margin-bottom: 10px; }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="cart-container">
    <h2>Your Shopping Cart</h2>
    <div id="cart-error" class="error-message" style="display:none;"></div>
    <c:choose>
        <c:when test="${empty cartItems || cartItems.size() == 0}">
            <div class="empty-cart">
                <i class='bx bx-cart' style='font-size:60px;color:#ccc;'></i>
                <h3>Your cart is empty</h3>
                <a href="all-product" class="btn btn-primary">Continue Shopping</a>
            </div>
        </c:when>
        <c:otherwise>
            <table class="cart-table">
                <thead>
                <tr>
                    <th>Product</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${cartItems}">
                    <tr data-product-id="${item.productId}">
                        <td><img src="${item.productAvatar}" alt="${item.productName}" width="60"></td>
                        <td>${item.productName}</td>
                        <td>$${item.productPrice}</td>
                        <td>
                            <button class="btn btn-secondary btn-decrease">-</button>
                            <input type="number" class="qty-input" value="${item.quantity}" min="1" max="99">
                            <button class="btn btn-secondary btn-increase">+</button>
                        </td>
                        <td>$${item.productPrice * item.quantity}</td>
                        <td><button class="btn btn-danger btn-remove">Remove</button></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="cart-summary">
                <div class="row">
                    <div class="col-6"><strong>Total Items:</strong></div>
                    <div class="col-6 text-right">${totalItems}</div>
                </div>
                <div class="row">
                    <div class="col-6"><strong>Total Price:</strong></div>
                    <div class="col-6 text-right">$${totalPrice}</div>
                </div>
                <div class="cart-actions">
                    <a href="all-product" class="btn btn-secondary">Continue Shopping</a>
                    <a href="checkout" class="btn btn-primary">Proceed to Checkout</a>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
<script>
$(document).ready(function() {
    // Tăng số lượng
    $('.btn-increase').click(function() {
        var row = $(this).closest('tr');
        var input = row.find('.qty-input');
        var val = parseInt(input.val());
        input.val(val + 1).trigger('change');
    });
    // Giảm số lượng
    $('.btn-decrease').click(function() {
        var row = $(this).closest('tr');
        var input = row.find('.qty-input');
        var val = parseInt(input.val());
        if (val > 1) {
            input.val(val - 1).trigger('change');
        }
    });
    // Cập nhật số lượng
    $('.qty-input').change(function() {
        var row = $(this).closest('tr');
        var productId = row.data('product-id');
        var quantity = parseInt($(this).val());
        if (quantity < 1) quantity = 1;
        $.ajax({
            url: 'update-cart',
            type: 'POST',
            data: { productId: productId, quantity: quantity },
            dataType: 'json',
            success: function(response) {
                if (response.success) {
                    location.reload();
                } else {
                    showError(response.error || 'Update failed');
                }
            },
            error: function() { showError('Update failed'); }
        });
    });
    // Xóa sản phẩm
    $('.btn-remove').click(function() {
        var row = $(this).closest('tr');
        var productId = row.data('product-id');
        $.ajax({
            url: 'remove-from-cart',
            type: 'POST',
            data: { productId: productId },
            dataType: 'json',
            success: function(response) {
                if (response.success) {
                    location.reload();
                } else {
                    showError(response.error || 'Remove failed');
                }
            },
            error: function() { showError('Remove failed'); }
        });
    });
    function showError(msg) {
        $('#cart-error').text(msg).show();
        setTimeout(function(){ $('#cart-error').fadeOut(); }, 3000);
    }
});
</script>
</body>
</html> 