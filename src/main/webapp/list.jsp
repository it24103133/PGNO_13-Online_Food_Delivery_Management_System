
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<!DOCTYPE html>
<html>
<head>
  <title>${restaurant.name} Menu - Food Delivery System</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/menu.css" rel="stylesheet">
</head>
<body>

<jsp:include page="/navbar.jsp"/>

<div class="container mt-5">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <div>
      <h1>${restaurant.name} Menu</h1>
      <p class="text-muted">${restaurant.cuisineType}</p>
    </div>
    <a href="${pageContext.request.contextPath}/menu/add?restaurantId=${restaurant.id}" class="btn btn-primary">
      <i class="fas fa-plus me-2"></i>Add Item
    </a>
  </div>

  <div class="row">
    <c:forEach items="${menuItems}" var="item">
      <div class="col-md-6 col-lg-4 mb-4">
        <div class="card h-100">
          <!-- Load your uploaded image -->
          <img src="${pageContext.request.contextPath}/images/Pizza range.jpg" class="card-img-top" alt="${item.name}">

          <div class="card-body">
            <div class="d-flex justify-content-between">
              <h5 class="card-title mb-1">${item.name}</h5>
              <span class="badge bg-success">$${String.format("%.2f", item.price)}</span>
            </div>
            <p class="text-muted mb-1">${item.category}</p>
            <p class="small">${item.description}</p>
            <div class="d-flex flex-wrap gap-2 mt-2">
              <c:if test="${item.vegetarian}">
                                <span class="badge bg-success">
                                    <i class="fas fa-leaf me-1"></i>Vegetarian
                                </span>
              </c:if>
              <c:if test="${item.spicy}">
                                <span class="badge bg-danger">
                                    <i class="fas fa-pepper-hot me-1"></i>Spicy
                                </span>
              </c:if>
              <span class="badge bg-secondary">
                                <i class="fas fa-clock me-1"></i>${item.preparationTime} min
                            </span>
            </div>
          </div>



          <div class="card-footer bg-white d-flex justify-content-end gap-2">
            <a href="${pageContext.request.contextPath}/menu/view?id=${item.id}" class="btn btn-sm btn-outline-primary">
              <i class="fas fa-eye"></i>
            </a>
            <a href="${pageContext.request.contextPath}/menu/edit?id=${item.id}" class="btn btn-sm btn-outline-secondary">
              <i class="fas fa-edit"></i>
            </a>
            <form method="post" action="${pageContext.request.contextPath}/menu/delete"
                  onsubmit="return confirm('Are you sure you want to delete this item?');">
              <input type="hidden" name="id" value="${item.id}">
              <button type="submit" class="btn btn-sm btn-outline-danger">
                <i class="fas fa-trash"></i>
              </button>
            </form>
          </div>
        </div>
      </div>
    </c:forEach>
  </div>
</div>



<jsp:include page="/footer.jsp"/>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/menu.js"></script>
</body>
</html>
