package com.tel.ott.application.collection;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

	private String id;
	@CsvBindByName
	private String username;
	@CsvBindByName
	private String password;
	@CsvBindByName
	private String email;
	@CsvBindByName
	private String role;
	private String sub_type;
	private Subscriptions subscription;
	private LocalDate sub_startDate;
	private LocalDate sub_endDate;
	private boolean is_active;
}
