<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product List</title>
        <jsp:include page="../Header.jsp"/>
    </head>
    <body>
        </br></br></br>
        <h2 class="text-center mb-4">Product List</h2>
        <div class="row">
            <form method="get" action="product" class="row align-items-center g-2">
                <input type="hidden" name="action" value="list" />

                <div class="col-md-2">
                    <input class="form-control" type="text" name="text" placeholder="Search by name" value="${text}" id="text" />
                </div>
                <div class="col-md-2 multi-select-dropdown">
                    <button type="button" class="form-control">Select category:</button>
                    <div class="dropdown-content">
                        <c:forEach var="cate" items="${categories}">
                            <label>
                                <input type="checkbox" name="categoryIds" value="${cate.categoryId}"
                                       <c:if test="${categoryIds != null && categoryIds.contains(cate.categoryId)}">checked</c:if> />
                                ${cate.categoryName}
                            </label>
                        </c:forEach>
                    </div>
                </div>
                <div class="col-md-1">
                    <select name="status" class="form-select" id="status" >
                        <option value="">-- Status --</option>
                        <option value="1" ${status == 1 ? 'selected' : ''}>Active</option>
                        <option value="0" ${status == 0 ? 'selected' : ''}>Inactive</option>
                    </select>
                </div>
                <div class="col-md-3">
                    <label for="priceRange">Price(VND):</label>
                    <div style="display: flex; align-items: center; gap: 10px;">
                        <input type="number" id="minPriceInput" name="minPrice" value="${param.minPrice != null ? param.minPrice : 0}" style="width: 80px;" min="0" />
                        <input type="range" id="minPriceRange" min="0" max="10000000" step="10000" value="${param.minPrice != null ? param.minPrice : 0}" />
                        <span> - </span>
                        <input type="range" id="maxPriceRange" min="0" max="10000000" step="10000" value="${param.maxPrice != null ? param.maxPrice : 10000000}" />
                        <input type="number" id="maxPriceInput" name="maxPrice" value="${param.maxPrice != null ? param.maxPrice : 10000000}" style="width: 80px;" min="0" />
                    </div>
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-secondary">Search</button>
                </div>
                <div class="col-md-2">
                    <a href="<%=request.getContextPath()%>/new-product"><button type="button" class="btn btn-success " style="">Add New</button></a>
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
            <c:if test="${products != null && products.size() > 0 }" >
                <table class="table">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Name</th>
                            <th>Avatar</th>
                            <th>Price(VND)</th>
                            <th>Quantity</th>
                            <th>Status</th>
                            <th>Category</th>
                            <th>Create At</th>
                            <th>Update At</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody class="table-border-bottom-0">
                        <c:set var="startIndex" value="${(pagination.page - 1) * pagination.size}" />
                        <c:forEach var="pro" items="${products}" varStatus="loop">
                            <tr>
                                <td>${startIndex + loop.index + 1}</td>
                                <td>${pro.productName}</td>
                                <td><img src="${pro.productAvatar}" alt="Product avatar" class="product-avatar" /></td>
                                <td><fmt:formatNumber value="${pro.productPrice}" type="currency" currencySymbol="" /></td>
                                <td>${pro.productQuantity}</td>
                                <td><c:choose>
                                        <c:when test="${pro.status == 1}"><span class="badge bg-label-success me-1">Active</span></c:when>
                                        <c:otherwise><span class="badge bg-label-danger me-1">Inactive</span></c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:forEach var="cate" items="${pro.categoryList}" varStatus="loop">
                                        <div class="row">${cate.categoryName}</div>
                                    </c:forEach>
                                </td>
                                <td><fmt:formatDate value="${pro.createAt}" pattern="dd/MM/yyyy HH:mm" /></td>
                                <td><fmt:formatDate value="${pro.updateAt}" pattern="dd/MM/yyyy HH:mm" /></td>
                                <td>
                                    <div class="dropdown">
                                        <button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown">
                                            <i class="bx bx-dots-vertical-rounded"></i>
                                        </button>
                                        <div class="dropdown-menu">
                                            <a class="dropdown-item" href="edit-product?id=${pro.productId}"
                                               ><i class="bx bx-edit-alt me-1"></i> Edit</a
                                            >
                                            <button class="dropdown-item" type="button" data-bs-toggle="modal"
                                                    data-bs-target="#confirmModal" data-whatever="${pro.productId}"
                                                    ">
                                                <i class="bx bx-trash me-1"></i> Delete</button>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${products == null || products.size() == 0 }" >
                <div>
                    <h2 class="text-center">No content</h2>
                </div>
            </c:if>
        </div>
        <div class="row text-center">
            <div class="d-md-flex align-items-center text-center justify-content-between">
                <div class="pagination-info">
                    <c:if test="${pagination.page == pagination.totalPages}" >
                        <span class="text-muted me-3">Showing ${pagination.page} - ${pagination.currentElements} out of ${pagination.size}</span>
                    </c:if>
                    <c:if test="${pagination.page < pagination.totalPages}" >
                        <span class="text-muted me-3">Showing ${pagination.page} - ${pagination.size} out of ${pagination.size}</span>
                    </c:if>
                </div>

                <div class="pagination-controls">
                    <nav aria-label="Page navigation">
                        <ul class="pagination">
                            <c:if test="${pagination.page > 1}">
                                <li class="page-item">
                                    <a class="page-link" href="list-product?page=1&size=${pagination.size}&text=${text}&status=${status}&minPrice=${minPrice}&maxPrice=${maxPrice}<c:forEach var="catId" items="${categoryIds}">&categoryIds=${catId}</c:forEach>">
                                            First
                                        </a>
                                    </li>
                                    <li class="page-item">
                                        <a class="page-link" href="list-product?page=${pagination.page - 1}&size=${pagination.size}&text=${text}&status=${status}&minPrice=${minPrice}&maxPrice=${maxPrice}<c:forEach var="catId" items="${categoryIds}">&categoryIds=${catId}</c:forEach>">
                                            Previous
                                        </a>
                                    </li>
                            </c:if>

                            <c:forEach begin="1" end="${pagination.totalPages}" var="pageNumber">
                                <li class="page-item ${pageNumber == pagination.page ? 'active' : ''}">
                                    <a class="page-link" href="list-product?page=${pageNumber}&size=${pagination.size}&text=${text}&status=${status}&minPrice=${minPrice}&maxPrice=${maxPrice}<c:forEach var="catId" items="${categoryIds}">&categoryIds=${catId}</c:forEach>">
                                        ${pageNumber}
                                    </a>
                                </li>
                            </c:forEach>

                            <c:if test="${pagination.page < pagination.totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="list-product?page=${pagination.page + 1}&size=${pagination.size}&text=${text}&status=${status}&minPrice=${minPrice}&maxPrice=${maxPrice}<c:forEach var="catId" items="${categoryIds}">&categoryIds=${catId}</c:forEach>">
                                            Next
                                        </a>
                                    </li>
                                    <li class="page-item">
                                        <a class="page-link" 
                                           href="list-product?page=${pagination.totalPages}&size=${pagination.size}&text=${text}&status=${status}&minPrice=${minPrice}&maxPrice=${maxPrice}<c:forEach var="catId" items="${categoryIds}">&categoryIds=${catId}</c:forEach>">
                                            Last
                                        </a>
                                    </li>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </div>
        </div><!--end col-->
        <div class="page-size d-md-flex align-items-center text-center justify-content-center">
            Show 
            <select onchange="updatePageSize()" id="changePageSize">
                <option value="1" ${pagination.size == 1 ? 'selected' : ''}>1</option>
                <option value="3" ${pagination.size == 3 ? 'selected' : ''}>3</option>
                <option value="5" ${pagination.size == 5 ? 'selected' : ''}>5</option>
                <option value="7" ${pagination.size == 7 ? 'selected' : ''}>7</option>
                <option value="10" ${pagination.size == 10 ? 'selected' : ''}>10</option>
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
                        Are you sure you want to delete this?
                    </div>
                    <div class="modal-footer">
                        <form id="deleteForm" action="delete-product" method="post">
                            <input type="hidden" name="id" id="productIdDelete" />
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
                    const categoryId = this.getAttribute('data-whatever');
                    document.getElementById('productIdDelete').setAttribute("value", categoryId);
                    document.getElementById('buttonDelete').setAttribute("href", "delete-product?id=" + categoryId);
                });
            });
        </script>
        <script>
            const minRange = document.getElementById("minPriceRange");
            const maxRange = document.getElementById("maxPriceRange");
            const minInput = document.getElementById("minPriceInput");
            const maxInput = document.getElementById("maxPriceInput");

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
                params.append('size', size);
                params.append('page', 1);

                document.querySelectorAll('select[name="categoryIds"] option:checked').forEach(option => {
                    params.append('categoryIds', option.value);
                });
                console.log(params.toString());
                location.href = 'product?' + params.toString();
            }

        </script>
    </body>
    <div class="clearfix"> </div>
    <jsp:include page="../Footer.jsp" />
</html>
