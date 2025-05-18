package com.fooddelivery.online_food_delivery_management_system.model;

public class Appetizer {
    public class Appetizer extends MenuItem {
        public Appetizer(int id, String name, double price, String description, boolean available, int restaurantId) {
            super(id, name, price, description, "Appetizer", available, restaurantId);
        }

        @Override
        public double calculateFinalPrice() {
            return getPrice();
        }
    }

}
