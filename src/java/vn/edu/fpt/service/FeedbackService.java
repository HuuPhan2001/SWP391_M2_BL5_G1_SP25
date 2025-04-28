/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.service;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import vn.edu.fpt.common.Common;
import vn.edu.fpt.common.Constant;
import vn.edu.fpt.common.PaginatedResult;
import vn.edu.fpt.common.Pagination;
import vn.edu.fpt.dao.FeedbackDao;
import vn.edu.fpt.dto.FeedbackDto;
import vn.edu.fpt.model.UserFeedback;

/**
 *
 * @author MTTTT
 */
public class FeedbackService {

    private FeedbackDao feedbackDao = new FeedbackDao();
    private Gson gson = new Gson();

    public void createFeedback(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        try {
            String ratingRaw = request.getParameter("rating");
            String comment = request.getParameter("comment");
            String productIdRaw = request.getParameter("productId");
            String userIdRaw = request.getParameter("userId");

            if (ratingRaw == null || comment == null || productIdRaw == null || userIdRaw == null) {
                request.getSession().setAttribute("errorMessage", Constant.ADD_FAILED + ": " + Constant.DATA_INVALID);
                response.sendRedirect("product");
                return;
            }

            UserFeedback feedback = new UserFeedback();
            feedback.setFeedbackRating(Float.parseFloat(ratingRaw));
            feedback.setFeedbackComment(comment.trim());
            feedback.setProductId(Integer.parseInt(productIdRaw));
            feedback.setUserId(Integer.parseInt(userIdRaw));
            feedback.setCreateAt(new Timestamp(System.currentTimeMillis()));

            if (feedback.getFeedbackComment().isEmpty() || feedback.getFeedbackComment().length() >= 1000) {
                request.getSession().setAttribute("errorMessage", Constant.ADD_FAILED + ": " + Constant.DATA_INVALID);
                response.sendRedirect("product");
                return;
            }
            System.out.println(feedback.toString());

            if (feedbackDao.createFeedback(feedback)) {
                request.getSession().setAttribute("successMessage", Constant.ADD_SUCCESS);
            } else {
                request.getSession().setAttribute("errorMessage", Constant.ADD_FAILED);
            }
            response.sendRedirect("product");
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", Constant.ADD_FAILED + ": " + Constant.DATA_INVALID);
            response.sendRedirect("product");
        }
    }

    public void updateFeedback(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        try {
            String feedbackIdRaw = request.getParameter("feedbackId");
            String ratingRaw = request.getParameter("rating");
            String comment = request.getParameter("comment");

            if (feedbackIdRaw == null || ratingRaw == null || comment == null) {
                request.getSession().setAttribute("errorMessage", Constant.UPDATE_FAILED + ": " + Constant.DATA_INVALID);
                response.sendRedirect("feedback");
                return;
            }

            int feedbackId = Integer.parseInt(feedbackIdRaw);
            UserFeedback existingFeedback = feedbackDao.getFeedbackById(feedbackId);
            if (existingFeedback == null) {
                request.getSession().setAttribute("errorMessage", Constant.UPDATE_FAILED + ": Feedback not found");
                response.sendRedirect("feedback");
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

            if (feedbackDao.updateFeedback(existingFeedback)) {
                request.getSession().setAttribute("successMessage", Constant.UPDATE_SUCCESS);
            } else {
                request.getSession().setAttribute("errorMessage", Constant.UPDATE_FAILED);
            }
            response.sendRedirect("feedback");
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", Constant.UPDATE_FAILED + ": " + Constant.DATA_INVALID);
            response.sendRedirect("feedback");
        }
    }

    public void deleteFeedback(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        try {
            String feedbackIdRaw = request.getParameter("id");
            int feedbackId = Integer.parseInt(feedbackIdRaw);

            if (feedbackDao.deleteFeedback(feedbackId)) {
                request.getSession().setAttribute("successMessage", Constant.DELETE_SUCCESS);
            } else {
                request.getSession().setAttribute("errorMessage", Constant.DELETE_FAILED);
            }
            response.sendRedirect("feedback");
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", Constant.DELETE_FAILED + ": " + Constant.DATA_INVALID);
            response.sendRedirect("feedback");
        }
    }

    public UserFeedback getFeedbackById(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String feedbackIdRaw = request.getParameter("id");
        if (feedbackIdRaw == null || feedbackIdRaw.trim().isEmpty()) {
            return null;
        }

        try {
            int feedbackId = Integer.parseInt(feedbackIdRaw);
            return feedbackDao.getFeedbackById(feedbackId);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public List<FeedbackDto> getFeedbackByProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String productIdRaw = request.getParameter("productId");
        if (productIdRaw == null || productIdRaw.trim().isEmpty()) {
            return new ArrayList<>();
        }

        try {
            int productId = Integer.parseInt(productIdRaw);
            return feedbackDao.getFeedbackByProduct(productId);
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    public void getFeedbackByIdForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String feedbackId = request.getParameter("id");
            if (feedbackId == null || feedbackId.trim().isEmpty()) {
                out.print(gson.toJson(new HashMap<String, Object>() {
                    {
                        put("success", false);
                        put("message", "Invalid feedback ID");
                    }
                }));
                return;
            }

            FeedbackDto feedback = feedbackDao.getFeedbackDtoById(Integer.parseInt(feedbackId));
            if (feedback != null) {
                out.print(gson.toJson(feedback));
            } else {
                out.print(gson.toJson(new HashMap<String, Object>() {
                    {
                        put("success", false);
                        put("message", "Feedback not found");
                    }
                }));
            }
        } catch (NumberFormatException e) {
            out.print(gson.toJson(new HashMap<String, Object>() {
                {
                    put("success", false);
                    put("message", "Invalid feedback ID format");
                }
            }));
        }
    }

    public void listAllFeedbacks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<FeedbackDto> feedbacks = feedbackDao.getAllFeedbacks();
        System.out.println(feedbacks.toString());
        request.setAttribute("feedbacks", feedbacks);
        request.getRequestDispatcher("./feedback/ListFeedback.jsp").forward(request, response);
    }

    public boolean updateFeedbackDirect(UserFeedback feedback) {
        return feedbackDao.updateFeedback(feedback);
    }

    public boolean createFeedbackDirect(UserFeedback feedback) {
        return feedbackDao.createFeedback(feedback);
    }
}
