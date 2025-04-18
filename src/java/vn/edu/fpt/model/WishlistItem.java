/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.model;

import java.sql.Timestamp;

public class WishlistItem {

    private int wishlistItemId;
    private Integer wishlistId;
    private Integer productId;
    private Timestamp createAt;

    public WishlistItem() {
    }

    public WishlistItem(int wishlistItemId, Integer wishlistId, Integer productId, Timestamp createAt) {
        this.wishlistItemId = wishlistItemId;
        this.wishlistId = wishlistId;
        this.productId = productId;
        this.createAt = createAt;
    }

    public int getWishlistItemId() {
        return wishlistItemId;
    }

    public void setWishlistItemId(int wishlistItemId) {
        this.wishlistItemId = wishlistItemId;
    }

    public Integer getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Integer wishlistId) {
        this.wishlistId = wishlistId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

}
