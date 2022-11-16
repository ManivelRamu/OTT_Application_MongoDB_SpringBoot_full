package com.tel.ott.application.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ForgotPassword {

	@NotBlank
	String username;

	@NotBlank
	@Size(min=5, max=15, message = "password should have atleast 5 charector and less than 15 charector")
	String newPassword;
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}



}

