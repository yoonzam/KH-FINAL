package com.kh.eatsMap.index.model.repository;

import java.util.List;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.kh.eatsMap.member.model.dto.Follow;
import com.kh.eatsMap.timeline.model.dto.Review;

public interface ReviewRepository extends MongoRepository<Review, String>{

	

	//리뷰 검색 
	@Query("{ 'resName' : ?0 }")	//정규식 문제있음
	List<Review> findReviewByResNameLike(String keyword);
	
	List<Review> findReviewByCategoryLike(String string);
	
	List<Review> findReviewByHashtagLike(String string);

	//위치 
	List<Review> findByLocationNear(Point location, Distance distance);	//문제있음 

	//해시
	@Query("{ 'like' : ?0 }")		
	List<Review> findReviewByLike(int like);


}
