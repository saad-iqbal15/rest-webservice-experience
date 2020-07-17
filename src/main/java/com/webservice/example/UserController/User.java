package com.webservice.example.UserController;


public class User {

	public User() {}

	
	
	long id;
	
	String firstName;
	
	String lastName;
	
	Long phone;
	
	public User(long id, String firstName, String lastName, Long phone) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone=phone;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	
}
