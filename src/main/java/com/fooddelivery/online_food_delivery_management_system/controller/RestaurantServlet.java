package com.fooddelivery.online_food_delivery_management_system.controller;

import com.fooddelivery.online_food_delivery_management_system.model.Restaurant;
import com.fooddelivery.online_food_delivery_management_system.util.RestaurantFileHandler;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/restaurant/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 5,   // 5 MB
        maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class RestaurantServlet extends HttpServlet {
    private static final String RESTAURANT_IMAGES_DIR = "src/main/webapp/images/restaurants";

    private List<Restaurant> getTopRatedRestaurants(int limit) {
        List<Restaurant> allRestaurants = RestaurantFileHandler.readRestaurants();
        return allRestaurants.stream()
                .sorted((r1, r2) -> Double.compare(r2.getRating(), r1.getRating()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = request.getPathInfo();
        if (action == null) action = "/";

        switch (action) {
            case "/":
            case "/home": {
                // Get top 4 rated restaurants for the home page
                List<Restaurant> featuredRestaurants = getTopRatedRestaurants(4);
                request.setAttribute("featuredRestaurants", featuredRestaurants);
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            }
            case "/list":
            case "list": {
                List<Restaurant> restaurants = RestaurantFileHandler.readRestaurants();
                request.setAttribute("restaurants", restaurants);
                request.getRequestDispatcher("/c2/list.jsp").forward(request, response);
                break;
            }
            case "/view":
            case "view": {
                String restaurantId = request.getParameter("id");
                Restaurant restaurant = RestaurantFileHandler.getRestaurantById(restaurantId);
                if (restaurant != null) {
                    request.setAttribute("restaurant", restaurant);
                    request.getRequestDispatcher("/c2/view.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/restaurant/list");
                }
                break;
            }
            case "/edit":
            case "edit": {
                String restaurantId = request.getParameter("id");
                Restaurant restaurant = RestaurantFileHandler.getRestaurantById(restaurantId);
                if (restaurant != null) {
                    request.setAttribute("restaurant", restaurant);
                    request.getRequestDispatcher("/c2/edit.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/restaurant/list");
                }
                break;
            }
            case "/delete":
            case "delete": {
                String restaurantId = request.getParameter("id");
                RestaurantFileHandler.deleteRestaurant(restaurantId);
                response.sendRedirect(request.getContextPath() + "/restaurant/list");
                break;
            }
            case "/search":
            case "search": {
                String query = request.getParameter("query");
                List<Restaurant> restaurants = RestaurantFileHandler.searchRestaurants(query);
                request.setAttribute("restaurants", restaurants);
                request.setAttribute("searchQuery", query);
                request.getRequestDispatcher("/c2/list.jsp").forward(request, response);
                break;
            }
            default:
                response.sendRedirect(request.getContextPath() + "/restaurant/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = request.getPathInfo();
        if (action == null) action = "/";

        switch (action) {
            case "/add":
            case "add": {
                Restaurant restaurant = new Restaurant();
                restaurant.setName(request.getParameter("name"));
                restaurant.setCuisineType(request.getParameter("cuisineType"));
                restaurant.setLocation(request.getParameter("location"));
                restaurant.setAddress(request.getParameter("address"));
                restaurant.setPhoneNumber(request.getParameter("phoneNumber"));
                restaurant.setEmail(request.getParameter("email"));
                restaurant.setOpeningHours(request.getParameter("openingHours"));
                restaurant.setClosingHours(request.getParameter("closingHours"));
                restaurant.setDescription(request.getParameter("description"));
                restaurant.setRating(0.0); // Default rating

                // Handle image upload
                Part filePart = request.getPart("image");
                if (filePart != null && filePart.getSize() > 0) {
                    String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
                    Path imagePath = Paths.get(RESTAURANT_IMAGES_DIR, fileName);
                    Files.createDirectories(imagePath.getParent());
                    filePart.write(imagePath.toString());
                    restaurant.setImageUrl("/images/restaurants/" + fileName);
                } else {
                    restaurant.setImageUrl("/images/restaurants/default.jpg");
                }

                RestaurantFileHandler.saveRestaurant(restaurant);
                response.sendRedirect(request.getContextPath() + "/restaurant/list");
                break;
            }
            case "/update":
            case "update": {
                String restaurantId = request.getParameter("restaurantId");
                Restaurant restaurant = RestaurantFileHandler.getRestaurantById(restaurantId);
                if (restaurant != null) {
                    restaurant.setName(request.getParameter("name"));
                    restaurant.setCuisineType(request.getParameter("cuisineType"));
                    restaurant.setLocation(request.getParameter("location"));
                    restaurant.setAddress(request.getParameter("address"));
                    restaurant.setPhoneNumber(request.getParameter("phoneNumber"));
                    restaurant.setEmail(request.getParameter("email"));
                    restaurant.setOpeningHours(request.getParameter("openingHours"));
                    restaurant.setClosingHours(request.getParameter("closingHours"));
                    restaurant.setDescription(request.getParameter("description"));

                    // Handle image upload
                    Part filePart = request.getPart("image");
                    if (filePart != null && filePart.getSize() > 0) {
                        String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
                        Path imagePath = Paths.get(RESTAURANT_IMAGES_DIR, fileName);
                        Files.createDirectories(imagePath.getParent());
                        filePart.write(imagePath.toString());
                        restaurant.setImageUrl("/images/restaurants/" + fileName);
                    }

                    RestaurantFileHandler.updateRestaurant(restaurant);
                }
                response.sendRedirect(request.getContextPath() + "/restaurant/list");
                break;
            }
            case "/rate":
            case "rate": {
                String restaurantId = request.getParameter("restaurantId");
                double rating = Double.parseDouble(request.getParameter("rating"));
                Restaurant restaurant = RestaurantFileHandler.getRestaurantById(restaurantId);
                if (restaurant != null) {
                    restaurant.setRating(rating);
                    RestaurantFileHandler.updateRestaurant(restaurant);
                }
                response.sendRedirect(request.getContextPath() + "/restaurant/view?id=" + restaurantId);
                break;
            }
            default:
                response.sendRedirect(request.getContextPath() + "/restaurant/list");
        }
    }
}