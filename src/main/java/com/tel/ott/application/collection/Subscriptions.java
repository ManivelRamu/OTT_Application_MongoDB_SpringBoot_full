package com.tel.ott.application.collection;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("subscriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscriptions {
	
	private String id;
	private String type;
	private List<String> screens;
	private List<String> features;
	private long period;
	private boolean active;
}
