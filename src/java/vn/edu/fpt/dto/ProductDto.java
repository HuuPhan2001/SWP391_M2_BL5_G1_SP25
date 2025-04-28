/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import vn.edu.fpt.model.Category;
import vn.edu.fpt.model.ProductImage;

/**
 *
 * @author MTTTT
 */
public class ProductDto {

    private int productId;
    private String productName;
    private String productAvatar;
    private String productDesc;
    private BigDecimal productPrice;
    private Integer productQuantity;
    private int status;
    private Timestamp createAt;
    private Timestamp updateAt;
    private BigDecimal weight;
    private BigDecimal length;
    private BigDecimal width;
    private BigDecimal height;
    private List<ProductCategoryDto> categoryList;
    private List<ProductImage> images;

    public ProductDto() {
    }

    public ProductDto(int productId, String productName, String productAvatar, String productDesc, BigDecimal productPrice, Integer productQuantity, int status, Timestamp createAt, Timestamp updateAt, BigDecimal weight, BigDecimal length, BigDecimal width, BigDecimal height, List<ProductCategoryDto> categoryList, List<ProductImage> images) {
        this.productId = productId;
        this.productName = productName;
        this.productAvatar = productAvatar;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.categoryList = categoryList;
        this.images = images;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductAvatar() {
        return productAvatar;
    }

    public void setProductAvatar(String productAvatar) {
        this.productAvatar = productAvatar;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
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

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public List<ProductCategoryDto> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<ProductCategoryDto> categoryList) {
        this.categoryList = categoryList;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }
    
    
}
