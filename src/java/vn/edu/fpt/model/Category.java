/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.model;

import java.sql.Timestamp;

public class Category {

    private int categoryId;
    private Integer categoryTypeId;
    private String categoryName;
    private String categoryDesc;
    private Integer parent;
    private String categoryBanner;
    private int status;
    private Timestamp createAt;
    private Timestamp updateAt;

    public Category() {
    }

    public Category(int categoryId, Integer categoryTypeId, String categoryName, String categoryDesc, Integer parent, String categoryBanner, int status, Timestamp createAt, Timestamp updateAt) {
        this.categoryId = categoryId;
        this.categoryTypeId = categoryTypeId;
        this.categoryName = categoryName;
        this.categoryDesc = categoryDesc;
        this.parent = parent;
        this.categoryBanner = categoryBanner;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryTypeId() {
        return categoryTypeId;
    }

    public void setCategoryTypeId(Integer categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getCategoryBanner() {
        return categoryBanner;
    }

    public void setCategoryBanner(String categoryBanner) {
        this.categoryBanner = categoryBanner;
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

    @Override
    public String toString() {
        return "Category{" + "categoryId=" + categoryId + ", categoryTypeId=" + categoryTypeId + ", categoryName=" + categoryName + ", categoryDesc=" + categoryDesc + ", parent=" + parent + ", categoryBanner=" + categoryBanner + ", status=" + status + ", createAt=" + createAt + ", updateAt=" + updateAt + '}';
    }
    

}
