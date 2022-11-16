package com.tel.ott.application.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.client.model.Updates;
import com.tel.ott.application.collection.Subscriptions;
import com.tel.ott.application.collection.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	  @Query(value = "{'email': ?0}")
	    Optional<User> getUserByEmail(String email);
	  
	  @Query(value = "{'username':{$regex:'^?0'}}")
	  List<User> findAllByName(String name);

}
