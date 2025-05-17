<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Processing Payment - FoodExpress</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/payment.css" rel="stylesheet">
</head>
<body>
<%--<jsp:include page="../partials/navbar.jsp" />--%>

<main class="container my-5">
    <div class="row justify-content-center">
        <div class="col-lg-6 text-center">
            <div class="payment-processing">
                <div class="spinner-border text-primary mb-4" style="width: 3rem; height: 3rem;" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
                <h2 class="mb-3">Processing Your Payment</h2>
                <p class="text-muted mb-4">Please wait while we verify your payment details...</p>

                <div class="progress mb-4">
                    <div class="progress-bar progress-bar-striped progress-bar-animated"
                         role="progressbar" style="width: 100%"></div>
                </div>

                <div class="payment-details bg-light p-4 rounded mb-4 text-start">
                    <h5 class="mb-3"><i class="fas fa-info-circle me-2"></i>Payment Summary</h5>
                    <ul class="list-unstyled">
                        <li class="mb-2"><strong>Order ID:</strong> ${param.orderId}</li>
                        <li class="mb-2"><strong>Amount:</strong> $${param.amount}</li>
                        <li class="mb-2"><strong>Method:</strong> ${param.paymentMethod}</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</main>

<%--<jsp:include page="../partials/footer.jsp" />--%>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Automatically redirect after 3 seconds
    setTimeout(function() {
        window.location.href = "${pageContext.request.contextPath}/payments/details?paymentId=PAY${System.currentTimeMillis()}";
    }, 3000);
</script>
</body>
</html>

