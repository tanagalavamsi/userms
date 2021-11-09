package com.user.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Buyer {

	@Id
	private String buyerId;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false,unique=true)
	private String email;
	@Column(nullable = false,unique=true)
	private String phoneNo;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String isPriviliged;
	@Column(nullable = false)
	private Integer rewardPoints;
	@Column(nullable = false)
	private String isActive;
	
	private String address;
	
	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	@Override
	public String toString() {
		return "Buyer [buyerId=" + buyerId + ", name=" + name + ", email=" + email + ", phoneNo=" + phoneNo
				+ ", password=" + password + ", isPriviliged=" + isPriviliged + ", rewardPoints=" + rewardPoints
				+ ", isActive=" + isActive + "]";
	}


	public String getBuyerId() {
		return buyerId;
	}


	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	

	public String getPhoneNo() {
		return phoneNo;
	}


	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getIsPriviliged() {
		return isPriviliged;
	}


	public void setIsPriviliged(String isPriviliged) {
		this.isPriviliged = isPriviliged;
	}


	public Integer getRewardPoints() {
		return rewardPoints;
	}


	public void setRewardPoints(Integer rewardPoints) {
		this.rewardPoints = rewardPoints;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	public Buyer() {
		super();
	}

	
}
