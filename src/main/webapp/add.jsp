<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Add Restaurant - FoodExpress</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
  <!-- Optionally include your custom CSS if needed -->
  <link href="${pageContext.request.contextPath}/css/restaurants.css" rel="stylesheet" type="text/css" />



</head>
<body>
<main class="container my-5">
  <div class="row justify-content-center">
    <div class="col-lg-8">
      <div class="card">
        <div class="card-header bg-primary text-white">
          <h2 class="mb-0">Add New Restaurant</h2>
        </div>
        <div class="card-body">
          <!-- Add this near the top of your form -->
          <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                ${errorMessage}
            </div>
          </c:if>
          <form action="${pageContext.request.contextPath}/restaurants/add" method="post" enctype="multipart/form-data">
            <div class="mb-3">
              <label for="name" class="form-label">Restaurant Name</label>
              <input type="text" class="form-control" id="name" name="name" required>
            </div>

            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="cuisineType" class="form-label">Cuisine Type</label>
                <select class="form-select" id="cuisineType" name="cuisineType" required>
                  <option value="">Select Cuisine</option>
                  <option value="Italian">Italian</option>
                  <option value="Chinese">Chinese</option>
                  <option value="Indian">Indian</option>
                  <option value="Japanese">Japanese</option>
                  <option value="American">American</option>
                  <option value="Mexican">Mexican</option>
                  <option value="Thai">Thai</option>
                  <option value="Other">Other</option>
                </select>
              </div>
              <div class="col-md-6 mb-3">
                <label for="rating" class="form-label">Rating (1-5)</label>
                <input type="number" class="form-control" id="rating" name="rating"
                       min="1" max="5" step="0.1" value="4.0" required>
              </div>
            </div>

            <div class="mb-3">
              <label for="description" class="form-label">Description</label>
              <textarea class="form-control" id="description" name="description" rows="3"></textarea>
            </div>

            <div class="mb-3">
              <label for="imageUrl" class="form-label">Image URL</label>
              <input type="url" class="form-control" id="imageUrl" name="imageUrl">
              <small class="text-muted">Paste a direct image link from Unsplash or similar</small>
            </div>

            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="address" class="form-label">Address</label>
                <input type="text" class="form-control" id="address" name="address">
              </div>
              <div class="col-md-6 mb-3">
                <label for="phone" class="form-label">Phone Number</label>
                <input type="tel" class="form-control" id="phone" name="phone" maxlength="10" pattern="\d{10}" required>
                <small class="text-muted">Enter a 10-digit phone number</small>
                <c:if test="${not empty phoneError}">
                  <small class="text-danger">${phoneError}</small>
                </c:if>
              </div>
            </div>

            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email">
              </div>
              <div class="col-md-6 mb-3">
                <label for="openingHours" class="form-label">Opening Hours</label>
                <input type="text" class="form-control" id="openingHours" name="openingHours"
                       placeholder="e.g., 9:00 AM - 10:00 PM">
              </div>
            </div>

            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
              <button type="reset" class="btn btn-secondary me-md-2">
                Cancel
              </button>
              <button type="submit" class="btn btn-primary">
                <i class="fas fa-save me-1"></i> Save Restaurant
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>