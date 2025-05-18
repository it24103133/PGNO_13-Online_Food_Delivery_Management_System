<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<!DOCTYPE html>
<html>
<head>
  <title>Edit ${menuItem.name} - Food Delivery System</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/menu.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/navbar.jsp" />

<div class="container py-5">
  <div class="row justify-content-center">
    <div class="col-lg-8">
      <div class="card shadow">
        <div class="card-header bg-white">
          <h4 class="mb-0">Edit Menu Item</h4>
        </div>
        <div class="card-body">
          <form action="${pageContext.request.contextPath}/menu/update" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${menuItem.id}">

            <div class="text-center mb-4">
              <img src="${menuItem.imageUrl}" id="currentImage" alt="${menuItem.name}" class="img-thumbnail" style="max-height: 200px;">
            </div>

            <div class="mb-3">
              <label for="name" class="form-label">Item Name</label>
              <input type="text" class="form-control" id="name" name="name" value="${menuItem.name}" required>
            </div>

            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="category" class="form-label">Category</label>
                <select class="form-select" id="category" name="category" required>
                  <option value="Appetizer" ${menuItem.category == 'Appetizer' ? 'selected' : ''}>Appetizer</option>
                  <option value="Main Course" ${menuItem.category == 'Main Course' ? 'selected' : ''}>Main Course</option>
                  <option value="Dessert" ${menuItem.category == 'Dessert' ? 'selected' : ''}>Dessert</option>
                  <option value="Beverage" ${menuItem.category == 'Beverage' ? 'selected' : ''}>Beverage</option>
                  <option value="Side Dish" ${menuItem.category == 'Side Dish' ? 'selected' : ''}>Side Dish</option>
                </select>
              </div>
              <div class="col-md-6 mb-3">
                <label for="price" class="form-label">Price ($)</label>
                <input type="number" step="0.01" min="0" class="form-control" id="price" name="price" value="${menuItem.price}" required>
              </div>
            </div>

            <div class="mb-3">
              <label for="description" class="form-label">Description</label>
              <textarea class="form-control" id="description" name="description" rows="3">${menuItem.description}</textarea>
            </div>

            <div class="row">
              <div class="col-md-4 mb-3">
                <label for="preparationTime" class="form-label">Prep Time (minutes)</label>
                <input type="number" min="1" class="form-control" id="preparationTime" name="preparationTime" value="${menuItem.preparationTime}">
              </div>
              <div class="col-md-4 mb-3">
                <div class="form-check pt-4">
                  <input class="form-check-input" type="checkbox" id="vegetarian" name="vegetarian" ${menuItem.vegetarian ? 'checked' : ''}>
                  <label class="form-check-label" for="vegetarian">Vegetarian</label>
                </div>
              </div>
              <div class="col-md-4 mb-3">
                <div class="form-check pt-4">
                  <input class="form-check-input" type="checkbox" id="spicy" name="spicy" ${menuItem.spicy ? 'checked' : ''}>
                  <label class="form-check-label" for="spicy">Spicy</label>
                </div>
              </div>
            </div>

            <div class="mb-4">
              <label for="image" class="form-label">Update Item Image</label>
              <input type="file" class="form-control" id="image" name="image" accept="image/*">
              <div class="mt-2" id="imagePreview" style="display: none;">
                <img src="" alt="Preview" class="img-thumbnail" style="max-height: 200px;">
              </div>
              <small class="text-muted">Leave blank to keep current image</small>
            </div>

            <div class="d-flex justify-content-between">
              <a href="${pageContext.request.contextPath}/menu/?restaurantId=${menuItem.restaurantId}"
                 class="btn btn-outline-secondary">
                Cancel
              </a>
              <button type="submit" class="btn btn-primary">Update Item</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<jsp:include page="/footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/menu.js"></script>
</body>
</html>
