package com.tel.ott.application.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tel.ott.application.collection.Season;
import com.tel.ott.application.collection.Shows;
import com.tel.ott.application.repository.ShowRepository;
import com.tel.ott.application.response.Response;

//import com.tataelxsi.OTTApplication.CmsModule.entity.Season;
//import com.tataelxsi.OTTApplication.CmsModule.entity.Shows;
//import com.tataelxsi.OTTApplication.CmsModule.repository.ShowRepository;
//import com.tataelxsi.OTTApplication.CmsModule.response.Response;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;


@Service
public class SeasonService {
	
	
	@Autowired
	private ShowRepository showRepository;

	 
	 public Response save(Season season)
		{
			String showName = season.getShowName();			
			Optional<Shows> findByShowName = showRepository.findByShowName(showName);			
			List<Season> season2 = findByShowName.get().getSeason();			
			Iterator<Season> iterator = season2.iterator();
			while(iterator.hasNext())
			{
			Season next = iterator.next();
			try {
				
				
				if(next.getSeasonName().equals(season.getSeasonName()))
	  			{
						return new Response(400, "Season Name Already Exist", null);
				}
			}
			catch(NullPointerException e)
			{}	
			}
			String uniqueId = uniqueId();
			season.setSeasonId(uniqueId); 
			LocalDate date = LocalDate.now();
			season.setCreatedAt(date);
			season2.add(season);
			season.setActive(true);
			showRepository.save(findByShowName.get());	   			
			return new Response(200, "Season save successfully", season) ;
		}
	 
	 
	
	 public Response findAllSeason(String showName)
	 {
		 Optional<Shows> findByShowName = showRepository.findByShowName(showName);
		 if(findByShowName.isPresent())
		 {
			 List<Season> seasonList = new ArrayList<>();
			 List<Season> season = findByShowName.get().getSeason();
			 Iterator<Season> iterator = season.iterator();
			 while(iterator.hasNext())
			 {
				 Season next = iterator.next();
				 if(next.isActive())
				 {
					 seasonList.add(next);
				 }
			 }
			 
			 return new Response(200, "all season present in "+showName, seasonList);
		 }
		 return new Response(400, "Show name not found", null);
	 }
	 
	 
	 public Response findByIdSeason(String showName, String id)
	 {
		 Optional<Shows> findByShowName = showRepository.findByShowName(showName);
		 if(findByShowName.isPresent())
		 {
			 List<Season> season = findByShowName.get().getSeason();
			 Iterator<Season> iterator = season.iterator();
			 while(iterator.hasNext())
			 {
				 Season next = iterator.next();
				 try {
					 if(next.getSeasonId().equals(id) && next.isActive()) 
				 {
					 return new Response(200, "Season with Id :" +id, next);
				 }}
				 
				 catch(NullPointerException e)
				 {} 
			 }
		 }
		 
		 return new Response(400, "Show name or Season Id not found", null);
	 }
	 
	 
	 public Response updateseason(Season season, String id)
	 {
		 String showName = season.getShowName();
		 Optional<Shows> findByShowName = showRepository.findByShowName(showName);
		 if(findByShowName.isPresent())
		 {
			 List<Season> season2 = findByShowName.get().getSeason();
			 Iterator<Season> iterator = season2.iterator();
			 while(iterator.hasNext())
			 {
				 Season next = iterator.next();
				 try {
					 if(next.getSeasonId().equals(id)) 
				 {
						 next.setSeasonName(season.getSeasonName());
						 next.setAvailableOn(season.getAvailableOn());
						 LocalDate date = LocalDate.now();
						 next.setUpdatedAt(date);
						 showRepository.save(findByShowName.get());
					 return new Response(200, "Season Updated Successful", next);
				 }}
				 
				 catch(NullPointerException e)
				 {} 
			 }
		 }
		 
		 return new Response(400, "Show name or Season Id not found", null);
	 }
	 

	 public Response deleteSeason(String showName,String id)
	 {
		 
		 Optional<Shows> findByShowName = showRepository.findByShowName(showName);
		 if(findByShowName.isPresent())
		 {
			 List<Season> season = findByShowName.get().getSeason();
			 Iterator<Season> iterator = season.iterator();
			 while(iterator.hasNext())
			 {
				 Season next = iterator.next();
				 try {
					 if(next.getSeasonId().equals(id)) 
				 {
						next.setActive(false);
						LocalDate date = LocalDate.now();
						next.setDeletedAt(date);
						 showRepository.save(findByShowName.get());
					     return new Response(200, "Season deleted Successful", null);
				 }}
				 
				 catch(NullPointerException e)
				 {} 
			 }
		 }
		 
		 return new Response(400, "Show name or Season Id not found", null);
	 }
	 
	 
		public static String uniqueId()
		{
		        int length = 25;
		       // String symbol = "-/.^&*_!@%=+>)"; 
		        //String cap_letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
		        String small_letter = "abcdef"; 
		        String numbers = "0123456789"; 
		        String finalString = numbers +small_letter ; 
		        Random random = new Random(); 
		        char[] password = new char[length]; 
		        for (int i = 0; i < length; i++) 
		        { 
		            password[i] = finalString.charAt(random.nextInt(finalString.length())); 
		        }  
		        String string = new String(password); 
		        return string;
		}

}
