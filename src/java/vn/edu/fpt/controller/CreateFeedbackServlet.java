/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package vn.edu.fpt.controller;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.fpt.model.User;
import vn.edu.fpt.model.UserFeedback;
import vn.edu.fpt.service.FeedbackService;
import java.sql.SQLException;

/**
 *
 * @author MTTTT
 */
@MultipartConfig
@WebServlet("/ajax-feedback")
public class CreateFeedbackServlet extends HttpServlet {

    private FeedbackService feedbackService;

    public void init() {
        feedbackService = new FeedbackService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            User user = (User) request.getSession().getAttribute("acc");
            if (user == null) {
                sendJsonResponse(response, false, "Please login to submit feedback");
                return;
            }

            String productId = request.getParameter("productId");
            String rating = request.getParameter("rating");
            String comment = request.getParameter("comment");

            if (productId == null || rating == null) {
                sendJsonResponse(response, false, "Missing required parameters");
                return;
            }

            UserFeedback feedback = new UserFeedback();
            feedback.setUserId(user.getUserId());
            feedback.setProductId(Integer.parseInt(productId));
            feedback.setFeedbackRating(Float.parseFloat(rating));
            feedback.setFeedbackComment(comment != null ? comment : "");

            boolean success = feedbackService.createFeedbackDirect(feedback);
            String message = success ? "Feedback submitted successfully" : "Error submitting feedback";

            sendJsonResponse(response, success, message);

        } catch (NumberFormatException e) {
            sendJsonResponse(response, false, "Invalid input parameters");
        } catch (Exception e) {
            sendJsonResponse(response, false, "An unexpected error occurred");
        }
    }

    private void sendJsonResponse(HttpServletResponse response, boolean success, String message)
            throws IOException {
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", success);
        jsonResponse.addProperty("message", message);
        out.print(jsonResponse.toString());
        out.flush();
    }

}
