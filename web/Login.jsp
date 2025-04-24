<%-- 
    Document   : Login
    Created on : Apr 20, 2025, 11:27:20 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="js/jquery.min.js"></script>
        <!-- Custom Theme files -->
        <!--theme-style-->
        <link href="css/style.css" rel="stylesheet" type="text/css" media="all" />	
        <!--//theme-style-->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="keywords" content="New Store Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
              Smartphone Compatible web template, free webdesigns for Nokia, Sam sung, LG, SonyErricsson, Motorola web design" />
        <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
        <!--fonts-->
        <link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900' rel='stylesheet' type='text/css'><!--//fonts-->
        <!-- start menu -->
        <link href="css/memenu.css" rel="stylesheet" type="text/css" media="all" />
        <script type="text/javascript" src="js/memenu.js"></script>
        <script>$(document).ready(function () {
                $(".memenu").memenu();
            });</script>
        <script src="js/simpleCart.min.js"></script>
    </head>
    <body>
        <!--header-->
        <jsp:include page="Header.jsp" />


        <!--content-->
        <div class="container">
            <div class="account">
                <h1>Account</h1>
                <div class="account-pass">
                    <div class="col-md-8 account-top">
                        <form action="login" method="post">		
                            <div> 	
                                <span>Username</span>
                                <input type="text" name="username" required>  
                            </div>
                            <div> 
                                <span>Password</span>
                                <input type="password" name="password" required>
                            </div>
                            <div style="margin: 10px 0;">
                                <div style="margin: 10px 0;">
                                    <label>
                                        <input type="checkbox" name="remember"> Remember me
                                    </label>
                                </div>
                            </div>
                            <input type="submit" value="Login"> 
                        </form>
                    </div>
                    <div class="col-md-4 left-account ">
                        <a href="Register.jsp" class="create">Create an account</a>
                        <div class="clearfix"> </div>
                    </div>
                    <div class="clearfix"> </div>
                </div>
            </div>

        </div>

        <!--//content-->
        <div class="footer">
            <jsp:include page="Footer.jsp" />
        </div>
    </body>
</html>

