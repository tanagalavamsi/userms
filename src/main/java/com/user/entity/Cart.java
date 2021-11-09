package com.user.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(cartkeys.class)
public class Cart {
	@Id
	private String buyerId;
	@Id
	private String prodId;
	private Integer quantity;
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


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public Cart() {
		super();
	}

	
}
