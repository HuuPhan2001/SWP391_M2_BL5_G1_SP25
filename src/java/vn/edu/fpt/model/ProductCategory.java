/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.model;

public class ProductCategory {
    private int productCategoryId;
    private Integer productId;
    private Integer categoryId;

    public ProductCategory() {
    }

    public ProductCategory(int productCategoryId, Integer productId, Integer categoryId) {
        this.productCategoryId = productCategoryId;
        this.productId = productId;
        this.categoryId = categoryId;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    
    
}
