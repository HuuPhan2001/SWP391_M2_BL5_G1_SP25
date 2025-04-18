/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.model;

import java.sql.Timestamp;

public class CategoryType {

    private int categoryTypeId;
    private String categoryTypeName;
    private String categoryTypeDesc;
    private int status;
    private Timestamp createAt;
    private Timestamp updateAt;

    public CategoryType() {
    }

    public CategoryType(int categoryTypeId, String categoryTypeName, String categoryTypeDesc, int status, Timestamp createAt, Timestamp updateAt) {
        this.categoryTypeId = categoryTypeId;
        this.categoryTypeName = categoryTypeName;
        this.categoryTypeDesc = categoryTypeDesc;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public int getCategoryTypeId() {
        return categoryTypeId;
    }

    public void setCategoryTypeId(int categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
    }

    public String getCategoryTypeName() {
        return categoryTypeName;
    }

    public void setCategoryTypeName(String categoryTypeName) {
        this.categoryTypeName = categoryTypeName;
    }

    public String getCategoryTypeDesc() {
        return categoryTypeDesc;
    }

    public void setCategoryTypeDesc(String categoryTypeDesc) {
        this.categoryTypeDesc = categoryTypeDesc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
