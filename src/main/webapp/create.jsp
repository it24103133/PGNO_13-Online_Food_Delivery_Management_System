<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order from ${restaurant.name} - Food Delivery System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/orders.css" rel="stylesheet">
</head>
<body>
<%--<jsp:include page="../partials/navbar.jsp" />--%>

<div class="container py-5">
    <div class="row">
        <div class="col-lg-8">
            <div class="card shadow-sm mb-4">
                <div class="card-header bg-white">
                    <h4 class="mb-0">Order from ${restaurant.name}</h4>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/orders/create" method="post">
                        <input type="hidden" name="restaurantId" value="${restaurant.id}">

                        <div class="mb-4">
                            <h5 class="mb-3"><i class="fas fa-utensils me-2"></i>Menu Items</h5>
                            <div class="table-responsive">
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
                                    <c:forEach items="${menuItems}" var="item">
                                        <tr>
                                            <td>
                                                <div class="form-check">
                                                    <input class="form-check-input item-checkbox" type="checkbox"
                                                           name="itemId" value="${item.id}" id="item-${item.id}">
                                                    <label class="form-check-label" for="item-${item.id}">
                                                            ${item.name}
                                                        <c:if test="${item.vegetarian}">
                                                            <span class="badge bg-success ms-2">Veg</span>
                                                        </c:if>
                                                        <c:if test="${item.spicy}">
                                                            <span class="badge bg-danger ms-2">Spicy</span>
                                                        </c:if>
                                                    </label>
                                                </div>
                                                <small class="text-muted">${item.description}</small>
                                            </td>
                                            <td>$${String.format("%.2f", item.price)}</td>
                                            <td>
                                                <input type="number" min="0" max="10"
                                                       class="form-control form-control-sm quantity-input"
                                                       name="quantity" value="0" style="width: 70px;"
                                                       data-item-id="${item.id}">
                                            </td>
                                            <td class="subtotal">$0.00</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <th colspan="3" class="text-end">Total:</th>
                                        <th id="order-total">$0.00</th>
                                    </tr>
                                    </tfoot>
                                </table>
                            </div>
                        </div>

                        <div class="mb-4">
                            <h5 class="mb-3"><i class="fas fa-truck me-2"></i>Delivery Details</h5>
                            <div class="mb-3">
                                <label for="deliveryAddress" class="form-label">Delivery Address</label>
                                <textarea class="form-control" id="deliveryAddress" name="deliveryAddress"
                                          rows="2" required>${user.address}</textarea>
                            </div>
                            <div class="mb-3">
                                <label for="instructions" class="form-label">Special Instructions</label>
                                <textarea class="form-control" id="instructions" name="instructions"
                                          rows="2" placeholder="e.g., Leave at front door"></textarea>
                            </div>
                        </div>

                        <div class="mb-4">
                            <h5 class="mb-3"><i class="fas fa-credit-card me-2"></i>Payment Method</h5>
                            <div class="form-check mb-2">
                                <input class="form-check-input" type="radio" name="paymentMethod"
                                       id="cash" value="Cash on Delivery" checked>
                                <label class="form-check-label" for="cash">
                                    Cash on Delivery
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="paymentMethod"
                                       id="card" value="Credit Card">
                                <label class="form-check-label" for="card">
                                    Credit Card
                                </label>
                            </div>
                        </div>

                        <div class="d-flex justify-content-between">
                            <a href="${pageContext.request.contextPath}/restaurants"
                               class="btn btn-outline-secondary">
                                Cancel
                            </a>
                            <button type="submit" class="btn btn-primary" id="place-order-btn" disabled>
                                Place Order
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%--<jsp:include page="../partials/footer.jsp" />--%>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/orders.js"></script>
</body>
</html>
