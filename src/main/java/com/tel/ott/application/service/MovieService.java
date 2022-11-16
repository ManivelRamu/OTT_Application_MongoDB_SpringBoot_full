package com.tel.ott.application.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.CsvToBeanBuilder;
import com.tel.ott.application.collection.Movie;
import com.tel.ott.application.repository.MovieRepository;
import com.tel.ott.application.response.Response;

@Service
public class MovieService {
	
	@Autowired 
	MovieRepository movieRepository;
	
	public Response saveMovie(Movie movie)
	{
		String movieName= movie.getMovieName();
		List<Movie> findAllMovies=movieRepository.findAll();
		boolean isPresent=false;
		for(Movie movies:findAllMovies)
		{
			if(movies.getMovieName().equals(movieName))
			{
				isPresent=true;
			}
		}
		
		if(!isPresent)
		{
			movie.setCreatedAt(new Date());
			movie.setActive(true);
			movie.setUpdatedAt(new Date());
			Movie movieDetails = movieRepository.save(movie);
			 return new Response(200,"Movie added successfully", movieDetails);
		}

		return new Response(400,"Movie already present", null);
	}
	public List<Movie> findAllMovies(Integer pageNo, Integer pageSize, String sortBy) 
	{
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
		Page<Movie> pagedResult = movieRepository.findAllByIsActive(paging);
		List<Movie> list = pagedResult.getContent();
		return list;
	}
	
	public Response updateMovie(String id,Movie movie)
	{
		Optional<Movie>	findById=movieRepository.findById(id);
		if(findById.isPresent())
		{
			Movie movie2=findById.get();
			movie2.setMovieName(movie.getMovieName());
			movie2.setMovieDirector(movie2.getMovieDirector());
			movie2.setMovieRating(movie.getMovieRating());
			movie2.setYear(movie.getYear());
			movie2.setUpdatedAt(new Date());
			movie2.setActive(true);
			movieRepository.save(movie2);
			return new Response(200,"Movie updated successfully", movie2);

		}
		return new Response(401,"Movie not found", null);
	}
	public Response getMovieById(String id)
	{
		Optional<Movie>	findById=movieRepository.findById(id);
		if(findById.isPresent())
		{
			Movie movie= findById.get();
			return new Response(200,"Movie found", movie);
		}
		
			return new Response(401,"Movie not found", null);
	}
	public Response softDelete(String id)
	{
		Optional<Movie> movie = movieRepository.findById(id);
		if (!movie.isPresent()){
			return new Response(401,"Movie not found", null);

		}
		
		else {
			movie.get().setDeletedAt(new Date());
			movie.get().setActive(false);
			movieRepository.save(movie.get());
			return new Response(200,"Movie deleted successfully", null);
		}
	}
	
	public Response imageUpload(MultipartFile file, String id)
	{
		Movie movie = new Movie(); 
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String extension= FilenameUtils.getExtension(fileName);
		List<String> list = Arrays.asList("jpg" ,"jpeg", "jpe", "jif" ,"jfif" ,"jfi"
				  ,"png" ,"gif" ,"webp","tiff","tif" ,"psd","raw" ,"arw", "cr2"
				  ,"nrw","k25","bmp","dib","heif",
				  "heic","ind","indd","indt","jp2","j2k","jpf","jpx","jpm","mj2");
		if(!(list.contains(extension))) 
		{
			return new Response(400,"upload Valid Image Format",null );
		}
		try 
		{ 
			Optional<Movie> findById = movieRepository.findById(id);
			if (findById.isPresent())
			{
				Movie movie2 = findById.get();
				movie2.setPoster(file.getBytes()); 
				movieRepository.save(movie2);
			}
		} 
		catch(IOException e)
		{ 
			return new Response(400,"IO Exception found, file must be in image format",null );
		}
		return new Response(200,"Imaged uploaded successfully",null );
	}
	
}

