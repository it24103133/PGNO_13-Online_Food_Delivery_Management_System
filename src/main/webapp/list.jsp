<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Orders - Food Delivery System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/orders.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../partials/navbar.jsp" />

<div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>My Orders</h1>
        <a href="${pageContext.request.contextPath}/restaurants" class="btn btn-outline-primary">
            <i class="fas fa-plus me-2"></i>New Order
        </a>
    </div>

    <c:if test="${not empty orders}">
        <div class="list-group">
            <c:forEach items="${orders}" var="order">
                <a href="${pageContext.request.contextPath}/orders/details?id=${order.id}"
                   class="list-group-item list-group-item-action">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1">Order #${order.id}</h5>
                        <small class="text-${order.status == 'CANCELLED' ? 'danger' :
                                              order.status == 'DELIVERED' ? 'success' : 'primary'}">
                                ${order.status.replace('_', ' ')}
                        </small>
                    </div>
                    <p class="mb-1">${order.items.size()} items</p>
                    <small>Placed on ${order.createdAt}</small>
                    <div class="d-flex justify-content-between mt-2">
                        <strong>$${String.format("%.2f", order.totalAmount)}</strong>
                        <span class="badge bg-light text-dark">
                                <i class="fas fa-${order.paymentMethod.toLowerCase().contains('cash') ? 'money-bill-wave' : 'credit-card'} me-1"></i>
                                ${order.paymentMethod}
                            </span>
                    </div>
                </a>
            </c:forEach>
        </div>
    </c:if>

    <c:if test="${empty orders}">
        <div class="card shadow-sm">
            <div class="card-body text-center py-5">
                <i class="fas fa-shopping-bag fa-4x text-muted mb-4"></i>
                <h4>No orders yet</h4>
                <p class="text-muted">When you place an order, it will appear here</p>
                <a href="${pageContext.request.contextPath}/restaurants" class="btn btn-primary mt-3">
                    Browse Restaurants
                </a>
            </div>
        </div>
    </c:if>
</div>

<jsp:include page="../partials/footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

