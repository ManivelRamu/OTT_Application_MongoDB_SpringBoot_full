package com.tel.ott.application.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tel.ott.application.collection.Subscriptions;
import com.tel.ott.application.collection.User;
import com.tel.ott.application.repository.SubscriptionRepository;
import com.tel.ott.application.repository.UserRepository;
import com.tel.ott.application.response.Response;


@Service
public class AdminService {

	@Autowired
	SubscriptionRepository subscriptionRepository;
	@Autowired
	UserRepository userRepository;

	
	public Response saveSubscription(Subscriptions subscription) {
		String subType=subscription.getType();
		List<Subscriptions> allSubscriptions=subscriptionRepository.findAll();
		boolean isPresent=false;
		for(Subscriptions subscriptions:allSubscriptions)
		{
			if(subscriptions.getType().equals(subType))
			{
				isPresent=true;
			}
		}
		
		if(!isPresent)
		{
			subscription.setActive(true);
			Subscriptions subscriptionDetails = subscriptionRepository.save(subscription);
			 return new Response(200,"Movie added successfully", subscriptionDetails);
		}

		return new Response(400,"Subscription already present", null);
	}
	public Response getSubscriptionById(String id) {
			Optional<Subscriptions>	findById=subscriptionRepository.findById(id);
			if(findById.isPresent())
			{
				Subscriptions subscription= findById.get();
				return new Response(200,"subscription found", subscription);
			}
			
				return new Response(401,"subscription not found", null);
	}
	public Response updateSubscription(String id, Subscriptions subscription) {
		Optional<Subscriptions>	existing=subscriptionRepository.findById(id);
		if(existing.isPresent())
		{
			Subscriptions existingSub=existing.get();
			
			if(subscription.getType()!=null)
				existingSub.setType(subscription.getType());
			if(subscription.getScreens()!=null)
				existingSub.setScreens(subscription.getScreens());
			if(subscription.getPeriod()!=0)
				existingSub.setPeriod(subscription.getPeriod());
			if(subscription.getFeatures()!=null)
				existingSub.setFeatures(subscription.getFeatures());
			subscriptionRepository.save(existingSub);
			return new Response(200,"subscription updated successfully", existingSub);

		}
		return new Response(401,"subscription not found", null);
	}
	public Response saveUserInfo(User userInfo) {
		 
		Optional<User> user = userRepository.getUserByEmail(userInfo.getEmail());

	        if (user.isPresent()) {
	        	
	        	if(!user.get().getRole().equalsIgnoreCase("admin")) {
	        	User existUser=user.get();
	        	Optional<Subscriptions>	existing=subscriptionRepository.findAllByType(userInfo.getSub_type());
	        	if(existing.isPresent())
	        		{
	        			existUser.setSubscription(existing.get());
	        			existUser.setSub_endDate(null);
	        		}
	        	else	
	        		return  new Response(401,"Invalid subscription type",null);
	        	existUser.setSub_startDate(LocalDate.now());
	        	existUser.set_active(true);
	            userRepository.save(existUser);
	            return  new Response(200,"User info updated sucessfully",null);
	            }
	        	return  new Response("User cannot be admin",null);
	        }
	        return  new Response(401,"User not found",null);
		
	}
	
	
	public Response updateUserInfo(User userInfo, String id) {
		Optional<User>	existing=userRepository.findById(id);
		if(existing.isPresent())
		{
			User existingInfo=existing.get();
			if(!userInfo.getSub_type().equalsIgnoreCase(existingInfo.getSubscription().getType()))
			{
				Optional<Subscriptions>	subType=subscriptionRepository.findAllByType(userInfo.getSub_type());
				if(subType.isPresent())
				{existingInfo.setSubscription(subType.get());
				existingInfo.setSub_startDate(LocalDate.now());
				existingInfo.setSub_endDate(null);
				}
				else
					return new Response("Invalid subscription type");
			}
			else
			{
				return new Response(400,"user info subscription already exist", existingInfo);
			}
			userRepository.save(existingInfo);
			return new Response(200,"user info updated successfully", existingInfo);

		}
		return new Response(401,"subscription not found", null);

	}
	public Response deleteUserSubscription(String userId) {
		Optional<User>	existing=userRepository.findById(userId);
		if(existing.isPresent())
		{
			User existingInfo=existing.get();
			existingInfo.setSubscription(null);
			existingInfo.set_active(false);
			existingInfo.setSub_endDate(LocalDate.now());
			userRepository.save(existingInfo);
			return new Response(200,"user subscriptions removed successfully", existingInfo);

		}
		
		return new Response(401,"subscription not found", null);
	}
	
	
	 
	  public Response getSubscriptionDaysLeft(String id) {
		  Optional<User>	existing=userRepository.findById(id);
		  if(existing.isPresent() && existing.get().getRole().equalsIgnoreCase("user"))
		  {
			  if(existing.get().is_active()) {
			  LocalDate dateAfter = LocalDate.now();
			  LocalDate dateBefore = existing.get().getSub_startDate();
			  long validity=existing.get().getSubscription().getPeriod()- ChronoUnit.DAYS.between(dateBefore, dateAfter);
			  return new Response("subscription valid for: "+validity+" days");
			  }
			  return new Response("subscription expired");
		  }
		  return new Response(401,"user not found or user is Admin", null);
		 
		}
	  
	  
	public Response getUsersByValidity(long expirationIn) {
		
		List<User> usersRequired=new ArrayList<User>();
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(users::add);
	      if (users.isEmpty()) {
	        return new Response(401,"No user found",null);
	      }
	     for(User user:users)
	     {
	    	 if(user.is_active()) {
	    	 LocalDate dateAfter = LocalDate.now();
			  LocalDate dateBefore = user.getSub_startDate();
	    	 long validity=user.getSubscription().getPeriod()- ChronoUnit.DAYS.between(dateBefore, dateAfter);
	    	 if(validity<=expirationIn)
	    		 usersRequired.add(user);
	    	 }
	     }
	     if(usersRequired.size()>0)
		return new Response(200,"Users Found",usersRequired);
	     else
	    return new Response("No Users Found for expiration: "+expirationIn);
	}
}
