package com.fooddelivery.online_food_delivery_management_system.model;

public class Dessert {

    public class Dessert extends MenuItem {
        public Dessert(int id, String name, double price, String description, boolean available, int restaurantId) {
            super(id, name, price, description, "Dessert", available, restaurantId);
        }

        @Override
        public double calculateFinalPrice() {
            return getPrice() * 0.9;
        }
    }

}
