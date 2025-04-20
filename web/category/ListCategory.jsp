<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Category List</title>
        <jsp:include page="../Header.jsp"/>
    </head>
    <body>
        <h2>Category List</h2>

        <form method="get" action="category">
            <input type="hidden" name="action" value="list" />

            <input type="text" name="text" placeholder="Search by name" value="${text}" />

            <select name="typeId">
                <option value="">-- Category Type --</option>
                <c:forEach var="type" items="${categoryTypes}">
                    <option value="${type.categoryTypeId}" 
                            <c:if test="${typeId != null and typeId == type.categoryTypeId}">selected</c:if>>
                        ${type.categoryTypeName}
                    </option>
                </c:forEach>
            </select>

            <select name="status">
                <option value="">-- Status --</option>
                <option value="1" ${status == 1 ? 'selected' : ''}>Active</option>
                <option value="0" ${status == 0 ? 'selected' : ''}>Inactive</option>
            </select>

            <button type="submit">Search</button>
        </form>

        <br/>

        <!-- Table -->
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Status</th>
                    <th>Create At</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody class="table-border-bottom-0">
                <c:set var="startIndex" value="${(pagination.page - 1) * pagination.size}" />
                <c:forEach var="cate" items="${listCategories}" varStatus="loop">
                    <tr>
                        <td>${startIndex + loop.index + 1}</td>
                        <td>${cate.categoryName}</td>
                        <td>${cate.categoryTypeName}</td>
                        <td><c:choose>
                                <c:when test="${cate.status == 1}"><span class="badge bg-label-success me-1">Active</span></c:when>
                                <c:otherwise><span class="badge bg-label-danger me-1">Inactive</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td>${cate.createAt}</td>
                        <td>
                            <div class="dropdown">
                                <button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown">
                                    <i class="bx bx-dots-vertical-rounded"></i>
                                </button>
                                <div class="dropdown-menu">
                                    <a class="dropdown-item" href="javascript:void(0);"
                                       ><i class="bx bx-edit-alt me-1"></i> Edit</a
                                    >
                                    <a class="dropdown-item" href="javascript:void(0);"
                                       ><i class="bx bx-trash me-1"></i> Delete</a
                                    >
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
       
    </body>
     <div class="clearfix"> </div>
        <jsp:include page="../Footer.jsp" />
</html>
