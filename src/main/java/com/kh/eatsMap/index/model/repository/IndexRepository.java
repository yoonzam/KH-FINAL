package com.kh.eatsMap.index.model.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.timeline.model.dto.Review;

public interface IndexRepository extends MongoRepository<Member, String>{

	
//	List<Review> findReview(String keyword, String[] category, String[] hashtag);

}
