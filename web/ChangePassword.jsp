<%-- 
    Document   : ChangePassword
    Created on : Apr 24, 2025, 8:00:27 PM
    Author     : ADMIN
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <jsp:include page="Header.jsp" />
    <head>
        <title>Đổi mật khẩu</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h2>Đổi mật khẩu</h2>
            <form action="ChangePassword" method="post">
                <div class="mb-3">
                    <label for="oldPassword" class="form-label">Mật khẩu cũ:</label>
                    <input type="password" class="form-control" id="oldPassword" name="oldPassword" required>
                </div>
                <div class="mb-3">
                    <label for="newPassword" class="form-label">Mật khẩu mới:</label>
                    <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                </div>
                <div class="mb-3">
                    <label for="confirmPassword" class="form-label">Xác nhận mật khẩu mới:</label>
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                </div>
                <button type="submit" class="btn btn-primary">Đổi mật khẩu</button>
                <a href="UserProfile.jsp" class="btn btn-secondary">Hủy</a>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger mt-3">${error}</div>
                </c:if>
                <c:if test="${not empty message}">
                    <div class="alert alert-success mt-3">${message}</div>
                </c:if>
            </form>
        </div>
    </body>
    <div class="footer">
        <jsp:include page="Footer.jsp" />
    </div>
</html>