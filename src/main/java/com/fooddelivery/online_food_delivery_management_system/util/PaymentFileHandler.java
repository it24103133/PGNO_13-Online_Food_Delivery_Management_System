package com.fooddelivery.online_food_delivery_management_system.util;

import com.fooddelivery.online_food_delivery_management_system.model.Payment;
import model.Payment;
import javax.servlet.ServletContext;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PaymentFileHandler {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static String FILE_PATH;

    public static void initialize(ServletContext context) {
        try {
            // Create the data directory in WEB-INF for better security
            String dataDir = context.getRealPath("/WEB-INF/data");
            FILE_PATH = dataDir + "/payments.txt";
            System.out.println("Initializing PaymentFileHandler with path: " + FILE_PATH);

            // Create directory if it doesn't exist
            File directory = new File(dataDir);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                System.out.println("Created directory: " + created + " at " + dataDir);
            }

            // Create file if it doesn't exist
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                boolean created = file.createNewFile();
                System.out.println("Created file: " + created + " at " + FILE_PATH);
            }

            System.out.println("File exists: " + file.exists());
            System.out.println("File can read: " + file.canRead());
            System.out.println("File can write: " + file.canWrite());
            System.out.println("File absolute path: " + file.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Error initializing PaymentFileHandler: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void savePayment(Payment payment) {
        try {
            System.out.println("Saving payment to file: " + FILE_PATH);
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                System.out.println("File doesn't exist, creating new file");
                file.createNewFile();
            }

            try (FileWriter fw = new FileWriter(file, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(payment.toString());
                System.out.println("Payment saved successfully");
            }
        } catch (IOException e) {
            System.err.println("Error saving payment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        try {
            System.out.println("Reading payments from file: " + FILE_PATH);
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                System.out.println("File doesn't exist, returning empty list");
                return payments;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println("Reading line: " + line);
                    String[] parts = line.split(",");
                    if (parts.length >= 7) {
                        try {
                            Payment payment = new Payment(
                                    parts[0], // paymentId
                                    parts[1], // userId
                                    Double.parseDouble(parts[2]), // amount
                                    parts[3], // paymentMethod
                                    parts[4], // status
                                    LocalDateTime.parse(parts[5], DATE_FORMATTER), // timestamp
                                    parts[6]  // description
                            );
                            payments.add(payment);
                            System.out.println("Added payment: " + payment.getPaymentId());
                        } catch (Exception e) {
                            System.err.println("Error parsing payment line: " + line);
                            e.printStackTrace();
                        }
                    }
                }
            }
            System.out.println("Total payments read: " + payments.size());
        } catch (IOException e) {
            System.err.println("Error reading payments: " + e.getMessage());
            e.printStackTrace();
        }
        return payments;
    }

    public static List<Payment> getPaymentsByUser(String userId) {
        List<Payment> allPayments = getAllPayments();
        List<Payment> userPayments = new ArrayList<>();

        for (Payment payment : allPayments) {
            if (payment.getUserId().equals(userId)) {
                userPayments.add(payment);
            }
        }
        return userPayments;
    }

    public static Payment getPaymentById(String paymentId) {
        System.out.println("Getting payment by ID: " + paymentId);
        List<Payment> payments = getAllPayments();
        for (Payment payment : payments) {
            if (payment.getPaymentId().equals(paymentId)) {
                System.out.println("Found payment: " + payment.getPaymentId());
                return payment;
            }
        }
        System.out.println("Payment not found");
        return null;
    }

    public static void updatePayment(Payment updatedPayment) {
        try {
            System.out.println("Updating payment: " + updatedPayment.getPaymentId());
            List<Payment> payments = getAllPayments();
            try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
                for (Payment payment : payments) {
                    if (payment.getPaymentId().equals(updatedPayment.getPaymentId())) {
                        pw.println(updatedPayment.toString());
                    } else {
                        pw.println(payment.toString());
                    }
                }
            }
            System.out.println("Payment updated successfully");
        } catch (IOException e) {
            System.err.println("Error updating payment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void deletePayment(String paymentId) {
        try {
            System.out.println("Deleting payment: " + paymentId);
            List<Payment> payments = getAllPayments();
            try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
                for (Payment payment : payments) {
                    if (!payment.getPaymentId().equals(paymentId)) {
                        pw.println(payment.toString());
                    }
                }
            }
            System.out.println("Payment deleted successfully");
        } catch (IOException e) {
            System.err.println("Error deleting payment: " + e.getMessage());
            e.printStackTrace();
        }
    }
}