package com.kh.eatsMap.index.model.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.kh.eatsMap.member.model.dto.Follow;
import com.kh.eatsMap.timeline.model.dto.Review;

public interface ReviewRepository extends MongoRepository<Review, String>{

	

	//리뷰 검색 
	List<Review> findReviewByResNameContaining(String keyword);
	
	List<Review> findReviewByCategoryLike(String[] category);
	
	List<Review> findReviewByHashtagLike(String[] hashtag);

	//위치
	List<Review> findByLocationNear(Point location, Distance distance);

	//해시
	List<Review> findReviewByLike(int like);

	List<Review> findByMemberId(ObjectId id);

	
//	Review findFirstByIdOrderByCategoryDesc(ObjectId id);

//	@Query(value = "{category : ?0}", count = true)
//	Integer findReviewCountByCategory;


}
