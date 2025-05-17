package com.fooddelivery.controller;

import com.fooddelivery.model.Restaurant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HomeServlet", value = "/index")
//@WebServlet("/home")
//@WebServlet(name = "HomeServlet", value = {"/home", "/"})
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get featured restaurants (mock data - replace with actual data from your files)
        List<Restaurant> featuredRestaurants = getFeaturedRestaurants();
        request.setAttribute("featuredRestaurants", featuredRestaurants);

        // Get popular categories (mock data)
        List<String> popularCategories = new ArrayList<>();
        popularCategories.add("Pizza");
        popularCategories.add("Burger");
        popularCategories.add("Sushi");
        popularCategories.add("Pasta");
        popularCategories.add("Salad");
        request.setAttribute("popularCategories", popularCategories);

//        request.getRequestDispatcher("/views/home.jsp").forward(request, response);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private List<Restaurant> getFeaturedRestaurants() {
        // In a real app, you would read this from restaurants.txt
        List<Restaurant> restaurants = new ArrayList<>();

        Restaurant r1 = new Restaurant();
        r1.setRestaurantId("R001");
        r1.setName("Pizza Heaven");
        r1.setCuisineType("Italian");
        r1.setRating(4.5);
        r1.setImageUrl("/images/restaurants/pizza.jpg");
        restaurants.add(r1);

        Restaurant r2 = new Restaurant();
        r2.setRestaurantId("R002");
        r2.setName("Burger King");
        r2.setCuisineType("American");
        r2.setRating(4.2);
        r2.setImageUrl("/images/restaurants/burger.jpg");
        restaurants.add(r2);

        Restaurant r3 = new Restaurant();
        r3.setRestaurantId("R003");
        r3.setName("Sushi World");
        r3.setCuisineType("Japanese");
        r3.setRating(4.7);
        r3.setImageUrl("/images/restaurants/sushi.jpg");
        restaurants.add(r3);

        Restaurant r4 = new Restaurant();
        r4.setRestaurantId("R004");
        r4.setName("Pasta Palace");
        r4.setCuisineType("Italian");
        r4.setRating(4.3);
        r4.setImageUrl("/images/restaurants/pasta.jpg");
        restaurants.add(r4);

        return restaurants;
    }
}