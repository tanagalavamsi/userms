package com.user.entity;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;


@Entity
public class Seller {

	@Id
	private String sellerId;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false,unique=true)
	private String email;
	@Column(nullable = false,unique=true)
	private String phoneNo;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String isActive;
	
	
	public String getsellerId() {
		return sellerId;
	}


	public void setsellerId(String sellerId) {
		this.sellerId = sellerId;
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


	public  String getPhoneNo() {
		return phoneNo;
	}


	public void setPhoneNo( String phoneNo) {
		this.phoneNo = phoneNo;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public String getIsActive() {
		return isActive;
	}


	public void setActive(String isActive) {
		this.isActive = isActive;
	}

	public Seller() {
		super();
	}
}
