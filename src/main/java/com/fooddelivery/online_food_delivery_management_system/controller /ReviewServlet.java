package com.fooddelivery.online_food_delivery_management_system.controller;


import com.fooddelivery.online_food_delivery_management_system.model.Review;
import com.fooddelivery.online_food_delivery_management_system.util.ReviewFileHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "ReviewServlet", value = {
        "/reviews",
        "/reviews/submit",
        "/reviews/edit",
        "/reviews/delete",
        "/reviews/restaurant",
        "/reviews/user"
})
public class ReviewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/reviews":
                showReviewForm(request, response);
                break;
            case "/reviews/restaurant":
                showRestaurantReviews(request, response);
                break;
            case "/reviews/user":
                showUserReviews(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (!action.equals("/reviews/submit")) {
            if (action.equals("/reviews/edit")) {
                editReview(request, response);
            } else if (action.equals("/reviews/delete")) {
                deleteReview(request, response);
            }
        } else {
            submitReview(request, response);
        }
    }

    private void showReviewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("orderId");
        String restaurantId = request.getParameter("restaurantId");

        request.setAttribute("orderId", orderId);
        request.setAttribute("restaurantId", restaurantId);
        request.getRequestDispatcher("/views/review/form.jsp").forward(request, response);
    }

    private void submitReview(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String reviewId = "REV" + System.currentTimeMillis();
        String orderId = request.getParameter("orderId");
        String userId = (String) request.getSession().getAttribute("userId");
        String restaurantId = request.getParameter("restaurantId");
        int foodRating = Integer.parseInt(request.getParameter("foodRating"));
        int deliveryRating = Integer.parseInt(request.getParameter("deliveryRating"));
        String comments = request.getParameter("comments");

        Review review = new Review(
                reviewId, orderId, userId, restaurantId,
                foodRating, deliveryRating, comments, LocalDateTime.now()
        );

        ReviewFileHandler.saveReview(review);
        response.sendRedirect(request.getContextPath() + "/orders?reviewSubmitted=true");
    }

    private void showRestaurantReviews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String restaurantId = request.getParameter("restaurantId");
        List<Review> reviews = ReviewFileHandler.getReviewsByRestaurant(restaurantId);

        request.setAttribute("reviews", reviews);
        request.setAttribute("restaurantId", restaurantId);
        request.getRequestDispatcher("/views/review/restaurant-reviews.jsp").forward(request, response);
    }

    private void showUserReviews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = (String) request.getSession().getAttribute("userId");
        List<Review> reviews = ReviewFileHandler.getReviewsByUser(userId);

        request.setAttribute("reviews", reviews);
        request.getRequestDispatcher("/views/review/user-reviews.jsp").forward(request, response);
    }

    private void editReview(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String reviewId = request.getParameter("reviewId");
        Review existingReview = ReviewFileHandler.getReviewById(reviewId);

        int foodRating = Integer.parseInt(request.getParameter("foodRating"));
        int deliveryRating = Integer.parseInt(request.getParameter("deliveryRating"));
        String comments = request.getParameter("comments");

        Review updatedReview = new Review(
                reviewId, existingReview.getOrderId(), existingReview.getUserId(),
                existingReview.getRestaurantId(), foodRating, deliveryRating,
                comments, existingReview.getReviewDate()
        );

        ReviewFileHandler.updateReview(updatedReview);
        response.sendRedirect(request.getContextPath() + "/reviews/user?updated=true");
    }

    private void deleteReview(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String reviewId = request.getParameter("reviewId");
        ReviewFileHandler.deleteReview(reviewId);
        response.sendRedirect(request.getContextPath() + "/reviews/user?deleted=true");
    }
}
