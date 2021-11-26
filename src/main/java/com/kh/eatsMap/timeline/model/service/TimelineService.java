package com.kh.eatsMap.timeline.model.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.timeline.model.dto.Review;

public interface TimelineService {

	void insertReview(Review review, double latitude, double longitude, List<MultipartFile> photos, Member member);

	List<Review> findAllReviews();

	Map<String, Object> findReviewById(String id);

}
