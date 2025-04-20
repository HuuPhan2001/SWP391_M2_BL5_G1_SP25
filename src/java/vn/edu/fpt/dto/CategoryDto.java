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
public class CategoryDto {

    private int categoryId;
    private Integer categoryTypeId;
    private String categoryTypeName;
    private String categoryName;
    private String categoryDesc;
    private Integer parent;
    private String parentName;
    private String categoryBanner;
    private int status;
    private Timestamp createAt;
    private Timestamp updateAt;

    public CategoryDto() {
    }

    public CategoryDto(int categoryId, Integer categoryTypeId, String categoryTypeName, String categoryName, String categoryDesc, Integer parent, String parentName, String categoryBanner, int status, Timestamp createAt, Timestamp updateAt) {
        this.categoryId = categoryId;
        this.categoryTypeId = categoryTypeId;
        this.categoryTypeName = categoryTypeName;
        this.categoryName = categoryName;
        this.categoryDesc = categoryDesc;
        this.parent = parent;
        this.parentName = parentName;
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

    public String getCategoryTypeName() {
        return categoryTypeName;
    }

    public void setCategoryTypeName(String categoryTypeName) {
        this.categoryTypeName = categoryTypeName;
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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
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
    
    
}
