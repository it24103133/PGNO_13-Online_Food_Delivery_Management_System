<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>--%>
<!DOCTYPE html>
<html>
<head>
    <title>Restaurant Reviews - FoodExpress</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/review.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/navbar.jsp" />

<main class="container my-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>Customer Reviews</h1>
        <a href="${pageContext.request.contextPath}/restaurants/view?id=${restaurantId}"
           class="btn btn-outline-primary">
            <i class="fas fa-arrow-left me-1"></i> Back to Restaurant
        </a>
    </div>

    <c:choose>
        <c:when test="${not empty reviews}">
            <div class="row">
                <div class="col-md-4">
                    <div class="card mb-4">
                        <div class="card-body text-center">
                            <h3 class="mb-3">Overall Rating</h3>
                            <div class="display-4 mb-2 text-warning">
                                <fmt:formatNumber value="${averageRating}" pattern="0.0" />
                            </div>
                            <div class="mb-3">
                                <c:forEach begin="1" end="5" var="i">
                                    <i class="fas fa-star${i <= averageRating ? '' : '-half-alt'} text-warning"></i>
                                </c:forEach>
                            </div>
                            <p class="text-muted">Based on ${reviews.size()} reviews</p>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <h5 class="mb-3">Rating Breakdown</h5>
                            <c:forEach begin="5" end="1" step="-1" var="i">
                                <div class="row align-items-center mb-2">
                                    <div class="col-2 text-end">
                                        <span>${i} <i class="fas fa-star text-warning"></i></span>
                                    </div>
                                    <div class="col-7">
                                        <div class="progress" style="height: 10px;">
                                            <div class="progress-bar bg-warning"
                                                 style="width: ${(ratingCounts[i] / reviews.size()) * 100}%"></div>
                                        </div>
                                    </div>
                                    <div class="col-3">
                                        <span class="text-muted">${ratingCounts[i]}</span>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>

                <div class="col-md-8">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="mb-4">All Reviews</h5>
                            <c:forEach items="${reviews}" var="review">
                                <jsp:include page="/review-card.jsp">
                                    <jsp:param name="reviewId" value="${review.reviewId}" />
                                    <jsp:param name="orderId" value="${review.orderId}" />
                                    <jsp:param name="foodRating" value="${review.foodRating}" />
                                    <jsp:param name="deliveryRating" value="${review.deliveryRating}" />
                                    <jsp:param name="comments" value="${review.comments}" />
                                    <jsp:param name="reviewDate" value="${review.reviewDate}" />
                                    <jsp:param name="editable" value="false" />
                                </jsp:include>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="text-center py-5">
                <i class="fas fa-comment-slash fa-4x text-muted mb-4"></i>
                <h3>No Reviews Yet</h3>
                <p class="text-muted">This restaurant doesn't have any reviews yet.</p>
            </div>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="/footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

