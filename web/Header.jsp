<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<!DOCTYPE html>
<html>
<head>
    <title>Online Shop | Home</title>
    <link href="assets/css/bootstrap.css" rel="stylesheet" type="text/css" media="all"/>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- Custom Theme files -->
    <!--theme-style-->
    <link href="assets/css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <!--//theme-style-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords" content="New Store Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template,
                  Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design"/>
    <script type="application/x-javascript"> addEventListener("load", function () {
        setTimeout(hideURLbar, 0);
    }, false);

    function hideURLbar() {
        window.scrollTo(0, 1);
    } </script>
    <!--fonts-->
    <link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900' rel='stylesheet' type='text/css'>
    <!--//fonts-->
    <!-- start menu -->
    <link href="assets/css/memenu.css" rel="stylesheet" type="text/css" media="all"/>
    <link rel="stylesheet" href="assets/css/boxicons.css"/>
    <script type="text/javascript" src="assets/js/memenu.js"></script>
    <!--                        <script>$(document).ready(function () {
                                    $(".memenu").memenu();
                                });</script>-->
    <script src="assets/js/helpers.js"></script>

    <script src="assets/js/config.js"></script>
    <script src="assets/js/jquery.js"></script>
    <script src="assets/js/menu.js"></script>
    <script src="assets/js/main.js"></script>
    <script src="assets/js/handler.js"></script>
    <script src="assets/js/bootstrap.bundle.min.js"></script>
    <script src="assets/js/responsiveslides.min.js"></script>
</head>
<body>
<div class="header">
    <div class="header-top">
        <div class="container">
            <div class="search">
                <form>
                    <input type="text" value="Search " onfocus="this.value = '';" onblur="if (this.value == '') {
                                        this.value = 'Search';
                                    }">
                    <input type="submit" value="Go">
                </form>
            </div>
            <div class="header-left">
                <ul>
                    <c:if test="${not empty sessionScope.acc}">
                        <li>
                            <a style="font-size: 25px" href="userInfo?accId=${sessionScope.acc.userName}">
                                Hello ${sessionScope.acc.userName}
                            </a>
                        </li>
                        <li><a style="font-size: 25px" href="logout">Logout</a></li>
                    </c:if>

                    <c:if test="${empty sessionScope.acc}">
                        <li><a style="font-size: 25px" href="Login.jsp">Login</a></li>
                    </c:if>
                </ul>
                <div class="cart box_1">
                    <a href="<%=request.getContextPath()%>/checkout" class="cart-link">
                        <div class="cart-icon">
                            <i class="bx bx-cart"></i>
                            <span class="cart-count">${sessionScope.totalItems != null ? sessionScope.totalItems : 0}</span>
                        </div>
<<<<<<< Updated upstream
                        <div class="cart-info">
                            <span class="cart-total">${sessionScope.totalPrice != null ? '$'.concat(sessionScope.totalPrice) : '$0.00'}</span>
                        </div>
                    </a>
                    <div class="clearfix"></div>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
    <div class="container">
        <div class="head-top">
            <div class="logo">
                <a href="home"><img src="assets/images/logo.png" alt=""></a>
            </div>
            <div class=" h_menu4">
                <ul class="memenu skyblue">
                    <li class="active grid"><a class="color8" href="home">Home</a></li>
                    <c:if test="${not empty sessionScope.acc}">
                        <li><a class="color1" href="<%=request.getContextPath()%>/category">Category</a></li>
                        <li><a class="color1" href="<%=request.getContextPath()%>/category-type">Category Type</a></li>
                        <li><a class="color1" href="<%=request.getContextPath()%>/product">Product</a></li>
                        <li><a class="color1" href="<%=request.getContextPath()%>/list-voucher">Voucher</a></li>
                        <li><a class="color1" href="<%=request.getContextPath()%>/all-product">All Product</a></li>
                        <li><a class="color1" href="<%=request.getContextPath()%>/list-order">Order</a></li>
                    </c:if>
                    <li><a class="color1" href="#">Men</a>
                        <div class="mepanel">
                            <div class="row">
                                <div class="col1">
                                    <div class="h_nav">
                                        <ul>
                                            <li><a href="#">Accessories</a></li>
                                            <li><a href="#">Bags</a></li>
                                            <li><a href="#">Caps & Hats</a></li>
                                            <li><a href="#">Hoodies & Sweatshirts</a></li>
                                            <li><a href="#">Jackets & Coats</a></li>
                                            <li><a href="#">Jeans</a></li>
                                            <li><a href="#">Jewellery</a></li>
                                            <li><a href="#">Jumpers & Cardigans</a></li>
                                            <li><a href="#">Leather Jackets</a></li>
                                            <li><a href="#">Long Sleeve T-Shirts</a></li>
                                            <li><a href="#">Loungewear</a></li>
                                        </ul>
