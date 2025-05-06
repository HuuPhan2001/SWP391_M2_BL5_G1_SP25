<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <title>${product != null ? "Udpate" : "Add"} Product</title>
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

        <input type="text" hidden="true" id="failedProductJson" value='${failedProductJson}'/>
        <div class="card mb-4">
            <div class="card-header d-flex align-items-center justify-content-between">
                <h3 class="mb-0">${product != null ? "Udpate" : "Add"} Product</h3>
            </div>
            <div class="card-body">
                <form id="productForm" action="${product != null ? 'update-product' : 'create-product'}" method="post" enctype="multipart/form-data">
                    <c:if test="${product != null}">
                        <input type="hidden" name="id" value="${product.productId}" />
                    </c:if>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label" for="name" >Name<span style="color: red">*</span></label>
                        <div class="col-sm-10">
                            <input type="text" id="name" class="form-control" name="name" 
                                   value="${sessionScope.formData.productName != null ? sessionScope.formData.productName : (product != null ? product.productName : '')}" 
                                   required />
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label" for="desc">Description</label>
                        <div class="col-sm-10">
                            <textarea
                                name="desc"
                                id="desc"
                                class="form-control"
                                aria-describedby="basic-icon-default-message2"
                                >${sessionScope.formData.productDesc != null ? sessionScope.formData.productDesc : (product != null ? product.productDesc : '')}</textarea>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label" for="price" >Price<span style="color: red">*</span></label>
                        <div class="col-sm-10">
                            <input type="number" id="price" class="form-control" name="price" min="0" max="999999999"
                                   value="${sessionScope.formData.productPrice != null ? sessionScope.formData.productPrice : (product != null ? product.productPrice : 0)}" 
                                   required />
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label" for="quantity" >Quantity<span style="color: red">*</span></label>
                        <div class="col-sm-10">
                            <input type="number" id="quantity" class="form-control" name="quantity" min="0" max="999999999"
                                   value="${sessionScope.formData.productQuantity != null ? sessionScope.formData.productQuantity : (product != null ? product.productQuantity : 0)}" 
                                   required />
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label" for="avatar">Avatar</label>
                        <div class="col-sm-10">
                            <input type="file" id="avatar" class="form-control" name="avatar" accept="image/*" />
                            <c:if test="${not empty product.productAvatar}">
                                <img id="avatarPreview" src="${product.productAvatar}" 
                                     class="avatar-preview" alt="Avatar Preview" />
                            </c:if>
                        </div>
                    </div>


                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label" for="image">Images</label>
                        <div class="col-sm-10">
                            <input type="file" id="images" class="form-control" name="images" multiple accept="image/*" />
                            <div class="product-image-container" id="imagesPreview">
                                <c:if test="${not empty productImages}">
                                    <c:forEach var="img" items="${productImages}">
                                        <img src="${img.productImage}" alt="Product Image" class="product-image-preview" id="imgPrev"/>
                                    </c:forEach>
                                </c:if>
                            </div>
                        </div>
                    </div>

                    <c:if test="${product != null}" >
                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label" for="imageAdditional">Additional Images</label>
                            <div class="col-sm-10">
                                <input type="file" id="imageAdditional" class="form-control" name="images" multiple accept="image/*"/>
                                <div class="product-image-container" id="imageAdditionalPreview">

                                </div>

                            </div>
                        </div>
                    </c:if>
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label" for="typeId">Category</label>
                        <div class="col-sm-10">
                            <c:forEach var="cate" items="${categories}">
                                <c:set var="isChecked" value="false" />
                                <c:forEach var="id" items="${selectedCategoryIds}">
                                    <c:if test="${id == cate.categoryId}">
                                        <c:set var="isChecked" value="true" />
                                    </c:if>
                                </c:forEach>
                                <label>
                                    <input type="checkbox" name="categoryIds" value="${cate.categoryId}" 
                                           <c:if test="${isChecked}">checked</c:if> />
                                    ${cate.categoryName}
                                </label>
                            </c:forEach>
                            </select>
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
                                ${sessionScope.formData.status != null ? sessionScope.formData.status : (product != null && product.status == 1 ? 'checked' : '')}
                                ${sessionScope.formData.status == null ? 'checked' : (product == null ? 'checked' : '')}
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
            window.location.href = 'list-product';
        });

        document.getElementById('resetButton').addEventListener('click', function () {
            document.getElementById('productForm').reset();

        });

        document.getElementById('avatar').addEventListener('change', function (e) {
            const file = e.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    const preview = document.getElementById('avatarPreview');
                    preview.src = e.target.result;
                }
                reader.readAsDataURL(file);
            }
        });

        const imagesInput = document.getElementById('images');
        if (imagesInput) {
            imagesInput.addEventListener('change', function (e) {
                const files = Array.from(e.target.files);
                const container = document.getElementById('imagesPreview');
                if (container) {
                    container.innerHTML = '';

                    files.forEach(file => {
                        const reader = new FileReader();
                        reader.onload = function (e) {
                            const img = document.createElement('img');
                            img.src = e.target.result;
                            img.className = 'product-image-preview';
                            container.appendChild(img);
                        };
                        reader.readAsDataURL(file);
                    });
                }
            });
        }

        const additionalImagesInput = document.getElementById('imageAdditional');
        if (additionalImagesInput) {
            additionalImagesInput.addEventListener('change', function (e) {
                const files = Array.from(e.target.files);
                const container = document.getElementById('imageAdditionalPreview');
                if (container) {
                    container.innerHTML = '';

                    files.forEach(file => {
                        const reader = new FileReader();
                        reader.onload = function (e) {
                            const img = document.createElement('img');
                            img.src = e.target.result;
                            img.className = 'product-image-preview';
                            container.appendChild(img);
                        };
                        reader.readAsDataURL(file);
                    });
                }
            });
        }
    </script>

    <div class="clearfix"> </div>
    <jsp:include page="../Footer.jsp" />
</html>
