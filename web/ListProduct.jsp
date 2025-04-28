<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>All Products</title>
        <jsp:include page="Header.jsp"/>
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
                    <div class="bottom-product row">
                        <c:forEach var="product" items="${products}" varStatus="loop">
                            <div class="col-md-4 bottom-cd simpleCart_shelfItem">
                                <div class="product-at">
                                    <a href="product-detail?id=${product.productId}">
                                        <div class="product-image-container">
                                            <img class="img-responsive" src="${product.productAvatar}" alt="${product.productName}">
                                        </div>
                                        <div class="pro-grid">
                                            <span class="buy-in">Buy Now</span>
                                        </div>
                                    </a>    
                                </div>
                                <p class="tun">${product.productName}</p>
                                <a href="#" class="item_add">
                                    <p class="number item_price"><i> </i>$${product.productPrice}</p>
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
