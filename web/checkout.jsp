<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout</title>
        <jsp:include page="Header.jsp"/>
        <style>
            .checkout-container {
                padding: 20px;
            }
            .checkout-form {
                background: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            .form-group {
                margin-bottom: 15px;
            }
            .form-group label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
            }
            .form-control {
                width: 100%;
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }
            .order-summary {
                background: #f8f9fa;
                padding: 20px;
                border-radius: 8px;
                margin-top: 20px;
            }
            .error-message {
                color: #dc3545;
                margin-bottom: 15px;
            }
            .btn-checkout {
                background: #28a745;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                width: 100%;
                margin-top: 20px;
            }
            .btn-checkout:hover {
                background: #218838;
            }
        </style>
    </head>
    <body>
        <div class="checkout-container">
            <div class="row">
                <div class="col-md-8">
                    <div class="checkout-form">
                        <h2>Shipping Information</h2>
                        <c:if test="${not empty param.error}">
                            <div class="error-message">
                                <c:choose>
                                    <c:when test="${param.error == 'missing_info'}">
                                        Please fill in all required fields
                                    </c:when>
                                    <c:when test="${param.error == 'stock_unavailable'}">
                                        Some items are out of stock
                                    </c:when>
                                    <c:when test="${param.error == 'system_error'}">
                                        An error occurred. Please try again
                                    </c:when>
                                </c:choose>
                            </div>
                        </c:if>
                        <form action="checkout" method="POST" id="checkoutForm">
                            <div class="form-group">
                                <label for="nameReceiver">Full Name *</label>
                                <input type="text" class="form-control" id="nameReceiver" name="nameReceiver" 
                                       value="${user.userFullName}" required>
                            </div>
                            <div class="form-group">
                                <label for="phoneReceiver">Phone Number *</label>
                                <input type="tel" class="form-control" id="phoneReceiver" name="phoneReceiver" 
                                       value="${user.phone}" required>
                            </div>
                            <div class="form-group">
                                <label for="address">Address *</label>
                                <input type="text" class="form-control" id="address" name="address" 
                                       value="${user.address}" required>
                            </div>
                            <div class="row">
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label for="province">Province *</label>
                                        <input type="text" class="form-control" id="province" name="province" required>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label for="district">District *</label>
                                        <input type="text" class="form-control" id="district" name="district" required>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label for="ward">Ward *</label>
                                        <input type="text" class="form-control" id="ward" name="ward" required>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="paymentMethodId">Payment Method *</label>
                                <select class="form-control" id="paymentMethodId" name="paymentMethodId" required>
                                    <c:forEach var="method" items="${paymentMethods}">
                                        <option value="${method.paymentMethodId}">${method.paymentMethodName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <input type="hidden" name="shipCost" value="0">
                            <input type="hidden" name="discount" value="0">
                        </form>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="order-summary">
                        <h3>Order Summary</h3>
                        <div class="cart-items">
                            <c:forEach var="item" items="${cartItems}">
                                <div class="cart-item">
                                    <div class="row">
                                        <div class="col-8">
                                            <h5>${item.productName}</h5>
                                            <p>Quantity: ${item.quantity}</p>
                                        </div>
                                        <div class="col-4 text-right">
                                            <p>$${item.productPrice * item.quantity}</p>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <hr>
                        <div class="order-total">
                            <div class="row">
                                <div class="col-6">
                                    <h4>Total:</h4>
                                </div>
                                <div class="col-6 text-right">
                                    <h4>$${totalPrice}</h4>
                                </div>
                            </div>
                        </div>
                        <button type="submit" form="checkoutForm" class="btn-checkout">Place Order</button>
                    </div>
                </div>
            </div>
        </div>

        <script>
            $(document).ready(function() {
                // Form validation
                $('#checkoutForm').on('submit', function(e) {
                    var isValid = true;
                    $(this).find('[required]').each(function() {
                        if (!$(this).val()) {
                            isValid = false;
                            $(this).addClass('is-invalid');
                        } else {
                            $(this).removeClass('is-invalid');
                        }
                    });
                    
                    if (!isValid) {
                        e.preventDefault();
                        alert('Please fill in all required fields');
                    }
                });

                // Phone number validation
                $('#phoneReceiver').on('input', function() {
                    var phone = $(this).val();
                    if (!/^\d{10,11}$/.test(phone)) {
                        $(this).addClass('is-invalid');
                    } else {
                        $(this).removeClass('is-invalid');
                    }
                });
            });
        </script>
    </body>
</html> 

