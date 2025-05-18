<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<!DOCTYPE html>
<html>
<head>
  <title>${menuItem.name} - Food Delivery System</title>
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
          <div class="d-flex justify-content-between align-items-center">
            <h4 class="mb-0">${menuItem.name}</h4>
            <a href="${pageContext.request.contextPath}/menu/?restaurantId=${menuItem.restaurantId}"
               class="btn btn-sm btn-outline-secondary">
              Back to Menu
            </a>
          </div>
        </div>
        <div class="card-body">
          <div class="text-center mb-4">
            <img src="${menuItem.imageUrl}" alt="${menuItem.name}" class="img-fluid rounded" style="max-height: 300px;">
          </div>

          <div class="row mb-4">
            <div class="col-md-6">
              <h5><i class="fas fa-info-circle me-2"></i>Details</h5>
              <ul class="list-group list-group-flush">
                <li class="list-group-item">
                  <strong>Category:</strong> ${menuItem.category}
                </li>
                <li class="list-group-item">
                  <strong>Price:</strong> $${String.format("%.2f", menuItem.price)}
                </li>
                <li class="list-group-item">
                  <strong>Preparation Time:</strong> ${menuItem.preparationTime} minutes
                </li>
              </ul>
            </div>
            <div class="col-md-6">
              <h5><i class="fas fa-tags me-2"></i>Attributes</h5>
              <ul class="list-group list-group-flush">
                <li class="list-group-item">
                  <strong>Vegetarian:</strong>
                  <span class="badge bg-${menuItem.vegetarian ? 'success' : 'secondary'}">
                    ${menuItem.vegetarian ? 'Yes' : 'No'}
                  </span>
                </li>
                <li class="list-group-item">
                  <strong>Spicy:</strong>
                  <span class="badge bg-${menuItem.spicy ? 'danger' : 'secondary'}">
                    ${menuItem.spicy ? 'Yes' : 'No'}
                  </span>
                </li>
              </ul>
            </div>
          </div>

          <h5><i class="fas fa-align-left me-2"></i>Description</h5>
          <p class="card-text">${menuItem.description}</p>
        </div>
        <div class="card-footer bg-white">
          <div class="d-flex justify-content-end gap-2">
            <a href="${pageContext.request.contextPath}/menu/edit?id=${menuItem.id}"
               class="btn btn-primary">
              <i class="fas fa-edit me-2"></i>Edit
            </a>
            <form method="post" action="${pageContext.request.contextPath}/menu/delete"
                  onsubmit="return confirm('Are you sure you want to delete this item?');">
              <input type="hidden" name="id" value="${menuItem.id}">
              <button type="submit" class="btn btn-danger">
                <i class="fas fa-trash me-2"></i>Delete
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<jsp:include page="/footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
