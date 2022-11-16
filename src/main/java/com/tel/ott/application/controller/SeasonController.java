package com.tel.ott.application.controller;

import java.util.List;

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

import com.tel.ott.application.collection.Season;
import com.tel.ott.application.response.Response;
import com.tel.ott.application.service.SeasonService;
import com.tel.ott.application.service.TokenService;


@RestController
@RequestMapping("/season")
public class SeasonController {
	
	@Autowired
	private SeasonService seasonservice;
	@Autowired
	TokenService tokenService;
	@PostMapping("/save")
	public Response insertSeason(@RequestBody Season season,@RequestParam(name = "token", defaultValue = "null") String token)throws Exception
    {
        if (tokenService.validateToken(token)) {
            if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
                return seasonservice.save(season);
            }
            return new Response(401, "Only admin can add", null);
        }
        return new Response(401, "Invalid token", null);
        }



	@GetMapping("/findall/{showName}")
	public Response findAll(@PathVariable String showName)
	{	
		Response findAllSeason = seasonservice.findAllSeason(showName);
		
		return findAllSeason;
	}

	
	
	@GetMapping("/findbyid/{showName}/{id}")
	public Response findById(@PathVariable String showName,@PathVariable String id)
	{
		Response findByIdSeason = seasonservice.findByIdSeason(showName, id);
		return findByIdSeason;
	}
	
	
	
	@PutMapping("/update/{id}")
	public Response updateSeason(@RequestBody Season season, @PathVariable String id,@RequestParam(name = "token", defaultValue = "null") String token)throws Exception
    {
        if (tokenService.validateToken(token)) {
            if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
                return seasonservice.updateseason(season, id);
            }
            return new Response(401, "Only admin can add", null);
        }
        return new Response(401, "Invalid token", null);
    }

	
	
	
	@DeleteMapping("/delete/{showName}/{ids}")
	public Response deleteSeason(@PathVariable String showName,  @PathVariable String ids,@RequestParam(name = "token", defaultValue = "null") String token)throws Exception
    {
        if (tokenService.validateToken(token)) {
            if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
                return seasonservice.deleteSeason(showName, ids);
            }
            return new Response(401, "Only admin can add", null);
        }
        return new Response(401, "Invalid token", null);
        }
	

	
			
	
	
}
