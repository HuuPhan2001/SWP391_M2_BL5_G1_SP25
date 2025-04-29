<%-- 
    Document   : adminDashboard
    Created on : Apr 23, 2025, 2:29:25 AM
    Author     : ADMIN
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <jsp:include page="Header.jsp" />
    <head>
        <title>Admin Dashboard</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h1>Admin Dashboard</h1>
            <div class="row">
                <div class="col-md-4">
                    <div class="card text-center">
                        <div class="card-body">
                            <h5 class="card-title">Users</h5>
                            <p class="card-text">${userCount}</p>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card text-center">
                        <div class="card-body">
                            <h5 class="card-title">Orders</h5>
                            <p class="card-text">${orderCount}</p>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card text-center">
                        <div class="card-body">
                            <h5 class="card-title">Products</h5>
                            <p class="card-text">${productCount}</p>
                        </div>
                    </div>
                </div>

                <div class="col-md-6 mt-4">
                    <div class="card text-center">
                        <div class="card-body">
                            <h5 class="card-title">Total Revenue</h5>
                            <p class="card-text">${totalRevenue} VND</p>
                        </div>
                    </div>
                </div>

                <div class="col-md-6 mt-4">
                    <div class="card text-center">
                        <div class="card-body">
                            <h5 class="card-title">Active Vouchers</h5>
                            <p class="card-text">${activeVoucherCount}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>