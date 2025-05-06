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
import vn.edu.fpt.common.Constant;
import vn.edu.fpt.model.User;
import vn.edu.fpt.model.UserFeedback;
import vn.edu.fpt.service.FeedbackService;

/**
 *
 * @author MTTTT
 */
@MultipartConfig
@WebServlet("/ajax-get-feedback")
public class GetFeedbackServlet extends HttpServlet {

    private FeedbackService feedbackService;

    public void init() {
        feedbackService = new FeedbackService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = (User) request.getSession().getAttribute("acc");
            if (user == null) {
                sendJsonResponse(response, false, "Please login to submit feedback");
                return;
            }

            String feedbackIdRaw = request.getParameter("feedbackId");
            if (feedbackIdRaw == null) {
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

            response.setContentType("application/json");

            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("feedbackId", existingFeedback.getFeedbackId());
            jsonResponse.addProperty("userId", existingFeedback.getUserId());
            jsonResponse.addProperty("productId", existingFeedback.getProductId());
            jsonResponse.addProperty("comment", existingFeedback.getFeedbackComment());
            jsonResponse.addProperty("rating", existingFeedback.getFeedbackRating());
            jsonResponse.addProperty("createAt", existingFeedback.getCreateAt().toString());
            jsonResponse.addProperty("updateAt", existingFeedback.getUpdateAt() != null ? existingFeedback.getUpdateAt().toString() : null);

            PrintWriter out = response.getWriter();
            out.print(jsonResponse.toString());
            out.flush();

        } catch (IOException | NumberFormatException e) {
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
