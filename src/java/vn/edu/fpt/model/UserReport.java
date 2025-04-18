/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.model;

import java.sql.Timestamp;

public class UserReport {

    private int reportId;
    private String reportContent;
    private Integer userId;
    private Timestamp createAt;
    private Timestamp updateAt;
    private String reportResponse;
    private Integer typeId;
    private int status;

    public UserReport() {
    }

    public UserReport(int reportId, String reportContent, Integer userId, Timestamp createAt, Timestamp updateAt, String reportResponse, Integer typeId, int status) {
        this.reportId = reportId;
        this.reportContent = reportContent;
        this.userId = userId;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.reportResponse = reportResponse;
        this.typeId = typeId;
        this.status = status;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getReportResponse() {
        return reportResponse;
    }

    public void setReportResponse(String reportResponse) {
        this.reportResponse = reportResponse;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
