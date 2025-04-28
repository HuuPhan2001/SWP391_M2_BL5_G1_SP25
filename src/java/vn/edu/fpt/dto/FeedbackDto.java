/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.dto;

import java.sql.Timestamp;

/**
 *
 * @author MTTTT
 */
public class FeedbackDto {

    private int feedbackId;
    private Float feedbackRating;
    private String feedbackComment;
    private Integer userId;
    private String userName;
    private Integer productId;
    private Timestamp createAt;
    private Timestamp updateAt;
    private String productName;

    public FeedbackDto() {
    }

    public FeedbackDto(int feedbackId, Float feedbackRating, String feedbackComment, Integer userId, String userName, Integer productId, Timestamp createAt, Timestamp updateAt, String productName) {
        this.feedbackId = feedbackId;
        this.feedbackRating = feedbackRating;
        this.feedbackComment = feedbackComment;
        this.userId = userId;
        this.userName = userName;
        this.productId = productId;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.productName = productName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
}
