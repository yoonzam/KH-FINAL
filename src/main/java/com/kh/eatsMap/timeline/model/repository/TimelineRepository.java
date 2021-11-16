package com.kh.eatsMap.timeline.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.timeline.model.dto.Review;

public interface TimelineRepository extends MongoRepository<Review, String>{

}
