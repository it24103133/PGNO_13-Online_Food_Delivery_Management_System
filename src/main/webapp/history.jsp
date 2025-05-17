<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Payment History - FoodExpress</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/payment.css" rel="stylesheet">
</head>
<body>
<%--<jsp:include page="../partials/navbar.jsp" />--%>

<main class="container my-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>Payment History</h1>
        <a href="${pageContext.request.contextPath}/orders" class="btn btn-outline-primary">
            <i class="fas fa-arrow-left me-1"></i> Back to Orders
        </a>
    </div>

    <div class="card">
        <div class="card-body">
            <c:choose>
                <c:when test="${not empty payments}">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>Payment ID</th>
                                <th>Date</th>
                                <th>Order ID</th>
                                <th>Method</th>
                                <th>Amount</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${payments}" var="payment">
                                <tr>
                                    <td>${payment.paymentId}</td>
                                    <td><fmt:formatDate value="${payment.paymentDate}" pattern="dd MMM yyyy" /></td>
                                    <td>${payment.orderId}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${payment.paymentMethod == 'Credit Card'}">
                                                <i class="far fa-credit-card me-1"></i>
                                            </c:when>
                                            <c:when test="${payment.paymentMethod == 'PayPal'}">
                                                <i class="fab fa-paypal me-1 text-primary"></i>
                                            </c:when>
                                            <c:when test="${payment.paymentMethod == 'Digital Wallet'}">
                                                <i class="fas fa-wallet me-1 text-success"></i>
                                            </c:when>
                                            <c:otherwise>
                                                <i class="fas fa-money-bill-wave me-1 text-info"></i>
                                            </c:otherwise>
                                        </c:choose>
                                            ${payment.paymentMethod}
                                    </td>
                                    <td>$${payment.amount}</td>
                                    <td>
                                                <span class="badge bg-${payment.status == 'Completed' ? 'success' : 'warning'}">
                                                        ${payment.status}
                                                </span>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/payments/details?paymentId=${payment.paymentId}"
                                           class="btn btn-sm btn-outline-primary">
                                            <i class="fas fa-receipt"></i> Receipt
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="text-center py-5">
                        <i class="fas fa-money-bill-wave fa-4x text-muted mb-4"></i>
                        <h3>No Payment History Found</h3>
                        <p class="text-muted">You haven't made any payments yet.</p>
                        <a href="${pageContext.request.contextPath}/home" class="btn btn-primary mt-3">
                            <i class="fas fa-utensils me-1"></i> Order Now
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</main>

<%--<jsp:include page="../partials/footer.jsp" />--%>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>





<jsp:include page="partials/payment-card.jsp">
    <jsp:param name="paymentId" value="${payment.paymentId}" />
    <jsp:param name="orderId" value="${payment.orderId}" />
    <jsp:param name="paymentMethod" value="${payment.paymentMethod}" />
    <jsp:param name="amount" value="${payment.amount}" />
    <jsp:param name="status" value="${payment.status}" />
    <jsp:param name="paymentDate" value="${payment.paymentDate}" />
</jsp:include>





</body>
</html>
