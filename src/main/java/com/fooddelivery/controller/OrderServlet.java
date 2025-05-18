package com.fooddelivery.controller;

import com.fooddelivery.model.*;
import com.fooddelivery.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.\*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderServlet", value = "/orders/\*")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

```
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }

        if (action == null) action = "/";

        switch (action) {
            case "/":
                listOrders(request, response, user);
                break;
            case "/create":
                showCreateForm(request, response, user);
                break;
            case "/track":
                trackOrder(request, response, user);
                break;
            case "/details":
                orderDetails(request, response, user);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }

        if (action == null) action = "/";

        switch (action) {
            case "/create":
                createOrder(request, response, user);
                break;
            case "/cancel":
                cancelOrder(request, response, user);
                break;
            case "/update-status":
                updateOrderStatus(request, response, user);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        List<Order> orders;

        if (user.isAdmin()) {
            orders = OrderFileHandler.getAllOrders();
        } else {
            orders = OrderFileHandler.getOrdersByUser(user.getUserId());
        }

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/views/orders/list.jsp").forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        String restaurantId = request.getParameter("restaurantId");
        Restaurant restaurant = (Restaurant) RestaurantFileHandler.findRestaurantById(restaurantId);
        List<MenuItem> menuItems = MenuItemFileHandler.getMenuItemsByRestaurant(restaurantId);

        if (restaurant != null) {
            request.setAttribute("restaurant", restaurant);
            request.setAttribute("menuItems", menuItems);
            request.getRequestDispatcher("/views/orders/create.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void trackOrder(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        String orderId = request.getParameter("id");
        Order order = OrderFileHandler.getOrderById(orderId);

        if (order != null && (user.getUserId().equals(order.getUserId()) || user.isAdmin())) {
            request.setAttribute("order", order);
            request.getRequestDispatcher("/views/orders/track.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void orderDetails(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        String orderId = request.getParameter("id");
        Order order = OrderFileHandler.getOrderById(orderId);

        if (order != null && (user.getUserId().equals(order.getUserId()) || user.isAdmin())) {
            Restaurant restaurant = (Restaurant) RestaurantFileHandler.findRestaurantById(order.getRestaurantId());
            request.setAttribute("order", order);
            request.setAttribute("restaurant", restaurant);
            request.getRequestDispatcher("/views/orders/details.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void createOrder(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        String restaurantId = request.getParameter("restaurantId");
        String deliveryAddress = request.getParameter("deliveryAddress");
        String paymentMethod = request.getParameter("paymentMethod");
        String instructions = request.getParameter("instructions");

        Order order = new Order(user.getUserId(), restaurantId, deliveryAddress);
        order.setPaymentMethod(paymentMethod);
        order.setDeliveryInstructions(instructions);

        // Add items to order
        String[] itemIds = request.getParameterValues("itemId");
        String[] quantities = request.getParameterValues("quantity");

        if (itemIds != null) {
            for (int i = 0; i < itemIds.length; i++) {
                MenuItem menuItem = MenuItemFileHandler.getMenuItemById(itemIds[i]);
                if (menuItem != null) {
                    int quantity = Integer.parseInt(quantities[i]);
                    if (quantity > 0) {
                        OrderItem item = new OrderItem(
                                menuItem.getId(),
                                menuItem.getName(),
                                menuItem.getPrice(),
                                quantity
                        );
                        order.addItem(item);
                    }
                }
            }
        }

        if (!order.getItems().isEmpty()) {
            OrderFileHandler.saveOrder(order);
            response.sendRedirect(request.getContextPath() + "/orders/details?id=" + order.getId());
        } else {
            request.setAttribute("errorMessage", "Your cart is empty");
            showCreateForm(request, response, user);
        }
    }

    private void cancelOrder(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
        String orderId = request.getParameter("id");
        Order order = OrderFileHandler.getOrderById(orderId);

        if (order != null && user.getUserId().equals(order.getUserId())) {
            OrderFileHandler.cancelOrder(orderId);
        }

        response.sendRedirect(request.getContextPath() + "/orders/details?id=" + orderId);
    }

    private void updateOrderStatus(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
        if (!user.isAdmin()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String orderId = request.getParameter("id");
        String status = request.getParameter("status");
        Order order = OrderFileHandler.getOrderById(orderId);

        if (order != null) {
            order.setStatus(status);
            OrderFileHandler.updateOrder(order);
        }

        response.sendRedirect(request.getContextPath() + "/orders/details?id=" + orderId);
    }
```

}