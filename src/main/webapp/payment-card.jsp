<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="card payment-card mb-3">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-start mb-2">
            <h5 class="card-title mb-0">Payment #${param.paymentId}</h5>
            <span class="badge bg-${param.status == 'Completed' ? 'success' : 'warning'}">
                ${param.status}
            </span>
        </div>

        <div class="payment-method mb-2">
            <c:choose>
                <c:when test="${param.paymentMethod == 'Credit Card'}">
                    <i class="far fa-credit-card me-2"></i>
                </c:when>
                <c:when test="${param.paymentMethod == 'PayPal'}">
                    <i class="fab fa-paypal me-2 text-primary"></i>
                </c:when>
                <c:when test="${param.paymentMethod == 'Digital Wallet'}">
                    <i class="fas fa-wallet me-2 text-success"></i>
                </c:when>
                <c:otherwise>
                    <i class="fas fa-money-bill-wave me-2 text-info"></i>
                </c:otherwise>
            </c:choose>
            ${param.paymentMethod}
        </div>

        <div class="row">
            <div class="col-md-6">
                <small class="text-muted">Order ID</small>
                <p class="mb-2">${param.orderId}</p>
            </div>
            <div class="col-md-6">
                <small class="text-muted">Date</small>
                <p class="mb-2"><fmt:formatDate value="${param.paymentDate}" pattern="dd MMM yyyy" /></p>
            </div>
        </div>

        <div class="d-flex justify-content-between align-items-center mt-2">
            <h5 class="mb-0 text-success">$${param.amount}</h5>
            <a href="${pageContext.request.contextPath}/payments/details?paymentId=${param.paymentId}"
               class="btn btn-sm btn-outline-primary">
                <i class="fas fa-receipt me-1"></i> Details
            </a>
        </div>
    </div>
</div>
