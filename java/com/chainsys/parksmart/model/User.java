package com.chainsys.parksmart.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class User {
	private int userId;
	private String userName;
	private String userPassword;
	private String phoneNumber;
	private String email;
	private boolean status;

	public User(int userId, String userName, String userPassword, String phoneNumber, String email, boolean status) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword + ", phoneNumber="
				+ phoneNumber + ", email=" + email + ", status=" + status + ", getUserId()=" + getUserId()
				+ ", getUserName()=" + getUserName() + ", getUserPassword()=" + getUserPassword()
				+ ", getPhoneNumber()=" + getPhoneNumber() + ", getEmail()=" + getEmail() + ", getStatus()="
				+ getStatus() + "]";
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

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Autowired
	public User() {
	}

}
