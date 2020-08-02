package com.citi.sanctions.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserMaster")
public class UserMaster {

	@Id
	@Column(name ="userId")
	String userId;
	
	@Column(name ="password")
	String password;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserMaster [userId=" + userId + ", password=" + password + "]";
	}
	
	
	
}
