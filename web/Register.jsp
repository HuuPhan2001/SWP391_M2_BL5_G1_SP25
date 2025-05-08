<!--A Design by W3layouts 
Author: W3layout
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Register</title>
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
        <script src="js/jquery.min.js"></script>
        <link href="css/style.css" rel="stylesheet" type="text/css" media="all" />	
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900' rel='stylesheet' type='text/css'>
        <link href="css/memenu.css" rel="stylesheet" type="text/css" media="all" />
        <script type="text/javascript" src="js/memenu.js"></script>
        <script>
            $(document).ready(function () {
                $(".memenu").memenu();
            });
        </script>
        <script src="js/simpleCart.min.js"></script>
    </head>
    <body>
        <jsp:include page="Header.jsp" />

        <div class="container">
            <div class="register">
                <h1>Register</h1>

                <!-- Hiển thị thông báo lỗi nếu có -->
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" style="margin-top: 10px;">${error}</div>
                </c:if>

                <form action="Register" method="post"> 
                    <div class="col-md-6 register-top-grid">
                        <h3>Personal information</h3>
                        <div>
                            <span>User Name</span>
                            <input type="text" name="username" value="${param.username}" required> 
                        </div>
                        <div>
                            <span>Phone</span>
                            <input type="text" name="phone" value="${param.phone}" required> 
                        </div>
                        <div>
                            <span>Email Address</span>
                            <input type="email" name="email" value="${param.email}" required> 
                        </div>
                        <a class="news-letter" href="#">
                            <label class="checkbox">
                                <input type="checkbox" name="newsletter" checked><i> </i>Sign Up for Newsletter
                            </label>
                        </a>
                    </div>
                    <div class="col-md-6 register-bottom-grid">
                        <h3>Login information</h3>
                        <div>
                            <span>Password</span>
                            <input type="password" name="password" required>
                        </div>
                        <div>
                            <span>Confirm Password</span>
                            <input type="password" name="confirmPassword" required>
                        </div>
                        <input type="submit" value="Submit">
                    </div>
                    <div class="clearfix"> </div>
                </form>
            </div>
        </div>

        <div class="footer">
            <jsp:include page="Footer.jsp" />		
        </div>

        <!-- Kiểm tra mật khẩu xác nhận -->
        <script>
            $(document).ready(function () {
                $("form").on("submit", function (e) {
                    const password = $("input[name='password']").val();
                    const confirmPassword = $("input[name='confirmPassword']").val();
                    if (password !== confirmPassword) {
                        alert("Mật khẩu nhập lại không trùng khớp.");
                        e.preventDefault();
                    }
                });
            });
        </script>
    </body>
</html>
