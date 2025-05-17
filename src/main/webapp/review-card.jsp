<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>--%>

<div class="card review-card mb-3">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-start mb-2">
            <div>
                <h5 class="card-title mb-1">Order #${param.orderId}</h5>
                <small class="text-muted">
                    <fmt:formatDate value="${param.reviewDate}" pattern="dd MMM yyyy" />
                </small>
            </div>
            <c:if test="${param.editable == 'true'}">
                <div class="dropdown">
                    <button class="btn btn-sm btn-outline-secondary dropdown-toggle"
                            type="button" data-bs-toggle="dropdown">
                        <i class="fas fa-ellipsis-v"></i>
                    </button>
                    <ul class="dropdown-menu">
                        <li>
                            <a class="dropdown-item" href="#" data-bs-toggle="modal"
                               data-bs-target="#editReviewModal${param.reviewId}">
                                <i class="fas fa-edit me-1"></i> Edit
                            </a>
                        </li>
                        <li>
                            <form action="${pageContext.request.contextPath}/reviews/delete" method="post">
                                <input type="hidden" name="reviewId" value="${param.reviewId}">
                                <button type="submit" class="dropdown-item text-danger">
                                    <i class="fas fa-trash me-1"></i> Delete
                                </button>
                            </form>
                        </li>
                    </ul>
                </div>
            </c:if>
        </div>

        <div class="row mb-2">
            <div class="col-md-6">
                <h6>Food Rating:</h6>
                <div class="food-rating">
                    <c:forEach begin="1" end="5" var="i">
                        <i class="fas fa-star${i <= param.foodRating ? '' : '-half-alt'} text-warning"></i>
                    </c:forEach>
                </div>
            </div>
            <div class="col-md-6">
                <h6>Delivery Rating:</h6>
                <div class="delivery-rating">
                    <c:forEach begin="1" end="5" var="i">
                        <i class="fas fa-star${i <= param.deliveryRating ? '' : '-half-alt'} text-warning"></i>
                    </c:forEach>
                </div>
            </div>
        </div>

        <c:if test="${not empty param.comments}">
            <div class="review-comments mt-3">
                <p class="mb-0">"${param.comments}"</p>
            </div>
        </c:if>
    </div>
</div>

<!-- Edit Review Modal -->
<c:if test="${param.editable == 'true'}">
    <div class="modal fade" id="editReviewModal${param.reviewId}" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Edit Review</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <form action="${pageContext.request.contextPath}/reviews/edit" method="post">
                    <input type="hidden" name="reviewId" value="${param.reviewId}">
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label">Food Rating</label>
                            <div class="edit-food-rating">
                                <c:forEach begin="1" end="5" var="i">
                                    <i class="far fa-star fa-2x ${i <= param.foodRating ? 'text-warning fas' : ''}"
                                       data-value="${i}"></i>
                                </c:forEach>
                                <input type="hidden" name="foodRating" value="${param.foodRating}">
                            </div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Delivery Rating</label>
                            <div class="edit-delivery-rating">
                                <c:forEach begin="1" end="5" var="i">
                                    <i class="far fa-star fa-2x ${i <= param.deliveryRating ? 'text-warning fas' : ''}"
                                       data-value="${i}"></i>
                                </c:forEach>
                                <input type="hidden" name="deliveryRating" value="${param.deliveryRating}">
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="editComments${param.reviewId}" class="form-label">Comments</label>
                            <textarea class="form-control" id="editComments${param.reviewId}"
                                      name="comments" rows="3">${param.comments}</textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary">Save Changes</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</c:if>