package com.fooddelivery.online_food_delivery_management_system.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payment implements Serializable {
    private String paymentId;
    private String userId;
    private double amount;
    private String paymentMethod;
    private String status;
    private LocalDateTime timestamp;
    private String description;

    public Payment() {
    }

    public Payment(String paymentId, String userId, double amount, String paymentMethod, String status, LocalDateTime timestamp, String description) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.timestamp = timestamp;
        this.description = description;
    }

    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.join(",",
                paymentId,
                userId,
                String.valueOf(amount),
                paymentMethod,
                status,
                timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                description != null ? description : ""
        );
    }
}