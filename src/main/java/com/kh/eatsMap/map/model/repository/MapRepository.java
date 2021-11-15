package com.kh.eatsMap.map.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.map.model.dto.Map;

public interface MapRepository extends MongoRepository<Map, String>{

}
