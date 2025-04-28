<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
    <head>
        <title>Feedback Management</title>
        <jsp:include page="../Header.jsp"/>
    </head>
    <body>
        <!-- Messages -->
        <div class="container mt-4">
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="bi bi-check-circle-fill me-2"></i>
                    ${successMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% session.removeAttribute("successMessage"); %>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="bi bi-exclamation-circle-fill me-2"></i>
                    ${errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% session.removeAttribute("errorMessage"); %>
            </c:if>

            <!-- Feedback List -->
            <div class="card">
                <div class="card-header">
                    <h3 class="card-title">Feedback Management</h3>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Product</th>
                                    <th>User</th>
                                    <th>Rating</th>
                                    <th>Comment</th>
                                    <th>Date</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="feedback" items="${feedbacks}">
                                    <tr>
                                        <td>${feedback.feedbackId}</td>
                                        <td>${feedback.productName}</td>
                                        <td>${feedback.userName}</td>
                                        <td>
                                            <div class="rating">
                                                <c:forEach begin="1" end="5" var="i">
                                                    <i class='bx ${i <= feedback.feedbackRating ? "bxs-star" : "bx-star"}'></i>
                                                </c:forEach>
                                            </div>
                                        </td>
                                        <td>${feedback.feedbackComment}</td>
                                        <td><fmt:formatDate value="${feedback.createAt}" pattern="dd-MM-yyyy HH:mm"/></td>
                                <td>
                                    <button class="btn btn-sm btn-danger" 
                                            onclick="deleteFeedback(${feedback.feedbackId})">
                                        <i class='bx bx-trash'></i>
                                    </button>
                                </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="deleteModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Confirm Delete</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        Are you sure you want to delete this feedback?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-danger" id="confirmDelete">Delete</button>
                    </div>
                </div>
            </div>
        </div>

        <script>
            let feedbackToDelete = null;
            const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));

            function deleteFeedback(feedbackId) {
                feedbackToDelete = feedbackId;
                deleteModal.show();
            }

            document.getElementById('confirmDelete').addEventListener('click', function () {
                if (feedbackToDelete) {
                    window.location.href = 'delete-feedback?id=' + feedbackToDelete;
                }
                deleteModal.hide();
            });
        </script>

        <jsp:include page="../Footer.jsp"/>
    </body>
</html>