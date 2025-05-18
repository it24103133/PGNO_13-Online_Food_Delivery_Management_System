package com.fooddelivery.controller;

import com.fooddelivery.model.User;
import com.fooddelivery.util.FileHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/user/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 5,   // 5 MB
    maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class UserServlet extends HttpServlet {
    private static final String USERS_FILE = "C:\\Users\\Asus\\Desktop\\webapp-11 new (2)\\webapp-11 new\\webapp-11\\src\\main\\webapp\\data\\users.txt";
    private static final String AVATAR_DIR = "C:\\Users\\Asus\\Desktop\\webapp-11 new (2)\\webapp-11 new\\webapp-11\\src\\main\\webapp\\images\\avatars\\profilepic";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = request.getPathInfo();
        if (action == null) action = "/";

        switch (action) {
            case "/register":
            case "register": {
                // Get form data
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String email = request.getParameter("email");
                String fullName = request.getParameter("fullName");
                String phoneNumber = request.getParameter("phoneNumber");
                String address = request.getParameter("address");
                // Create new user
                User newUser = new User();
                newUser.setUserId(FileHandler.getNextUserId());
                newUser.setUsername(username);
                newUser.setPassword(password);
                newUser.setEmail(email);
                newUser.setFullName(fullName);
                newUser.setPhoneNumber(phoneNumber);
                newUser.setAddress(address);
                // Save to file
                FileHandler.saveUser(newUser);
                // Redirect to login
                response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
                break;
            }
            case "/login":
            case "login": {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                List<User> users = FileHandler.readUsers();
                for (User user : users) {
                    if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);
                        response.sendRedirect(request.getContextPath() + "/auth/profile.jsp");
                        return;
                    }
                }
                request.setAttribute("errorMessage", "Invalid username or password");
                request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
                break;
            }
            case "/update":
            case "update": {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                if (user == null) {
                    response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
                    return;
                }
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                String phoneNumber = request.getParameter("phoneNumber");
                String address = request.getParameter("address");
                String currentPassword = request.getParameter("currentPassword");
                if (!user.getPassword().equals(currentPassword)) {
                    request.setAttribute("errorMessage", "Current password is incorrect.");
                    request.getRequestDispatcher("/auth/editProfile.jsp").forward(request, response);
                    return;
                }
                user.setFullName(fullName);
                user.setEmail(email);
                user.setPhoneNumber(phoneNumber);
                user.setAddress(address);
                // Update in file
                List<User> users = FileHandler.readUsers();
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getUserId().equals(user.getUserId())) {
                        users.set(i, user);
                        break;
                    }
                }
                FileHandler.writeAllUsers(users);
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/auth/profile.jsp");
                break;
            }
            case "/upload-avatar":
            case "upload-avatar": {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                if (user == null) {
                    response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
                    return;
                }
                Part filePart = request.getPart("avatar");
                if (filePart != null && filePart.getSize() > 0) {
                    // Create directory if it doesn't exist
                    Path avatarDir = Paths.get(AVATAR_DIR);
                    if (!Files.exists(avatarDir)) {
                        Files.createDirectories(avatarDir);
                    }

                    // Generate unique filename
                    String fileName = user.getUserId() + "_" + System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
                    Path avatarPath = avatarDir.resolve(fileName);
                    
                    // Save the file
                    filePart.write(avatarPath.toString());
                    
                    // Update user's avatar URL
                    String avatarUrl = request.getContextPath() + "/images/avatars/profilepic/" + fileName;
                    user.setAvatarUrl(avatarUrl);
                    
                    // Update in file
                    List<User> users = FileHandler.readUsers();
                    for (int i = 0; i < users.size(); i++) {
                        if (users.get(i).getUserId().equals(user.getUserId())) {
                            users.set(i, user);
                            break;
                        }
                    }
                    FileHandler.writeAllUsers(users);
                    session.setAttribute("user", user);
                    request.setAttribute("successMessage", "Avatar updated successfully!");
                } else {
                    request.setAttribute("errorMessage", "Please select an image to upload!");
                }
                request.getRequestDispatcher("/auth/editProfile.jsp").forward(request, response);
                break;
            }
            default:
                response.sendRedirect(request.getContextPath() + "/");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = request.getPathInfo();
        if (action == null) action = "/";
        switch (action) {
            case "/logout":
            case "logout": {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                response.sendRedirect(request.getContextPath() + "/");
                break;
            }
            default:
                response.sendRedirect(request.getContextPath() + "/");
        }
    }
}