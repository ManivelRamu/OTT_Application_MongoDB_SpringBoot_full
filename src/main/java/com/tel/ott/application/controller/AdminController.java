package com.tel.ott.application.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tel.ott.application.collection.Movie;
import com.tel.ott.application.collection.Subscriptions;
import com.tel.ott.application.collection.User;
import com.tel.ott.application.repository.SubscriptionRepository;
import com.tel.ott.application.repository.UserRepository;
import com.tel.ott.application.response.Response;
import com.tel.ott.application.service.AdminService;
import com.tel.ott.application.service.CSVService;
import com.tel.ott.application.service.TokenService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	 @Autowired
	 SubscriptionRepository subscriptionRepository;
	 @Autowired
	 AdminService adminService;
	 @Autowired
	 TokenService tokenService;
	 @Autowired
	 UserRepository userRepository;
	 @Autowired
	 CSVService csvservice;
		

	 //32
	  @GetMapping("/subscription/get/allSubscriptions")
	  public Response getAllSubscriptions(@RequestParam(name = "token", defaultValue = "null") String token) throws Exception {
		  
		  if (tokenService.validateToken(token)) {
				if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
					
				      List<Subscriptions> subscriptions = new ArrayList<Subscriptions>();
			    	  subscriptionRepository.findAll().forEach(subscriptions::add);
				      if (subscriptions.isEmpty()) {
				        return new Response("Subscription is empty",null);
				      }
				      return new Response(200,"sucessful",subscriptions);
				      
				}
				return new Response(401, "Only admin have access", null);
			}
			return new Response(401, "Invalid token", null);
		
	  }
	  
	  //31
	  @PostMapping("/subscription/modify/add")
		public Response save(@RequestBody Subscriptions subscription,
				 @RequestParam(name = "token", defaultValue = "null") String token) throws Exception
		{
			if (tokenService.validateToken(token)) {
				if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
					return adminService.saveSubscription(subscription);
				}
				return new Response(401, "Only admin have access", null);
			}
			return new Response(401, "Invalid token", null);
			
		}

	  //33
	  @GetMapping("/subscription/get/subscriptionByid/{id}")
	  public Response getSubscriptionByid(@PathVariable("id") String id,
			  @RequestParam(name = "token", defaultValue = "null") String token) throws Exception 
	  {
		if (tokenService.validateToken(token)) {
			if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
				return adminService.getSubscriptionById(id);
			}
			return new Response(401, "Only admin have access", null);
		}
		return new Response(401, "Invalid token", null);
	  }
	 
	  //34
	  @PutMapping("/subscription/modify/subscriptionByid/{id}")
		public Response updateSubscription(@PathVariable("id") String id,@RequestBody Subscriptions subscription,
				@RequestParam(name = "token", defaultValue = "null") String token) throws Exception
		{
			if (tokenService.validateToken(token)) {
				if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
					return adminService.updateSubscription(id,subscription);
				}
				return new Response(401, "Only admin have access", null);
			}
			return new Response(401, "Invalid token", null);
			
		}
	  

	  //35
	  @DeleteMapping("/subscription/modify/delete/{id}")
	  public Response deleteSubscription(@PathVariable("id") String id,
			  @RequestParam(name = "token", defaultValue = "null") String token) throws Exception {
	    if (tokenService.validateToken(token)) {
			if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
				try {

				      Optional<Subscriptions>	subscriptions=subscriptionRepository.findById(id);
				         subscriptionRepository.delete(subscriptions.get());
				    	  return new Response(200,"subscription deleted",HttpStatus.OK);
				     
				    } catch (Exception e) {
				      return new Response(200,"subscription not found",HttpStatus.INTERNAL_SERVER_ERROR);
				    }
			}
			return new Response(401, "Only admin have access", null);
		}
		return new Response(401, "Invalid token", null);
	    
	  }
	  
	  //36//42
	  @PostMapping("/userInfo/modify/add")
		public Response saveUserInfo(@RequestBody User userInfo,
				 @RequestParam(name = "token", defaultValue = "null") String token) throws Exception
		{
			if (tokenService.validateToken(token)) {
				if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
								
					return adminService.saveUserInfo(userInfo);
				}
				return new Response(401, "Only admin have access", null);
			}
			return new Response(401, "Invalid token", null);
			
		}

	  
	  //37//43
	  @PutMapping("/userInfo/modify/userInfoById/{id}")
		public Response upadteUserInfo(@PathVariable("id") String id,@RequestBody User userInfo,
				 @RequestParam(name = "token", defaultValue = "null") String token) throws Exception
		{
			if (tokenService.validateToken(token)) {
				if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
								
					return adminService.updateUserInfo(userInfo,id);
				}
				return new Response(401, "Only admin have access", null);
			}
			return new Response(401, "Invalid token", null);
			
		}
	  

	  //38
	  @GetMapping("/userInfo/get/allUsers")
	  public Response getAllUsers(@RequestParam(name = "token", defaultValue = "null") String token) throws Exception 
	  {
		if (tokenService.validateToken(token)) {
			if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
				List<User> users = new ArrayList<User>();
				userRepository.findAll().forEach(users::add);
			      if (users.isEmpty()) {
			        return new Response(200,"No user found",null);
			      }
			      return new Response(200,"sucessful",users);
			}
			return new Response(401, "Only admin have access", null);
		}
		return new Response(401, "Invalid token", null);
	  }
	  
	  
	  //39
	  @GetMapping("/userInfo/get/userInfoByName")
	  public Response getUsersByName(@RequestParam(name = "name", defaultValue = "null") String name,
			  @RequestParam(name = "token", defaultValue = "null") String token) throws Exception 
	  {
		if (tokenService.validateToken(token)) {
			if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
				 List<User> users = userRepository.findAllByName(name);
			      if (users.isEmpty()) {
			        return new Response(200,"No user found",null);
			      }
			      return new Response(200,"sucessful",users);
			}
			return new Response(401, "Only admin have access", null);
		}
		return new Response(401, "Invalid token", null);
	  }
	  
	  
	  //40
	  @DeleteMapping("/userInfo/modify/delete/{id}")
	  public Response deleteUserById(@PathVariable("id") String id,
			  @RequestParam(name = "token", defaultValue = "null") String token) throws Exception {
	    if (tokenService.validateToken(token)) {
			if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
				try {

				      Optional<User> user=userRepository.findById(id);
				      userRepository.delete(user.get());
				    	  return new Response(200,"user deleted",HttpStatus.OK);
				     
				    } catch (Exception e) {
				      return new Response(200,"user not found",HttpStatus.INTERNAL_SERVER_ERROR);
				    }
			}
			return new Response(401, "Only admin have access", null);
		}
		return new Response(401, "Invalid token", null);
	    
	  }
	  
	  
	  //41
	  @GetMapping("/userInfo/get/userSubscriptionById")
	  public Response getUserSubscription(@RequestParam(name = "id", defaultValue = "null") String id,
			  @RequestParam(name = "token", defaultValue = "null") String token) throws Exception 
	  {
		if (tokenService.validateToken(token)) {
			if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
				 Optional<User> users = userRepository.findById(id);
				 if(users.get().getRole().equalsIgnoreCase("user")) {
			      if (users.isEmpty()) {
			        return new Response(401,"No user found",null);
			      }
			      return new Response(200,"sucessful",users.get().getSubscription());
				 }
				 else
					 return new Response(410,"Admin user, no subscription available",null); 
			}
			return new Response(401, "Only admin have access", null);
		}
		return new Response(401, "Invalid token", null);
	  }
	  

	  //44
	  @DeleteMapping("/userInfo/modify/deleteUserSubscription")
	  public Response deleteUserSubscriptionById(@RequestParam(name = "id", defaultValue = "null") String id,
			  @RequestParam(name = "token", defaultValue = "null") String token) throws Exception {
	    if (tokenService.validateToken(token)) {
			if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
				try {

				      Optional<User> user=userRepository.findById(id);
				      
				    	  return adminService.deleteUserSubscription(user.get().getId());
				     
				    } catch (Exception e) {
				      return new Response(200,"user not found",HttpStatus.INTERNAL_SERVER_ERROR);
				    }
			}
			return new Response(401, "Only admin have access", null);
		}
		return new Response(401, "Invalid token", null);
	    
	  }

	 
	//45
	  @GetMapping("/userInfo/get/subscriptionValidity")
	  public Response getsubscriptionValidity(@RequestParam(name = "id", defaultValue = "null") String id,
			  @RequestParam(name = "token", defaultValue = "null") String token) throws Exception 
	  {
		if (tokenService.validateToken(token)) {
			if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
				return adminService.getSubscriptionDaysLeft(id);
			}
			return new Response(401, "Only admin have access", null);
		}
		return new Response(401, "Invalid token", null);
	  }
	  
	//46
	  @GetMapping("/userInfo/get/usersByValidity")
	  public Response getUsersByValigdity(@RequestParam(name = "expirationIn", defaultValue = "null") long expirationIn,
			  @RequestParam(name = "token", defaultValue = "null") String token) throws Exception 
	  {
		if (tokenService.validateToken(token)) {
			if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
				return adminService.getUsersByValidity(expirationIn);
			}
			return new Response(401, "Only admin have access", null);
		}
		return new Response(401, "Invalid token", null);
	  }
	  
	  //csv add user info
	@PostMapping("/userInfo/CsvAddUsers")
		public Response saveCSVFile(@RequestParam("file") MultipartFile file,@RequestParam(name = "token", defaultValue = "null") String token) throws Exception 
		{
			if (tokenService.validateToken(token))
			{
				if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) 
				{
					return csvservice.saveUsers(file);
					//return new Response(200, "Movie added successfully", list);
				}
				return new Response(401, "Only admin can add", null);
			}
			return new Response(401, "Invalid token", null);
		}
}
