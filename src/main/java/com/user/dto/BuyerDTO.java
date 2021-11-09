package com.user.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class BuyerDTO {

	private String buyerId;
	@NotNull(message = "please enter the name in valid format")
	@Pattern(regexp = "[A-Za-z]+( [A-Za-z]+){0,20}", message = "please enter the name in valid format")
	private String name;
	@NotNull(message = "{customer.emailid.absent}")	
	@Pattern(regexp="[A-Za-z][A-Za-z0-9]{0,20}[/@][A-Za-z]{1,8}[/.][A-Za-z]{1,8}",message="please enter the email in the format abc@gmail.com")
	private String email;
	@NotNull(message = "{customer.phonenumber.absent}")
	@Pattern(regexp="[6-9][0-9]{9}",message="please enter the 10 digit phone number")
	private String phoneNo;
	@NotNull(message="Password must not be blank.")
	@Pattern(regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$", 
	message="please enter the password in valid format which contain alteast 1 uppercase letter,1 lowercase ,1 digit and any one symbols @,#$ %,,^,&")
	private String password;
	private String isPriviliged;
	private Integer rewardPoints;
	private String isActive;
	private String address;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String  getIsPriviliged() {
		return isPriviliged;
	}
	public void setIsPriviliged(String  isPriviliged) {
		this.isPriviliged = isPriviliged;
	}
	public Integer getRewardPoints() {
		return rewardPoints;
	}
	public void setRewardPoints(Integer rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	public String  getIsActive() {
		return isActive;
	}
	public void setIsActive(String  isActive) {
		this.isActive = isActive;
	}
	public BuyerDTO() {
		super();
	}
	@Override
	public String toString() {
		return "BuyerDTO [buyerId=" + buyerId + ", name=" + name + ", email=" + email + ", phoneNo=" + phoneNo
				+ ", password=" + password + ", isPriviliged=" + isPriviliged + ", rewardPoints=" + rewardPoints
				+ ", isActive=" + isActive + "]";
	}

	}
	
	

