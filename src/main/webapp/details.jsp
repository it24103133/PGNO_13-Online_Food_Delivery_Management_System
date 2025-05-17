<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Payment Receipt - FoodExpress</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/payment.css" rel="stylesheet">
</head>
<body>
<%--<jsp:include page="../partials/navbar.jsp" />--%>

<main class="container my-5">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="card">
                <div class="card-header bg-success text-white">
                    <h2 class="mb-0">
                        <i class="fas fa-check-circle me-2"></i> Payment Successful
                    </h2>
                </div>
                <div class="card-body">
                    <div class="receipt-header text-center mb-4">
                        <h3>FoodExpress</h3>
                        <p class="text-muted">123 Delivery Street, Food City</p>
                    </div>

                    <div class="receipt-details">
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <h5>Payment ID:</h5>
                                <p>${payment.paymentId}</p>
                            </div>
                            <div class="col-md-6">
                                <h5>Transaction ID:</h5>
                                <p>${payment.transactionId}</p>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <h5>Order ID:</h5>
                                <p>${payment.orderId}</p>
                            </div>
                            <div class="col-md-6">
                                <h5>Date:</h5>
                                <p><fmt:formatDate value="${payment.paymentDate}" pattern="dd MMM yyyy HH:mm" /></p>
                            </div>
                        </div>

                        <div class="payment-method mb-4">
                            <h5>Payment Method:</h5>
                            <div class="method-display">
                                <c:choose>
                                    <c:when test="${payment.paymentMethod == 'Credit Card'}">
                                        <i class="far fa-credit-card fa-2x me-3"></i>
                                    </c:when>
                                    <c:when test="${payment.paymentMethod == 'PayPal'}">
                                        <i class="fab fa-paypal fa-2x me-3 text-primary"></i>
                                    </c:when>
                                    <c:when test="${payment.paymentMethod == 'Digital Wallet'}">
                                        <i class="fas fa-wallet fa-2x me-3 text-success"></i>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="fas fa-money-bill-wave fa-2x me-3 text-info"></i>
                                    </c:otherwise>
                                </c:choose>
                                <span class="h4">${payment.paymentMethod}</span>
                            </div>
                        </div>

                        <div class="amount-paid bg-light p-3 rounded mb-4">
                            <div class="d-flex justify-content-between">
                                <h5>Amount Paid:</h5>
                                <h3 class="text-success">$${payment.amount}</h3>
                            </div>
                        </div>

                        <div class="status-badge text-center mb-4">
                                <span class="badge bg-success p-2">
                                    <i class="fas fa-check-circle me-1"></i> ${payment.status}
                                </span>
                        </div>
                    </div>

                    <div class="receipt-footer text-center mt-4">
                        <p class="text-muted">Thank you for your order!</p>
                        <p>Your food will be delivered soon.</p>
                        <div class="d-grid gap-2 d-md-flex justify-content-md-center mt-3">
                            <a href="${pageContext.request.contextPath}/orders" class="btn btn-outline-primary me-md-2">
                                <i class="fas fa-list-alt me-1"></i> View Orders
                            </a>
                            <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">
                                <i class="fas fa-home me-1"></i> Back to Home
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<%--<jsp:include page="../partials/footer.jsp" />--%>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
