<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product</title>
        <link rel="stylesheet" href="assets/css/flexslider.css" type="text/css" media="screen" />
        <script src="assets/js/jquery.min.js"></script>
        <script defer src="assets/js/jquery.flexslider.js"></script>

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
                <!---->
                <div class="product-bottom">
                    <div class="of-left-in">
                        <h3 class="best">Best Sellers</h3>
                    </div>
                    <div class="product-go">
                        <div class=" fashion-grid">
                            <a href="#"><img class="img-responsive " src="assets/images/p1.jpg" alt=""></a>

                        </div>
                        <div class=" fashion-grid1">
                            <h6 class="best2"><a href="#" >Lorem ipsum dolor sit
                                    amet consectetuer  </a></h6>

                            <span class=" price-in1"> $40.00</span>
                        </div>

                        <div class="clearfix"> </div>
                    </div>
                    <div class="product-go">
                        <div class=" fashion-grid">
                            <a href="#"><img class="img-responsive " src="assets/images/p2.jpg" alt=""></a>

                        </div>
                        <div class="fashion-grid1">
                            <h6 class="best2"><a href="#" >Lorem ipsum dolor sit
                                    amet consectetuer </a></h6>

                            <span class=" price-in1"> $40.00</span>
                        </div>

                        <div class="clearfix"> </div>
                    </div>

                </div>
                <div class=" per1">
                    <a href="#" ><img class="img-responsive" src="assets/images/pro.jpg" alt="">
                        <div class="six1">
                            <h4>DISCOUNT</h4>
                            <p>Up to</p>
                            <span>60%</span>
                        </div></a>
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

                        <h5 class="item_price">$${product.productPrice}</h5>
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
                            <a href="#" class="add-cart item_add">ADD TO CART</a>
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
                    </ul> 
                </div> 
                <div class=" bottom-product row">
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
                </div>
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
</html>
