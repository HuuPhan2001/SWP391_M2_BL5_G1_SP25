/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.model;

import java.sql.Timestamp;

public class UserReportImage {

    private int reportImageId;
    private String reportImageContent;
    private Integer reportId;
    private Timestamp createAt;
    private Timestamp updateAt;

    public UserReportImage() {
    }

    public UserReportImage(int reportImageId, String reportImageContent, Integer reportId, Timestamp createAt, Timestamp updateAt) {
        this.reportImageId = reportImageId;
        this.reportImageContent = reportImageContent;
        this.reportId = reportId;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public int getReportImageId() {
        return reportImageId;
    }

    public void setReportImageId(int reportImageId) {
        this.reportImageId = reportImageId;
    }

    public String getReportImageContent() {
        return reportImageContent;
    }

    public void setReportImageContent(String reportImageContent) {
        this.reportImageContent = reportImageContent;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
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
