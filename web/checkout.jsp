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
<<<<<<< Updated upstream
        }
        #notification-container {
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 9999;
        }

        .notification {
            background-color: #4CAF50;
            color: white;
            padding: 12px 20px;
            margin-bottom: 10px;
            border-radius: 4px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
            opacity: 0;
            transform: translateY(-20px);
            transition: all 0.3s ease;
        }

        .notification.show {
            opacity: 1;
            transform: translateY(0);
        }

        .notification.error {
            background-color: #f44336;
        }

        .cart-count.updated, .cart-total.updated {
            animation: highlight 1s ease;
        }

        @keyframes highlight {
            0% { color: inherit; }
            50% { color: #4CAF50; }
            100% { color: inherit; }
        }

        .pro-grid:hover .buy-in {
            background-color: #4CAF50;
            transition: background-color 0.3s ease;
        }

        .item_add {
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .item_add:hover {
            opacity: 0.8;
        }
    </style>

</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h2>Your Shopping Cart</h2>

            <c:choose>
                <c:when test="${empty cartItems || cartItems.size() == 0}">
                    <div class="empty-cart">
                        <i class='bx bx-cart-alt empty-cart-icon'></i>
                        <h3>Your cart is empty</h3>
                        <p>Looks like you haven't added any products to your cart yet.</p>
                        <a href="<%=request.getContextPath()%>/products" class="btn btn-primary">Continue Shopping</a>
=======
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
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
                            </tbody>
                        </table>

                        <div class="cart-summary">
                            <div class="cart-summary-row">
                                <span>Subtotal:</span>
                                <span class="subtotal">$${totalPrice}</span>
                            </div>
                            <div class="cart-summary-row">
                                <span>Shipping:</span>
                                <span class="shipping">Calculated at checkout</span>
                            </div>
                            <div class="cart-summary-row total">
                                <span>Total:</span>
                                <span class="total-price">$${totalPrice}</span>
                            </div>

                            <div class="cart-actions">
                                <a href="<%=request.getContextPath()%>/product" class="btn btn-secondary">Continue Shopping</a>
                                <form action="<%=request.getContextPath()%>/checkout" method="post">
                                    <button type="submit" class="btn btn-primary checkout-btn">Proceed to Checkout</button>
                                </form>
=======
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
>>>>>>> Stashed changes
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