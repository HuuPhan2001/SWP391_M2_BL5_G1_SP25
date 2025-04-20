<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Category Type List</title>
        <jsp:include page="../Header.jsp"/>
    </head>
    <body>
        </br></br></br>
        <h2 class="text-center mb-4">Category List</h2>
        <div class="row">
            <form method="get" action="category-type">
                <input type="hidden" name="action" value="list" />

                <div class="col-md-5">
                    <input class="form-control" type="text" name="text" placeholder="Search by name" value="${text}" />
                </div>
                
                <div class="col-md-3">
                    <select name="status" class="form-select" >
                        <option value="">-- Status --</option>
                        <option value="1" ${status == 1 ? 'selected' : ''}>Active</option>
                        <option value="0" ${status == 0 ? 'selected' : ''}>Inactive</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-secondary">Search</button>
                </div>
                <div class="col-md-2">
                    <a href="<%=request.getContextPath()%>/new-category-type"><button type="button" class="btn btn-success " style="">Add New</button></a>
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
            <table class="table">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Name</th>
                        <th>Status</th>
                        <th>Create At</th>
                        <th>Update At</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody class="table-border-bottom-0">
                    <c:set var="startIndex" value="${(pagination.page - 1) * pagination.size}" />
                    <c:forEach var="type" items="${categoryTypes}" varStatus="loop">
                        <tr>
                            <td>${startIndex + loop.index + 1}</td>
                            <td>${type.categoryTypeName}</td>
                            <td><c:choose>
                                    <c:when test="${type.status == 1}"><span class="badge bg-label-success me-1">Active</span></c:when>
                                    <c:otherwise><span class="badge bg-label-danger me-1">Inactive</span></c:otherwise>
                                </c:choose>
                            </td>
                            <td>${type.createAt}</td>
                            <td>${type.updateAt}</td>
                            <td>
                                <div class="dropdown">
                                    <button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown">
                                        <i class="bx bx-dots-vertical-rounded"></i>
                                    </button>
                                    <div class="dropdown-menu">
                                        <a class="dropdown-item" href="edit-category-type?id=${type.categoryTypeId}"
                                           ><i class="bx bx-edit-alt me-1"></i> Edit</a
                                        >
                                        <!--                                        <button class="dropdown-item" type="button" data-bs-toggle="modal" 
                                                                                        data-bs-target="#confirmModal"
                                                                                        onclick="setDeleteId(${type.categoryTypeId})">
                                                                                    <i class="bx bx-trash me-1"></i> Delete</button>-->
                                        <form id="deleteForm" action="delete-category-type" method="post">
                                            <input type="hidden" name="id" id="categoryTypeIdDelete" value="${type.categoryTypeId}" />
                                            <button type="submit" class="dropdown-item"><i class="bx bx-trash me-1"></i>Delete</button>
                                        </form>

                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
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
                                    <a class="page-link" href="category-type?page=1&size=${pagination.size}&text=${text}&status=${status}">First</a>
                                </li>
                                <li class="page-item">
                                    <a class="page-link" href="category-type?page=${pagination.page - 1}&size=${pagination.size}&text=${text}&status=${status}">Previous</a>
                                </li>
                            </c:if>

                            <c:forEach begin="1" end="${pagination.totalPages}" var="pageNumber">
                                <c:choose>
                                    <c:when test="${pageNumber == pagination.page}">
                                        <li class="page-item active">
                                            <span class="page-link">${pageNumber}</span>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item">
                                            <a class="page-link" href="category-type?page=${pageNumber}&size=${pagination.size}&text=${text}&status=${status}">${pageNumber}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                            <c:if test="${pagination.page < pagination.totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="category-type?page=${pagination.page + 1}&size=${pagination.size}&text=${text}&status=${status}">Next</a>
                                </li>
                                <li class="page-item">
                                    <a class="page-link" href="category-type?page=${pagination.totalPages}&size=${pagination.size}&text=${text}&status=${status}">Last</a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </div>
        </div><!--end col-->
        <div class="page-size d-md-flex align-items-center text-center justify-content-center">
            Show 
            <select onchange="location.href = 'category-type?page=1&size=' + this.value + '&text=${text}&status=${status}'">
                <option value="1" ${pagination.size == 1 ? 'selected' : ''}>1</option>
                <option value="3" ${pagination.size == 3 ? 'selected' : ''}>3</option>
                <option value="5" ${pagination.size == 5 ? 'selected' : ''}>5</option>
                <option value="7" ${pagination.size == 7 ? 'selected' : ''}>7</option>
                <option value="10" ${pagination.size == 10 ? 'selected' : ''}>10</option>
            </select>
            entries
        </div>

        <!-- Modal -->
        <div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
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
                        <form id="deleteForm" action="delete-categoryType" method="post">
                            <input type="hidden" name="id" id="categoryTypeIdDelete" />
                            <button type="submit" class="btn btn-danger">Yes</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function setDeleteId(id) {
                document.getElementById("categoryTypeIdDelete").value = id;
            }
        </script>
    </body>
    <div class="clearfix"> </div>
    <jsp:include page="../Footer.jsp" />
</html>
