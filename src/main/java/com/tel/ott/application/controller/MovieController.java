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
import org.springframework.web.multipart.MultipartFile;

import com.tel.ott.application.collection.Movie;
import com.tel.ott.application.response.Response;
import com.tel.ott.application.service.CSVService;
import com.tel.ott.application.service.MovieService;
import com.tel.ott.application.service.TokenService;


@RestController
@RequestMapping("/movies")
public class MovieController {
	@Autowired
	MovieService movieService;
	@Autowired
	TokenService tokenService;
	@Autowired
	private CSVService csvservice;
	
	//adding movies(7)
	@PostMapping("/modify/add")
	public Response save(@RequestBody Movie movie,
			 @RequestParam(name = "token", defaultValue = "null") String token) throws Exception
	{
		if (tokenService.validateToken(token)) {
			if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
				return movieService.saveMovie(movie);
			}
			return new Response(401, "Only admin can add", null);
		}
		return new Response(401, "Invalid token", null);
		
	}
	//getAllMovies(9)
	@GetMapping("/allMovies")
	public Response findAll(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) 
	{
		List<Movie> list = movieService.findAllMovies(pageNo, pageSize, sortBy);
		return new Response(200, "List of movies found", list);
		
		
	}
	//get movie by passing id(10)
	@GetMapping("/get/moviesByid/{id}")
	  public Response getMovieById(@PathVariable("id") String id) 
	  {
		return movieService.getMovieById(id);
	  }
	
	 //update movies(8)
	@PutMapping("/modify/moviesByid/{id}")
	public Response updateMovie(@PathVariable("id") String id,@RequestBody Movie movie,
			@RequestParam(name = "token", defaultValue = "null") String token) throws Exception
	{
		if (tokenService.validateToken(token)) {
			if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
				return movieService.updateMovie(id,movie);
			}
			return new Response(401, "Only admin can add", null);
		}
		return new Response(401, "Invalid token", null);
		
	}
	//soft delete(11)
	@DeleteMapping("/modify/delete/{id}")
	public  Response softDeleteMovie(@PathVariable String id,
			@RequestParam(name = "token", defaultValue = "null") String token) throws Exception
	{
		if (tokenService.validateToken(token)) {
			if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
				return movieService.softDelete(id);
			}
			return new Response(401, "Only admin can delete", null);
		}
		return new Response(401, "Invalid token", null);
		
	}
	
	//image upload(12)
	@PutMapping("/modify/uploadImage")
	public Response uploadimage(@RequestParam("file") MultipartFile file,@RequestParam String id,@RequestParam(name = "token", defaultValue = "null") String token) throws Exception 
	{
		if (tokenService.validateToken(token)) {
			if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) {
				return movieService.imageUpload(file,id);
			}
			return new Response(401, "Only admin can add", null);
		}
		return new Response(401, "Invalid token", null);
	}
	//csv file upload
	@PostMapping("/CSVFileUpload")
	public Response saveCSVFile(@RequestParam("file") MultipartFile file,@RequestParam(name = "token", defaultValue = "null") String token) throws Exception 
	{
		if (tokenService.validateToken(token))
		{
			if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin")) 
			{
				List<Movie> list= csvservice.saveAll(file);
				return new Response(200, "Movie added successfully", list);
			}
			return new Response(401, "Only admin can add", null);
		}
		return new Response(401, "Invalid token", null);
	}
	
	@PostMapping("/modify/CSVFileUpload")
    public Response updateCSVFile(@RequestParam("file") MultipartFile file,@RequestParam(name = "token", defaultValue = "null") String token) throws Exception
    {
        if (tokenService.validateToken(token))
        {
            if (tokenService.getRoleFromToken(token).equalsIgnoreCase("admin"))
            {
                List<Movie> list=  csvservice.update(file);
                return new Response(200, "CSV File uploaded successfully", list);
            }
            return new Response(401, "Only admin can add", null);
        }
        return new Response(401, "Invalid token", null);
    }
}
