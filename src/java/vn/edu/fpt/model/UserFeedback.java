/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.model;

import java.sql.Timestamp;

public class UserFeedback {

    private int feedbackId;
    private Float feedbackRating;
    private String feedbackComment;
    private Integer userId;
    private Integer productId;
    private Timestamp createAt;
    private Timestamp updateAt;

    public UserFeedback() {
    }

    public UserFeedback(int feedbackId, Float feedbackRating, String feedbackComment, Integer userId, Integer productId, Timestamp createAt, Timestamp updateAt) {
        this.feedbackId = feedbackId;
        this.feedbackRating = feedbackRating;
        this.feedbackComment = feedbackComment;
        this.userId = userId;
        this.productId = productId;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Float getFeedbackRating() {
        return feedbackRating;
    }

    public void setFeedbackRating(Float feedbackRating) {
        this.feedbackRating = feedbackRating;
    }

    public String getFeedbackComment() {
        return feedbackComment;
    }

    public void setFeedbackComment(String feedbackComment) {
        this.feedbackComment = feedbackComment;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

}
