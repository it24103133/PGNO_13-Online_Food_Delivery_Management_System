<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Restaurants - Food Delivery System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .restaurant-card {
            transition: transform 0.2s;
        }
        .restaurant-card:hover {
            transform: translateY(-5px);
        }
        .search-box {
            max-width: 500px;
            margin: 0 auto;
        }
    </style>
</head>
<body>
<jsp:include page="../navbar.jsp" />

<div class="container py-5">
    <div class="row mb-4">
        <div class="col-12">
            <div class="d-flex justify-content-between align-items-center">
                <h2 class="mb-0">Restaurants</h2>
                <a href="${pageContext.request.contextPath}/c2/add.jsp" class="btn btn-primary">
                    <i class="fas fa-plus me-2"></i>Add Restaurant
                </a>
            </div>
        </div>
    </div>

    <div class="row mb-4">
        <div class="col-12">
            <form action="${pageContext.request.contextPath}/restaurant/search" method="get" class="search-box">
                <div class="input-group">
                    <input type="text" class="form-control" name="query" placeholder="Search restaurants..."
                           value="${searchQuery}">
                    <button class="btn btn-primary" type="submit">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
        </div>
    </div>

    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
        <c:forEach items="${restaurants}" var="restaurant">
            <div class="col">
                <div class="card h-100 restaurant-card shadow-sm">
                    <img src="${pageContext.request.contextPath}${restaurant.imageUrl}"
                         class="card-img-top" alt="${restaurant.name}"
                         style="height: 200px; object-fit: cover;">
                    <div class="card-body">
                        <h5 class="card-title">${restaurant.name}</h5>
                        <p class="card-text text-muted">
                            <i class="fas fa-utensils me-2"></i>${restaurant.cuisineType}
                        </p>
                        <p class="card-text">
                            <i class="fas fa-map-marker-alt me-2"></i>${restaurant.location}
                        </p>
                        <div class="d-flex justify-content-between align-items-center">
                            <div class="rating">
                                <i class="fas fa-star text-warning"></i>
                                <span>${restaurant.rating}</span>
                            </div>
                            <div class="btn-group">
                                <a href="${pageContext.request.contextPath}/restaurant/view?id=${restaurant.restaurantId}"
                                   class="btn btn-outline-primary btn-sm">
                                    <i class="fas fa-eye"></i>
                                </a>
                                <a href="${pageContext.request.contextPath}/restaurant/edit?id=${restaurant.restaurantId}"
                                   class="btn btn-outline-secondary btn-sm">
                                    <i class="fas fa-edit"></i>
                                </a>
                                <a href="${pageContext.request.contextPath}/restaurant/delete?id=${restaurant.restaurantId}"
                                   class="btn btn-outline-danger btn-sm"
                                   onclick="return confirm('Are you sure you want to delete this restaurant?')">
                                    <i class="fas fa-trash"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<jsp:include page="../footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>