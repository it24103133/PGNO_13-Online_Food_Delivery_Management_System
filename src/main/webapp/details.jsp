<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order #${order.id} Details - Food Delivery System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/orders.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../partials/navbar.jsp" />

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-lg-10">
            <div class="card shadow-sm">
                <div class="card-header bg-white">
                    <div class="d-flex justify-content-between align-items-center">
                        <h4 class="mb-0">Order #${order.id}</h4>
                        <div>
                                <span class="status-badge status-${order.status.toLowerCase().replace('_', '-')} me-2">
                                    ${order.status.replace('_', ' ')}
                                </span>
                            <a href="${pageContext.request.contextPath}/orders/track?id=${order.id}"
                               class="btn btn-sm btn-outline-primary">
                                <i class="fas fa-map-marker-alt me-1"></i>Track
                            </a>
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <!-- Restaurant Info -->
                    <div class="mb-4 p-3 bg-light rounded">
                        <div class="d-flex align-items-center">
                            <img src="${restaurant.imageUrl}" alt="${restaurant.name}"
                                 class="rounded me-3" width="60" height="60">
                            <div>
                                <h5 class="mb-1">${restaurant.name}</h5>
                                <p class="text-muted mb-0">${restaurant.cuisineType}</p>
                            </div>
                        </div>
                    </div>

                    <!-- Order Items -->
                    <h5 class="mb-3"><i class="fas fa-utensils me-2"></i>Order Items</h5>
                    <div class="table-responsive mb-4">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Item</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Subtotal</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${order.items}" var="item">
                                <tr>
                                    <td>
                                            ${item.name}
                                        <c:if test="${not empty item.specialInstructions}">
                                            <div class="text-muted small mt-1">
                                                <i class="fas fa-info-circle me-1"></i>${item.specialInstructions}
                                            </div>
                                        </c:if>
                                    </td>
                                    <td>$${String.format("%.2f", item.price)}</td>
                                    <td>${item.quantity}</td>
                                    <td>$${String.format("%.2f", item.price * item.quantity)}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                            <tfoot>
                            <tr>
                                <th colspan="3" class="text-end">Subtotal:</th>
                                <td>$${String.format("%.2f", order.totalAmount)}</td>
                            </tr>
                            <tr>
                                <th colspan="3" class="text-end">Delivery Fee:</th>
                                <td>$2.99</td>
                            </tr>
                            <tr>
                                <th colspan="3" class="text-end">Total:</th>
                                <td>$${String.format("%.2f", order.totalAmount + 2.99)}</td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>

                    <!-- Order Summary -->
                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <h5><i class="fas fa-truck me-2"></i>Delivery Information</h5>
                            <div class="p-3 bg-light rounded">
                                <p><strong>Address:</strong><br>
                                    ${order.deliveryAddress}</p>

                                <p><strong>Payment Method:</strong><br>
                                    ${order.paymentMethod}</p>

                                <c:if test="${not empty order.deliveryInstructions}">
                                    <p><strong>Instructions:</strong><br>
                                            ${order.deliveryInstructions}</p>
                                </c:if>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <h5><i class="fas fa-clock me-2"></i>Order Timeline</h5>
                            <div class="p-3 bg-light rounded">
                                <p><strong>Order Placed:</strong><br>
                                    ${order.createdAt}</p>

                                <c:choose>
                                    <c:when test="${order.status == 'DELIVERED'}">
                                        <p><strong>Delivered At:</strong><br>
                                                ${order.createdAt}</p>
                                    </c:when>
                                    <c:when test="${order.status == 'CANCELLED'}">
                                        <p><strong>Cancelled At:</strong><br>
                                                ${order.createdAt}</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p><strong>Estimated Delivery:</strong><br>
                                            About 30-45 minutes</p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>

                    <!-- Admin Actions -->
                    <c:if test="${user.admin}">
                        <div class="mt-4 p-3 bg-light rounded">
                            <h5><i class="fas fa-user-shield me-2"></i>Admin Actions</h5>
                            <form method="post" action="${pageContext.request.contextPath}/orders/update-status"
                                  class="row g-3 align-items-center">
                                <input type="hidden" name="id" value="${order.id}">
                                <div class="col-auto">
                                    <label for="status" class="col-form-label">Update Status:</label>
                                </div>
                                <div class="col-auto">
                                    <select class="form-select" id="status" name="status">
                                        <option value="PENDING" ${order.status == 'PENDING' ? 'selected' : ''}>Pending</option>
                                        <option value="PREPARING" ${order.status == 'PREPARING' ? 'selected' : ''}>Preparing</option>
                                        <option value="ON_THE_WAY" ${order.status == 'ON_THE_WAY' ? 'selected' : ''}>On The Way</option>
                                        <option value="DELIVERED" ${order.status == 'DELIVERED' ? 'selected' : ''}>Delivered</option>
                                    </select>
                                </div>
                                <div class="col-auto">
                                    <button type="submit" class="btn btn-primary">Update</button>
                                </div>
                            </form>
                        </div>
                    </c:if>

                    <!-- User Actions -->
                    <div class="d-flex justify-content-between mt-4">
                        <a href="${pageContext.request.contextPath}/orders" class="btn btn-outline-secondary">
                            <i class="fas fa-arrow-left me-2"></i>Back to Orders
                        </a>
                        <c:if test="${order.status != 'DELIVERED' && order.status != 'CANCELLED' && !user.admin}">
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
</body>
</html>
