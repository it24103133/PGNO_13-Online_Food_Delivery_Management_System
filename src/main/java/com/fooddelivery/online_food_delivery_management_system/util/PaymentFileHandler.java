package com.fooddelivery.online_food_delivery_management_system.util;

import com.fooddelivery.online_food_delivery_management_system.model.Payment;
import java.util.ArrayList;
import java.util.List;

public class PaymentFileHandler {
    private static final String FILE_PATH = "payments.txt";

    public static void savePayment(Payment payment) {
        String data = payment.toFileString() + "\n";
        FileHandler.appendToFile(FILE_PATH, data);
    }

    public static List<Payment> getAllPayments() {
        List<String> lines = FileHandler.readFromFile(FILE_PATH);
        List<Payment> payments = new ArrayList<>();

        for (String line : lines) {
            Payment payment = Payment.fromFileString(line);
            if (payment != null) {
                payments.add(payment);
            }
        }
        return payments;
    }

    public static Payment getPaymentById(String paymentId) {
        List<Payment> payments = getAllPayments();
        for (Payment payment : payments) {
            if (payment.getPaymentId().equals(paymentId)) {
                return payment;
            }
        }
        return null;
    }

    public static List<Payment> getPaymentsByUser(String userId) {
        List<Payment> payments = getAllPayments();
        List<Payment> userPayments = new ArrayList<>();

        for (Payment payment : payments) {
            if (payment.getUserId().equals(userId)) {
                userPayments.add(payment);
            }
        }
        return userPayments;
    }

    public static void updatePayment(Payment updatedPayment) {
        List<Payment> payments = getAllPayments();
        List<String> updatedLines = new ArrayList<>();

        for (Payment payment : payments) {
            if (payment.getPaymentId().equals(updatedPayment.getPaymentId())) {
                updatedLines.add(updatedPayment.toFileString());
            } else {
                updatedLines.add(payment.toFileString());
            }
        }

        FileHandler.writeToFile(FILE_PATH, String.join("\n", updatedLines));
    }
}
