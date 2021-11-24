package com.kh.eatsMap.index.model.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.timeline.model.dto.Review;

public interface IndexService {
	
//	//member 테이블에 location 추가 
//	void updateLocation(String memberId, GeoJsonPoint location);
//
//	//해쉬태그 추천 리스트 
//	List<Review> findReviewByHashtag(String location);

	//위치기반 잇친픽 리뷰리스트 출력 
//	List<Review> locationList(String memberid, GeoJsonPoint location) throws Exception;

	//리뷰 검색 
	List<Review> searchReview(String keyword, String[] category, String[] hashtag);





}
