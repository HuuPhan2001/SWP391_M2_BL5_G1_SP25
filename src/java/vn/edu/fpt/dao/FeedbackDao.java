/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import vn.edu.fpt.config.DbContext;
import static vn.edu.fpt.config.DbContext.getConnection;
import vn.edu.fpt.model.UserFeedback;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import vn.edu.fpt.dto.FeedbackDto;

/**
 *
 * @author MTTTT
 */
public class FeedbackDao extends DbContext {

    public boolean createFeedback(UserFeedback feedback) {
        String sql = "INSERT INTO user_feedback (feedback_rating, feedback_comment, user_id, product_id, create_at) "
                + "VALUES (?, ?, ?, ?, GETDATE())";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setFloat(1, feedback.getFeedbackRating());
            ps.setString(2, feedback.getFeedbackComment());
            ps.setInt(3, feedback.getUserId());
            ps.setInt(4, feedback.getProductId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserFeedback getFeedbackById(int feedbackId) {
        String sql = "SELECT * FROM user_feedback WHERE feedback_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, feedbackId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToFeedback(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<FeedbackDto> getFeedbackByProduct(int productId) {
        List<FeedbackDto> feedbackList = new ArrayList<>();
        String sql = "SELECT f.*, u.user_name as user_name FROM user_feedback f "
                + "JOIN [user] u ON f.user_id = u.user_id "
                + "WHERE f.product_id = ? ORDER BY f.create_at DESC";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                feedbackList.add(mapResultSetToFeedbackDto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    public boolean updateFeedback(UserFeedback feedback) {
        String sql = "UPDATE user_feedback SET feedback_rating = ?, feedback_comment = ?, "
                + "update_at = GETDATE() WHERE feedback_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setFloat(1, feedback.getFeedbackRating());
            ps.setString(2, feedback.getFeedbackComment());
            ps.setInt(3, feedback.getFeedbackId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFeedback(int feedbackId) {
        String sql = "DELETE FROM user_feedback WHERE feedback_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, feedbackId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private UserFeedback mapResultSetToFeedback(ResultSet rs) throws SQLException {
        UserFeedback feedback = new UserFeedback();
        feedback.setFeedbackId(rs.getInt("feedback_id"));
        feedback.setFeedbackRating(rs.getFloat("feedback_rating"));
        feedback.setFeedbackComment(rs.getString("feedback_comment"));
        feedback.setUserId(rs.getInt("user_id"));
        feedback.setProductId(rs.getInt("product_id"));
        feedback.setCreateAt(rs.getTimestamp("create_at"));
        feedback.setUpdateAt(rs.getTimestamp("update_at"));
        return feedback;
    }

    private FeedbackDto mapResultSetToFeedbackDto(ResultSet rs) throws SQLException {
        FeedbackDto feedback = new FeedbackDto();
        feedback.setFeedbackId(rs.getInt("feedback_id"));
        feedback.setFeedbackRating(rs.getFloat("feedback_rating"));
        feedback.setFeedbackComment(rs.getString("feedback_comment"));
        feedback.setUserId(rs.getInt("user_id"));
        feedback.setUserName(rs.getString("user_name"));
        feedback.setProductId(rs.getInt("product_id"));
        feedback.setCreateAt(rs.getTimestamp("create_at"));
        feedback.setUpdateAt(rs.getTimestamp("update_at"));
        return feedback;
    }

    public FeedbackDto getFeedbackDtoById(int feedbackId) {
        String sql = "SELECT f.*, u.user_name FROM user_feedback f "
                + "JOIN users u ON f.user_id = u.user_id "
                + "WHERE feedback_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, feedbackId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                FeedbackDto dto = new FeedbackDto();
                dto.setFeedbackId(rs.getInt("feedback_id"));
                dto.setFeedbackRating(rs.getFloat("feedback_rating"));
                dto.setFeedbackComment(rs.getString("feedback_comment"));
                dto.setUserId(rs.getInt("user_id"));
                dto.setProductId(rs.getInt("product_id"));
                dto.setUserName(rs.getString("user_name"));
                dto.setCreateAt(rs.getTimestamp("create_at"));
                return dto;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<FeedbackDto> getAllFeedbacks() {
        List<FeedbackDto> feedbacks = new ArrayList<>();
        String sql = "SELECT f.*, u.user_name, p.product_name FROM user_feedback f "
                + "JOIN [user] u ON f.user_id = u.user_id "
                + "JOIN product p ON f.product_id = p.product_id "
                + "ORDER BY f.create_at DESC";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                FeedbackDto dto = new FeedbackDto();
                dto.setFeedbackId(rs.getInt("feedback_id"));
                dto.setFeedbackRating(rs.getFloat("feedback_rating"));
                dto.setFeedbackComment(rs.getString("feedback_comment"));
                dto.setUserId(rs.getInt("user_id"));
                dto.setProductId(rs.getInt("product_id"));
                dto.setUserName(rs.getString("user_name"));
                dto.setProductName(rs.getString("product_name"));
                dto.setCreateAt(rs.getTimestamp("create_at"));
                feedbacks.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbacks;

    }

}
