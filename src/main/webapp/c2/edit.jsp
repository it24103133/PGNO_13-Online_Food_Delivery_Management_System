<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Restaurant - Food Delivery System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../navbar.jsp" />

<div class="container py-5">
    <div class="row">
        <div class="col-lg-8 mx-auto">
            <div class="card shadow-sm">
                <div class="card-header bg-white">
                    <h4 class="mb-0">Edit Restaurant</h4>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/restaurant/update" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="restaurantId" value="${restaurant.restaurantId}">
                        
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="name" class="form-label">Restaurant Name</label>
                                <input type="text" class="form-control" id="name" name="name" 
                                       value="${restaurant.name}" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="cuisineType" class="form-label">Cuisine Type</label>
                                <input type="text" class="form-control" id="cuisineType" name="cuisineType" 
                                       value="${restaurant.cuisineType}" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="location" class="form-label">Location</label>
                                <input type="text" class="form-control" id="location" name="location" 
                                       value="${restaurant.location}" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="address" class="form-label">Address</label>
                                <input type="text" class="form-control" id="address" name="address" 
                                       value="${restaurant.address}" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="phoneNumber" class="form-label">Phone Number</label>
                                <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" 
                                       value="${restaurant.phoneNumber}" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email" 
                                       value="${restaurant.email}" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="openingHours" class="form-label">Opening Hours</label>
                                <input type="time" class="form-control" id="openingHours" name="openingHours" 
                                       value="${restaurant.openingHours}" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="closingHours" class="form-label">Closing Hours</label>
                                <input type="time" class="form-control" id="closingHours" name="closingHours" 
                                       value="${restaurant.closingHours}" required>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="description" class="form-label">Description</label>
                            <textarea class="form-control" id="description" name="description" 
                                      rows="3" required>${restaurant.description}</textarea>
                        </div>

                        <div class="mb-3">
                            <label for="image" class="form-label">Restaurant Image</label>
                            <input type="file" class="form-control" id="image" name="image" accept="image/*">
                            <small class="text-muted">Leave empty to keep the current image</small>
                            <c:if test="${not empty restaurant.imageUrl}">
                                <div class="mt-2">
                                    <img src="${pageContext.request.contextPath}${restaurant.imageUrl}" 
                                         alt="Current image" class="img-thumbnail" style="max-height: 200px;">
                                </div>
                            </c:if>
                        </div>

                        <div class="d-flex justify-content-between">
                            <a href="${pageContext.request.contextPath}/c2/list.jsp" class="btn btn-outline-secondary">
                                Cancel
                            </a>
                            <button type="submit" class="btn btn-primary">
                                Update Restaurant
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>