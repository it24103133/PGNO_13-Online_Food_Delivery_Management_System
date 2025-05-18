package com.fooddelivery.util;

import com.fooddelivery.model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String DATA_DIR = "C:\\Users\\Asus\\Desktop\\webapp-11 new (2)\\webapp-11 new\\webapp-11\\src\\main\\webapp\\data\\users.txt";
//    private static final String USERS_FILE = "users.txt";

    // Full path to users.txt file
    private static Path getUsersFilePath() {
        return Paths.get(DATA_DIR);
    }

    public static List<User> readUsers() {
        List<User> users = new ArrayList<>();
        Path path = getUsersFilePath();

        try {
            if (Files.exists(path)) {
                List<String> lines = Files.readAllLines(path);
                for (String line : lines) {
                    User user = User.fromFileString(line);
                    if (user != null) {
                        users.add(user);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void saveUser(User user) throws IOException {
        Path path = getUsersFilePath();

        // Create directories if not exist
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }

        // Create file if not exist
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        String data = user.toFileString() + System.lineSeparator();
        Files.write(path, data.getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

    public static String getNextUserId() {
        List<User> users = readUsers();
        return "USR" + (users.size() + 1);
    }

    public static void appendToFile(String fileName, String fileString) {
        Path path = Paths.get(DATA_DIR, fileName);

        try {
            // Ensure directory exists
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            // Create file if it does not exist
            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            Files.write(path, fileString.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getResourcePath(String fileName) {
        // This example assumes the resource is under /resources folder or a similar setup.
        // Adjust this according to your actual project structure.
        String basePath = System.getProperty("user.dir"); // current working directory
        return basePath + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + fileName;}

    public static void writeAllUsers(List<User> users) {
        Path path = getUsersFilePath();
        List<String> lines = new ArrayList<>();
        for (User user : users) {
            lines.add(user.toFileString());
        }
        try {
            // Ensure directory exists
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}