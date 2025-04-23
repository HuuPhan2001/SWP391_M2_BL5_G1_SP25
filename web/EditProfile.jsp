<%-- 
    Document   : EditProfile
    Created on : Apr 23, 2025, 10:50:45 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    vn.edu.fpt.model.User user = (vn.edu.fpt.model.User) session.getAttribute("acc");
%>
<!DOCTYPE html>
<html>
    <jsp:include page="Header.jsp" />
    <head>
        <meta charset="UTF-8">
        <title>Chỉnh sửa thông tin cá nhân</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
         <script>
        function validateForm() {
            const fullName = document.getElementById("userFullName").value.trim();
            const email = document.getElementById("userEmail").value.trim();
            const phone = document.getElementById("phone").value.trim();
            const idNumber = document.getElementById("identificationNumber").value.trim();

            let errors = [];

            if (fullName === "") {
                errors.push("Họ tên không được để trống.");
            }

            const emailRegex = /^[\\w.-]+@[\\w.-]+\\.\\w{2,}$/;
            if (!emailRegex.test(email)) {
                errors.push("Email không hợp lệ.");
            }

            if (!/^\d{9,11}$/.test(phone)) {
                errors.push("Số điện thoại phải từ 9 đến 11 chữ số.");
            }

            if (idNumber && !/^\d{9,12}$/.test(idNumber)) {
                errors.push("CMND/CCCD phải là số từ 9 đến 12 chữ số.");
            }

            if (errors.length > 0) {
                alert(errors.join("\n"));
                return false; // chặn không submit
            }

            return true; // cho phép submit
        }
    </script>
    </head>
    <body>
        <div class="container mt-4 mb-5">
            <h2 class="mb-4">Chỉnh sửa Thông tin cá nhân</h2>
            <form action="UpdateProfile" method="post" onsubmit="return validateForm();">
                <input type="hidden" name="userId" value="${sessionScope.acc.userId}" />

                <div class="mb-3">
                    <label for="userFullName" class="form-label">Họ và tên</label>
                    <input type="text" class="form-control" id="userFullName" name="userFullName"
                           value="${sessionScope.acc.userFullName}" required>
                </div>

                <div class="mb-3">
                    <label for="userEmail" class="form-label">Email</label>
                    <input type="email" class="form-control" id="userEmail" name="userEmail"
                           value="${sessionScope.acc.userEmail}" required>
                </div>

                <div class="mb-3">
                    <label for="phone" class="form-label">Số điện thoại</label>
                    <input type="text" class="form-control" id="phone" name="phone"
                           value="${sessionScope.acc.phone}">
                </div>

                <div class="mb-3">
                    <label for="address" class="form-label">Địa chỉ</label>
                    <input type="text" class="form-control" id="address" name="address"
                           value="${sessionScope.acc.address}">
                </div>

                <div class="mb-3">
                    <label for="identificationNumber" class="form-label">CMND/CCCD</label>
                    <input type="text" class="form-control" id="identificationNumber" name="identificationNumber"
                           value="${sessionScope.acc.identificationNumber}">
                </div>

                <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                <a href="UserProfile.jsp" class="btn btn-secondary">Hủy</a>
            </form>
        </div>
    </body>
    <div class="footer">
        <jsp:include page="Footer.jsp" />
    </div>
</html>
