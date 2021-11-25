package com.kh.eatsMap.timeline.model.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.timeline.model.dto.Review;

public interface TimelineService {

	void insertReview(Review review, List<MultipartFile> photos);

	List<Review> findAllReviews();

	List<Fileinfo> findFiles(ObjectId id);

}
