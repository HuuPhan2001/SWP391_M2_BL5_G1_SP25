/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.model;

import java.sql.Timestamp;

public class BlackList {

    private int blackListId;
    private Integer userId;
    private Integer orderQuantity;
    private Timestamp createAt;

    public BlackList() {
    }

    public BlackList(int blackListId, Integer userId, Integer orderQuantity, Timestamp createAt) {
        this.blackListId = blackListId;
        this.userId = userId;
        this.orderQuantity = orderQuantity;
        this.createAt = createAt;
    }

    public int getBlackListId() {
        return blackListId;
    }

    public void setBlackListId(int blackListId) {
        this.blackListId = blackListId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }
    
    
}
