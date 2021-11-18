package com.kh.eatsMap.index.model.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.kh.eatsMap.timeline.model.dto.Review;


@Service
public interface IndexService {

	List<Review> findByHashtag(ObjectId id);

//	List<Review> findReview(String keyword, String[] category, String[] hashtag);




}
