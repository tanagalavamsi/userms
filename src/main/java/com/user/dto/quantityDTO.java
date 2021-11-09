package com.user.dto;



public class quantityDTO {
	
	Integer quantity;
	
	String prodId;
	String sellerId;
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	@Override
	public String toString() {
		return "quantityDTO [quantity=" + quantity + ", prodId=" + prodId + ", sellerId=" + sellerId + "]";
	}
	

}
