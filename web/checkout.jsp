<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout | Online Shop</title>
    <jsp:include page="Header.jsp"/>
    <style>
        .cart-table-container {
            margin: 30px 0;
        }

        .cart-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
        }

        .cart-table th,
        .cart-table td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #e0e0e0;
        }

        .cart-table th {
            background-color: #f9f9f9;
            font-weight: 600;
        }
        .cart-table .cart-item{
            width: 100%;
            float: unset;
            margin-right: unset;
        }
        .product-image img {
            max-width: 80px;
            border-radius: 4px;
        }

        .product-name {
            font-weight: 500;
        }

        .quantity-control {
            display: flex;
            align-items: center;
            max-width: 120px;
        }

        .qty-btn {
            width: 30px;
            height: 30px;
            background-color: #f0f0f0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .qty-btn:hover {
            background-color: #e0e0e0;
        }

        .qty-input {
            width: 40px;
            height: 30px;
            text-align: center;
            border: 1px solid #ddd;
            margin: 0 5px;
            border-radius: 4px;
        }

        .product-actions button {
            border: none;
            background: none;
            cursor: pointer;
            margin-right: 10px;
            font-size: 18px;
            color: #666;
        }

        .update-item-btn:hover {
            color: #4CAF50;
        }

        .remove-item-btn:hover {
            color: #f44336;
        }

        .cart-summary {
            background-color: #f9f9f9;
            padding: 20px;
            border-radius: 6px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            margin-left: auto;
        }

        .cart-summary-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 15px;
        }

        .cart-summary-row.total {
            margin-top: 20px;
            padding-top: 15px;
            border-top: 1px solid #e0e0e0;
            font-weight: 600;
            font-size: 18px;
        }

        .cart-actions {
            margin-top: 25px;
            display: flex;
            justify-content: space-between;
        }

        .btn {
            padding: 12px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 500;
            text-decoration: none;
            text-align: center;
            transition: all 0.3s ease;
        }

        .btn-primary {
            background-color: #4CAF50;
            color: white;
        }

        .btn-primary:hover {
            background-color: #45a049;
        }

        .btn-secondary {
            background-color: #f0f0f0;
            color: #333;
        }

        .btn-secondary:hover {
            background-color: #e0e0e0;
        }

        .empty-cart {
            text-align: center;
            padding: 50px 0;
        }

        .empty-cart-icon {
            font-size: 60px;
            color: #ccc;
            margin-bottom: 20px;
        }

        .cart-item.updated {
            background-color: #e8f5e9;
            transition: background-color 1s ease;
        }

        @media (max-width: 768px) {
            .cart-table th:nth-child(1),
            .cart-table td:nth-child(1) {
                display: none;
            }

            .cart-actions {
                flex-direction: column;
                gap: 10px;
            }

            .btn {
                width: 100%;
            }
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
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="cart-table-container">
                        <table class="cart-table">
                            <thead>
                            <tr>
                                <th>Product</th>
                                <th>Name</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Total</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${cartItems}">
                                <tr class="cart-item" data-product-id="${item.productId}">
                                    <td class="product-image">
                                        <img src="${item.productAvatar}" alt="${item.productName}" width="80">
                                    </td>
                                    <td class="product-name">${item.productName}</td>
                                    <td class="product-price">$${item.productPrice}</td>
                                    <td class="product-quantity">
                                        <div class="quantity-control">
                                            <button class="qty-btn decrease">-</button>
                                            <input type="number" class="qty-input" value="${item.quantity}" min="1"
                                                   max="99">
                                            <button class="qty-btn increase">+</button>
                                        </div>
                                    </td>
                                    <td class="product-total">$${item.productPrice * item.quantity}</td>
                                    <td class="product-actions">
                                        <button class="update-item-btn" title="Update"><i class='bx bx-refresh'></i>
                                        </button>
                                        <button class="remove-item-btn" title="Remove"><i class='bx bx-trash'></i>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
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
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<jsp:include page="Footer.jsp"/>
<script>
    $(document).ready(function() {
        initCartEvents();

        updateCartTotals();
        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.has('error')) {
            showNotification(`Unexpected error!`, 'error');
        }
    });

    function initCartEvents() {
        $('.increase').on('click', function() {
            const input = $(this).siblings('.qty-input');
            input.val(parseInt(input.val()) + 1);
            updateItemSubtotal($(this).closest('.cart-item'));
            updateCartTotals();
        });

        $('.decrease').on('click', function() {
            const input = $(this).siblings('.qty-input');
            const currentVal = parseInt(input.val());
            if (currentVal > 1) {
                input.val(currentVal - 1);
                updateItemSubtotal($(this).closest('.cart-item'));
                updateCartTotals();
            }
        });

        $('.qty-input').on('change', function() {
            updateItemSubtotal($(this).closest('.cart-item'));
            updateCartTotals();
        });

        $('.update-item-btn').on('click', function() {
            const cartItem = $(this).closest('.cart-item');
            const productId = cartItem.data('product-id');
            const quantity = parseInt(cartItem.find('.qty-input').val());

            updateCartItem(productId, quantity, cartItem);
        });

        $('.remove-item-btn').on('click', function() {
            const cartItem = $(this).closest('.cart-item');
            const productId = cartItem.data('product-id');

            removeCartItem(productId, cartItem);
        });
    }
    function updateItemSubtotal(cartItem) {
        const price = parseFloat(cartItem.find('.product-price').text().replace('$', ''));
        const quantity = parseInt(cartItem.find('.qty-input').val());
        const subtotal = price * quantity;

        cartItem.find('.product-total').text('$' + subtotal.toFixed(2));
    }

    function updateCartTotals() {
        let subtotal = 0;

        $('.product-total').each(function() {
            subtotal += parseFloat($(this).text().replace('$', ''));
        });

        $('.subtotal').text('$' + subtotal.toFixed(2));
        $('.total-price').text('$' + subtotal.toFixed(2));
    }

    function updateCartItem(productId, quantity, cartItem) {
        $.ajax({
            url: 'update-cart',
            type: 'POST',
            data: {
                productId: productId,
                quantity: quantity
            },
            dataType: 'json',
            success: function(response) {
                if (response.success) {
                    updateHeaderCart(response.totalItems, response.totalPrice);

                    cartItem.addClass('updated');
                    setTimeout(function() {
                        cartItem.removeClass('updated');
                    }, 1000);

                    showNotification('Cart updated successfully');

                    updateCartTotals();
                } else {
                    showNotification('Failed to update cart: ' + response.error, 'error');
                }
            },
            error: function() {
                showNotification('Error updating cart. Please try again.', 'error');
            }
        });
    }

    function removeCartItem(productId, cartItem) {
        $.ajax({
            url: 'remove-from-cart',
            type: 'POST',
            data: {
                productId: productId
            },
            dataType: 'json',
            success: function(response) {
                if (response.success) {
                    cartItem.fadeOut(300, function() {
                        $(this).remove();
                        updateCartTotals();
                    });

                    updateHeaderCart(response.totalItems, response.totalPrice);
                    showNotification('Item removed from cart');
                } else {
                    showNotification('Failed to remove item: ' + response.error, 'error');
                }
            },
            error: function() {
                showNotification('Error removing item. Please try again.', 'error');
            }
        });
    }
    function updateHeaderCart(totalItems, totalPrice) {
        $('.cart-count').text(totalItems);
        $('.cart-total').text('$' + totalPrice.toFixed(2));

        $('.cart-count, .cart-total').addClass('updated');
        setTimeout(function() {
            $('.cart-count, .cart-total').removeClass('updated');
        }, 1000);
    }

    function showNotification(message, type = 'success') {
        if ($('#notification-container').length === 0) {
            $('body').append('<div id="notification-container"></div>');
        }

        const notification = $('<div class="notification ' + type + '">' + message + '</div>');
        $('#notification-container').append(notification);

        setTimeout(function() {
            notification.addClass('show');
        }, 10);

        setTimeout(function() {
            notification.removeClass('show');
            setTimeout(function() {
                notification.remove();
            }, 300);
        }, 3000);
    }
</script>
<script>
    function removeItem(productId) {
        $.ajax({
            url: "<%=request.getContextPath()%>/remove-from-cart",
            type: "POST",
            data: {productId: productId},
            success: function (response) {
                if (response.success) {
                    location.reload();
                } else {
                    alert("Failed to remove item");
                }
            }
        });
    }

    function proceedToCheckout() {
        window.location.href = "<%=request.getContextPath()%>/checkout-process";
    }
</script>
</body>
</html> 