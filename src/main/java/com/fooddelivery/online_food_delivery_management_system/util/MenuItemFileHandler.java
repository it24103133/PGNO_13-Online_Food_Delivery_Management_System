package com.fooddelivery.online_food_delivery_management_system.util;

import com.fooddelivery.online_food_delivery_management_system.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemFileHandler {
    private static final String FILE_PATH = "data/menuitems.txt";

    public static void saveMenuItem(MenuItem item) {
        List<MenuItem> items = loadMenuItems();

        int maxId = items.stream()
                .mapToInt(MenuItem::getId)
                .max()
                .orElse(0);
        item.setId(maxId + 1);
        items.add(item);
        saveMenuItems(items);
    }

    public static List<MenuItem> loadMenuItems() {
        List<MenuItem> items = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return items;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 8) {
                    try {
                        MenuItem item = new MenuItem(
                                Integer.parseInt(parts[0]),
                                parts[1],
                                Double.parseDouble(parts[2]),
                                parts[3],
                                parts[4],
                                Boolean.parseBoolean(parts[5]),
                                Integer.parseInt(parts[6]),
                                parts[7]
                        );
                        items.add(item);
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing menu item: " + line);
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading menu items: " + e.getMessage());
            e.printStackTrace();
        }
        return items;
    }

    public static void updateMenuItem(MenuItem updatedItem) {
        List<MenuItem> items = loadMenuItems();
        boolean found = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == updatedItem.getId()) {
                items.set(i, updatedItem);
                found = true;
                break;
            }
        }
        if (!found) {
            System.err.println("Warning: Menu item with ID " + updatedItem.getId() + " not found for update");
        }
        saveMenuItems(items);
    }

    public static void deleteMenuItem(int itemId) {
        List<MenuItem> items = loadMenuItems();
        int initialSize = items.size();
        items.removeIf(item -> item.getId() == itemId);
        if (items.size() == initialSize) {
            System.err.println("Warning: Menu item with ID " + itemId + " not found for deletion");
        }
        saveMenuItems(items);
    }

    private static void saveMenuItems(List<MenuItem> items) {
        File file = new File(FILE_PATH);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                System.err.println("Error: Could not create directory: " + parentDir.getAbsolutePath());
                return;
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (MenuItem item : items) {
                writer.println(String.format("%d|%s|%.2f|%s|%s|%b|%d|%s",
                        item.getId(),
                        item.getName(),
                        item.getPrice(),
                        item.getDescription(),
                        item.getCategory(),
                        item.isAvailable(),
                        item.getRestaurantId(),
                        item.getImageUrl()
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving menu items: " + e.getMessage());
            e.printStackTrace();
        }
    }
}