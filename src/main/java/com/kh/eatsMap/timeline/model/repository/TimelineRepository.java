package com.kh.eatsMap.timeline.model.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.kh.eatsMap.timeline.model.dto.Review;

public interface TimelineRepository extends MongoRepository<Review, String>{

	List<Review> findByResNameOrderByIdAsc(String resName, Sort sort);

	List<Review> findReviewByResNameContaining(String keyword);

	List<Review> findReviewByCategoryLike(String[] category);

	//테스트 쿼리
	List<Review> findByHashtagOrCategory(String[] hashtag, String string);

	List<Review> findByResNameLike(String string);

	List<Review> findByResNameLikeAndCategory(String string, String string2);
	
	List<Review> findByResNameLikeAndCategoryOrHashtag(String string2, String string, String[] hashtag);

	List<Review> findByResNameLikeAndHashtag(String string, String[] hashtag);

}
