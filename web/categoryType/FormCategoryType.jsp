<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>${categoryType != null ? "Udpate" : "Add"} CategoryType Type</title>
        <jsp:include page="../Header.jsp"/>
    </head>
    <body>

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

        <input type="text" hidden="true" id="failedCategoryJson" value='${failedCategoryJson}'/>

        <div class="card mb-4">
            <div class="card-header d-flex align-items-center justify-content-between">
                <h3 class="mb-0">${category != null ? "Udpate" : "Add"} Category Type</h3>
            </div>
            <div class="card-body">
                <form id="categoryForm" action="${categoryType != null ? 'update-category-type' : 'create-category-type'}" method="post">
                    <c:if test="${categoryType != null}">
                        <input type="hidden" name="id" value="${categoryType.categoryTypeId}" />
                    </c:if>


                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label" for="name">Name</label>
                        <div class="col-sm-10">
                            <input type="text" id="name" class="form-control" name="typeName" value="${categoryType != null ? categoryType.categoryTypeName : ''}" required />
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label" for="description">Description</label>
                        <div class="col-sm-10">
                            <textarea
                                name="typeDesc"
                                id="description"
                                class="form-control"
                                aria-describedby="basic-icon-default-message2"
                                >${categoryType != null ? categoryType.categoryTypeDesc : ''}</textarea>
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
                                ${categoryType != null && categoryType.status == 1 ? 'checked' : ''}
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
                </form>
            </div>
        </div>
    </body>
    <script>
        document.getElementById('cancelButton').addEventListener('click', function () {
            window.location.href = 'list-category-type';
        });

        document.getElementById('resetButton').addEventListener('click', function () {
            document.getElementById('categoryForm').reset();

        });
    </script>

    <div class="clearfix"> </div>
    <jsp:include page="../Footer.jsp" />
</html>
