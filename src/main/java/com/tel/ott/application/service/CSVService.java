package com.tel.ott.application.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.CsvToBeanBuilder;
import com.tel.ott.application.collection.Movie;
import com.tel.ott.application.collection.Subscriptions;
import com.tel.ott.application.collection.User;
import com.tel.ott.application.repository.MovieRepository;
import com.tel.ott.application.repository.SubscriptionRepository;
import com.tel.ott.application.repository.UserRepository;
import com.tel.ott.application.response.Response;

@Service
public class CSVService 
{
	@Autowired
	MovieRepository movieRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	SubscriptionRepository subscriptionRepository;
	
	private  String[] values;
	private  transient CSVParser parser;
	private static String csvExtension = "csv";
	
	public List<Movie> saveAll(MultipartFile file) throws IOException
	{
		Movie movie=new Movie();
			
		Reader reader = new InputStreamReader(file.getInputStream());
		@SuppressWarnings("unchecked")
		List<Movie> csvRecords = new CsvToBeanBuilder(reader).withType(Movie.class).build().parse();
		List<Movie> movies = new ArrayList<Movie>();
		csvRecords.forEach((record) -> {
			String movieName = record.getMovieName();
			List<Movie> findAllMovies = movieRepository.findAll();
			boolean isPresent = false;
			for (Movie movies1 : findAllMovies) {
				if (movies1.getMovieName().equals(movieName)) {
					isPresent = true;
				}
			}
			if (!isPresent)
			{
				movie.setMovieName((record.getMovieName()));
				movie.setMovieDirector(record.getMovieDirector());
				movie.setMovieRating(record.getMovieRating());
				movie.setYear(record.getYear());
				movie.setActive(true);
				movie.setCreatedAt(new Date());
				movie.setUpdatedAt(new Date());
				movies.add(movie);
			}
		});
		List<Movie> records = movieRepository.saveAll(movies);
		return records;
	}
	

	public List<Movie> update(MultipartFile file) throws IOException {
        Movie movie=new Movie();
        
        Reader reader = new InputStreamReader(file.getInputStream());
        List<Movie> csvRecords = new CsvToBeanBuilder(reader).withType(Movie.class).build().parse();
        List<Movie> movies = new ArrayList<Movie>();
        csvRecords.forEach((record) -> {
            String id = record.getId();
            Optional<Movie>    findById=movieRepository.findById(id);
            if(findById.isPresent())
            {
                Movie movie2 = findById.get();
                movie2.setMovieName((record.getMovieName()));
                movie2.setMovieDirector(record.getMovieDirector());
                movie2.setMovieRating(record.getMovieRating());
                movie2.setYear(record.getYear());
                movie2.setActive(true);
                movie2.setUpdatedAt(new Date());
                movies.add(movie2);
            }
            });
        
        List<Movie> records = movieRepository.saveAll(movies);
        return records;
        }


		/*
		 * public Response saveAllUsers(MultipartFile file) throws IOException {
		 * 
		 * PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); Reader reader
		 * = new InputStreamReader(file.getInputStream());
		 * 
		 * @SuppressWarnings("unchecked") List<User> csvRecords = new
		 * CsvToBeanBuilder(reader).withType(User.class).build().parse(); List<User>
		 * users = new ArrayList<User>(); csvRecords.forEach((record) -> { User user=new
		 * User(); user.setEmail(record.getEmail());
		 * user.setUsername(record.getUsername()); user.setRole(record.getRole());
		 * user.set_active(false); Optional<User> existingUser =
		 * userRepository.getUserByEmail(record.getEmail());
		 * if(!existingUser.isPresent()){ String encode =
		 * passwordEncoder.encode(record.getPassword()); user.setPassword(encode);
		 * users.add(user); }
		 * 
		 * });
		 * 
		 * 
		 * if(users.size()>0) userRepository.saveAll(users); else return new
		 * Response(401, "No valid users", null); return null; }
		 * 
		 */
	
	public Response saveUsers(MultipartFile file) { 
		List<User> users = new ArrayList<>();
		 try 
		    
		    (
		InputStream is=file.getInputStream();
	   BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));		
	    @SuppressWarnings("deprecation")
		CSVParser csvParser = new CSVParser(fileReader,
	                                         CSVFormat.DEFAULT
	                                         .withFirstRecordAsHeader()
	                                         .withIgnoreHeaderCase()
	                                         .withTrim());) 
	    
	    {
			 
		  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	      
	      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
	      for (CSVRecord csvRecord : csvRecords) 
		    {
	    	  User user=new User();
	    	  Optional<User> existingUser = userRepository.getUserByEmail(csvRecord.get("email"));
				 if(!existingUser.isPresent()){
			            user.setEmail(csvRecord.get("email"));
			            user.setUsername(csvRecord.get("username"));
					 	String encode = passwordEncoder.encode(csvRecord.get("password"));
			            user.setPassword(encode);
			            user.setRole(csvRecord.get("role"));
			            if(!user.getRole().equalsIgnoreCase("admin"))
			            {
			            	String subType=csvRecord.get("subscription.type");
			            	Optional<Subscriptions>	existing=subscriptionRepository.findAllByType(subType);
			            	if(existing.isPresent())
			            	{
			            		user.setSubscription(existing.get());
			            		user.setSub_startDate(LocalDate.now());
			            		user.set_active(true);
			            		users.add(user);
			            	}
			            	else if(!existing.isPresent() && subType!=null)
			            	{
			            		Subscriptions newSubscription=new Subscriptions();
			            		newSubscription.setType(subType);
			            		List<String> features = Arrays.asList(csvRecord.get("subscription.features").split(",", -1));
			            		newSubscription.setFeatures(features);
			            		List<String> screens = Arrays.asList(csvRecord.get("subscription.screens").split(",", -1));
			            		newSubscription.setScreens(screens);
			            		newSubscription.setActive(true);
			            		newSubscription.setPeriod(Long.parseLong(csvRecord.get("subscription.period")) );
			            		subscriptionRepository.save(newSubscription);
			            		
			            		user.setSubscription(newSubscription);
			            		user.setSub_startDate(LocalDate.now());
			            		user.set_active(true);
			            		users.add(user);
			            	}
			            }
			            else
			            	{	user.set_active(true);
			            		users.add(user);
			            	}
			           }
				 else
					 continue;
	    	 
		    }
	    }
		 catch (IOException e) 
		    
		    {
		      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		    }
		 
		 if(users.size()>0)
				{
			 		userRepository.saveAll(users);
			 		return new Response(200, "users saved sucessfully", users);
				}
			else
				return new Response(401, "No valid users", null);
	
   }
}
