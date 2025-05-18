package com.fooddelivery.online_food_delivery_management_system.util;

import com.fooddelivery.model.Restaurant;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class RestaurantFileHandler extends FileHandler {
    private static final String RESTAURANTS_FILE = "data/restaurants.txt";

    public static List<Restaurant> getAllRestaurants() throws IOException {
        List<Restaurant> restaurants = new ArrayList<>();
        List<String> lines = readAllLines(getFilePath(RESTAURANTS_FILE));

        for (String line : lines) {
            Restaurant restaurant = Restaurant.fromFileString(line);
            if (restaurant != null) {
                restaurants.add(restaurant);
            }
        }
        return restaurants;
    }

    private static Path getFilePath(String restaurantsFile) {
        return java.nio.file.Paths.get(restaurantsFile);
    }

    public static void saveRestaurant(Restaurant restaurant) {
        appendLine(getFilePath(RESTAURANTS_FILE), restaurant.toFileString());
    }

    public static void updateRestaurant(Restaurant updatedRestaurant) throws IOException {
        List<Restaurant> restaurants = getAllRestaurants();
        List<String> updatedLines = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            if (restaurant.getId().equals(updatedRestaurant.getId())) {
                updatedLines.add(updatedRestaurant.toFileString());
            } else {
                updatedLines.add(restaurant.toFileString());
            }
        }

        writeAllLines(getFilePath(RESTAURANTS_FILE), updatedLines);
    }

    private static void appendLine(Path filePath, String line) {
        try {
            java.nio.file.Files.write(filePath, (line + System.lineSeparator()).getBytes(), java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeAllLines(Path filePath, List<String> lines) {
        try {
            java.nio.file.Files.write(filePath, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteRestaurant(String restaurantId) throws IOException {
        List<Restaurant> restaurants = getAllRestaurants();
        List<String> updatedLines = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            if (!restaurant.getId().equals(restaurantId)) {
                updatedLines.add(restaurant.toFileString());
            }
        }

        writeAllLines(getFilePath(RESTAURANTS_FILE), updatedLines);
    }

    public static Object findRestaurantById(String restaurantId) {
        return null;
    }
}