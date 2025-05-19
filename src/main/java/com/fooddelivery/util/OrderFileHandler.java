package com.restaurant.util;

import com.restaurant.model.Order;
import com.restaurant.model.OrderItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderFileHandler {
    private static final String FILE_PATHS = "C:\\Users\\Asus\\Desktop\\webapp-14\\webapp-11\\src\\main\\webapp\\data\\orders.txt";
    private static final String ITEMS_FILE_PATH = "C:\\Users\\Asus\\Desktop\\webapp-14\\webapp-11\\src\\main\\webapp\\data\\order_items.txt";

    public static void saveOrder(Order order) throws IOException {
        List<Order> orders = loadOrders();
        orders.add(order);
        saveOrders(orders);
        saveOrderItems(order);
    }

    public static List<Order> loadOrders() throws IOException {
        List<Order> orders = new ArrayList<>();
        File file = new File(FILE_PATHS);
        if (!file.exists()) {
            return orders;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 6) {
                        Order order = new Order(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            parts[2],
                            parts[3],
                            parts[4]
                        );
                        order.setStatus(parts[5]);
                        if (parts.length > 6) {
                            order.setOrderDate(parts[6]);
                        }
                        if (parts.length > 7) {
                            order.setDeliveryDate(parts[7]);
                        }
                        orders.add(order);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing order: " + line);
                }
            }
        }

        // Load order items
        for (Order order : orders) {
            loadOrderItems(order);
        }

        return orders;
    }

    private static void saveOrders(List<Order> orders) throws IOException {
        File file = new File(FILE_PATHS);
        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Order order : orders) {
                writer.write(String.format("%d|%d|%s|%s|%s|%s|%s|%s\n",
                    order.getId(),
                    order.getCustomerId(),
                    order.getCustomerName(),
                    order.getCustomerAddress(),
                    order.getCustomerPhone(),
                    order.getStatus(),
                    order.getOrderDate(),
                    order.getDeliveryDate() != null ? order.getDeliveryDate() : ""
                ));
            }
        }
    }

    private static void saveOrderItems(Order order) throws IOException {
        File file = new File(ITEMS_FILE_PATH);
        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for (OrderItem item : order.getItems()) {
                writer.write(String.format("%d|%d|%s|%.2f|%d\n",
                    order.getId(),
                    item.getMenuItemId(),
                    item.getMenuItemName(),
                    item.getPrice(),
                    item.getQuantity()
                ));
            }
        }
    }

    private static void loadOrderItems(Order order) throws IOException {
        File file = new File(ITEMS_FILE_PATH);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 5 && Integer.parseInt(parts[0]) == order.getId()) {
                        OrderItem item = new OrderItem(
                            Integer.parseInt(parts[1]),
                            parts[2],
                            Double.parseDouble(parts[3]),
                            Integer.parseInt(parts[4])
                        );
                        order.addItem(item);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing order item: " + line);
                }
            }
        }
    }

    public static void updateOrder(Order updatedOrder) throws IOException {
        List<Order> orders = loadOrders();
        boolean found = false;

        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == updatedOrder.getId()) {
                orders.set(i, updatedOrder);
                found = true;
                break;
            }
        }

        if (!found) {
            System.err.println("Warning: Order not found for update: " + updatedOrder.getId());
            return;
        }

        saveOrders(orders);
        // Update order items
        File itemsFile = new File(ITEMS_FILE_PATH);
        if (itemsFile.exists()) {
            itemsFile.delete();
        }
        saveOrderItems(updatedOrder);
    }

    public static void deleteOrder(int orderId) throws IOException {
        List<Order> orders = loadOrders();
        orders.removeIf(order -> order.getId() == orderId);
        saveOrders(orders);

        // Delete order items
        File itemsFile = new File(ITEMS_FILE_PATH);
        if (itemsFile.exists()) {
            List<String> remainingItems = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(itemsFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length > 0 && Integer.parseInt(parts[0]) != orderId) {
                        remainingItems.add(line);
                    }
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(itemsFile))) {
                for (String item : remainingItems) {
                    writer.write(item + "\n");
                }
            }
        }
    }

    public static Order getOrderById(int orderId) throws IOException {
        List<Order> orders = loadOrders();
        return orders.stream()
                .filter(order -> order.getId() == orderId)
                .findFirst()
                .orElse(null);
    }
} 
