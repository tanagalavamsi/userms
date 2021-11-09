package com.user.entity;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.IdClass;
@Entity
@IdClass(wishlistkeys.class)
public class Wishlist {
	@Id
	private String buyerId;
	@Id
	private String prodId;
	
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	
	
	
}
