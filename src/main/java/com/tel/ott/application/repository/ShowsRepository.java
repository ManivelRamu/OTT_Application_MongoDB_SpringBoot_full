package com.tel.ott.application.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tel.ott.application.collection.Shows;


public interface ShowsRepository extends MongoRepository<Shows, String> {
 
 List<Shows>findByShowNameContaining(String showName);
}
