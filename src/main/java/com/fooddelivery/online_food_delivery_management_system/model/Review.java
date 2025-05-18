package com.fooddelivery.online_food_delivery_management_system.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Review {
    private String reviewId;
    private String orderId;
    private String userId;
    private String restaurantId;
    private int foodRating;
    private int deliveryRating;
    private String comments;
    private LocalDateTime reviewDate;

    public Review(String reviewId, String orderId, String userId, String restaurantId,
                  int foodRating, int deliveryRating, String comments, LocalDateTime reviewDate) {
        this.reviewId = reviewId;
        this.orderId = orderId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.foodRating = foodRating;
        this.deliveryRating = deliveryRating;
        this.comments = comments;
        this.reviewDate = reviewDate;
    }

    // Getters
    public String getReviewId() { return reviewId; }
    public String getOrderId() { return orderId; }
    public String getUserId() { return userId; }
    public String getRestaurantId() { return restaurantId; }
    public int getFoodRating() { return foodRating; }
    public int getDeliveryRating() { return deliveryRating; }
    public String getComments() { return comments; }
    public LocalDateTime getReviewDate() { return reviewDate; }

    // File handling methods
    public String toFileString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return String.join("|",
                reviewId, orderId, userId, restaurantId,
                String.valueOf(foodRating), String.valueOf(deliveryRating),
                comments, reviewDate.format(formatter)
        );
    }

    public static Review fromFileString(String fileString) {
        String[] parts = fileString.split("\\|");
        if (parts.length == 8) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            return new Review(
                    parts[0], parts[1], parts[2], parts[3],
                    Integer.parseInt(parts[4]), Integer.parseInt(parts[5]),
                    parts[6], LocalDateTime.parse(parts[7], formatter)
            );
        }
        return null;
    }
}
