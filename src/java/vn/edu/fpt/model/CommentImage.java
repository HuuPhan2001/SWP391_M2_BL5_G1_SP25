/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.model;

import java.sql.Timestamp;

public class CommentImage {

    private int commentImageId;
    private String commentImageContent;
    private Integer commentId;
    private Timestamp createAt;
    private Timestamp updateAt;

    public CommentImage() {
    }

    public CommentImage(int commentImageId, String commentImageContent, Integer commentId, Timestamp createAt, Timestamp updateAt) {
        this.commentImageId = commentImageId;
        this.commentImageContent = commentImageContent;
        this.commentId = commentId;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public int getCommentImageId() {
        return commentImageId;
    }

    public void setCommentImageId(int commentImageId) {
        this.commentImageId = commentImageId;
    }

    public String getCommentImageContent() {
        return commentImageContent;
    }

    public void setCommentImageContent(String commentImageContent) {
        this.commentImageContent = commentImageContent;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
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
