package com.kh.eatsMap.timeline.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.myeats.model.dto.PageObject;
import com.kh.eatsMap.timeline.model.dto.Review;

public interface TimelineService {

	void insertReview(Review review, double latitude, double longitude, List<MultipartFile> photos, Member member);

	List<Review> findAllReviews();
	
	List<Review> findAllReviews(PageObject pageObject);
	
	List<HashMap<String, Object>> findReviewsForPaging(PageObject pageObject);

	Map<String, Object> findReviewById(String id);

	void editReview(Review review, String reviewId, Member member, String latitude, String longitude,
			List<MultipartFile> photos, List<String> hdPhotos);

	void deleteReview(String reviewId, Member member);

}
