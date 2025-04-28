/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package vn.edu.fpt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import vn.edu.fpt.model.User;
import vn.edu.fpt.model.UserFeedback;
import vn.edu.fpt.service.FeedbackService;

/**
 *
 * @author MTTTT
 */
@WebServlet(name = "FeedbackServlet", urlPatterns = {
    "/feedback", "/new-feedback", "/edit-feedback",
    "/delete-feedback", "/view-feedback", "/create-feedback",
    "/update-feedback", "/product-feedback", "/get-product-feedbacks",
    "/list-feedback"
})

public class FeedbackServlet extends HttpServlet {

    private FeedbackService feedbackService;

    public void init() {
        feedbackService = new FeedbackService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println(action);
        User user = (User) request.getSession().getAttribute("acc");
        if (user == null) {
            request.getRequestDispatcher("./Login.jsp").forward(request, response);
            return;
        }

        try {
            if (user.getRoleId() == null) {
                request.getRequestDispatcher("./Login.jsp").forward(request, response);
            } else {
                Integer roleId = user.getRoleId();
                switch (roleId) {
                    case 3:
                        switch (action) {
                            case "/product-feedback":
                                feedbackService.getFeedbackByProduct(request, response);
                                break;
                            case "/create-feedback":
                                feedbackService.createFeedback(request, response);
                                break;
                            case "/edit-feedback":
                                UserFeedback feedback = feedbackService.getFeedbackById(request, response);
                                if (feedback != null && feedback.getUserId().equals(user.getUserId())) {
                                    feedbackService.updateFeedback(request, response);
                                } else {
                                    request.getSession().setAttribute("errorMessage", "You can only edit your own feedback");
                                    response.sendRedirect("product");
                                }
                                break;
                            case "/delete-feedback":
                                feedback = feedbackService.getFeedbackById(request, response);
                                if (feedback != null && feedback.getUserId().equals(user.getUserId())) {
                                    feedbackService.deleteFeedback(request, response);
                                } else {
                                    request.getSession().setAttribute("errorMessage", "You can only delete your own feedback");
                                    response.sendRedirect("product");
                                }
                                break;
                            case "/get-feedback":
                                feedbackService.getFeedbackByIdForm(request, response);
                                break;

                            default:
                                response.sendRedirect("product");
                        }
                        break;

                    case 1:
                        switch (action) {
                            case "/list-feedback":
                                feedbackService.listAllFeedbacks(request, response);
                                break;
                            case "/feedback":
                                feedbackService.getFeedbackByProduct(request, response);
                                break;
                            case "/view-feedback":
                                feedbackService.getFeedbackById(request, response);
                                break;
                            case "/delete-feedback":
                                feedbackService.deleteFeedback(request, response);
                                break;
                            default:
                                feedbackService.getFeedbackByProduct(request, response);
                        }
                        break;

                    default:
                        request.getRequestDispatcher("./Login.jsp").forward(request, response);
                        break;
                }
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
