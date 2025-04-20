<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${category != null ? "Udpate" : "Add"} Category</title>
</head>
<body>

<h2>${category != null ? "Udpate" : "Add"} Category</h2>

<form action="${category != null ? 'update-category' : 'add-category'}" method="post">
    <c:if test="${category != null}">
        <input type="hidden" name="category_id" value="${category.categoryId}" />
    </c:if>

    <label>Category Type:</label>
    <select name="category_type_id" required>
        <c:forEach var="type" items="${categoryTypes}">
            <option value="${type.categoryTypeId}"
                <c:if test="${category != null && category.categoryTypeId == type.categoryTypeId}">selected</c:if>>
                ${type.categoryTypeName}
            </option>
        </c:forEach>
    </select>
    <br/>

    <label>Category Name:</label>
    <input type="text" name="category_name" value="${category != null ? category.categoryName : ''}" required />
    <br/>

    <label>Description:</label>
    <textarea name="category_desc">${category != null ? category.categoryDesc : ''}</textarea>
    <br/>

    <label>Parent (ID):</label>
    <input type="number" name="parent" value="${category != null ? category.parent : ''}" />
    <br/>

    <label>Banner:</label>
    <input type="text" name="category_banner" value="${category != null ? category.categoryBanner : ''}" />
    <br/>

    <label>Status:</label>
    <select name="status">
        <option value="1" ${category != null && category.status == 1 ? "selected" : ""}>Active</option>
        <option value="0" ${category != null && category.status == 0 ? "selected" : ""}>InActive</option>
    </select>
    <br/>

    <button type="submit">${category != null ? "Udpate" : "Add new"}</button>
</form>

</body>
</html>
