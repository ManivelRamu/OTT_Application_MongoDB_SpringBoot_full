package com.tel.ott.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tel.ott.application.collection.Episode;
import com.tel.ott.application.response.Response;
import com.tel.ott.application.service.EpisodeService;
import com.tel.ott.application.service.TokenService;


@RestController
@RequestMapping("/episode")
public class EpisodeController {
	
	@Autowired
	private EpisodeService episodeService;
	@Autowired
	TokenService tokenService;
	
	@PostMapping("/save")
	public Response saveEpisode(@RequestBody Episode episode,@RequestParam(name = "token", defaultValue = "null") String token) throws Exception
	{
		if (tokenService.validateToken(token)) {
			if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
				return episodeService.saveEpisode(episode);
			}
			return new Response(401, "Only admin can add", null);
		}
		return new Response(401, "Invalid token", null);
		
	}
	
	
	@GetMapping("/findall/{seasonName}")
	public Response findallEpisode(@PathVariable String seasonName)
	{
		Response findAllEpisode1 = episodeService.findAllEpisode(seasonName);
		return findAllEpisode1;
	}
	
	@GetMapping("/findbyid/{seasonName}/{id}")
	public Response findByIdEpisode(@PathVariable String seasonName, @PathVariable String id)
	{
		
		Response findByIdEpisode = episodeService.findByIdEpisode(seasonName, id);
		return findByIdEpisode;
	}

	@PutMapping("/update/{id}")
	public Response updateEpisode(@RequestBody Episode episode,@PathVariable String id, @RequestParam(name = "token", defaultValue = "null") String token) throws Exception
	{
		if (tokenService.validateToken(token)) {
			if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
				return episodeService.updateEpisode(episode, id);
			}
			return new Response(401, "Only admin can add", null);
		}
		return new Response(401, "Invalid token", null);
		
	}
	
	
	@DeleteMapping("/delete/{seasonName}/{id}")
	public Response deleteEpisode(@PathVariable String seasonName, @PathVariable String id,@RequestParam(name = "token", defaultValue = "null") String token) throws Exception
	{
		if (tokenService.validateToken(token)) {
			if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
				return episodeService.deleteEpisode(seasonName, id);
			}
			return new Response(401, "Only admin can add", null);
		}
		return new Response(401, "Invalid token", null);
		
	}
	
}
