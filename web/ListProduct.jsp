<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>All Products</title>
        <jsp:include page="Header.jsp"/>
        <script>
            $(document).ready(function() {
                $('.item_add').on('click', function(e) {
                    e.preventDefault();

                    const productId = $(this).find('.item_product_id').val();
                    const quantity = parseInt($(this).find('.item_quantity').val());
                    const productName = $(this).find('.item_name').val();
                    const productPrice = parseFloat($(this).find('.item_price').text().replace('$', ''));

                    addToCart(productId, quantity, productName, productPrice);
                });
            });

            function addToCart(productId, quantity, productName, productPrice) {
                $.ajax({
                    url: 'add-to-cart',
                    type: 'POST',
                    data: {
                        productId: productId,
                        quantity: quantity
                    },
                    dataType: 'json',
                    success: function(response) {
                        if (response.success) {
                            updateCartDisplay(response.totalItems, response.totalPrice);
                            showNotification(`Added ${productName} to your cart`);
                        } else {
                            showNotification('Failed to add item to cart: ' + response.error, 'error');
                        }
                    },
                    error: function(xhr, status, error) {
                        showNotification('Error adding to cart. Please try again.', 'error');
                        console.error('AJAX Error: ' + status + ' - ' + error);
                    }
                });
            }
            function updateCartDisplay(totalItems, totalPrice) {
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
            $(document).ready(function() {
                const urlParams = new URLSearchParams(window.location.search);
                if (urlParams.has('orderId')) {
                    const orderId = urlParams.get('orderId');
                    showNotification(`Order #${orderId} placed successfully!`, 'success');
                }
            });
        </script>
        <style>
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
        <div class="product">
            <div class="container row">
                <div class="col-md-3 product-price">

                    <div class=" rsidebar span_1_of_left">
                        <div class="of-left">
                            <h3 class="cate">Categories</h3>
                        </div>
                        <ul class="menu">
                            <li class="${empty param.categoryIds ? 'active' : ''}">
                                <a href="<%=request.getContextPath()%>/all-product">All Categories</a>
                            </li>
                            <c:forEach var="cate" items="${categories}" varStatus="loop">
                                <li class="item${loop.index + 1} ${param.categoryIds eq cate.categoryId ? 'active' : ''}">
                                    <a href="<%=request.getContextPath()%>/all-product?categoryIds=${cate.categoryId}">
                                        ${cate.categoryBanner}${cate.categoryName}

                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <script type="text/javascript">
                        $(function () {
                            var menu_ul = $('.menu > li > ul'),
                                    menu_a = $('.menu > li > a');
                            menu_ul.hide();
                            menu_a.click(function (e) {
                                var href = $(this).attr('href');
                                if (!href || href === '#') {
                                    e.preventDefault();
                                    if (!$(this).hasClass('active')) {
                                        menu_a.removeClass('active');
                                        menu_ul.filter(':visible').slideUp('normal');
                                        $(this).addClass('active').next().stop(true, true).slideDown('normal');
                                    } else {
                                        $(this).removeClass('active');
                                        $(this).next().stop(true, true).slideUp('normal');
                                    }
                                }
                            });
                        });

                    </script>
                    <!---->
                    <div class="product-middle">

                        <div class="fit-top">
                            <h6 class="shop-top">Lorem Ipsum</h6>
                            <a href="single.html" class="shop-now">SHOP NOW</a>
                            <div class="clearfix"> </div>
                        </div>
                    </div>	 
                    <div class="sellers">
                        <div class="of-left-in">
                            <h3 class="tag">Tags</h3>
                        </div>
                        <div class="tags">
                            <ul>
                                <li><a href="#">design</a></li>
                                <li><a href="#">fashion</a></li>
                                <li><a href="#">lorem</a></li>
                                <li><a href="#">dress</a></li>
                                <li><a href="#">fashion</a></li>
                                <li><a href="#">dress</a></li>
                                <li><a href="#">design</a></li>
                                <li><a href="#">dress</a></li>
                                <li><a href="#">design</a></li>
                                <li><a href="#">fashion</a></li>
                                <li><a href="#">lorem</a></li>
                                <li><a href="#">dress</a></li>

                                <div class="clearfix"> </div>
                            </ul>

                        </div>

                    </div>
                    <!---->
                    <div class="product-bottom">
                        <div class="of-left-in">
                            <h3 class="best">Best Sellers</h3>
                        </div>
                        <div class="product-go">
                            <div class=" fashion-grid">
                                <a href="single.html"><img class="img-responsive " src="assets/images/p1.jpg" alt=""></a>

                            </div>
                            <div class=" fashion-grid1">
                                <h6 class="best2"><a href="single.html" >Lorem ipsum dolor sit
                                        amet consectetuer  </a></h6>

                                <span class=" price-in1"> $40.00</span>
                            </div>

                            <div class="clearfix"> </div>
                        </div>
                        <div class="product-go">
                            <div class=" fashion-grid">
                                <a href="single.html"><img class="img-responsive " src="assets/images/p2.jpg" alt=""></a>

                            </div>
                            <div class="fashion-grid1">
                                <h6 class="best2"><a href="single.html" >Lorem ipsum dolor sit
                                        amet consectetuer </a></h6>

                                <span class=" price-in1"> $40.00</span>
                            </div>

                            <div class="clearfix"> </div>
                        </div>

                    </div>
                    <div class=" per1">
                        <a href="single.html" ><img class="img-responsive" src="assets/images/pro.jpg" alt="">
                            <div class="six1">
                                <h4>DISCOUNT</h4>
                                <p>Up to</p>
                                <span>60%</span>
                            </div></a>
                    </div>
                </div>
                <div class="col-md-9 product1">
                    <div class="bottom-product">
                        <c:forEach var="product" items="${products}" varStatus="loop">
                            <div class="col-md-4 bottom-cd product">
                                <div class="product-at">
                                    <a href="<%=request.getContextPath()%>/product-detail?id=${product.productId}">
                                        <img class="img-responsive" src="${product.productAvatar}" alt="${product.productName}">
                                        <div class="pro-grid">
                                            <span class="buy-in">Buy Now</span>
                                        </div>
                                    </a>    
                                </div>
                                <p class="tun">${product.productName}</p>
                                <a href="javascript:;" class="item_add">
                                    <p class="number item_price"><i> </i>$${product.productPrice}</p>
                                    <input type="hidden" class="item_name" value="${product.productName}">
                                    <input type="hidden" class="item_product_id" value="${product.productId}">
                                    <input type="hidden" class="item_quantity" value="1">
                                </a>                        
                            </div>
                            <c:if test="${loop.index % 3 == 2 || loop.last}">
                                <div class="clearfix"> </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
                <div class="clearfix"> </div>
                <nav class="in">
                    <ul class="pagination">
                        <c:if test="${pagination.page > 1}">
                            <li class="page-item">
                                <a class="page-link" href="all-product?page=1&size=${pagination.size}&text=${text}&minPrice=${minPrice}&maxPrice=${maxPrice}<c:forEach var="catId" items="${categoryIds}">&categoryIds=${catId}</c:forEach>">
                                        |«
                                    </a>
                                </li>
                                <li class="page-item">
                                    <a class="page-link" href="all-product?page=${pagination.page - 1}&size=${pagination.size}&text=${text}&minPrice=${minPrice}&maxPrice=${maxPrice}<c:forEach var="catId" items="${categoryIds}">&categoryIds=${catId}</c:forEach>">
                                        «
                                    </a>
                                </li>
                        </c:if>

                        <c:forEach begin="1" end="${pagination.totalPages}" var="pageNumber">
                            <li class="page-item ${pageNumber == pagination.page ? 'active' : ''}">
                                <a class="page-link" href="all-product?page=${pageNumber}&size=${pagination.size}&text=${text}&minPrice=${minPrice}&maxPrice=${maxPrice}<c:forEach var="catId" items="${categoryIds}">&categoryIds=${catId}</c:forEach>">
                                    ${pageNumber} <span class="sr-only"></span>
                                </a>
                            </li>
                        </c:forEach>

                        <c:if test="${pagination.page < pagination.totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="all-product?page=${pagination.page + 1}&size=${pagination.size}&text=${text}&minPrice=${minPrice}&maxPrice=${maxPrice}<c:forEach var="catId" items="${categoryIds}">&categoryIds=${catId}</c:forEach>">
                                        »
                                    </a>
                                </li>
                                <li class="page-item">
                                    <a class="page-link" 
                                       href="all-product?page=${pagination.totalPages}&size=${pagination.size}&text=${text}&minPrice=${minPrice}&maxPrice=${maxPrice}<c:forEach var="catId" items="${categoryIds}">&categoryIds=${catId}</c:forEach>">
                                        »|
                                    </a>
                                </li>
                        </c:if>
                    </ul>
                </nav>
            </div>

        </div>
    </body>
    <div class="clearfix"> </div>
    <jsp:include page="Footer.jsp" />
</html>
