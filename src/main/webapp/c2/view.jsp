<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${restaurant.name} - Food Delivery System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .restaurant-image {
            height: 400px;
            object-fit: cover;
        }
        .info-item {
            margin-bottom: 1rem;
        }
        .info-item i {
            width: 25px;
            color: #0d6efd;
        }
    </style>
</head>
<body>
<jsp:include page="../navbar.jsp" />

<div class="container py-5">
    <div class="row">
        <div class="col-lg-8 mx-auto">
            <div class="card shadow-sm">
                <img src="${pageContext.request.contextPath}${restaurant.imageUrl}"
                     class="card-img-top restaurant-image" alt="${restaurant.name}">
                <div class="card-body">
                    <h2 class="card-title mb-4">${restaurant.name}</h2>

                    <div class="info-item">
                        <i class="fas fa-utensils"></i>
                        <strong>Cuisine Type:</strong> ${restaurant.cuisineType}
                    </div>

                    <div class="info-item">
                        <i class="fas fa-map-marker-alt"></i>
                        <strong>Location:</strong> ${restaurant.location}
                    </div>

                    <div class="info-item">
                        <i class="fas fa-map"></i>
                        <strong>Address:</strong> ${restaurant.address}
                    </div>

                    <div class="info-item">
                        <i class="fas fa-phone"></i>
                        <strong>Phone:</strong> ${restaurant.phoneNumber}
                    </div>

                    <div class="info-item">
                        <i class="fas fa-envelope"></i>
                        <strong>Email:</strong> ${restaurant.email}
                    </div>

                    <div class="info-item">
                        <i class="fas fa-clock"></i>
                        <strong>Operating Hours:</strong> ${restaurant.openingHours} - ${restaurant.closingHours}
                    </div>

                    <div class="info-item">
                        <i class="fas fa-star"></i>
                        <strong>Rating:</strong> ${restaurant.rating}
                    </div>

                    <div class="info-item">
                        <i class="fas fa-info-circle"></i>
                        <strong>Description:</strong>
                        <p class="mt-2">${restaurant.description}</p>
                    </div>

                    <div class="d-flex justify-content-between mt-4">
                        <a href="${pageContext.request.contextPath}/c2/list.jsp" class="btn btn-outline-secondary">
                            <i class="fas fa-arrow-left me-2"></i>Back to List
                        </a>
                        <div>
                            <a href="${pageContext.request.contextPath}/restaurant/edit?id=${restaurant.restaurantId}"
                               class="btn btn-primary me-2">
                                <i class="fas fa-edit me-2"></i>Edit
                            </a>
                            <a href="${pageContext.request.contextPath}/restaurant/delete?id=${restaurant.restaurantId}"
                               class="btn btn-danger"
                               onclick="return confirm('Are you sure you want to delete this restaurant?')">
                                <i class="fas fa-trash me-2"></i>Delete
                            </a>
                        </div>
                    </div>

                    <!-- Rating Form -->
                    <div class="mt-4">
                        <h6>Rate this Restaurant</h6>
                        <form action="${pageContext.request.contextPath}/restaurant/rate" method="post" class="d-flex align-items-center">
                            <input type="hidden" name="restaurantId" value="${restaurant.restaurantId}">
                            <div class="rating-input me-3">
                                <select name="rating" class="form-select">
                                    <option value="1">1 Star</option>
                                    <option value="2">2 Stars</option>
                                    <option value="3">3 Stars</option>
                                    <option value="4">4 Stars</option>
                                    <option value="5">5 Stars</option>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary">Submit Rating</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>