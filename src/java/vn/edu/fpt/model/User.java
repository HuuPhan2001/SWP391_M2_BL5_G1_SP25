/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.model;

import java.sql.Timestamp;

public class User {

    private int userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userFullName;
    private String userAvatar;
    private String phone;
    private String identificationNumber;
    private String forgotPasswordCode;
    private String address;
    private Integer roleId;
    private int status;
    private Timestamp createAt;
    private Timestamp updateAt;

    public User() {
    }

    public User(int userId, String userName, String userEmail, String userPassword, String userFullName, String userAvatar, String phone, String identificationNumber, String forgotPasswordCode, String address, Integer roleId, int status, Timestamp createAt, Timestamp updateAt) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userFullName = userFullName;
        this.userAvatar = userAvatar;
        this.phone = phone;
        this.identificationNumber = identificationNumber;
        this.forgotPasswordCode = forgotPasswordCode;
        this.address = address;
        this.roleId = roleId;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getForgotPasswordCode() {
        return forgotPasswordCode;
    }

    public void setForgotPasswordCode(String forgotPasswordCode) {
        this.forgotPasswordCode = forgotPasswordCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
