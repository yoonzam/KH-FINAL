package com.kh.eatsMap.index.model.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.index.model.repository.FollowRepository;
import com.kh.eatsMap.index.model.repository.IndexFileRepository;
import com.kh.eatsMap.index.model.repository.IndexRepository;
import com.kh.eatsMap.index.model.repository.ReviewRepository;
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
	
	@Autowired
	private final IndexFileRepository fileRepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	
	


	@Override
	public List<Review> findReviewByHashtag(Member member) {

		//내가 좋아요 한 리뷰 리스트 
		List<Review> likeList = reviewRepository.findReviewByLike(1);
//
//		if(likeList != null) {
//			Review maxCategory = new Review();
//			for (int i = 0; i < likeList.size(); i++) {
//				maxCategory = reviewRepository.findFirstByIdOrderByCategoryDesc(likeList.get(i).getId());
//			}
//		}
		


		
		
		
		return null;
	}


	
	
	//멤버테이블에 로케이션값 저장 
	@Override
	public void updateLocation(Member member, GeoJsonPoint location) {
		member.setLocation(location);
		indexRepository.save(member);
	}


	
	
	
//////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	//위치반경에 따른 잇친픽 리뷰리스트 출력 	
	@Override
	public List<Review> localReview(Member member) {

		
		//내 반경 5키로 미터 이내의 모든 식당 리뷰 조회
		List<Review> locationReviewList = 
				reviewRepository.findByLocationNear(
//						member.getLocation(), new Distance(5, Metrics.KILOMETERS));
						new Point(127.0956659043071, 37.546965436775125), new Distance(5, Metrics.KILOMETERS));
		
		// 내가 팔로우한 사람
		List<Follow> followList = followRepository.findFollowByMemberId(member.getId());
		
		//교집합 합칠 리스트 
		List<Review> locationFollowReviewList = new ArrayList<Review>(); 
		
		for (int i = 0; i < locationReviewList.size(); i++) {
			for (int j = 0; j < followList.size(); j++) {
				String locationMemberId = locationReviewList.get(i).getMemberId().toString();
				String followMemberId = followList.get(j).getFollowingId().toString();
				
				if(locationMemberId.equals(followMemberId)) {
					locationFollowReviewList.add(locationReviewList.get(i));
					
				System.out.println(locationReviewList.get(i).getAddr());
			}
		}	
	}
		
		
		return locationFollowReviewList;
	}
	
	

	
	
	
	
	//리뷰 검색 
	@Override
	public List<Review> searchReview(String keyword, String[] category, String[] hashtag) {
		
		List<Review> searchKeyword = new ArrayList<Review>();
		if(!keyword.equals("")) {
			searchKeyword = reviewRepository.findReviewByResNameContaining(keyword);
		}
		
		List<Review> searchCategory = new ArrayList<Review>();
		if(category.length > 0) {
			for (int i = 0; i < category.length; i++) {
				searchCategory = reviewRepository.findReviewByCategoryLike(category[i]);
			}
		}
		
		List<Review> searchHashtag= new ArrayList<Review>();
		if(hashtag.length > 0) {
			for (int i = 0; i < hashtag.length; i++) {
				searchHashtag = reviewRepository.findReviewByHashtagLike(hashtag[i]);
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


	//파일 
	@Override
	public List<Fileinfo> findFiles(Object id) {
		// TODO Auto-generated method stub
		return fileRepository.findByTypeId(id);
	}





}