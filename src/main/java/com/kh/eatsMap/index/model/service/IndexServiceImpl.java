package com.kh.eatsMap.index.model.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import com.kh.eatsMap.index.model.repository.FollowRepository;
import com.kh.eatsMap.index.model.repository.IndexRepository;
import com.kh.eatsMap.index.model.repository.ReviewRepository;
import com.kh.eatsMap.map.model.dto.Map;
import com.kh.eatsMap.map.model.repository.MapRepository;
import com.kh.eatsMap.member.model.dto.Follow;
import com.kh.eatsMap.member.model.dto.Member;

import com.kh.eatsMap.timeline.model.dto.Review;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService{
	
	@Autowired
	private final IndexRepository indexRepository;

	@Autowired
	private final FollowRepository followRepository;
	

	@Autowired
	private final ReviewRepository reviewRepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

//
//
//	//member 테이블에 location 추가 
//	@Override
//	public void updateLocation(String memberId, GeoJsonPoint location) {
//		Member member = indexRepository.findMemberById(memberId);
//		member.setLocation(location);
//	}
//
//	
//	
//	
//////////////////////////////////////////////////////////////////////////	
//	
////	--hashtag픽(잇친아님) 
////	세션에서 현재 접속한 멤버의 id값 받아옴 
////	멤버의 like컬럼값이 1인 리뷰중(=내가 찜한 리뷰중) 높은 빈도의 hashtag 2개 선별
////	그 2개의 hashtag가 포함된 모든 리뷰 출력
//	
//	@Override
//	public List<Review> findReviewByHashtag(String memberid) {
////		멤버의 like컬럼값이 1인 리뷰중(=내가 찜한 리뷰중)
//		List<Review> likedList = reviewRepository.findReviewByLike(memberid, 1);
//
////		높은 빈도의 hashtag 2개 선별		
//
//		
//		return null;
//	}
//
//	
//	
	
	
	
	
////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	
	
	
//위치반경에 따른 잇친픽 리뷰리스트 출력 	
//	@Override
//	public List<Review> locationList(String memberid, GeoJsonPoint location) throws Exception {
//
//		// 내가 팔로우한 사람의 follow테이블 고유키 
//		List<Follow> followList = followRepository.findFollowByMemberId(memberid);
//		
//		// 내가 팔로우한 사람의 memberId (followingId) 리스트 : followingList
//		List<String> followingIdList = new LinkedList<String>();
//		
//		String followingId;
//		String reviewMemberId;
//		
//		for (Follow follow : followList) {
//			followingId = follow.getFollowingId().toString();
//			followingIdList.add(followingId);	//내가 팔로우한 사람의 memberId모음 
//		}
//		

		
		
		

		//내 위치 반경 5키로미터 내 식당리뷰
//		List<Review> locationReviewList = 
//				reviewRepository.findByLocationNear(location, new Distance(0.05, Metrics.KILOMETERS)); //에러발생 
//				
//
//		List<String> locationReviewIdList = new LinkedList<String>();
//		
//		for (Review review : locationReviewList) {
//			reviewMemberId = review.getMemberId().toString();
//			locationReviewIdList.add(reviewMemberId);	//내가 팔로우한 사람의 memberId모음 
//		}
//		
//		List<Review> locationFollowingReviewList = new LinkedList<Review>();
//		
//		
//		for (int i = 0; i < followingIdList.size(); i++) {
//			for (int j = 0; j < locationReviewIdList.size() ; j++) {	
//				followingId = followingIdList.get(i);
//				reviewMemberId = locationReviewIdList.get(j);	////내가 follow하는 사람의 ObjectId List들
//				
//				if( followingId.equals(reviewMemberId) ) {//일치여부 확인후
//					locationFollowingReviewList.add(i, Review);
//				}
//			}
//		}
//
//		return null;
//	}
//	
	
	
	
	
	
	//리뷰 검색 
	@Override
	public List<Review> searchReview(String keyword, String[] category, String[] hashtag) {
		
		List<Review> searchKeyword = new ArrayList<Review>();
		if(!keyword.equals("")) {
			searchKeyword = reviewRepository.findReviewByResNameLike(keyword);
		}
		
		List<Review> searchCategory = new ArrayList<Review>();
		if(category.length != 0) {
			for (int i = 0; i < category.length; i++) {
				searchCategory = reviewRepository.findReviewByCategoryLike(category[i]);
			}
		}
		
		List<Review> searchHashtag= new ArrayList<Review>();
		if(hashtag.length != 0) {
			for (int i = 0; i < hashtag.length; i++) {
				searchHashtag = reviewRepository.findReviewByCategoryLike(hashtag[i]);
			}
		}
		
		
		List<Review> searchReview_ = new ArrayList<Review>();
		searchReview_.addAll(searchKeyword);
		searchReview_.addAll(searchCategory);
		searchReview_.addAll(searchHashtag);		
		
		Set<Review> searchReviewSet = new HashSet<Review>(searchReview_);	//중복제거 
		
		List<Review> searchReview = new ArrayList<Review>(searchReviewSet);

		return searchReview;
	}







	

}

