/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.model;

import java.sql.Timestamp;

public class FeedbackImage {

    private int feedbackImageId;
    private String feedbackImageContent;
    private Integer feedbackId;
    private Timestamp createAt;
    private Timestamp updateAt;

    public FeedbackImage() {
    }

    public FeedbackImage(int feedbackImageId, String feedbackImageContent, Integer feedbackId, Timestamp createAt, Timestamp updateAt) {
        this.feedbackImageId = feedbackImageId;
        this.feedbackImageContent = feedbackImageContent;
        this.feedbackId = feedbackId;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public int getFeedbackImageId() {
        return feedbackImageId;
    }

    public void setFeedbackImageId(int feedbackImageId) {
        this.feedbackImageId = feedbackImageId;
    }

    public String getFeedbackImageContent() {
        return feedbackImageContent;
    }

    public void setFeedbackImageContent(String feedbackImageContent) {
        this.feedbackImageContent = feedbackImageContent;
    }

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
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
