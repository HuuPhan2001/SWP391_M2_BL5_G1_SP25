<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order List</title>
    <jsp:include page="../Header.jsp"/>
    <style>
        .product-avatar {
            max-width: 50px;
        }
        .message-container {
            margin: 20px 0;
        }
    </style>
</head>
<body>
<br/><br/><br/>
<h2 class="text-center mb-4">Order List</h2>
<div class="row">
    <form method="get" action="list-order" class="row align-items-center g-2">
        <input type="hidden" name="action" value="list"/>

        <div class="col-md-2">
            <input class="form-control" type="text" name="text" placeholder="Search by order code" value="${text}" id="text"/>
        </div>
        <div class="col-md-1">
            <select name="status" class="form-select" id="status">
                <option value="">-- Status --</option>
                <option value="Pending" ${status == 'Pending' ? 'selected' : ''}>Pending</option>
                <option value="Shipped" ${status == 'Shipped' ? 'selected' : ''}>Shipped</option>
                <option value="Delivered" ${status == 'Delivered' ? 'selected' : ''}>Delivered</option>
                <option value="Cancelled" ${status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
            </select>
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-secondary">Search</button>
        </div>
    </form>
</div>

<div id="message" class="message-container">
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i>
                ${successMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <%
            session.removeAttribute("successMessage");
            session.removeAttribute("showSuccessMessage");
        %>
    </c:if>
</div>

<br/>

<!-- Table -->
<div>
    <c:if test="${orders != null && orders.size() > 0}">
        <table class="table">
            <thead>
            <tr>
                <th>No.</th>
                <th>Order Code</th>
                <c:if test="${sessionScope.acc.roleId == 1}">
                    <th>User</th>
                </c:if>
                <th>Order Total</th>
                <th>Ship Cost</th>
                <th>Discount</th>
                <th>Status</th>
                <th>Order Date</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody class="table-border-bottom-0">
            <c:set var="startIndex" value="${(currentPage - 1) * pageSize}"/>
            <c:forEach var="order" items="${orders}" varStatus="loop">
                <tr>
                    <td>${startIndex + loop.index + 1}</td>
                    <td>${order.orderCode}</td>
                    <c:if test="${sessionScope.acc.roleId == 1}">
                        <td>${order.userName}</td>
                    </c:if>
                    <td>${order.orderTotal != null ? order.orderTotal : 'N/A'}</td>
                    <td>${order.shipCost}</td>
                    <td>${order.discount != null ? order.discount : 'N/A'}</td>
                    <td>
                        <c:choose>
                            <c:when test="${order.orderStatus == 'Pending'}"><span class="badge bg-label-warning me-1">Pending</span></c:when>
                            <c:when test="${order.orderStatus == 'Shipped'}"><span class="badge bg-label-info me-1">Shipped</span></c:when>
                            <c:when test="${order.orderStatus == 'Delivered'}"><span class="badge bg-label-success me-1">Delivered</span></c:when>
                            <c:otherwise><span class="badge bg-label-danger me-1">Cancelled</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>${order.createAt}</td>
                    <td>
                        <div class="dropdown">
                            <button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown">
                                <i class="bx bx-dots-vertical-rounded"></i>
                            </button>
                            <div class="dropdown-menu">
                                <c:choose>
                                    <c:when test="${sessionScope.acc.roleId == 1}">
                                        <a class="dropdown-item" href="edit-order?orderId=${order.orderId}">
                                            <i class="bx bx-detail me-1"></i> View/Edit
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="dropdown-item" href="view-order?orderId=${order.orderId}">
                                            <i class="bx bx-detail me-1"></i> View
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${sessionScope.acc.roleId == 1 && order.orderStatus != 'Cancelled' && order.orderStatus != 'Delivered'}">
                                    <button class="dropdown-item" type="button" data-bs-toggle="modal"
                                            data-bs-target="#cancelModal" data-whatever="${order.orderId}">
                                        <i class="bx bx-trash me-1"></i> Cancel
                                    </button>
                                </c:if>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${orders == null || orders.size() == 0}">
        <div>
            <h2 class="text-center">No content</h2>
        </div>
    </c:if>
</div>

<div class="row text-center">
    <div class="d-md-flex align-items-center text-center justify-content-between">
        <div class="pagination-info">
            <c:if test="${currentPage == totalPages}">
                <span class="text-muted me-3">Showing ${currentPage} - ${orders.size()} out of ${pageSize}</span>
            </c:if>
            <c:if test="${currentPage < totalPages}">
                <span class="text-muted me-3">Showing ${currentPage} - ${pageSize} out of ${pageSize}</span>
            </c:if>
        </div>

        <div class="pagination-controls">
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <c:if test="${currentPage > 1}">
                        <li class="page-item">
                            <a class="page-link" href="list-order?page=1&size=${pageSize}&text=${text}&status=${status}">
                                First
                            </a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="list-order?page=${currentPage - 1}&size=${pageSize}&text=${text}&status=${status}">
                                Previous
                            </a>
                        </li>
                    </c:if>

                    <c:forEach begin="1" end="${totalPages}" var="pageNumber">
                        <li class="page-item ${pageNumber == currentPage ? 'active' : ''}">
                            <a class="page-link" href="list-order?page=${pageNumber}&size=${pageSize}&text=${text}&status=${status}">
                                    ${pageNumber}
                            </a>
                        </li>
                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">
                        <li class="page-item">
                            <a class="page-link" href="list-order?page=${currentPage + 1}&size=${pageSize}&text=${text}&status=${status}">
                                Next
                            </a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="list-order?page=${totalPages}&size=${pageSize}&text=${text}&status=${status}">
                                Last
                            </a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
</div>

<div class="page-size d-md-flex align-items-center text-center justify-content-center">
    Show
    <select onchange="updatePageSize()" id="changePageSize">
        <option value="1" ${pageSize == 1 ? 'selected' : ''}>1</option>
        <option value="3" ${pageSize == 3 ? 'selected' : ''}>3</option>
        <option value="5" ${pageSize == 5 ? 'selected' : ''}>5</option>
        <option value="7" ${pageSize == 7 ? 'selected' : ''}>7</option>
        <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
    </select>
    entries
</div>

<!-- Cancel Modal -->
<div class="modal" id="cancelModal" tabindex="-1" aria-labelledby="cancelModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Confirm Cancel</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Are you sure you want to cancel this order?
            </div>
            <div class="modal-footer">
                <form id="cancelForm" action="cancel-order" method="post">
                    <input type="hidden" name="orderId" id="orderIdCancel"/>
                    <button type="submit" class="btn btn-danger">Yes</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">No</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    document.querySelectorAll('[data-bs-toggle="modal"]').forEach(trigger => {
        trigger.addEventListener('click', function () {
            const orderId = this.getAttribute('data-whatever');
            document.getElementById('orderIdCancel').setAttribute("value", orderId);
        });
    });

    function updatePageSize() {
        const params = new URLSearchParams(window.location.search);
        const size = document.getElementById('changePageSize').value;
        params.set('size', size);
        params.set('page', 1);
        location.href = 'list-order?' + params.toString();
    }
</script>

<div class="clearfix"></div>
<jsp:include page="../Footer.jsp"/>
</body>
</html>