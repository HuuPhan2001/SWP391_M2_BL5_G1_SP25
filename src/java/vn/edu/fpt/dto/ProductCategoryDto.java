/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.dto;

/**
 *
 * @author MTTTT
 */
public class ProductCategoryDto {
    private Integer productId;
    private String productName;
    private int productCategoryId;
    private Integer categoryId;
    private String categoryName;

    public ProductCategoryDto() {
    }

    public ProductCategoryDto(Integer productId, String productName, int productCategoryId, Integer categoryId, String categoryName) {
        this.productId = productId;
        this.productName = productName;
        this.productCategoryId = productCategoryId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    
}
