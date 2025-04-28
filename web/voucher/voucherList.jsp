<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Voucher List</title>
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
<h2 class="text-center mb-4">Voucher List</h2>
<div class="row">
    <form method="get" action="list-voucher" class="row align-items-center g-2">
        <input type="hidden" name="action" value="list"/>

        <div class="col-md-2">
            <input class="form-control" type="text" name="text" placeholder="Search by code" value="${text}" id="text"/>
        </div>
        <div class="col-md-1">
            <select name="status" class="form-select" id="status">
                <option value="">-- Status --</option>
                <option value="1" ${status == 1 ? 'selected' : ''}>Active</option>
                <option value="0" ${status == 0 ? 'selected' : ''}>Inactive</option>
            </select>
        </div>
        <div class="col-md-3">
            <%--@declare id="discountrange"--%><label for="discountRange">Discount Amount:</label>
            <div style="display: flex; align-items: center; gap: 10px;">
                <input type="number" id="minDiscountInput" name="minDiscount" value="${param.minDiscount != null ? param.minDiscount : 0}" style="width: 80px;" min="0"/>
                <input type="range" id="minDiscountRange" min="0" max="100000" step="100" value="${param.minDiscount != null ? param.minDiscount : 0}"/>
                <span> - </span>
                <input type="range" id="maxDiscountRange" min="0" max="100000" step="100" value="${param.maxDiscount != null ? param.maxDiscount : 100000}"/>
                <input type="number" id="maxDiscountInput" name="maxDiscount" value="${param.maxDiscount != null ? param.maxDiscount : 100000}" style="width: 80px;" min="0"/>
            </div>
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-secondary">Search</button>
        </div>
        <c:if test="${sessionScope.acc.roleId == 1}">
            <div class="col-md-2">
                <a href="<%=request.getContextPath()%>/new-voucher"><button type="button" class="btn btn-success">Add New</button></a>
            </div>
        </c:if>
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
    <c:if test="${vouchers != null && vouchers.size() > 0}">
        <table class="table">
            <thead>
            <tr>
                <th>No.</th>
                <th>Code</th>
                <th>Description</th>
                <th>Discount Amount</th>
                <th>Min Order Amount</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Quantity</th>
                <th>Product ID</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody class="table-border-bottom-0">
            <c:set var="startIndex" value="${(currentPage - 1) * pageSize}"/>
            <c:forEach var="voucher" items="${vouchers}" varStatus="loop">
                <tr>
                    <td>${startIndex + loop.index + 1}</td>
                    <td>${voucher.code}</td>
                    <td>${voucher.description != null ? voucher.description : 'N/A'}</td>
                    <td>${voucher.discountAmount != null ? voucher.discountAmount : 'N/A'}</td>
                    <td>${voucher.minOrderAmount != null ? voucher.minOrderAmount : 'N/A'}</td>
                    <td>${voucher.startDate}</td>
                    <td>${voucher.endDate}</td>
                    <td>${voucher.quantity != null ? voucher.quantity : 'N/A'}</td>
                    <td>${voucher.productId != null ? voucher.productId : 'N/A'}</td>
                    <td>
                        <c:choose>
                            <c:when test="${voucher.status == 1}"><span class="badge bg-label-success me-1">Active</span></c:when>
                            <c:otherwise><span class="badge bg-label-danger me-1">Inactive</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${sessionScope.acc.roleId == 1}">
                            <div class="dropdown">
                                <button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown">
                                    <i class="bx bx-dots-vertical-rounded"></i>
                                </button>
                                <div class="dropdown-menu">
                                    <a class="dropdown-item" href="edit-voucher?voucherId=${voucher.voucherId}">
                                        <i class="bx bx-edit-alt me-1"></i> Edit
                                    </a>
                                    <button class="dropdown-item" type="button" data-bs-toggle="modal"
                                            data-bs-target="#confirmModal" data-whatever="${voucher.voucherId}">
                                        <i class="bx bx-trash me-1"></i> Delete
                                    </button>
                                </div>
                            </div>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${vouchers == null || vouchers.size() == 0}">
        <div>
            <h2 class="text-center">No content</h2>
        </div>
    </c:if>
