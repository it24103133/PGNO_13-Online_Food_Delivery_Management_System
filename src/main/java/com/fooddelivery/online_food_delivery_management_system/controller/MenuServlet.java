package com.fooddelivery.online_food_delivery_management_system.controller;

import com.fooddelivery.online_food_delivery_management_system.model.MenuItem;
import com.fooddelivery.online_food_delivery_management_system.util.MenuItemFileHandler;
import com.fooddelivery.online_food_delivery_management_system.model.MenuItem;
import com.fooddelivery.online_food_delivery_management_system.util.MenuItemFileHandler;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.UUID;

@WebServlet("/menu/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 15
)
public class MenuServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIRECTORY = "images/menuitems";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {

            listMenuItems(request, response);
        } else if (pathInfo.equals("/add")) {

            request.getRequestDispatcher("/WEB-INF/c3/addmenu.jsp").forward(request, response);
        } else if (pathInfo.equals("/edit")) {

            int itemId = Integer.parseInt(request.getParameter("id"));
            List<MenuItem> items = MenuItemFileHandler.loadMenuItems();
            MenuItem item = items.stream()
                    .filter(i -> i.getId() == itemId)
                    .findFirst()
                    .orElse(null);
            if (item == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Menu item not found");
                return;
            }
            request.setAttribute("menuItem", item);
            request.getRequestDispatcher("/WEB-INF/c3/editmenu.jsp").forward(request, response);
        } else if (pathInfo.equals("/view")) {

            int itemId = Integer.parseInt(request.getParameter("id"));
            List<MenuItem> items = MenuItemFileHandler.loadMenuItems();
            MenuItem item = items.stream()
                    .filter(i -> i.getId() == itemId)
                    .findFirst()
                    .orElse(null);
            if (item == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Menu item not found");
                return;
            }
            request.setAttribute("menuItem", item);
            request.getRequestDispatcher("/WEB-INF/c3/viewmenu.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {

                createMenuItem(request, response);
            } else if (pathInfo.equals("/update")) {

                updateMenuItem(request, response);
            } else if (pathInfo.equals("/delete")) {

                deleteMenuItem(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input format");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    private void listMenuItems(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<MenuItem> items = MenuItemFileHandler.loadMenuItems();
        request.setAttribute("menuItems", items);
        request.getRequestDispatcher("/WEB-INF/c3/listmenu.jsp").forward(request, response);
    }

    private void createMenuItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        boolean available = request.getParameter("available") != null;
        int restaurantId = Integer.parseInt(request.getParameter("restaurantId"));


        String imageUrl = handleFileUpload(request);

        MenuItem item = new MenuItem(0, name, price, description, category, available, restaurantId, imageUrl);
        MenuItemFileHandler.saveMenuItem(item);

        response.sendRedirect(request.getContextPath() + "/menu");
    }

    private void updateMenuItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        boolean available = request.getParameter("available") != null;
        int restaurantId = Integer.parseInt(request.getParameter("restaurantId"));


        String imageUrl = handleFileUpload(request);
        if (imageUrl == null) {

            List<MenuItem> items = MenuItemFileHandler.loadMenuItems();
            MenuItem existingItem = items.stream()
                    .filter(i -> i.getId() == id)
                    .findFirst()
                    .orElse(null);
            if (existingItem != null) {
                imageUrl = existingItem.getImageUrl();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Menu item not found");
                return;
            }
        }

        MenuItem item = new MenuItem(id, name, price, description, category, available, restaurantId, imageUrl);
        MenuItemFileHandler.updateMenuItem(item);

        response.sendRedirect(request.getContextPath() + "/menu");
    }

    private void deleteMenuItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int itemId = Integer.parseInt(request.getParameter("id"));
        MenuItemFileHandler.deleteMenuItem(itemId);
        response.sendRedirect(request.getContextPath() + "/menu");
    }

    private String handleFileUpload(HttpServletRequest request) throws ServletException, IOException {
        try {
            Part filePart = request.getPart("image");
            if (filePart == null || filePart.getSize() == 0) {
                return null;
            }


            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                if (!uploadDir.mkdirs()) {
                    throw new IOException("Failed to create upload directory");
                }
            }

            String fileName = UUID.randomUUID().toString() + getFileExtension(filePart.getSubmittedFileName());
            String filePath = uploadPath + File.separator + fileName;


            filePart.write(filePath);


            return UPLOAD_DIRECTORY + "/" + fileName;
        } catch (Exception e) {
            getServletContext().log("Error uploading file", e);
            return null;
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) return "";
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) return "";
        return fileName.substring(lastIndexOf);
    }
}