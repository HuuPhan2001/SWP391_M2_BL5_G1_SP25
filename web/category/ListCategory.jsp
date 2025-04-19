<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Category List</title>
        <style>
            table, th, td {
                border: 1px solid black;
                border-collapse: collapse;
                padding: 8px;
            }
            .pagination a {
                margin: 0 5px;
                text-decoration: none;
            }
            .pagination .current {
                font-weight: bold;
                text-decoration: underline;
            }
        </style>
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
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Status</th>
                    <th>Create At</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="startIndex" value="${(pagination.page - 1) * pagination.size}" />
                <c:forEach var="cate" items="${listCategories}" varStatus="loop">
                    <tr>
                        <td>${startIndex + loop.index + 1}</td>
                        <td>${cate.categoryName}</td>
                        <td>${cate.categoryTypeName}</td>
                        <td><c:choose>
                                <c:when test="${cate.status == 1}">Active</c:when>
                                <c:otherwise>Inactive</c:otherwise>
                            </c:choose>
                        </td>
                        <td>${cate.createAt}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
