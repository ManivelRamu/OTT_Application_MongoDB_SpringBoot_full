package com.tel.ott.application.collection;
import com.opencsv.bean.CsvBindByName;


import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("Movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
	@Id
	@CsvBindByName
	private String id;
	private  Date createdAt;
	private  Date updatedAt;
	private  Date deletedAt;
	
	//@Indexed(unique=true)
	@CsvBindByName
	private String movieName;
	@CsvBindByName
	private String movieDirector;
	@CsvBindByName
	private String movieRating;
	private byte[] poster;
	@CsvBindByName
	private String year;
	private boolean isActive;
}
	