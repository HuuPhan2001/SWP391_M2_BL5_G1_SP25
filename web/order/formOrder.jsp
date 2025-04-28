<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order Details</title>
    <jsp:include page="../Header.jsp"/>
    <style>
        .product-avatar {
            max-width: 100px;
        }
        .message-container {
            margin: 20px 0;
        }
        .order-details-table {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div id="message" class="message-container">
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success alert-dismissible show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i>
                ${successMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <% session.removeAttribute("successMessage"); %>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible show" role="alert">
            <i class="bi bi-exclamation-circle-fill me-2"></i>
                ${errorMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <% session.removeAttribute("errorMessage"); %>
    </c:if>
</div>

<div class="card mb-4">
    <div class="card-header d-flex align-items-center justify-content-between">
        <h3 class="mb-0">Order Details - ${order.orderCode}</h3>
    </div>
    <div class="card-body">
        <form id="orderForm" <c:if test="${sessionScope.acc.roleId == 1}">action="update-order-status" method="post"</c:if>>
            <input type="hidden" name="orderId" value="${order.orderId}"/>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">Order Code</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" value="${order.orderCode}" readonly/>
                </div>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">Customer</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" value="${order.userName}" readonly/>
                </div>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">Order Total</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" value="${order.orderTotal != null ? order.orderTotal : 'N/A'}" readonly/>
                </div>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">Ship Cost</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" value="${order.shipCost}" readonly/>
                </div>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">Discount</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" value="${order.discount != null ? order.discount : 'N/A'}" readonly/>
                </div>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">Status</label>
                <div class="col-sm-10">
                    <c:if test="${sessionScope.acc.roleId == 1}">
                        <select name="order_status" class="form-select" required>
                            <option value="Pending" ${order.orderStatus == 'Pending' ? 'selected' : ''}>Pending</option>
                            <option value="Shipped" ${order.orderStatus == 'Shipped' ? 'selected' : ''}>Shipped</option>
                            <option value="Delivered" ${order.orderStatus == 'Delivered' ? 'selected' : ''}>Delivered</option>
                            <option value="Cancelled" ${order.orderStatus == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
                        </select>
                    </c:if>
                    <c:if test="${sessionScope.acc.roleId != 1}">
                        <input type="text" class="form-control" value="${order.orderStatus}" readonly/>
                    </c:if>
                </div>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">Shipping Address</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" value="${order.address}, ${order.ward}, ${order.district}, ${order.province}" readonly/>
                </div>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">Receiver</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" value="${order.nameReceiver} (${order.phoneReceiver})" readonly/>
                </div>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">Order Date</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" value="${order.createAt}" readonly/>
                </div>
            </div>

            <div class="order-details-table">
                <h4>Order Items</h4>
                <table class="table">
                    <thead>
                    <tr>
                        <th>Product</th>
                        <th>Name</th>
                        <th>Quantity</th>
                        <th>Unit Price</th>
                        <th>Total</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="detail" items="${order.orderDetails}">
                        <tr>
                            <td><img src="${detail.productAvatar}" alt="${detail.productName}" class="product-avatar"/></td>
                            <td>${detail.productName}</td>
                            <td>${detail.quantity != null ? detail.quantity : 'N/A'}</td>
                            <td>${detail.unitPrice != null ? detail.unitPrice : 'N/A'}</td>
                            <td>${detail.unitPrice != null && detail.quantity != null ? detail.unitPrice * detail.quantity : 'N/A'}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="row justify-content-end">
                <div class="col-sm-10">
                    <button type="button" class="btn btn-secondary" id="cancelButton">Back</button>
                    <c:if test="${sessionScope.acc.roleId == 1}">
                        <button type="submit" class="btn btn-primary">Save</button>
                    </c:if>
                </div>
            </div>
        </form>
    </div>
</div>

<script>
    document.getElementById('cancelButton').addEventListener('click', function () {
        window.location.href = 'list-order';
    });
</script>

<div class="clearfix"></div>
<jsp:include page="../Footer.jsp"/>
</body>
</html>