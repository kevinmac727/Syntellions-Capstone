package domain;

import java.util.ArrayList;

public class User {
	
	String userId;
	String firstName;
	String lastName;
	String phone;
	String email;
	String password;
	String userStatusId;
	
	//ArrayList<Card> cards = new ArrayList<Card>();
	public User() {
		super();
	}

	

	public User(String userId, String firstName, String lastName, String phone, String email, String password,
			String userStatusId) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.userStatusId = userStatusId;
	}



	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserStatusId() {
		return userStatusId;
	}

	public void setUserStatusId(String userStatusId) {
		this.userStatusId = userStatusId;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone
				+ ", email=" + email + ", password=" + password + ", userStatusId=" + userStatusId + "]";
	}

	
	
}
