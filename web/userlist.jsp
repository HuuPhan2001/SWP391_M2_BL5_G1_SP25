
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="Header.jsp" />
        <meta charset="UTF-8">
        <title>Danh sách người dùng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    </head>
    <body>

        <div class="container mt-5">
            <h2 class="mb-4">Danh sách người dùng</h2>

            <!-- Search Form -->
            <form class="mb-3" method="get" action="UserList">
                <div class="input-group">
                    <input type="text" name="keyword" class="form-control" placeholder="Tìm kiếm tên hoặc email"
                           value="${keyword != null ? keyword : ''}">
                    <button class="btn btn-primary" type="submit">Tìm kiếm</button>
                </div>
            </form>

            <!-- User Table -->
            <table class="table table-bordered table-striped">
                <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Tên đăng nhập</th>
                        <th>Email</th>
                        <th>Họ tên</th>
                        <th>SĐT</th>
                        <th>Địa chỉ</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${userList}">
                        <tr>
                            <td>${user.userId}</td>
                            <td>${user.userName}</td>
                            <td>${user.userEmail}</td>
                            <td>${user.userFullName}</td>
                            <td>${user.phone}</td>
                            <td>${user.address}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${user.status == 1}">Hoạt động</c:when>
                                    <c:otherwise>Đã khoá</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <form action="EditUser" method="post" style="display:inline-block;">
                                    <input type="hidden" name="userId" value="${user.userId}">
                                    <input type="hidden" name="action" value="delete">
                                    <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc chắn muốn xoá người dùng này?')">Xoá</button>
                                </form>
                                <form action="EditUser" method="post" style="display:inline-block;">
                                    <input type="hidden" name="userId" value="${user.userId}">
                                    <input type="hidden" name="action" value="${user.status == 1 ? 'lock' : 'unlock'}">
                                    <button type="submit" class="btn btn-warning btn-sm">
                                        ${user.status == 1 ? 'Khoá' : 'Mở khoá'}
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>

                </tbody>
                
            </table>
            
        </div>
    </body>
</html>