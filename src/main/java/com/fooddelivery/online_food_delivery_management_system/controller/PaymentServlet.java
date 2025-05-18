package com.fooddelivery.online_food_delivery_management_system.controller;

import com.fooddelivery.online_food_delivery_management_system.model.Payment;
import com.fooddelivery.online_food_delivery_management_system.util.PaymentFileHandler;
import com.fooddelivery.online_food_delivery_management_system.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = {"/payment/*"})
public class PaymentServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("Initializing PaymentServlet");
        PaymentFileHandler.initialize(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        User user = (User) request.getSession().getAttribute("user");
        String userId = user != null ? user.getUserId() : null;

        if (userId == null) {
            // Redirect to login if not authenticated
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        if (pathInfo == null || pathInfo.equals("/")) {
            // Show payment form
            request.getRequestDispatcher("/c5/paymentdetails.jsp").forward(request, response);
        } else if (pathInfo.equals("/history")) {
            // Show payment history
            List<Payment> payments = PaymentFileHandler.getPaymentsByUser(userId);
            System.out.println("Retrieved " + payments.size() + " payments for history page");
            request.setAttribute("payments", payments);
            request.getRequestDispatcher("/c5/paymenthistory.jsp").forward(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            // Show edit payment form
            String paymentId = pathInfo.substring(6);
            Payment payment = PaymentFileHandler.getPaymentById(paymentId);
            if (payment != null && payment.getUserId().equals(userId)) {
                request.setAttribute("payment", payment);
                request.getRequestDispatcher("/c5/paymentdetails.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/payment/history");
            }
        } else if (pathInfo.equals("/methods")) {
            // Show payment methods page
            request.getRequestDispatcher("/c5/paymentmethods.jsp").forward(request, response);
        } else if (pathInfo.equals("/process")) {
            // Show payment process page
            request.getRequestDispatcher("/c5/paymentprocess.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        User user = (User) request.getSession().getAttribute("user");
        String userId = user != null ? user.getUserId() : null;

        if (userId == null) {
            // Redirect to login if not authenticated
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        if ("process".equals(action)) {
            // Process new payment
            Payment payment = new Payment(
                    UUID.randomUUID().toString(),
                    userId,
                    Double.parseDouble(request.getParameter("amount")),
                    request.getParameter("paymentMethod"),
                    "PENDING",
                    LocalDateTime.now(),
                    request.getParameter("description")
            );

            PaymentFileHandler.savePayment(payment);
            System.out.println("Payment created successfully with ID: " + payment.getPaymentId());

            response.sendRedirect(request.getContextPath() + "/payment/history");
        } else if ("update".equals(action)) {
            // Update existing payment
            String paymentId = request.getParameter("paymentId");
            Payment existingPayment = PaymentFileHandler.getPaymentById(paymentId);

            if (existingPayment != null && existingPayment.getUserId().equals(userId)) {
                Payment updatedPayment = new Payment(
                        paymentId,
                        userId,
                        Double.parseDouble(request.getParameter("amount")),
                        request.getParameter("paymentMethod"),
                        request.getParameter("status"),
                        existingPayment.getTimestamp(), // Keep original timestamp
                        request.getParameter("description")
                );

                PaymentFileHandler.updatePayment(updatedPayment);
                System.out.println("Payment updated successfully");
            }
            response.sendRedirect(request.getContextPath() + "/payment/history");
        } else if ("delete".equals(action)) {
            // Delete payment
            String paymentId = request.getParameter("paymentId");
            Payment payment = PaymentFileHandler.getPaymentById(paymentId);

            if (payment != null && payment.getUserId().equals(userId)) {
                PaymentFileHandler.deletePayment(paymentId);
                System.out.println("Payment deleted successfully");
            }
            response.sendRedirect(request.getContextPath() + "/payment/history");
        }
    }
}