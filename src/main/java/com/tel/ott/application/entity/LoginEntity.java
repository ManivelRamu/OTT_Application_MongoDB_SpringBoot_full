package com.tel.ott.application.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



public class LoginEntity {


	@NotBlank(message = "user name cannot be blank")
    String username;

	@NotBlank(message = "password cannot be blank")
	@Size(min=5, max=15, message = "password should have atleast 5 charecters and less than 15 charecters")
    String password;





    public LoginEntity(@NotBlank(message = "user name can not br blank") String username,
                       @NotBlank(message = "password can not br blank") @Size(min = 5, max = 15, message = "password should have atleast 5 charector and less than 15 charector") String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public LoginEntity() {
	super();

}



	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	@Override
	public String toString() {
		return "JwtRequest [username=" + username + ", password=" + password + "]";
	}


}
