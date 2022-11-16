package com.tel.ott.application.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tel.ott.application.collection.Subscriptions;

public interface SubscriptionRepository extends MongoRepository<Subscriptions, String>{

	@Query(value = "{'type': ?0}")
	Optional<Subscriptions> findAllByType(String sub_type);

}
