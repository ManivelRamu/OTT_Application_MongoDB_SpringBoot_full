package com.tel.ott.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tel.ott.application.collection.User;
import com.tel.ott.application.entity.ForgotPassword;
import com.tel.ott.application.entity.LoginEntity;
import com.tel.ott.application.response.LoginResponse;
import com.tel.ott.application.response.SignupResponse;
import com.tel.ott.application.service.LoginService;


@RestController
@RequestMapping("/api")
public class LoginController {
	

    @Autowired
    LoginService loginService;
    
    @PostMapping("/user/saveUser")
    public SignupResponse saveUser(@RequestBody User user)
    {
       return  loginService.saveUser(user);
    }

	
	 @PostMapping("/user/login") 
	 public LoginResponse loginUser(@RequestBody LoginEntity loginEntity) throws Exception { 
		 
		 return loginService.login(loginEntity);
	 }


	    @PostMapping("/user/forgotPassword")
	    public String forgotPassword(@RequestBody ForgotPassword forgotPassword) {
	        return  loginService.forgotPassword(forgotPassword);
	    }

}
