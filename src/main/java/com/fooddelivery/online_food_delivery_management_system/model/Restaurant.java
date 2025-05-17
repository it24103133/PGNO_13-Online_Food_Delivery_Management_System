package com.fooddelivery.online_food_delivery_management_system.model;

import java.io.Serializable;
import java.util.UUID;

public class Restaurant implements Serializable {
    private String id;
    private String name;
    private String cuisineType;
    private String address;
    private String phone;
    private String email;
    private String openingHours;
    private double rating;
    private String deliveryTime;
    private String imageUrl;
    private String description;

    // Simplified constructors
    public Restaurant(String r001, String pizzaHeaven, String italian, String s, String string, String s1) {
        this.id = generateId();
        this.imageUrl = "/images/default.png";
    }

    public Restaurant(String name, String cuisineType, String address) {
        this("R001", "Pizza Heaven", "Italian", "4.5", "30-45 min", "/images/restaurants/pizza.jpg");
        this.name = name;
        this.cuisineType = cuisineType;
        this.address = address;
    }

    public Restaurant(String name, String cuisineType, String address,
                      String phone, String email, String openingHours,
                      double rating, String deliveryTime, String imageUrl,
                      String description) {
        this(name, cuisineType, address);
        this.phone = phone;
        this.email = email;
        this.openingHours = openingHours;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Restaurant(String name, String cuisineType, String address, Object o) {

    }

    // File handling methods
    public String toFileString() {
        return String.join("|",
                id,
                name,
                cuisineType,
                description != null ? description : "",
                address != null ? address : "",
                phone != null ? phone : "",
                email != null ? email : "",
                openingHours != null ? openingHours : "",
                imageUrl != null ? imageUrl : "",
                String.valueOf(rating),
                deliveryTime != null ? deliveryTime : ""
        );
    }

    public static Restaurant fromFileString(String fileString) {
        String[] parts = fileString.split("\\|");
        if (parts.length >= 10) {
            return new Restaurant(
                    parts[1],  // name
                    parts[2],  // cuisineType
                    parts[4],  // address
                    parts[5],  // phone
                    parts[6],  // email
                    parts[7],  // openingHours
                    Double.parseDouble(parts[9]),  // rating
                    parts.length > 10 ? parts[10] : "",  // deliveryTime
                    parts[8],  // imageUrl
                    parts[3]   // description
            ).setId(parts[0]); // id
        }
        return null;
    }

    private static String generateId() {

        return "R" + UUID.randomUUID().toString().substring(0, 8);
    }

    // Modified getters/setters (showing pattern for all fields)
    public String getId() { return id; }
    public Restaurant setId(String id) { this.id = id;
        return null;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCuisineType() { return cuisineType; }
    public void setCuisineType(String cuisineType) { this.cuisineType = cuisineType; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getOpeningHours() { return openingHours; }
    public void setOpeningHours(String openingHours) { this.openingHours = openingHours; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getDeliveryTime() { return deliveryTime; }
    public void setDeliveryTime(String deliveryTime) { this.deliveryTime = deliveryTime; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}