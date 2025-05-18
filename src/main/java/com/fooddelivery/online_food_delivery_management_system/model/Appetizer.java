package com.fooddelivery.online_food_delivery_management_system.model;

public class Appetizer {
    public class Appetizer extends MenuItem {
        public Appetizer(int id, String name, double price, String description, boolean available, int restaurantId) {
            super(id, name, price, description, "Appetizer", available, restaurantId);
            this.id = id;
            this.name =  name;
            this.price = price;
            this.description = description;
            this.available = available;
            this.restaurantId = restaurantId;

        }

        @Override
        public double calculateFinalPrice() {
            return getPrice();
        }
    }

}
