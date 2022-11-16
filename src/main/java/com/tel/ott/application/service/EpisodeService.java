package com.tel.ott.application.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tel.ott.application.collection.Episode;
import com.tel.ott.application.collection.Season;
import com.tel.ott.application.collection.Shows;
import com.tel.ott.application.repository.ShowRepository;
import com.tel.ott.application.response.Response;


@Service
public class EpisodeService {
	
	@Autowired
	private ShowRepository showrepository;
	
	public Response saveEpisode(Episode episode)
	{
		String seasonName = episode.getSeasonName();
		Optional<Shows> findBySeasonName = showrepository.findBySeasonName(seasonName);
		if(findBySeasonName.isPresent())
		{
			List<Season> season = findBySeasonName.get().getSeason();
			Iterator<Season> iterator = season.iterator();
			while(iterator.hasNext())
			{
				Season next = iterator.next();
				if(episode.getSeasonName().equals(next.getSeasonName()))
				{
				List<Episode> episode2 = next.getEpisode();
				try {				
   				    Iterator<Episode> iterator2 = episode2.iterator();					
					while(iterator2.hasNext())
					{					
						Episode next2 = iterator2.next();						
						try {
						if(next2.getEpisodeName().equals(episode.getEpisodeName()))
						{
							return new Response(400, "Episode name Already Exist", null);
						}
						}
						catch (Exception e) {							
						}
					}
					episode.setEpisodeId(uniqueId());
					episode.setActive(true);
					LocalDate date = LocalDate.now();
					episode.setCreatedAt(date);
					episode2.add(episode);
					showrepository.save(findBySeasonName.get());	
					return new Response(200, "save successfull", episode);
				}
				catch(NullPointerException e)
				{}	
				}
			}			
		}
		return new Response(400, "Season Name Not found", null);
	}

	
	
	public Response findAllEpisode(String seasonName)
	{
		Optional<Shows> findBySeasonName = showrepository.findBySeasonName(seasonName);
		if(findBySeasonName.isPresent())
		{
			List<Season> season = findBySeasonName.get().getSeason();
			Iterator<Season> iterator = season.iterator();
			while(iterator.hasNext())
			{
				Season next = iterator.next();
				if(seasonName.equals(next.getSeasonName()))
				{
					List<Episode> episodeList = new ArrayList<>();
				List<Episode> episode = next.getEpisode();
				try {
					
					Iterator<Episode> iterator2 = episode.iterator();
					while(iterator2.hasNext())
					{
						Episode next2 = iterator2.next();
						if(next2.isActive())
						{
							episodeList.add(next2);
						}
					}
				}
				catch(Exception e)
				{}
				return new Response(200, "All Episode with Season Name "+seasonName, episodeList);
				}
			}
		}
		
		return new Response(400, "Season Name not found", null);
	}
	
	
	
	public Response findByIdEpisode(String seasonName, String Id)
	{
		Optional<Shows> findBySeasonName = showrepository.findBySeasonName(seasonName);
		if(findBySeasonName.isPresent())
		{
			List<Season> season = findBySeasonName.get().getSeason();
			Iterator<Season> iterator = season.iterator();
			while(iterator.hasNext())
			{
				Season next = iterator.next();
				if(seasonName.equals(next.getSeasonName()))
				{
					List<Episode> episode = next.getEpisode();
					Iterator<Episode> iterator2 = episode.iterator();
					while(iterator2.hasNext())
					{
						
						Episode next2 = iterator2.next();
						if(Id.equals(next2.getEpisodeId()) && next2.isActive())
						{
		
							return new Response(200, "find By Id: "+Id, next2);
						}
					}
				}
			}
		}
		
		return new Response(400, "SeasonName or Id not Found", null);
	}
	
	
	
	public Response updateEpisode(Episode episode, String id)
	{
         String seasonName = episode.getSeasonName();
		Optional<Shows> findBySeasonName = showrepository.findBySeasonName(seasonName);
		if(findBySeasonName.isPresent())
		{
			List<Season> season = findBySeasonName.get().getSeason();
			Iterator<Season> iterator = season.iterator();
			while(iterator.hasNext())
			{
				Season next = iterator.next();
				if(seasonName.equals(next.getSeasonName()))
				{
					List<Episode> episode1 = next.getEpisode();
					Iterator<Episode> iterator2 = episode1.iterator();
					while(iterator2.hasNext())
					{
						Episode next2 = iterator2.next();
						if(id.equals(next2.getEpisodeId()))
						{
							next2.setEpisodeName(episode.getEpisodeName());
							next2.setEpisodeNo(episode.getEpisodeNo());
						    LocalDate date = LocalDate.now();
						    next2.setUpdatedAt(date);
						    next2.setEpisodeNo(episode.getEpisodeNo());
						    next2.setDescription(episode.getDescription());
						    next2.setPlatform(episode.getPlatform());
						    next2.setShowName(episode.getShowName());
						    showrepository.save(findBySeasonName.get());
							
							return new Response(200, "updated successful", next2);
						}
					}
				}
			}
		}
		
		return new Response(400, "SeasonName or Id not Found", null);
	}
	
	
	
	public Response deleteEpisode(String seasonName, String id)
	{
		Optional<Shows> findBySeasonName = showrepository.findBySeasonName(seasonName);
		if(findBySeasonName.isPresent())
		{
			List<Season> season = findBySeasonName.get().getSeason();
			Iterator<Season> iterator = season.iterator();
			while(iterator.hasNext())
			{
				Season next = iterator.next();
				if(seasonName.equals(next.getSeasonName()))
				{
					List<Episode> episode = next.getEpisode();
					Iterator<Episode> iterator2 = episode.iterator();
					while(iterator2.hasNext())
					{
						Episode next2 = iterator2.next();
						if(id.equals(next2.getEpisodeId()))
						{
							
							next2.setActive(false);
							LocalDate date = LocalDate.now();
							next2.setDeletedAt(date);
							showrepository.save(findBySeasonName.get());
							return new Response(200, "Id deleted Successfully", null);
						}
					}
				}
			}
		}
		
		return new Response(400, "SeasonName or Id not Found", null);
		
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
