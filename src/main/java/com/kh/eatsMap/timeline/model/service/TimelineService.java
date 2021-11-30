package com.kh.eatsMap.timeline.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eatsMap.common.util.PageObject;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.timeline.model.dto.Review;

public interface TimelineService {

	void insertReview(Review review, double latitude, double longitude, List<MultipartFile> photos, Member member);

	List<Review> findAllReviews();
	
	List<Review> findAllReviews(PageObject pageObject, Member member);
	
	List<HashMap<String, Object>> findReviewsForPaging(PageObject pageObject, Member member);

	Map<String, Object> findReviewById(String id, Member member);

	void editReview(Review review, String reviewId, Member member, String latitude, String longitude,
			List<MultipartFile> photos, List<String> hdPhotos);

	void deleteReview(String reviewId, Member member);

	void saveLike(String revId, Member member);

	void deleteLike(String revId, Member member);

	List<Review> searchReview(String keyword, String[] category, String[] hashtag);

}
