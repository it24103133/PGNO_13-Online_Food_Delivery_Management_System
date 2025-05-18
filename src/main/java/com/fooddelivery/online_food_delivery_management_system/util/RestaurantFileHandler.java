package com.fooddelivery.online_food_delivery_management_system.util;

import com.fooddelivery.online_food_delivery_management_system.model.Restaurant;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RestaurantFileHandler {
    private static final String RESTAURANTS_FILE = "src/main/webapp/data/restaurants.txt";

    private static void ensureFileExists() {
        File file = new File(RESTAURANTS_FILE);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Restaurant> readRestaurants() {
        ensureFileExists();
        List<Restaurant> restaurants = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RESTAURANTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    try {
                        restaurants.add(Restaurant.fromString(line));
                    } catch (Exception e) {
                        System.err.println("Error parsing restaurant: " + line);
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading restaurants file: " + e.getMessage());
            e.printStackTrace();
        }
        return restaurants;
    }

    public static void saveRestaurant(Restaurant restaurant) {
        ensureFileExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESTAURANTS_FILE, true))) {
            if (restaurant.getRestaurantId() == null) {
                restaurant.setRestaurantId(generateRestaurantId());
            }
            writer.write(restaurant.toString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving restaurant: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void updateRestaurant(Restaurant updatedRestaurant) {
        ensureFileExists();
        List<Restaurant> restaurants = readRestaurants();
        for (int i = 0; i < restaurants.size(); i++) {
            if (restaurants.get(i).getRestaurantId().equals(updatedRestaurant.getRestaurantId())) {
                restaurants.set(i, updatedRestaurant);
                break;
            }
        }
        writeAllRestaurants(restaurants);
    }

    public static void deleteRestaurant(String restaurantId) {
        ensureFileExists();
        List<Restaurant> restaurants = readRestaurants();
        restaurants.removeIf(r -> r.getRestaurantId().equals(restaurantId));
        writeAllRestaurants(restaurants);
    }

    public static void writeAllRestaurants(List<Restaurant> restaurants) {
        ensureFileExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESTAURANTS_FILE))) {
            for (Restaurant restaurant : restaurants) {
                writer.write(restaurant.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing restaurants: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String generateRestaurantId() {
        return "R" + UUID.randomUUID().toString().substring(0, 8);
    }

    public static Restaurant getRestaurantById(String restaurantId) {
        ensureFileExists();
        List<Restaurant> restaurants = readRestaurants();
        return restaurants.stream()
                .filter(r -> r.getRestaurantId().equals(restaurantId))
                .findFirst()
                .orElse(null);
    }

    public static List<Restaurant> searchRestaurants(String query) {
        ensureFileExists();
        List<Restaurant> restaurants = readRestaurants();
        final String searchQuery = query.toLowerCase();
        return restaurants.stream()
                .filter(r -> r.getName().toLowerCase().contains(searchQuery) ||
                        r.getCuisineType().toLowerCase().contains(searchQuery) ||
                        r.getLocation().toLowerCase().contains(searchQuery))
                .collect(Collectors.toList());
    }
}