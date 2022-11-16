package com.tel.ott.application.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

//import com.tataelxsi.OTTApplication.CmsModule.entity.Show;
import com.tel.ott.application.collection.Shows;



public interface ShowRepository extends MongoRepository<Shows, String> {
	
	//@Query(value = "{'showName': {$regex: '^?0'}}")
	public Optional<Shows> findByShowName(String string);
	
	@Query(value = "{'season.seasonName': {$regex: '^?0'}}")
	public Optional<Shows> findBySeasonName(String string);
	

}
