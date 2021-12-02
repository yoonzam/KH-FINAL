package com.kh.eatsMap.timeline.model.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.kh.eatsMap.timeline.model.dto.Review;

public interface TimelineRepository extends MongoRepository<Review, String>{

	List<Review> findByResNameOrderByIdAsc(String resName, Sort sort);

	List<Review> findByGroup(String group, Sort sort);

	List<Review> findByPrivacy(int privacy, Sort sort);
	
	List<Review> findByResNameIgnoreCaseContaining(String keyword);
	
	List<Review> findByCategoryLike(String[] category);
	
	List<Review> findByHashtagLike(String[] hashtag);

	List<Review> findByHashtagLikeAndPrivacy(String[] hashtag, int privacy);
	
	//테스트 쿼리
	List<Review> findByHashtagOrCategory(String[] hashtag, String string);

	List<Review> findByResNameLike(String string);




	











}
