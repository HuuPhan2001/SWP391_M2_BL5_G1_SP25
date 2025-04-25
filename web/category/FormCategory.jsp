<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>${category != null ? "Udpate" : "Add"} Category</title>
        <jsp:include page="../Header.jsp"/>
    </head>
    <body>
        <div id="message" class="message-container">
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success alert-dismissible show" role="alert">
                    <i class="bi bi-check-circle-fill me-2"></i>
                    ${successMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% 
    session.removeAttribute("successMessage"); 
                %>
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

        <input type="text" hidden="true" id="failedCategoryJson" value='${failedCategoryJson}'/>
        <div class="card mb-4">
            <div class="card-header d-flex align-items-center justify-content-between">
                <h3 class="mb-0">${category != null ? "Udpate" : "Add"} Category</h3>
            </div>
            <div class="card-body">
                <form id="categoryForm" action="${category != null ? 'update-category' : 'create-category'}" method="post">
                    <c:if test="${category != null}">
                        <input type="hidden" name="id" value="${category.categoryId}" />
                    </c:if>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label" for="typeId">Type</label>
                        <div class="col-sm-10">
                            <select name="typeId" id="typeId" class="form-select form-select-lg" required>
                                <c:forEach var="type" items="${categoryTypes}">
                                    <option value="${type.categoryTypeId}"
                                            <c:if test="${category != null && category.categoryTypeId == type.categoryTypeId}">selected</c:if>>
                                        ${type.categoryTypeName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label" for="name" >Name<span style="color: red">*</span></label>
                        <div class="col-sm-10">
                            <input type="text" id="name" class="form-control" name="name" 
                                   value="${sessionScope.formData.categoryName != null ? sessionScope.formData.categoryName : (category != null ? category.categoryName : '')}" 
                                   required />
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label" for="description">Description</label>
                        <div class="col-sm-10">
                            <textarea
                                name="description"
                                id="description"
                                class="form-control"
                                aria-describedby="basic-icon-default-message2"
                                >${sessionScope.formData.categoryDesc != null ? sessionScope.formData.categoryDesc : (category != null ? category.categoryDesc : '')}</textarea>
                        </div>
                    </div>

                    <!--            <label>Parent (ID):</label>
                                <input type="number" name="parent" value="${category != null ? category.parent : ''}" />
                                <br/>-->

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label" for="category-banner">Banner</label>
                        <div class="col-sm-10">
                            <input type="text" id="category-banner" class="form-control" name="banner" 
                                   value="${sessionScope.formData.categoryBanner != null ? sessionScope.formData.categoryBanner : (category != null ? category.categoryBanner : '')}
                                   " 
                                   />
                        </div>
                    </div>

                    <div class="row mb-3 align-items-center">
                        <label class="col-sm-2 col-form-label mb-0" for="status">Status</label>
                        <div class="col-sm-10 form-check mb-0">
                            <input 
                                name="status"
                                class="form-check-input" 
                                type="checkbox" 
                                value="1"
                                ${sessionScope.formData.status != null ? sessionScope.formData.status : (category != null && category.status == 1 ? 'checked' : '')}
                                ${sessionScope.formData.status == null ? 'checked' : (category == null ? 'checked' : '')}
                                id="status" />
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
                    <% session.removeAttribute("formData"); %>
                </form>
            </div>
        </div>
    </body>
    <script>
        document.getElementById('cancelButton').addEventListener('click', function () {
            window.location.href = 'list-category';
        });

        document.getElementById('resetButton').addEventListener('click', function () {
            document.getElementById('categoryForm').reset();

        });
    </script>

    <div class="clearfix"> </div>
    <jsp:include page="../Footer.jsp" />
</html>
