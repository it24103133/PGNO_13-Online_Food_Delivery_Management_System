<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Submit Review - FoodExpress</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/review.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/navbar.jsp" />

<main class="container my-5">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h2 class="mb-0">Share Your Experience</h2>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/reviews/submit" method="post">
                        <input type="hidden" name="orderId" value="${orderId}">
                        <input type="hidden" name="restaurantId" value="${restaurantId}">

                        <div class="mb-4">
                            <h4>How was the food?</h4>
                            <jsp:include page="/rating-stars.jsp">
                                <jsp:param name="ratingType" value="food" />
                            </jsp:include>
                        </div>

                        <div class="mb-4">
                            <h4>How was the delivery?</h4>
                            <jsp:include page="/rating-stars.jsp">
                                <jsp:param name="ratingType" value="delivery" />
                            </jsp:include>
                        </div>

                        <div class="mb-4">
                            <label for="comments" class="form-label">Additional Comments</label>
                            <textarea class="form-control" id="comments" name="comments"
                                      rows="4" placeholder="Share details about your experience..."></textarea>
                        </div>

                        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                            <a href="${pageContext.request.contextPath}/orders" class="btn btn-secondary me-md-2">
                                <i class="fas fa-times me-1"></i> Cancel
                            </a>
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-paper-plane me-1"></i> Submit Review
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="/footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/review.js"></script>
</body>
</html>