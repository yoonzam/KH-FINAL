package com.kh.eatsMap.index.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.myeats.model.dto.Like;

public interface IndexLikeRepository extends MongoRepository<Like, String>{

}
