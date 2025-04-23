<%-- 
    Document   : UserProfile
    Created on : Apr 23, 2025, 9:58:32 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <jsp:include page="Header.jsp" />
    <body>
        <div class="container">
    <div class="profile-card text-center">
        <h2 class="mb-4">Thông Tin Người Dùng</h2>
        <h4>${sessionScope.acc.userName}</h4>
        <div class="text-start">
            <p><strong>Họ và tên:</strong> ${sessionScope.acc.userFullName}</p>
            <p><strong>Email:</strong> ${sessionScope.acc.userEmail}</p>
            <p><strong>Số điện thoại:</strong> ${sessionScope.acc.phone}</p>
            <p><strong>Địa chỉ:</strong> ${sessionScope.acc.address}</p>
            <p><strong>Số CCCD:</strong> ${sessionScope.acc.identificationNumber}</p>
            <p><strong>Vai trò:</strong>
                <c:choose>
                    <c:when test="${sessionScope.acc.roleId == 1}">Admin</c:when>
                    <c:when test="${sessionScope.acc.roleId == 3}">Khách hàng</c:when>
                    <c:otherwise>Người dùng</c:otherwise>
                </c:choose>
            </p>
            <p><strong>Trạng thái:</strong>
                <c:choose>
                    <c:when test="${sessionScope.acc.status == 1}">Hoạt động</c:when>
                    <c:otherwise>Không hoạt động</c:otherwise>
                </c:choose>
            </p>
            <a class="btn btn-warning" href="EditProfile.jsp">Chỉnh sửa thông tin</a>
        </div>
        <a href="HomePage.jsp" class="btn btn-primary mt-3">Về trang chủ</a>
    </div>
</div>
    </body>
    <div class="footer">
        <jsp:include page="Footer.jsp" />
    </div>
</html>
