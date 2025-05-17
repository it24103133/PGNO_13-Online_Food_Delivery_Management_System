<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Payment Methods - FoodExpress</title>
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
                <div class="card-header bg-primary text-white">
                    <h2 class="mb-0">Select Payment Method</h2>
                </div>
                <div class="card-body">
                    <div class="alert alert-info">
                        <h5>Order #${orderId}</h5>
                        <p class="mb-0">Total Amount: <strong>$${amount}</strong></p>
                    </div>

                    <form action="${pageContext.request.contextPath}/payments/process" method="post">
                        <input type="hidden" name="orderId" value="${orderId}">
                        <input type="hidden" name="amount" value="${amount}">

                        <div class="payment-methods">
                            <div class="form-check mb-3">
                                <input class="form-check-input" type="radio" name="paymentMethod"
                                       id="creditCard" value="Credit Card" checked>
                                <label class="form-check-label" for="creditCard">
                                    <i class="far fa-credit-card fa-2x me-2"></i>
                                    <span class="h5">Credit/Debit Card</span>
                                </label>
                                <div class="card-details mt-2 ms-4" id="creditCardDetails">
                                    <div class="row">
                                        <div class="col-md-12 mb-3">
                                            <label for="cardNumber" class="form-label">Card Number</label>
                                            <input type="text" class="form-control" id="cardNumber"
                                                   placeholder="1234 5678 9012 3456">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label for="expiryDate" class="form-label">Expiry Date</label>
                                            <input type="text" class="form-control" id="expiryDate"
                                                   placeholder="MM/YY">
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label for="cvv" class="form-label">CVV</label>
                                            <input type="text" class="form-control" id="cvv"
                                                   placeholder="123">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12 mb-3">
                                            <label for="cardName" class="form-label">Name on Card</label>
                                            <input type="text" class="form-control" id="cardName"
                                                   placeholder="John Doe">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-check mb-3">
                                <input class="form-check-input" type="radio" name="paymentMethod"
                                       id="paypal" value="PayPal">
                                <label class="form-check-label" for="paypal">
                                    <i class="fab fa-paypal fa-2x me-2 text-primary"></i>
                                    <span class="h5">PayPal</span>
                                </label>
                            </div>

                            <div class="form-check mb-3">
                                <input class="form-check-input" type="radio" name="paymentMethod"
                                       id="wallet" value="Digital Wallet">
                                <label class="form-check-label" for="wallet">
                                    <i class="fas fa-wallet fa-2x me-2 text-success"></i>
                                    <span class="h5">Digital Wallet</span>
                                </label>
                            </div>

                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="paymentMethod"
                                       id="cod" value="Cash on Delivery">
                                <label class="form-check-label" for="cod">
                                    <i class="fas fa-money-bill-wave fa-2x me-2 text-info"></i>
                                    <span class="h5">Cash on Delivery</span>
                                </label>
                            </div>
                        </div>

                        <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-4">
                            <a href="${pageContext.request.contextPath}/cart" class="btn btn-secondary me-md-2">
                                <i class="fas fa-arrow-left me-1"></i> Back to Cart
                            </a>
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-lock me-1"></i> Complete Payment
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>

<%--<jsp:include page="../partials/footer.jsp" />--%>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/payment.js"></script>
</body>
</html>

