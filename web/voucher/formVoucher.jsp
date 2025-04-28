<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>${voucher != null ? 'Update' : 'Add'} Voucher</title>
    <jsp:include page="../Header.jsp"/>
    <style>
        .avatar-preview {
            max-width: 100px;
            margin-top: 10px;
        }
        .message-container {
            margin: 20px 0;
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
        <h3 class="mb-0">${voucher != null ? 'Update' : 'Add'} Voucher</h3>
    </div>
    <div class="card-body">
        <form id="voucherForm" action="${voucher != null ? 'update-voucher' : 'create-voucher'}" method="post">
            <c:if test="${voucher != null}">
                <input type="hidden" name="voucherId" value="${voucher.voucherId}"/>
            </c:if>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label" for="code">Code<span style="color: red">*</span></label>
                <div class="col-sm-10">
                    <input type="text" id="code" class="form-control" name="code" value="${voucher != null ? voucher.code : ''}" required/>
                </div>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label" for="description">Description</label>
                <div class="col-sm-10">
                    <textarea name="description" id="description" class="form-control">${voucher != null ? voucher.description : ''}</textarea>
                </div>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label" for="discount_amount">Discount Amount</label>
                <div class="col-sm-10">
                    <input type="number" id="discount_amount" class="form-control" name="discount_amount" min="0" value="${voucher != null && voucher.discountAmount != null ? voucher.discountAmount : ''}"/>
                </div>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label" for="min_order_amount">Min Order Amount</label>
                <div class="col-sm-10">
                    <input type="number" id="min_order_amount" class="form-control" name="min_order_amount" min="0" value="${voucher != null && voucher.minOrderAmount != null ? voucher.minOrderAmount : ''}"/>
                </div>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label" for="start_date">Start Date<span style="color: red">*</span></label>
                <div class="col-sm-10">
                    <input type="datetime-local" id="start_date" class="form-control" name="start_date" value="${voucher != null ? voucher.startDate.toString().substring(0, 16) : ''}" required/>
                </div>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label" for="end_date">End Date<span style="color: red">*</span></label>
                <div class="col-sm-10">
                    <input type="datetime-local" id="end_date" class="form-control" name="end_date" value="${voucher != null ? voucher.endDate.toString().substring(0, 16) : ''}" required/>
                </div>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label" for="quantity">Quantity</label>
                <div class="col-sm-10">
                    <input type="number" id="quantity" class="form-control" name="quantity" min="0" value="${voucher != null && voucher.quantity != null ? voucher.quantity : ''}"/>
                </div>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label" for="product_id">Product ID</label>
                <div class="col-sm-10">
                    <input type="number" id="product_id" class="form-control" name="product_id" min="0" value="${voucher != null && voucher.productId != null ? voucher.productId : ''}"/>
                </div>
            </div>

            <div class="row mb-3 align-items-center">
                <label class="col-sm-2 col-form-label mb-0" for="status">Status</label>
                <div class="col-sm-10 form-check mb-0">
                    <input name="status" class="form-check-input" type="checkbox" value="1" ${voucher != null && voucher.status == 1 ? 'checked' : ''} id="status"/>
                    <label class="form-check-label ms-2" for="status">Active</label>
                </div>
            </div>

            <div class="row justify-content-end">
                <div class="col-sm-10">
                    <button type="button" class="btn btn-warning" id="resetButton">Reset</button>
                    <button type="button" class="btn btn-secondary" id="cancelButton">Cancel</button>
                    <button type="submit" class="btn btn-primary">Save</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script>
    document.getElementById('cancelButton').addEventListener('click', function () {
        window.location.href = 'list-voucher';
    });

    document.getElementById('resetButton').addEventListener('click', function () {
        document.getElementById('voucherForm').reset();
    });
</script>

<div class="clearfix"></div>
<jsp:include page="../Footer.jsp"/>
</body>
</html>