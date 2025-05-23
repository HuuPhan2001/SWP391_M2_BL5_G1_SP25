<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product</title>
        <link rel="stylesheet" href="assets/css/flexslider.css" type="text/css" media="screen" />
        <script src="assets/js/jquery.min.js"></script>
        <script defer src="assets/js/jquery.flexslider.js"></script>
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
                0% {
                    color: inherit;
                }
                50% {
                    color: #4CAF50;
                }
                100% {
                    color: inherit;
                }
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
        <jsp:include page="Header.jsp"/>
    </head>
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
                <div class="product-middle">

                    <div class="fit-top">
                        <h6 class="shop-top">Lorem Ipsum</h6>
                        <a href="#" class="shop-now">SHOP NOW</a>
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
            </div>
            <div class="col-md-9 product-price1 row">
                <div class="col-md-5 single-top">	
                    <div class="flexslider">
                        <ul class="slides">
                            <c:forEach items="${productImages}" var="image">
                                <li data-thumb="${image.productImage}">
                                    <div class="thumb-image">
                                        <img src="${image.productImage}" data-imagezoom="true" class="img-responsive" alt="${product.productName}">
                                    </div>
                                </li>
                            </c:forEach>
                            <c:if test="${empty productImages}">
                                <p>No Images</p>
                            </c:if>
                        </ul>

                    </div>
                    <!-- FlexSlider -->
                    <script defer src="assets/js/jquery.flexslider.js"></script>
                    <link rel="stylesheet" href="assets/css/flexslider.css" type="text/css" media="screen" />

                    <script>
                    $(document).ready(function () {
                        $('.flexslider').flexslider({
                            animation: "slide",
                            controlNav: "thumbnails"
                        });
                    });
                    </script>
                </div>	
                <div class="col-md-7 single-top-in simpleCart_shelfItem">
                    <div class="single-para ">
                        <h4>${product.productName}</h4>
                        <div class="star-on">
                            <ul class="star-footer">
                                <li><a href="#"><i> </i></a></li>
                                <li><a href="#"><i> </i></a></li>
                                <li><a href="#"><i> </i></a></li>
                                <li><a href="#"><i> </i></a></li>
                                <li><a href="#"><i> </i></a></li>
                            </ul>
                            <div class="review">
                                <a href="#"> 1 customer review </a>

                            </div>
                            <div class="clearfix"> </div>
                        </div>

                        <h5 class="item_price"><fmt:formatNumber value="${product.productPrice}" type="currency" currencySymbol="VND" /></h5>
                        <p>${product.productDesc}</p>
                        <div class="available">
                            <ul>
                                <c:if test="${not empty colorCategories}">
                                    <li class="color-selection">
                                        <span>Color</span>
                                        <select name="color" id="productColor" class="form-control">
                                            <option value="">Select Color</option>
                                            <c:forEach items="${colorCategories}" var="color">
                                                <option value="${color.categoryId}">${color.categoryName}</option>
                                            </c:forEach>
                                        </select>
                                    </li>
                                </c:if>

                                <c:if test="${not empty sizeCategories}">
                                    <li class="size-selection">
                                        <span>Size</span>
                                        <select name="size" id="productSize" class="form-control">
                                            <option value="">Select Size</option>
                                            <c:forEach items="${sizeCategories}" var="size">
                                                <option value="${size.categoryId}">${size.categoryName}</option>
                                            </c:forEach>
                                        </select>
                                    </li>
                                </c:if>

                                <c:if test="${product.productQuantity != null}">
                                    <li>Quantity Available: ${product.productQuantity}</li>
                                    </c:if>
                            </ul>

                        </div>
                        <ul class="tag-men">
                            <li><span>Categories</span>
                                <span class="women1">
                                    <c:forEach items="${selectedCategoryIds}" var="categoryId" varStatus="loop">
                                        <c:forEach items="${categories}" var="category">
                                            <c:if test="${category.categoryId eq categoryId}">
                                                ${category.categoryName}${!loop.last ? ', ' : ''}
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                </span>
                            </li>
                        </ul>
                        <c:if test="${product.status == 1 && product.productQuantity > 0}">
                            <a href="javascript:;" class="item_add">
                                <p class="number item_price">ADD TO CART</p>
                                <input type="hidden" class="item_name" value="${product.productName}">
                                <input type="hidden" class="item_product_id" value="${product.productId}">
                                <input type="hidden" class="item_quantity" value="1">
                            </a>
                        </c:if>
                        <c:if test="${product.status != 1 || product.productQuantity <= 0}">
                            <span class="out-of-stock">Out of Stock</span>
                        </c:if>


                    </div>
                </div>
                <div class="clearfix"> </div>
                <!---->
                <div class="cd-tabs">
                    <nav>
                        <ul class="cd-tabs-navigation">
                            <li><a data-content="fashion" class="selected" href="#0">Description </a></li>
                            <li><a data-content="cinema" href="#0" >Additional Information</a></li>
                            <li><a data-content="television" href="#0">Reviews</a></li>
                        </ul> 

                    </nav>
                    <ul class="cd-tabs-content">
                        <li data-content="fashion" class="selected">
                            <div class="facts">
                                <p>${product.productDesc}</p>
                            </div>
                        </li>
                        <li data-content="cinema">
                            <div class="facts1">
                                <div class="color">
                                    <p>Dimensions</p>
                                    <span>Length: ${product.length}, Width: ${product.width}, Height: ${product.height}</span>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="color">
                                    <p>Weight</p>
                                    <span>${product.weight}</span>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </li>
                        <li data-content="television">
                            <div class="feedback-section">
                                <div id="feedbackForm" class="mb-4">
                                    <h5>Write a Review</h5>
                                    <form id="submitFeedbackForm" onsubmit="return submitFeedback(event)">
                                        <input type="hidden" id="feedbackId" name="feedbackId" value="" />
                                        <input type="hidden" name="productId" id="productId" value="${product.productId}" />
                                        <input type="hidden" name="userId" id="userId" value="${sessionScope.acc.userId}" />
                                        <input type="hidden" name="feedbackId" id="feedbackId" id="editFeedbackId" value="" />

                                        <div class="row mb-3">
                                            <label class="col-form-label col-2" style="vertical-align: central">Rating</label>
                                            <div class="rating col-10 rating-form" style="gap: 5px;">
                                                <c:forEach begin="1" end="5" var="i">
                                                    <input type="radio" name="rating" value="${i}" id="star${i}" style="display: none;" required>
                                                    <label for="star${i}" style="cursor: pointer; font-size: 32px; color: #ccc;">
                                                        <i class='bx bx-star'></i>
                                                    </label>
                                                </c:forEach>
                                            </div>
                                        </div>

                                        <div class="mb-3">
                                            <label for="feedbackComment" class="form-label">Your Review</label>
                                            <textarea class="form-control" id="feedbackComment" name="comment" rows="3" required></textarea>
                                        </div>
                                        <div class="mb-3 row d-flex justify-content-end">
                                            <button type="submit" class="btn btn-primary col-md-2" id="feedbackSubmitBtn">Submit Review</button>
                                            <button type="button" class="btn btn-secondary col-md-2" id="cancelEditBtn" style="display: none;" onclick="cancelEdit()">Cancel Edit</button>
                                        </div>

                                    </form>
                                </div>


                                <div id="feedbackList">
                                    <c:forEach var="feedback" items="${feedbacks}">
                                        <div class="feedback-item" id="feedback-${feedback.feedbackId}">
                                            <div class="d-flex justify-content-between">
                                                <strong>${feedback.userName}</strong>
                                                <c:if test="${sessionScope.acc.userId == feedback.userId}">
                                                    <div>
                                                        <c:if test="${acc.userId eq feedback.userId}">
                                                            <button type="button" class="btn btn-primary" onclick="getFeedback(${feedback.feedbackId})">
                                                                <i class="bx bx-edit-alt me-1"></i> Edit
                                                            </button>
                                                        </c:if>

                                                        <button class="btn btn-sm btn-outline-danger" onclick="deleteFeedback(${feedback.feedbackId})">
                                                            <i class="bx bx-trash me-1"></i>
                                                        </button>
                                                    </div>
                                                </c:if>
                                            </div>
                                            <div class="rating">
                                                <c:forEach begin="1" end="5" var="i">
                                                    <i class='bx ${i <= feedback.feedbackRating ? "bxs-star" : "bx-star"}'></i>
                                                </c:forEach>
                                            </div>

                                            <p>${feedback.feedbackComment}</p>
                                            <small>${feedback.createAt}</small>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </li>
                    </ul> 
                </div> 
                <!--                <div class=" bottom-product row">
                                    <div class="col-md-4 bottom-cd simpleCart_shelfItem">
                                        <div class="product-at ">
                                            <a href="#"><img class="img-responsive" src="assets/images/pi3.jpg" alt="">
                                                <div class="pro-grid">
                                                    <span class="buy-in">Buy Now</span>
                                                </div>
                                            </a>	
                                        </div>
                                        <p class="tun">It is a long established fact that a reader</p>
                                        <a href="#" class="item_add"><p class="number item_price"><i> </i>$500.00</p></a>						
                                    </div>
                                    <div class="col-md-4 bottom-cd simpleCart_shelfItem">
                                        <div class="product-at ">
                                            <a href="#"><img class="img-responsive" src="assets/images/pi1.jpg" alt="">
                                                <div class="pro-grid">
                                                    <span class="buy-in">Buy Now</span>
                                                </div>
                                            </a>	
                                        </div>
                                        <p class="tun">It is a long established fact that a reader</p>
                                        <a href="#" class="item_add"><p class="number item_price"><i> </i>$500.00</p></a>					</div>
                                    <div class="col-md-4 bottom-cd simpleCart_shelfItem">
                                        <div class="product-at ">
                                            <a href="#"><img class="img-responsive" src="assets/images/pi4.jpg" alt="">
                                                <div class="pro-grid">
                                                    <span class="buy-in">Buy Now</span>
                                                </div>
                                            </a>	
                                        </div>
                                        <p class="tun">It is a long established fact that a reader</p>
                                        <a href="#" class="item_add"><p class="number item_price"><i> </i>$500.00</p></a>					</div>
                                    <div class="clearfix"> </div>
                                </div>-->
            </div>
        </div>
    </div>
    <script>
        $(document).ready(function () {
            $('#productColor, #productSize').change(function () {
                validateSelections();
            });

            function validateSelections() {
                var color = $('#productColor').val();
                var size = $('#productSize').val();
                var addToCartBtn = $('.add-cart.item_add');

                // If both selections are required and not selected, disable add to cart
                if ((${not empty colorCategories} && !color) ||
                        (${not empty sizeCategories} && !size)) {
                    addToCartBtn.addClass('disabled');
                    addToCartBtn.attr('disabled', true);
                } else {
                    addToCartBtn.removeClass('disabled');
                    addToCartBtn.removeAttr('disabled');
                }
            }

            validateSelections();
        });
    </script>

    <script>
        $(document).ready(function () {
            $('.item_add').on('click', function (e) {
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
                success: function (response) {
                    if (response.success) {
                        updateCartDisplay(response.totalItems, response.totalPrice);
                        showNotification(`Added ${productName} to your cart`);
                    } else {
                        showNotification('Failed to add item to cart: ' + response.error, 'error');
                    }
                },
                error: function (xhr, status, error) {
                    showNotification('Error adding to cart. Please try again.', 'error');
                    console.error('AJAX Error: ' + status + ' - ' + error);
                }
            });
        }
        function updateCartDisplay(totalItems, totalPrice) {
            $('.cart-count').text(totalItems);
            $('.cart-total').text('$' + totalPrice.toFixed(2));
            $('.cart-count, .cart-total').addClass('updated');
            setTimeout(function () {
                $('.cart-count, .cart-total').removeClass('updated');
            }, 1000);
        }
        function showNotification(message, type = 'success') {
            if ($('#notification-container').length === 0) {
                $('body').append('<div id="notification-container"></div>');
            }
            const notification = $('<div class="notification ' + type + '">' + message + '</div>');
            $('#notification-container').append(notification);
            setTimeout(function () {
                notification.addClass('show');
            }, 10);
            setTimeout(function () {
                notification.removeClass('show');
                setTimeout(function () {
                    notification.remove();
                }, 300);
            }, 3000);
        }
        $(document).ready(function () {
            const urlParams = new URLSearchParams(window.location.search);
            if (urlParams.has('orderId')) {
                const orderId = urlParams.get('orderId');
                showNotification(`Order #${orderId} placed successfully!`, 'success');
            }
        });
    </script>
    <script>
        $(document).ready(function () {
            function updateStars(rating) {
                $('.rating label i').each(function () {
                    const value = $(this).parent().prev('input').val();
                    if (value <= rating) {
                        $(this).removeClass('bx-star').addClass('bxs-star').css('color', '#ffc107');
                    } else {
                        $(this).removeClass('bxs-star').addClass('bx-star').css('color', '#ccc');
                    }
                });
            }

            $('input[name="rating"]').change(function () {
                var selectedRating = $(this).val();
                updateStars(selectedRating);
            });

            $('.rating label').hover(function () {
                var hoverRating = $(this).prev('input').val();
                updateStars(hoverRating);
            }, function () {
                var selectedRating = $('input[name="rating"]:checked').val() || 0;
                updateStars(selectedRating);
            });
        });
    </script>
    <script>
        function submitFeedback(event) {
            event.preventDefault();
            const form = $('#submitFeedbackForm')[0];
            const formData = new FormData(form);
            const feedbackId = $('#editFeedbackId').val();

            if (feedbackId) {
                formData.append('feedbackId', feedbackId);
            }

            const data = {
                productId: $('#productId').val(),
                userId: $('#userId').val(),
                rating: $('#rating').val(),
                comment: $('#comment').val()
            };

            console.log("Sending data:", data);  // Debug log


            $.ajax({
                url: 'ajax-feedback',
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                success: function (data) {
                    if (data.success) {
                        $('#submitFeedbackForm')[0].reset();
                        $('#editFeedbackId').val('');
                        $('#cancelEditBtn').hide();
                        $('#feedbackSubmitBtn').text('Submit Review');
                        location.reload();
                    } else {
                        alert(data.message || 'Error saving feedback');
                    }
                },
                error: function (xhr, status, error) {
                    if (xhr.status === 302) {
                        showNotification('Your session has expired. Please login again.', 'error');
                        window.location.href = 'login';
                        return;
                    }
                }

            });

            return false;
        }

        function getFeedback(feedbackId) {
            $.ajax({
                url: 'ajax-get-feedback',
                type: 'GET',
                data: {
                    feedbackId: feedbackId
                },
                success: function (response) {
                    if (response.success) {
                        console.log(response.data);
                        console.log(response);
                        $('#feedbackForm').attr('action', 'edit-feedback');
                        $('#feedbackId').val(response.feedbackId);
                        $('#rating').val(response.rating);
                        $('#feedbackComment').val(response.comment);

                        $('#submitButton').text('Update Feedback');

                        $('html, body').animate({
                            scrollTop: $("#feedbackForm").offset().top
                        }, 500);
                    } else {
                        alert(response.message);
                    }
                },
                error: function () {
                    alert('Error getting feedback data');
                }
            });
        }


        function cancelEdit() {
            const form = document.getElementById('submitFeedbackForm');
            form.reset();
            document.getElementById('editFeedbackId').value = '';
            document.getElementById('cancelEditBtn').style.display = 'none';
            document.getElementById('feedbackSubmitBtn').textContent = 'Submit Review';
        }

        function deleteFeedback(feedbackId) {
            if (confirm('Are you sure you want to delete this review?')) {
                fetch(`delete-feedback?id=${feedbackId}`, {
                    method: 'POST'
                })
                        .then(response => response.json())
                        .then(data => {
                            if (data.success) {
                                loadFeedbacks(${product.productId});
                            } else {
                                alert(data.message || 'Error deleting feedback');
                            }
                        });
            }
        }

        const currentUserId = ${sessionScope.acc.userId};

        function loadFeedbacks(productId) {
            fetch(`get-product-feedbacks?productId=${productId}`)
                    .then(response => response.json())
                    .then(feedbacks => {
                        const feedbackList = document.getElementById('feedbackList');
                        let html = '';

                        feedbacks.forEach(feedback => {
                            let starsHtml = '';
                            for (let i = 1; i <= 5; i++) {
                                starsHtml += `<i class="bi ${i <= feedback.feedbackRating ? 'bi-star-fill' : 'bi-star'}"></i>`;
                            }

                            html += `
                <div class="feedback-item" id="feedback-${feedback.feedbackId}">
                    <div class="d-flex justify-content-between">
                        <strong>${feedback.userName}</strong>`;

                            if (feedback.userId == currentUserId) {
                                html += `
                        <div>
                            <button class="btn btn-sm btn-outline-primary" onclick="editFeedback(${feedback.feedbackId})">
                                <i class="bi bi-pencil"></i>
                            </button>
                            <button class="btn btn-sm btn-outline-danger" onclick="deleteFeedback(${feedback.feedbackId})">
                                <i class="bi bi-trash"></i>
                            </button>
                        </div>`;
                            }

                            html += `
                    </div>
                    <div class="rating">
        ${starsHtml}
                    </div>
                    <p>${feedback.feedbackComment}</p>
                    <small>${feedback.createAt}</small>
                </div>`;
                        });

                        feedbackList.innerHTML = html;
                    })
                    .catch(error => console.error('Error:', error));
        }
        function resetFeedbackForm() {
            $('#feedbackForm').attr('action', 'create-feedback');
            $('#feedbackId').val('');
            $('#feedbackForm')[0].reset();
            $('#submitButton').text('Submit');
        }
    </script>
</html>
