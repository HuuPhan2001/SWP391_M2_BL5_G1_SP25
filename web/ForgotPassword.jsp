<%-- 
    Document   : ForgotPassword
    Created on : Apr 29, 2025, 1:42:38 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Forgot Password</title>
    <link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
    <link href="css/style.css" rel="stylesheet" type="text/css" media="all" />    
</head>
<body>
    <jsp:include page="Header.jsp" />

    <div class="container">
        <div class="account">
            <h1>Forgot Password</h1>
            <div class="account-pass">
                <div class="col-md-8 account-top">
                    <form action="forgot-password" method="post">		
                        <div> 	
                            <span>Enter your email</span>
                            <input type="email" name="email" required>  
                        </div>
                        <input type="submit" value="Send Reset Link"> 
                    </form>
                </div>
                <div class="clearfix"> </div>
            </div>
        </div>
    </div>

    <jsp:include page="Footer.jsp" />
</body>
</html>