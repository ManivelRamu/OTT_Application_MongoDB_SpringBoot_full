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

import com.tel.ott.application.collection.Shows;
import com.tel.ott.application.response.Response;
import com.tel.ott.application.service.ShowService;
import com.tel.ott.application.service.TokenService;



@RestController
@RequestMapping("/show")
public class ShowController {
	
	
	@Autowired
    private ShowService showservice;
    @Autowired
    TokenService tokenService;
    @PostMapping("/save")
    public Response save(@RequestBody Shows show,@RequestParam(name = "token", defaultValue = "null") String token)throws Exception
    {
            if (tokenService.validateToken(token)) {
                if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
                    return showservice.save(show);
                }
                return new Response(401, "Only admin can add", null);
            }
            return new Response(401, "Invalid token", null);
    }
	
	
	@GetMapping("/findall")
	public Response findAll()
	{	
		return showservice.findAll();
	}

	
	
	@GetMapping("/findbyid/{id}")
	public Response findById(@PathVariable String id)
	{
		return showservice.findById(id);
	}
	
	
	@PutMapping("/update/{id}")
	public Response updateShow(@RequestBody Shows show,@PathVariable String id, @RequestParam(name = "token", defaultValue = "null") String token)throws Exception
{
        if (tokenService.validateToken(token)) {
            if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
                return showservice.updateSHow(show, id);
            }
            return new Response(401, "Only admin can add", null);
        }
        return new Response(401, "Invalid token", null);
}

	
	
	@DeleteMapping("/delete/{id}")
	public Response deleteShow(@PathVariable String id, @RequestParam(name = "token", defaultValue = "null") String token) throws Exception
	{
		if (tokenService.validateToken(token)) {
			if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
				return showservice.deleteShow(id);
			}
			return new Response(401, "Only admin can add", null);
		}
		return new Response(401, "Invalid token", null);
		
	}
}
	