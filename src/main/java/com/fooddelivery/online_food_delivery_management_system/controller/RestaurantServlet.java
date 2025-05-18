package com.fooddelivery.online_food_delivery_management_system.controller;

import com.fooddelivery.online_food_delivery_management_system.model.Restaurant;
import com.fooddelivery.online_food_delivery_management_system.util.FileHandler;
import com.fooddelivery.online_food_delivery_management_system.util.RestaurantFileHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "RestaurantServlet", value = "/restaurants/*")
@MultipartConfig
public class RestaurantServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action == null) action = "/";

        switch (action) {
            case "/":
                listRestaurants(request, response);
                break;
            case "/add":
                showAddForm(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/view":
                viewRestaurant(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action == null) action = "/";

        switch (action) {
            case "/add":
                addRestaurant(request, response);
                break;
            case "/update":
                updateRestaurant(request, response);
                break;
            case "/delete":
                deleteRestaurant(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void listRestaurants(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Restaurant> restaurants = RestaurantFileHandler.getAllRestaurants();
        request.setAttribute("restaurants", restaurants);
        request.getRequestDispatcher("/c2/list.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/c2/add.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Restaurant restaurant = findRestaurantById(id);

        if (restaurant != null) {
            request.setAttribute("restaurant", restaurant);
            request.getRequestDispatcher("/c2/edit.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void viewRestaurant(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Restaurant restaurant = findRestaurantById(id);

        if (restaurant != null) {
            request.setAttribute("restaurant", restaurant);
            request.getRequestDispatcher("/c2/view.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void addRestaurant(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Restaurant restaurant = new Restaurant(
                generateId(),
                request.getParameter("name"),
                request.getParameter("cuisineType"),
                request.getParameter("address")
        );

        restaurant.setPhone(request.getParameter("phone"));
        restaurant.setEmail(request.getParameter("email"));
        restaurant.setOpeningHours(request.getParameter("openingHours"));
        restaurant.setDescription(request.getParameter("description"));

        // Handle image upload
        Part filePart = request.getPart("image");
        if (filePart != null && filePart.getSize() > 0) {
            String imageUrl = handleImageUpload(filePart);
            restaurant.setImageUrl(imageUrl);
        }

        RestaurantFileHandler.saveRestaurant(restaurant);
        response.sendRedirect(request.getContextPath() + "/restaurants/");
    }

    private void updateRestaurant(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Restaurant restaurant = findRestaurantById(request.getParameter("id"));

        if (restaurant != null) {
            restaurant.setName(request.getParameter("name"));
            restaurant.setCuisineType(request.getParameter("cuisineType"));
            restaurant.setAddress(request.getParameter("address"));
            restaurant.setPhone(request.getParameter("phone"));
            restaurant.setEmail(request.getParameter("email"));
            restaurant.setOpeningHours(request.getParameter("openingHours"));
            restaurant.setDescription(request.getParameter("description"));

            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                String imageUrl = handleImageUpload(filePart);
                restaurant.setImageUrl(imageUrl);
            }

            RestaurantFileHandler.updateRestaurant(restaurant);
            response.sendRedirect(request.getContextPath() + "/restaurants/");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void deleteRestaurant(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        RestaurantFileHandler.deleteRestaurant(id);
        response.sendRedirect(request.getContextPath() + "/restaurants/");
    }

    private Restaurant findRestaurantById(String id) throws IOException {
        List<Restaurant> restaurants = RestaurantFileHandler.getAllRestaurants();
        return restaurants.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private String generateId() {
        return "R" + System.currentTimeMillis();
    }

    private String handleImageUpload(Part filePart) throws IOException {
        String fileName = "restaurant-" + System.currentTimeMillis() +
                filePart.getSubmittedFileName().substring(
                        filePart.getSubmittedFileName().lastIndexOf(".")
                );

        String uploadPath = FileHandler.getResourcePath("images/restaurants/" + fileName);
        filePart.write(uploadPath);

        return "/images/restaurants/" + fileName;
    }
}