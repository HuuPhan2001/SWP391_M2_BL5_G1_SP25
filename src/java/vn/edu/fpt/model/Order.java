/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.model;

import java.sql.Timestamp;

public class Order {

    private int orderId;
    private Integer userId;
    private Integer shopId;
    private Integer orderTotal;
    private String orderStatus;
    private int paymentStatus;
    private String address;
    private String district;
    private String ward;
    private String phoneReceiver;
    private String nameReceiver;
    private int shipCost;
    private int paymentMethodId;
    private Timestamp createAt;
    private Timestamp updateAt;
    private String province;
    private String orderCode;
    private Integer discount;

    public Order() {
    }

    public Order(int orderId, Integer userId, Integer shopId, Integer orderTotal, String orderStatus, int paymentStatus, String address, String district, String ward, String phoneReceiver, String nameReceiver, int shipCost, int paymentMethodId, Timestamp createAt, Timestamp updateAt, String province, String orderCode, Integer discount) {
        this.orderId = orderId;
        this.userId = userId;
        this.shopId = shopId;
        this.orderTotal = orderTotal;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.address = address;
        this.district = district;
        this.ward = ward;
        this.phoneReceiver = phoneReceiver;
        this.nameReceiver = nameReceiver;
        this.shipCost = shipCost;
        this.paymentMethodId = paymentMethodId;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.province = province;
        this.orderCode = orderCode;
        this.discount = discount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Integer orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getPhoneReceiver() {
        return phoneReceiver;
    }

    public void setPhoneReceiver(String phoneReceiver) {
        this.phoneReceiver = phoneReceiver;
    }

    public String getNameReceiver() {
        return nameReceiver;
    }

    public void setNameReceiver(String nameReceiver) {
        this.nameReceiver = nameReceiver;
    }

    public int getShipCost() {
        return shipCost;
    }

    public void setShipCost(int shipCost) {
        this.shipCost = shipCost;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

}
