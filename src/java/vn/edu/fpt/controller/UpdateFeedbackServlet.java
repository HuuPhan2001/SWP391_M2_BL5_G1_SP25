/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package vn.edu.fpt.controller;

import com.google.gson.JsonObject;
import jakarta.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import vn.edu.fpt.common.Constant;
import vn.edu.fpt.model.User;
import vn.edu.fpt.model.UserFeedback;
import vn.edu.fpt.service.FeedbackService;

/**
 *
 * @author MTTTT
 */
@MultipartConfig
@WebServlet("/ajax-update-feedback")
public class UpdateFeedbackServlet extends HttpServlet {

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

            String feedbackIdRaw = request.getParameter("feedbackId");
            String ratingRaw = request.getParameter("rating");
            String comment = request.getParameter("comment");

            if (feedbackIdRaw == null || ratingRaw == null || comment == null) {
                request.getSession().setAttribute("errorMessage", Constant.UPDATE_FAILED + ": " + Constant.DATA_INVALID);
                response.sendRedirect("feedback");
                return;
            }

            int feedbackId = Integer.parseInt(feedbackIdRaw);
            UserFeedback existingFeedback = feedbackService.getFeedbackById(feedbackId);
            if (existingFeedback == null) {
                request.getSession().setAttribute("errorMessage", Constant.UPDATE_FAILED + ": Feedback not found");
                response.sendRedirect("feedback");
                return;
            }

            String productId = request.getParameter("productId");
            String rating = request.getParameter("rating");

            if (productId == null || rating == null) {
                sendJsonResponse(response, false, "Missing required parameters");
                return;
            }

            existingFeedback.setFeedbackRating(Float.parseFloat(ratingRaw));
            existingFeedback.setFeedbackComment(comment.trim());
            existingFeedback.setUpdateAt(new Timestamp(System.currentTimeMillis()));

            if (existingFeedback.getFeedbackComment().isEmpty() || existingFeedback.getFeedbackComment().length() >= 1000) {
                request.getSession().setAttribute("errorMessage", Constant.UPDATE_FAILED + ": " + Constant.DATA_INVALID);
                response.sendRedirect("feedback");
                return;
            }

            boolean success = feedbackService.updateFeedbackDirect(existingFeedback);
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
