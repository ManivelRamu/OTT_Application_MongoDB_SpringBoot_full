package com.tel.ott.application.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tel.ott.application.collection.Movie;

public interface MovieRepository extends MongoRepository<Movie, String> {
	@Query(value="{'isActive':true}")
	Page<Movie> findAllByIsActive(Pageable paging);

}
