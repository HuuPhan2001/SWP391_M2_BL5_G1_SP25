/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.model;

import java.sql.Timestamp;

public class OrderDetail {

    private int orderDetailId;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Integer unitPrice;
    private String note;
    private Timestamp createAt;
    private Timestamp updateAt;
    private Integer productOptionId;
    private int isFeedback;

    public OrderDetail() {
    }

    public OrderDetail(int orderDetailId, Integer orderId, Integer productId, Integer quantity, Integer unitPrice, String note, Timestamp createAt, Timestamp updateAt, Integer productOptionId, int isFeedback) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.note = note;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.productOptionId = productOptionId;
        this.isFeedback = isFeedback;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Integer getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Integer productOptionId) {
        this.productOptionId = productOptionId;
    }

    public int getIsFeedback() {
        return isFeedback;
    }

    public void setIsFeedback(int isFeedback) {
        this.isFeedback = isFeedback;
    }

}
