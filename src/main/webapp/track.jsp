<%--@elvariable id="progressWidth" type="jakarta"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Track Order #${order.id} - Food Delivery System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/orders.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../partials/navbar.jsp" />

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="card shadow-sm">
                <div class="card-header bg-white">
                    <div class="d-flex justify-content-between align-items-center">
                        <h4 class="mb-0">Order #${order.id}</h4>
                        <span class="status-badge status-${order.status.toLowerCase().replace('_', '-')}">
                            ${order.status.replace('_', ' ')}
                        </span>
                    </div>
                </div>
                <div class="card-body">
                    <!-- Tracking Progress -->
                    <div class="tracking-progress">
                        <div class="tracking-progress-bar" style="width: 0;"></div>
                    </div>
                    <div class="tracking-steps">
                        <div class="tracking-step" data-status="PENDING">
                            <div class="step-icon">
                                <i class="fas fa-receipt"></i>
                            </div>
                            <small>Order Placed</small>
                        </div>
                        <div class="tracking-step" data-status="PREPARING">
                            <div class="step-icon">
                                <i class="fas fa-utensils"></i>
                            </div>
                            <small>Preparing</small>
                        </div>
                        <div class="tracking-step" data-status="ON_THE_WAY">
                            <div class="step-icon">
                                <i class="fas fa-motorcycle"></i>
                            </div>
                            <small>On The Way</small>
                        </div>
                        <div class="tracking-step" data-status="DELIVERED">
                            <div class="step-icon">
                                <i class="fas fa-check-circle"></i>
                            </div>
                            <small>Delivered</small>
                        </div>
                    </div>

                    <!-- Delivery Information -->
                    <div class="mt-5">
                        <h5><i class="fas fa-truck me-2"></i>Delivery Information</h5>
                        <div class="row mt-3">
                            <div class="col-md-6">
                                <p><strong>Delivery Address:</strong><br>
                                    ${order.deliveryAddress}</p>
                            </div>
                            <div class="col-md-6">
                                <p><strong>Estimated Delivery Time:</strong><br>
                                    <c:choose>
                                        <c:when test="${order.status == 'PENDING'}">
                                            Preparing estimate
                                        </c:when>
                                        <c:when test="${order.status == 'PREPARING'}">
                                            About 30-45 minutes
                                        </c:when>
                                        <c:when test="${order.status == 'ON_THE_WAY'}">
                                            Less than 15 minutes
                                        </c:when>
                                        <c:when test="${order.status == 'DELIVERED'}">
                                            Delivered at ${order.createdAt}
                                        </c:when>
                                        <c:otherwise>
                                            Not available
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                            </div>
                        </div>
                        <c:if test="${not empty order.deliveryInstructions}">
                            <p><strong>Special Instructions:</strong><br>
                                    ${order.deliveryInstructions}</p>
                        </c:if>
                    </div>

                    <!-- Action Buttons -->
                    <div class="d-flex justify-content-between mt-4">
                        <a href="${pageContext.request.contextPath}/orders/details?id=${order.id}"
                           class="btn btn-outline-secondary">
                            <i class="fas fa-receipt me-2"></i>View Details
                        </a>
                        <c:if test="${order.status != 'DELIVERED' && order.status != 'CANCELLED'}">
                            <form method="post" action="${pageContext.request.contextPath}/orders/cancel"
                                  onsubmit="return confirm('Are you sure you want to cancel this order?');">
                                <input type="hidden" name="id" value="${order.id}">
                                <button type="submit" class="btn btn-outline-danger">
                                    <i class="fas fa-times-circle me-2"></i>Cancel Order
                                </button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../partials/footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Initialize tracking status
    document.addEventListener('DOMContentLoaded', function() {
        const status = '${order.status}';
        updateTrackingProgress(status);

        function updateTrackingProgress(status) {
            const steps = document.querySelectorAll('.tracking-step');
            const progressBar = document.querySelector('.tracking-progress-bar');

            // Reset all steps
            steps.forEach(step => {
                step.classList.remove('active', 'completed');
            });

            // Update based on status
            let progressWidth = 0;
            switch(status) {
                case 'PENDING':
                    progressWidth = 0;
                    steps[0].classList.add('active');
                    break;
                case 'PREPARING':
                    progressWidth = 33;
                    steps[0].classList.add('completed');
                    steps[1].classList.add('active');
                    break;
                case 'ON_THE_WAY':
                    progressWidth = 66;
                    steps[0].classList.add('completed');
                    steps[1].classList.add('completed');
                    steps[2].classList.add('active');
                    break;
                case 'DELIVERED':
                    progressWidth = 100;
                    steps.forEach(step => step.classList.add('completed'));
                    break;
                case 'CANCELLED':
                    progressWidth = 0;
                    break;
            }

            if (progressBar) {
                progressBar.style.width = `${progressWidth}%`;
            }
        }
    });
</script>
</body>
</html>
