package com.tel.ott.application.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators.ToString;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.model.Collation;

import com.tel.ott.application.collection.Shows;
import com.tel.ott.application.repository.ShowRepository;
import com.tel.ott.application.response.Response;


@Service
public class ShowService {
	
	@Autowired
	public ShowRepository showRepository;
	
	
	public Response save(Shows show)
	{	
		
		List<Shows> findAll = showRepository.findAll();
	
		Iterator<Shows> iterator = findAll.iterator();
		while(iterator.hasNext())
		{
			Shows showObject = iterator.next();
		if(showObject.getShowName().equals(show.getShowName()))
		{
				return new Response(400, "Show Name Already exist",null);		}
		}
		LocalDate date = LocalDate.now();
	    show.setCreatedAt(date);
	    show.setActive(true);
		Shows save = showRepository.save(show);
		return new Response(200, "Show save successfully", save);	
	}
	
	
	
	public Response findAll()
	{
		
		List<Shows> findAll = showRepository.findAll();
		List<Shows> showList = new ArrayList<>();
		Iterator<Shows> iterator = findAll.iterator();
		while(iterator.hasNext())
		{
			Shows next = iterator.next();
			if(next.isActive())
			{
				showList.add(next);
			}
		}
		return new Response(200, "findAll Successful", showList);
	}
	
	
	
	public Response findById(String id)
	{
		
	    Optional<Shows> findById = showRepository.findById(id);
	    if(findById.isPresent() && findById.get().isActive())
	    {
	    	return new Response(200, "find by Id successfull. Id : "+id, findById);
	    	 
	    }
	    return new Response(400, "Id not found", null); 
	}
	
	
	public Response updateSHow(Shows show,String id)
	{
		Optional<Shows> showfromdb = showRepository.findById(id);
		if(showfromdb.isPresent())
		{
			showfromdb.get().setShowName(show.getShowName());
			showfromdb.get().setShowDirector(show.getShowDirector());
			showfromdb.get().setShowRating(show.getShowRating());
			showfromdb.get().setShowYear(show.getShowYear());;
			LocalDate date = LocalDate.now();
			showfromdb.get().setUpdatedAt(date);
			Shows save = showRepository.save(showfromdb.get());
			
			return new Response(200, "Updated Successful",save );
		}
		
		return new Response(400, "Id not found", null);
	}
	
	
	public Response deleteShow(String id) {
		
		Optional<Shows> findById = showRepository.findById(id);
		if(findById.isPresent())
		{
			findById.get().setActive(false);
			LocalDate date = LocalDate.now();
			findById.get().setDeletedAt(date);
			showRepository.save(findById.get());
			return new Response(200, "Id deleted Successfull", null);
		}
		return new Response(400, "id not found", null);
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
