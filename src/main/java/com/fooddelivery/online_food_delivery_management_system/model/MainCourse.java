package com.fooddelivery.online_food_delivery_management_system.model;

public class MainCourse {
    public class MainCourse extends MenuItem {
        public MainCourse(int id, String name, double price, String description, boolean available, int restaurantId, String imageUrl) {
            super(id, name, price, description, "Main Course", available, restaurantId, imageUrl);
            this.id = id;
            this.name =  name;
            this.price = price;
            this.description = description;
            this.available = available;
            this.restaurantId = restaurantId;

        }

        @Override
        public double calculateFinalPrice() {
            return getPrice() * 1.1; // Main courses have 10% premium
        }
    }

}
