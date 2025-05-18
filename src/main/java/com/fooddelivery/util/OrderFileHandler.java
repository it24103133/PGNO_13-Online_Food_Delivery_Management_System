package com.fooddelivery.util;

import com.fooddelivery.model.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

public class OrderFileHandler extends FileHandler {
    private static final String ORDERS_FILE = "orders.txt";
    private static List<Order> allOrders;

    public static List<Order> getOrdersByUser(String userId) {
        List<Order> orders = new ArrayList<>();
        List<String> lines = readAllLines(getFilePath(ORDERS_FILE));

        for (String line : lines) {
            Order order = Order.fromFileString(line);
            if (order != null && order.getUserId().equals(userId)) {
                orders.add(order);
            }
        }
        return orders;
    }

    public static List<Order> getOrdersByRestaurant(String restaurantId) {
        List<Order> orders = new ArrayList<>();
        List<String> lines = readAllLines(getFilePath(ORDERS_FILE));

        for (String line : lines) {
            Order order = Order.fromFileString(line);
            if (order != null && order.getRestaurantId().equals(restaurantId)) {
                orders.add(order);
            }
        }
        return orders;
    }

    public static Order getOrderById(String orderId) {
        List<String> lines = readAllLines(getFilePath(ORDERS_FILE));

        for (String line : lines) {
            Order order = Order.fromFileString(line);
            if (order != null && order.getId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    public static void saveOrder(Order order) {
        appendLine(getFilePath(ORDERS_FILE), order.toFileString());
    }

    public static void updateOrder(Order updatedOrder) {
        List<String> lines = readAllLines(getFilePath(ORDERS_FILE));
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            Order order = Order.fromFileString(line);
            if (order != null && order.getId().equals(updatedOrder.getId())) {
                updatedLines.add(updatedOrder.toFileString());
            } else {
                updatedLines.add(line);
            }
        }

        writeAllLines(getFilePath(ORDERS_FILE), updatedLines);
    }

    public static void cancelOrder(String orderId) {
        Order order = getOrderById(orderId);
        if (order != null) {
            order.setStatus("CANCELLED");
            updateOrder(order);
        }
    }

    public static List<Order> getAllOrders() {
        return allOrders;
    }

    public static void setAllOrders(List<Order> allOrders) {
        OrderFileHandler.allOrders = allOrders;
    }

    public static List<Order> getOrdersByUser(Object userId) {
        return null;
    }
}
