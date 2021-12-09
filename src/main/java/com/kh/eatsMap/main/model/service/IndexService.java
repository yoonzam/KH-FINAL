package com.kh.eatsMap.main.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.common.util.PageObject;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.timeline.model.dto.Review;


public interface IndexService {
	
	//멤버테이블에 위치값 저장 
	void updateLocation(Member member, GeoJsonPoint location);

	//위치기반 잇친픽 리뷰리스트
	List<HashMap<String, Object>> localReview(Member member);

	//해쉬태그 기반 리뷰리스트 
	Map<String,Object> findAllReview(Member member);

	//검색 
	List<Review> searchReview(String keyword, String[] area, String[] category, String[] hashtag, Member member,
			PageObject pageObject);

}
