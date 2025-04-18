/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.model;

import java.sql.Timestamp;

public class ShopTransaction {

    private int id;
    private Integer userId;
    private Integer orderId;
    private Integer amount;
    private Integer type;
    private int isAdminTransaction;
    private int isPaidCommission;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer netAmount;
    private String description;

    public ShopTransaction() {
    }

    public ShopTransaction(int id, Integer userId, Integer orderId, Integer amount, Integer type, int isAdminTransaction, int isPaidCommission, Timestamp createdAt, Timestamp updatedAt, Integer netAmount, String description) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.type = type;
        this.isAdminTransaction = isAdminTransaction;
        this.isPaidCommission = isPaidCommission;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.netAmount = netAmount;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public int getIsAdminTransaction() {
        return isAdminTransaction;
    }

    public void setIsAdminTransaction(int isAdminTransaction) {
        this.isAdminTransaction = isAdminTransaction;
    }

    public int getIsPaidCommission() {
        return isPaidCommission;
    }

    public void setIsPaidCommission(int isPaidCommission) {
        this.isPaidCommission = isPaidCommission;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(Integer netAmount) {
        this.netAmount = netAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