</div>

<div class="row text-center">
    <div class="d-md-flex align-items-center text-center justify-content-between">
        <div class="pagination-info">
            <c:if test="${currentPage == totalPages}">
                <span class="text-muted me-3">Showing ${currentPage} - ${vouchers.size()} out of ${pageSize}</span>
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
                            <a class="page-link" href="list-voucher?page=1&size=${pageSize}&text=${text}&status=${status}&minDiscount=${minDiscount}&maxDiscount=${maxDiscount}">
                                First
                            </a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="list-voucher?page=${currentPage - 1}&size=${pageSize}&text=${text}&status=${status}&minDiscount=${minDiscount}&maxDiscount=${maxDiscount}">
                                Previous
                            </a>
                        </li>
                    </c:if>

                    <c:forEach begin="1" end="${totalPages}" var="pageNumber">
                        <li class="page-item ${pageNumber == currentPage ? 'active' : ''}">
                            <a class="page-link" href="list-voucher?page=${pageNumber}&size=${pageSize}&text=${text}&status=${status}&minDiscount=${minDiscount}&maxDiscount=${maxDiscount}">
                                    ${pageNumber}
                            </a>
                        </li>
                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">
                        <li class="page-item">
                            <a class="page-link" href="list-voucher?page=${currentPage + 1}&size=${pageSize}&text=${text}&status=${status}&minDiscount=${minDiscount}&maxDiscount=${maxDiscount}">
                                Next
                            </a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="list-voucher?page=${totalPages}&size=${pageSize}&text=${text}&status=${status}&minDiscount=${minDiscount}&maxDiscount=${maxDiscount}">
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

<!-- Modal -->
<div class="modal" id="confirmModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Confirm Delete</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Are you sure you want to delete this voucher?
            </div>
            <div class="modal-footer">
                <form id="deleteForm" action="delete-voucher" method="get">
                    <input type="hidden" name="voucherId" id="voucherIdDelete"/>
                    <button type="submit" class="btn btn-danger">Yes</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    document.querySelectorAll('[data-bs-toggle="modal"]').forEach(trigger => {
        trigger.addEventListener('click', function () {
            const voucherId = this.getAttribute('data-whatever');
            document.getElementById('voucherIdDelete').setAttribute("value", voucherId);
        });
    });

    const minRange = document.getElementById("minDiscountRange");
    const maxRange = document.getElementById("maxDiscountRange");
    const minInput = document.getElementById("minDiscountInput");
    const maxInput = document.getElementById("maxDiscountInput");

    function syncValues() {
        if (parseInt(minRange.value) > parseInt(maxRange.value)) {
            minRange.value = maxRange.value;
            minInput.value = maxRange.value;
        }
        if (parseInt(minInput.value) > parseInt(maxInput.value)) {
            minInput.value = maxInput.value;
            minRange.value = maxInput.value;
        }

        minInput.value = minRange.value;
        maxInput.value = maxRange.value;
    }

    function syncInputs() {
        minRange.value = minInput.value;
        maxRange.value = maxInput.value;
        syncValues();
    }

    minRange.addEventListener("input", syncValues);
    maxRange.addEventListener("input", syncValues);
    minInput.addEventListener("change", syncInputs);
    maxInput.addEventListener("change", syncInputs);

    function updatePageSize() {
        const params = new URLSearchParams(window.location.search);
        const size = document.getElementById('changePageSize').value;
        params.set('size', size);
        params.set('page', 1);
        location.href = 'list-voucher?' + params.toString();
    }
</script>

<div class="clearfix"></div>
<jsp:include page="../Footer.jsp"/>
</body>
</html>