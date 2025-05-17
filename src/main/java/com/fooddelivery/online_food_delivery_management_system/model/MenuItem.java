package com.fooddelivery.online_food_delivery_management_system.model;

import java.io.Serializable;

public class MenuItem implements Serializable {
    private String id;
    private String restaurantId;
    private String name;
    private String category;
    private String description;
    private double price;
    private boolean isVegetarian;
    private boolean isSpicy;
    private String imageUrl;
    private int preparationTime; // in minutes
    private boolean vegetarian;
    private boolean spicy;

    // Constructors
    public MenuItem() {
        this.imageUrl = "/images/menu-items/default.jpg";
        this.id = generateId();
    }

    public MenuItem(String restaurantId, String name, String category, double price) {
        this();
        this.restaurantId = restaurantId;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    // ... (add all other getters and setters)

    // File operations
    public String toFileString() {
        return String.join(",",
                id, restaurantId, name, category, description,
                String.valueOf(price), String.valueOf(isVegetarian),
                String.valueOf(isSpicy), imageUrl, String.valueOf(preparationTime)
        );
    }

    public static MenuItem fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length >= 10) {
            MenuItem item = new MenuItem(
                    parts[1], parts[2], parts[3], Double.parseDouble(parts[5])
            );
            item.setId(parts[0]);
            item.setDescription(parts[4]);
            item.setVegetarian(Boolean.parseBoolean(parts[6]));
            item.setSpicy(Boolean.parseBoolean(parts[7]));
            item.setImageUrl(parts[8]);
            item.setPreparationTime(Integer.parseInt(parts[9]));
            return item;
        }
        return null;
    }

    private String generateId() {
        return "MI" + System.currentTimeMillis();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setSpicy(boolean spicy) {
        this.spicy = spicy;
    }

    public boolean isSpicy() {
        return spicy;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}