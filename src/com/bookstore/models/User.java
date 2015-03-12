package com.bookstore.models;

import java.io.Serializable;

public class User implements Serializable {

	private String username;
	private String passwd;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String signDate;
	private String lastDate;
	private boolean isStaff;
	
	public User(String username, String passwd, String fName, String lName,
			String phoneNumber, String email, String signDate, String lastDate,
			boolean isStaff) {
		this.username = username;
		this.passwd = passwd;
		this.firstName = fName;
		this.lastName = lName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.signDate = signDate;
		this.lastDate = lastDate;
		this.isStaff = isStaff;
	}

	public User() {
		this.username = "";
		this.passwd = "";
		this.firstName = "";
		this.lastName = "";
		this.phoneNumber = "";
		this.email = "";
		this.signDate = "";
		this.lastDate = "";
		this.isStaff = false;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isStaff() {
		return isStaff;
	}

	public void setStaff(boolean isStaff) {
		this.isStaff = isStaff;
	}
	
}
