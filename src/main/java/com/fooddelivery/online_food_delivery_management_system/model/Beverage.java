package com.fooddelivery.online_food_delivery_management_system.model;

public class Beverage {
    public class Beverage extends MenuItem {
        public Beverage(int id, String name, double price, String description, boolean available, int restaurantId) {
            super(id, name, price, description, "Beverage", available, restaurantId);
        }

        @Override
        public double calculateFinalPrice() {
            return getPrice();
        }
    }


}