=======
                        <div class="header-left">
                            <ul>
                                <c:if test="${not empty sessionScope.acc}">
                                    <li>
                                        <a style="font-size: 25px" href="userInfo?accId=${sessionScope.acc.userName}">
                                            Hello ${sessionScope.acc.userName}
                                        </a>
                                    </li>
                                    <li><a style="font-size: 25px" href="logout">Logout</a></li>
                                    </c:if>

                                <c:if test="${empty sessionScope.acc}">
                                    <li><a style="font-size: 25px" href="Login.jsp">Login</a></li>
                                    </c:if>
                            </ul>
                            <div class="cart box_1">
                                <a href="<%=request.getContextPath()%>/cart" class="cart-link">
                                    <div class="cart-icon">
                                        <i class="bx bx-cart"></i>
                                        <span class="cart-count">${sessionScope.totalItems != null ? sessionScope.totalItems : 0}</span>
>>>>>>> Stashed changes
                                    </div>
                                </div>
                                <div class="col1">
                                    <div class="h_nav">
                                        <ul>
                                            <li><a href="#">Shirts</a></li>
                                            <li><a href="#">Shoes, Boots & Trainers</a></li>
                                            <li><a href="#">Shorts</a></li>
                                            <li><a href="#">Suits & Blazers</a></li>
                                            <li><a href="#">Sunglasses</a></li>
                                            <li><a href="#">Sweatpants</a></li>
                                            <li><a href="#">Swimwear</a></li>
                                            <li><a href="#">Trousers & Chinos</a></li>
                                            <li><a href="#">T-Shirts</a></li>
                                            <li><a href="#">Underwear & Socks</a></li>
                                            <li><a href="#">Vests</a></li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col1">
                                    <div class="h_nav">
                                        <h4>Popular Brands</h4>
                                        <ul>
                                            <li><a href="#">Levis</a></li>
                                            <li><a href="#">Persol</a></li>
                                            <li><a href="#">Nike</a></li>
                                            <li><a href="#">Edwin</a></li>
                                            <li><a href="#">New Balance</a></li>
                                            <li><a href="#">Jack & Jones</a></li>
                                            <li><a href="#">Paul Smith</a></li>
                                            <li><a href="#">Ray-Ban</a></li>
                                            <li><a href="#">Wood Wood</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li class="grid"><a class="color2" href="#"> Women</a>
                        <div class="mepanel">
                            <div class="row">
                                <div class="col1">
                                    <div class="h_nav">
                                        <ul>
                                            <li><a href="#">Accessories</a></li>
                                            <li><a href="#">Bags</a></li>
                                            <li><a href="#">Caps & Hats</a></li>
                                            <li><a href="#">Hoodies & Sweatshirts</a></li>
                                            <li><a href="#">Jackets & Coats</a></li>
                                            <li><a href="#">Jeans</a></li>
                                            <li><a href="#">Jewellery</a></li>
                                            <li><a href="#">Jumpers & Cardigans</a></li>
                                            <li><a href="#">Leather Jackets</a></li>
                                            <li><a href="#">Long Sleeve T-Shirts</a></li>
                                            <li><a href="#">Loungewear</a></li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col1">
                                    <div class="h_nav">
                                        <ul>
                                            <li><a href="#">Shirts</a></li>
                                            <li><a href="#">Shoes, Boots & Trainers</a></li>
                                            <li><a href="#">Shorts</a></li>
                                            <li><a href="#">Suits & Blazers</a></li>
                                            <li><a href="#">Sunglasses</a></li>
                                            <li><a href="#">Sweatpants</a></li>
                                            <li><a href="#">Swimwear</a></li>
                                            <li><a href="#">Trousers & Chinos</a></li>
                                            <li><a href="#">T-Shirts</a></li>
                                            <li><a href="#">Underwear & Socks</a></li>
                                            <li><a href="#">Vests</a></li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col1">
                                    <div class="h_nav">
                                        <h4>Popular Brands</h4>
                                        <ul>
                                            <li><a href="#">Levis</a></li>
                                            <li><a href="#">Persol</a></li>
                                            <li><a href="#">Nike</a></li>
                                            <li><a href="#">Edwin</a></li>
                                            <li><a href="#">New Balance</a></li>
                                            <li><a href="#">Jack & Jones</a></li>
                                            <li><a href="#">Paul Smith</a></li>
                                            <li><a href="#">Ray-Ban</a></li>
                                            <li><a href="#">Wood Wood</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li><a class="color4" href="#">Blog</a></li>
                    <li><a class="color6" href="#">Conact</a></li>
                </ul>
            </div>

            <div class="clearfix"></div>
        </div>
    </div>

</div>

<style>
    .cart-link {
        display: flex;
        align-items: center;
        text-decoration: none;
        color: #333;
        padding: 8px 12px;
        border-radius: 4px;
        transition: all 0.3s ease;
    }

    .cart-link:hover {
        background: #f8f9fa;
    }

    .cart-icon {
        position: relative;
        margin-right: 8px;
    }

    .cart-icon i {
        font-size: 24px;
    }

    .cart-count {
        position: absolute;
        top: -8px;
        right: -8px;
        background: #e44d26;
        color: white;
        font-size: 12px;
        padding: 2px 6px;
        border-radius: 10px;
        min-width: 18px;
        text-align: center;
    }

    .cart-info {
        display: flex;
        flex-direction: column;
    }

    .cart-total {
        font-size: 14px;
        font-weight: bold;
        color: #e44d26;
    }
</style>
</body>
</html>
