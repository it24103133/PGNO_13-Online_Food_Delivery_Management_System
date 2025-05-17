package com.fooddelivery.online_food_delivery_management_system.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payment {
    private String paymentId;
    private String orderId;
    private String userId;
    private String paymentMethod;
    private double amount;
    private String status;
    private LocalDateTime paymentDate;
    private String transactionId;

    public Payment(String paymentId, String orderId, String userId, String paymentMethod,
                   double amount, String status, LocalDateTime paymentDate, String transactionId) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.userId = userId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.status = status;
        this.paymentDate = paymentDate;
        this.transactionId = transactionId;
    }

    // Getters
    public String getPaymentId() { return paymentId; }
    public String getOrderId() { return orderId; }
    public String getUserId() { return userId; }
    public String getPaymentMethod() { return paymentMethod; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public String getTransactionId() { return transactionId; }

    // File handling methods
    public String toFileString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return String.join("|",
                paymentId, orderId, userId, paymentMethod,
                String.valueOf(amount), status,
                paymentDate.format(formatter), transactionId
        );
    }

    public static Payment fromFileString(String fileString) {
        String[] parts = fileString.split("\\|");
        if (parts.length == 8) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            return new Payment(
                    parts[0], parts[1], parts[2], parts[3],
                    Double.parseDouble(parts[4]), parts[5],
                    LocalDateTime.parse(parts[6], formatter), parts[7]
            );
        }
        return null;
    }
}

