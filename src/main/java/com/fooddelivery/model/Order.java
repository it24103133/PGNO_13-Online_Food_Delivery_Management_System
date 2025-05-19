package com.restaurant.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private List<OrderItem> items;
    private double totalAmount;
    private String status; // PENDING, PREPARING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
    private String orderDate;
    private String deliveryDate;

    public Order(int id, int customerId, String customerName, String customerAddress, String customerPhone) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.items = new ArrayList<>();
        this.status = "PENDING";
        this.orderDate = java.time.LocalDateTime.now().toString();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
        calculateTotal();
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
        calculateTotal();
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        if (status.equals("DELIVERED")) {
            this.deliveryDate = java.time.LocalDateTime.now().toString();
        }
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    private void calculateTotal() {
        this.totalAmount = items.stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
    }
} 
