package com.kh.eatsMap.timeline.model.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.kh.eatsMap.timeline.model.dto.Review;

public interface TimelineRepository extends MongoRepository<Review, String>{

	List<Review> findByResNameOrderByIdAsc(String resName, Sort sort);

}
