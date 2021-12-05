package com.kh.eatsMap.index.model.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.timeline.model.dto.Review;

public interface ReviewRepository extends MongoRepository<Review, String>{


	//리뷰 검색 
	List<Review> findByResNameIgnoreCaseContaining(String keyword);

	List<Review> findByHashtagLike(String[] hashtag);
	
	//위치
	List<Review> findByLocationNear(Point location, Distance distance);

	//해시
	List<Review> findReviewByLike(int like);

	List<Review> findByMemberId(ObjectId id);

	List<Review> findReviewByPrivacyAndHashtagLike(int i, String[] strings);

	List<Review> findByPrivacyAndLocationNear(int i, Point point, Distance distance);

	long count();




}
