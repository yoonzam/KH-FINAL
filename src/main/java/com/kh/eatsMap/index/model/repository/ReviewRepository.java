package com.kh.eatsMap.index.model.repository;

import java.util.List;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.kh.eatsMap.timeline.model.dto.Review;

public interface ReviewRepository extends MongoRepository<Review, String>{

	
	List<Review> findReviewByLike(String memberid, int i);
	
	
	
	//리뷰 검색 
	List<Review> findReviewByCategoryLike(String string);
	
	List<Review> findReviewByHashtagLike(String string);

	List<Review> findReviewByLocationNear(Point location, Distance distance);
	
	@Query("{ 'resName' : ?0 }")	//정규식 문제있음
	List<Review> findReviewByResNameLike(String keyword);


}
