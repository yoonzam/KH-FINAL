package com.kh.eatsMap.map.model.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.map.model.dto.Map;

public interface myMapRepository extends MongoRepository<Map, String>{

	Map findByMemberId(ObjectId memberId);

	

}
