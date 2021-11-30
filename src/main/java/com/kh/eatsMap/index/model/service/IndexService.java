package com.kh.eatsMap.index.model.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.timeline.model.dto.Review;


public interface IndexService {
	
	//멤버테이블에 위치값 저장 
	void updateLocation(Member member, GeoJsonPoint location);

	//위치기반 잇친픽 리뷰리스트
	List<Review> localReview(Member member);

	//해쉬태그 기반 리뷰리스트 
	List<Review> findReviewByHashtag(Member member);
	
	//리뷰 검색 
	List<Review> searchReview(String keyword, String[] category, String[] hashtag);

	
	
	
	
	
//	List<Fileinfo> searchReviewImg(String searchReviewListId);

//	String[] searchReviewd(String reviewId);

//	List<Fileinfo> searchReviewImg(String[] searchReviewListId);


}
