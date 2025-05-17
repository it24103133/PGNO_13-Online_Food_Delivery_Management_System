package com.fooddelivery.model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String restaurantId;
    private String name;
    private String cuisineType;
    private String location;
    private String address;
    private String phoneNumber;
    private String email;
    private String openingHours;
    private String closingHours;
    private String description;
    private String imageUrl;
    private double rating;
    private List<String> menuItems;
    private boolean isActive;

    public Restaurant() {
        this.menuItems = new ArrayList<>();
        this.isActive = true;
    }

    // Getters and Setters
    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getClosingHours() {
        return closingHours;
    }

    public void setClosingHours(String closingHours) {
        this.closingHours = closingHours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<String> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<String> menuItems) {
        this.menuItems = menuItems;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return restaurantId + "|" + name + "|" + cuisineType + "|" + location + "|" + 
               address + "|" + phoneNumber + "|" + email + "|" + openingHours + "|" + 
               closingHours + "|" + description + "|" + imageUrl + "|" + rating + "|" + 
               String.join(",", menuItems) + "|" + isActive;
    }

    public static Restaurant fromString(String line) {
        String[] parts = line.split("\\|");
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(parts[0]);
        restaurant.setName(parts[1]);
        restaurant.setCuisineType(parts[2]);
        restaurant.setLocation(parts[3]);
        restaurant.setAddress(parts[4]);
        restaurant.setPhoneNumber(parts[5]);
        restaurant.setEmail(parts[6]);
        restaurant.setOpeningHours(parts[7]);
        restaurant.setClosingHours(parts[8]);
        restaurant.setDescription(parts[9]);
        restaurant.setImageUrl(parts[10]);
        restaurant.setRating(Double.parseDouble(parts[11]));
        if (parts.length > 12 && !parts[12].isEmpty()) {
            restaurant.setMenuItems(List.of(parts[12].split(",")));
        }
        restaurant.setActive(Boolean.parseBoolean(parts[13]));
        return restaurant;
    }
}