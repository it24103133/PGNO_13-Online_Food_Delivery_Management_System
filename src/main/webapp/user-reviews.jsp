<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>--%>
<!DOCTYPE html>
<html>
<head>
    <title>My Reviews - FoodExpress</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/review.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/navbar.jsp" />

<main class="container my-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>My Reviews</h1>
        <a href="${pageContext.request.contextPath}/orders" class="btn btn-outline-primary">
            <i class="fas fa-arrow-left me-1"></i> Back to Orders
        </a>
    </div>

    <c:if test="${param.updated == 'true'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Your review has been updated successfully!
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <c:if test="${param.deleted == 'true'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Your review has been deleted successfully!
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <c:choose>
        <c:when test="${not empty reviews}">
            <div class="card">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Order ID</th>
                                <th>Date</th>
                                <th>Food Rating</th>
                                <th>Delivery Rating</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${reviews}" var="review">
                                <tr>
                                    <td>${review.orderId}</td>
                                    <td><fmt:formatDate value="${review.reviewDate}" pattern="dd MMM yyyy" /></td>
                                    <td>
                                        <c:forEach begin="1" end="5" var="i">
                                            <i class="fas fa-star${i <= review.foodRating ? '' : '-half-alt'} text-warning"></i>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:forEach begin="1" end="5" var="i">
                                            <i class="fas fa-star${i <= review.deliveryRating ? '' : '-half-alt'} text-warning"></i>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <button class="btn btn-sm btn-outline-primary"
                                                data-bs-toggle="modal"
                                                data-bs-target="#reviewDetails${review.reviewId}">
                                            <i class="fas fa-eye"></i> View
                                        </button>
                                    </td>
                                </tr>

                                <!-- Review Details Modal -->
                                <div class="modal fade" id="reviewDetails${review.reviewId}" tabindex="-1">
                                    <div class="modal-dialog modal-lg">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title">Review Details</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                            </div>
                                            <div class="modal-body">
                                                <jsp:include page="/review-card.jsp">
                                                    <jsp:param name="reviewId" value="${review.reviewId}" />
                                                    <jsp:param name="orderId" value="${review.orderId}" />
                                                    <jsp:param name="foodRating" value="${review.foodRating}" />
                                                    <jsp:param name="deliveryRating" value="${review.deliveryRating}" />
                                                    <jsp:param name="comments" value="${review.comments}" />
                                                    <jsp:param name="reviewDate" value="${review.reviewDate}" />
                                                    <jsp:param name="editable" value="true" />
                                                </jsp:include>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="text-center py-5">
                <i class="fas fa-comment-slash fa-4x text-muted mb-4"></i>
                <h3>No Reviews Yet</h3>
                <p class="text-muted">You haven't submitted any reviews yet.</p>
                <a href="${pageContext.request.contextPath}/orders" class="btn btn-primary">
                    <i class="fas fa-utensils me-1"></i> View Orders
                </a>
            </div>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="/footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/review.js"></script>
</body>
</html>

