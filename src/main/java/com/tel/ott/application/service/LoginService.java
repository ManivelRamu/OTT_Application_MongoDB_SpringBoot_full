package com.tel.ott.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.tel.ott.application.collection.User;
import com.tel.ott.application.entity.ForgotPassword;
import com.tel.ott.application.entity.LoginEntity;
import com.tel.ott.application.repository.UserRepository;
import com.tel.ott.application.response.LoginResponse;
import com.tel.ott.application.response.SignupResponse;

@Service
public class LoginService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	TokenService tokenService;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
	public SignupResponse saveUser(User user) {
		 String email = user.getEmail();
	        List<User> findAllUser = userRepository.findAll();

	        boolean presentAlready=false;
	        for (User existUser:findAllUser) {
	            if (existUser.getEmail().equals(email)){
	                presentAlready=true;
	            }
	        }
	        if(!presentAlready){
	            String encode = passwordEncoder.encode(user.getPassword());
	            user.setPassword(encode);
	            User savedUser = userRepository.save(user);
	            return new SignupResponse(200,"SignedUp successfully", savedUser);
	        }
	        return new SignupResponse(400,"User already present", null);
	
	}

	public LoginResponse login(LoginEntity loginEntity) {
		 Optional<User> users = userRepository.getUserByEmail(loginEntity.getUsername());
	        if (users !=null) {

	            if (passwordEncoder.matches(loginEntity.getPassword(),users.get().getPassword())){
	                try {
	                    String token = tokenService.createToken(users.get());
	                    return new LoginResponse(200,"Login successful",users.get().getRole(),token);
	                }catch (Exception e){
	                    return new LoginResponse(401,"failed to generate JWT token",null,null);
	                }
	            }
	            else{
	                return new LoginResponse(401,"Incorrect password",null,null);
	            }
	        }
	        return new LoginResponse(401, "User not present", null, null);
	}

	public String forgotPassword(ForgotPassword forgotPassword) {
        Optional<User> user = userRepository.getUserByEmail(forgotPassword.getUsername());

        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(forgotPassword.getNewPassword()));
            userRepository.save(user.get());

            return  "New password updated successfully";
        }
        return  "User not found";
    }
	
}
