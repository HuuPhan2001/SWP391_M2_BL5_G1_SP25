<%-- 
    Document   : ResetPassword
    Created on : Apr 29, 2025, 2:06:28 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Reset Password</title>
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
        <link href="css/style.css" rel="stylesheet" type="text/css" media="all" />    
    </head>
    <body>
        <jsp:include page="Header.jsp" />

        <div class="container">
            <div class="account">
                <h1>Reset Your Password</h1>
                <div class="account-pass">
                    <div class="col-md-8 account-top">
                        <form action="reset-password" method="post">		
                            <input type="hidden" name="userId" value="${user.userId}" />
                            <div> 	
                                <span>New Password</span>
                                <input type="password" name="newPassword" required>  
                            </div>
                            <input type="submit" value="Reset Password"> 
                        </form>
                    </div>
                    <div class="clearfix"> </div>
                </div>
            </div>
        </div>

        <jsp:include page="Footer.jsp" />
    </body>
</html>