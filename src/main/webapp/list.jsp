<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<!DOCTYPE html>
<html>
<head>
  <title>Restaurants - FoodExpress</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/restaurants.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/navbar.jsp" />

<main class="container my-5">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h1>Our Restaurants</h1>
    <a href="${pageContext.request.contextPath}/c2/add.jsp" class="btn btn-primary">
      <i class="fas fa-plus me-2"></i>Add Restaurant
    </a>
  </div>

  <div class="row">
    <%--        @elvariable id="restaurants" type="java.util.List"--%>
    <c:forEach items="${restaurants}" var="restaurant">
      <div class="col-md-4 mb-4">
        <jsp:include page="/restaurant-card.jsp">
          <jsp:param name="id" value="${restaurant.id}" />
          <jsp:param name="name" value="${restaurant.name}" />
          <jsp:param name="cuisineType" value="${restaurant.cuisineType}" />
          <jsp:param name="imageUrl" value="${restaurant.imageUrl}" />
          <jsp:param name="rating" value="${restaurant.rating}" />
        </jsp:include>
      </div>
    </c:forEach>
  </div>
</main>

<jsp:include page="/footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/restaurants.js"></script>
</body>
</html>