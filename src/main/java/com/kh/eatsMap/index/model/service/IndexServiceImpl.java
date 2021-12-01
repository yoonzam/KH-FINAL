package com.kh.eatsMap.index.model.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import com.kh.eatsMap.index.model.repository.IndexFileRepository;
import com.kh.eatsMap.index.model.repository.IndexFollowRepository;
import com.kh.eatsMap.index.model.repository.IndexLikeRepository;
import com.kh.eatsMap.index.model.repository.IndexRepository;
import com.kh.eatsMap.index.model.repository.ReviewRepository;
import com.kh.eatsMap.member.model.dto.Follow;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.myeats.model.dto.Like;
import com.kh.eatsMap.timeline.model.dto.Review;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService{
	
	@Autowired
	private final IndexRepository indexRepository;

	@Autowired
	private final IndexFollowRepository followRepository;
	
	@Autowired
	private final ReviewRepository reviewRepository;
	
	@Autowired
	private final IndexFileRepository fileRepository;
	
	@Autowired
	private final IndexLikeRepository likeRepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());


	
	@Override
	public Map<String,Object> findAllReview(Member member) {
		List<Review> reviews_ = reviewRepository.findByMemberId(member.getId());
		int[] cnt = hashtagCnt(reviews_);
		String[] hashtags = maxOfHashtag(cnt);
		List<Review> reviews = reviewRepository.findReviewByPrivacyAndHashtagLike(0,hashtags);
//		debug(reviews.toString());
		
		for (Review review : reviews) {
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
			if(files.size() > 0) review.setThumUrl(files.get(0).getDownloadURL());
		}
		reviews.toString();
		String[] hashTags = hashToString(hashtags);
		
//		logger.debug("hashtag!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!hashTags[0]" + hashTags[0]);
//		logger.debug("hashtag!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!hashTags[1]" + hashTags[1]);
		
		return Map.of("reviews", reviews, "maxHash1", hashTags[0], "maxHash2", hashTags[1]);
	}  
	
	public int[] hashtagCnt(List<Review> reviews) {
		
		int md01 = 0;
		int md02 = 0;
		int md03 = 0;
		int md04 = 0;
		int md05 = 0;
		int md06 = 0;
		int pr01 = 0;
		int pr02 = 0;
		int pr03 = 0;
		int pr04 = 0;
		int pr05 = 0;
	
		//이 로거 지우면 안나옴
		logger.debug(reviews.toString());
		for (Review review : reviews) {
			
//			logger.debug("hashtag" + review.getHashtag().toString());
			
			for (int i = 0; i < review.getHashtag().length; i++) {
				switch (review.getHashtag()[i]) {
				case "친근함":md01++; break;
				case "고급짐":md02++; break;
				case "가족":md03++; break;
				case "데이트":md04++; break;
				case "혼밥":md05++; break;
				case "회식":md06++; break;
				case "가성비":pr01++; break;
				case "가심비":pr02++; break;
				case "1~2만원대":pr03++; break;
				case "2~3만원대":pr04++; break;
				case "3만원 이상":pr05++; break;
				}
			}
		}
		int[] cntArr = { md01,md02,md03,md04,md05,md06,pr01,pr02,pr03,pr04,pr05 };
		int max = cntArr[0];	//제일 많이나온 해시태그 카운트 수 
		int maxIndex = 0;		//제일 많이나온 해시태그 인덱스 
		int max2 = cntArr[1];	//두번째로 많이나온 해시태그 카운트 수 
		int maxIndex2 = 0;		//두번째로 많이나온 해시태그 인덱스 
		
		for (int i = 0; i < cntArr.length; i++) {
			if (cntArr[i] > max) {
				max2 = max;
				max = cntArr[i];
				maxIndex = i;
			}else if(cntArr[i] > max2) {
				max2 = cntArr[i];
				maxIndex2 = i;
			}
		}
		
//		System.out.println("max : " + max);
//		System.out.println("maxIndex : " + maxIndex); 
//		System.out.println("max2 : " + max2);
//		System.out.println("maxIndex2 : " + maxIndex2); 
		
		int[] cnt = new int[]{maxIndex, maxIndex2};
		
		return cnt;
	}
	
	
	public String[] maxOfHashtag(int[] cnt){
		
		String hashtag1 = "";
		String hashtag2 = "";
		
		switch (cnt[0]) {
		case 0: hashtag1 = "md01"; break;
		case 1: hashtag1 = "md02"; break;
		case 2: hashtag1 = "md03"; break;
		case 3: hashtag1 = "md04"; break;
		case 4: hashtag1 = "md05"; break;
		case 5: hashtag1 = "md06"; break;
		case 6: hashtag1 = "pr01"; break;
		case 7: hashtag1 = "pr02"; break;
		case 8: hashtag1 = "pr03"; break;
		case 9: hashtag1 = "pr04"; break;
		case 10: hashtag1 = "pr05"; break;
		}
		
		switch (cnt[1]) {
		case 0: hashtag2 = "md01"; break;
		case 1: hashtag2 = "md02"; break;
		case 2: hashtag2 = "md03"; break;
		case 3: hashtag2 = "md04"; break;
		case 4: hashtag2 = "md05"; break;
		case 5: hashtag2 = "md06"; break;
		case 6: hashtag2 = "pr01"; break;
		case 7: hashtag2 = "pr02"; break;
		case 8: hashtag2 = "pr03"; break;
		case 9: hashtag2 = "pr04"; break;
		case 10: hashtag2 = "pr05"; break;
		}
		
		
		String[] hashtags = new String[]{hashtag1, hashtag2};
//		System.out.println("hashtags[0]"+ hashtags[0]);
//		System.out.println("hashtags[1]"+ hashtags[1]);
		return hashtags;
	}


	public String[] hashToString(String[] hashTags_){
		
		String hashtag1 = "";
		String hashtag2 = "";
		
		switch (hashTags_[0]) {
		case "md01": hashtag1 = "친근함"; break;
		case "md02": hashtag1 = "고급짐"; break;
		case "md03": hashtag1 = "가족"; break;
		case "md04": hashtag1 = "데이트"; break;
		case "md05": hashtag1 = "혼밥"; break;
		case "md06": hashtag1 = "회식"; break;
		case "pr01": hashtag1 = "가성비"; break;
		case "pr02": hashtag1 = "가심비"; break;
		case "pr03": hashtag1 = "1~2만원대"; break;
		case "pr04": hashtag1 = "2~3만원대"; break;
		case "pr05": hashtag1 = "3만원 이상"; break;
		}
		
		switch (hashTags_[1]) {
		case "md01": hashtag2 = "친근함"; break;
		case "md02": hashtag2 = "고급짐"; break;
		case "md03": hashtag2 = "가족"; break;
		case "md04": hashtag2 = "데이트"; break;
		case "md05": hashtag2 = "혼밥"; break;
		case "md06": hashtag2 = "회식"; break;
		case "pr01": hashtag2 = "가성비"; break;
		case "pr02": hashtag2 = "가심비"; break;
		case "pr03": hashtag2 = "1~2만원대"; break;
		case "pr04": hashtag2 = "2~3만원대"; break;
		case "pr05": hashtag2 = "3만원 이상"; break;
		}
		
		
		String[] hashtags = new String[]{hashtag1, hashtag2};
//		System.out.println("hashtags[0]"+ hashtags[0]);
//		System.out.println("hashtags[1]"+ hashtags[1]);
		return hashtags;
	}
	

/////////////////////////////////////////////////////////////////////////////////////////////	
	
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
		//내 반경 5키로 미터 이내의 공개된 모든 식당 리뷰 조회
		List<Review> locationReviewList = 
				reviewRepository.findByPrivacyAndLocationNear(0, member.getLocation(), new Distance(50, Metrics.KILOMETERS));
		
		for (Review review : locationReviewList) {
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
			if(files.size() > 0) review.setThumUrl(files.get(0).getDownloadURL());
		}
		locationReviewList.toString();
		
		return locationReviewList;
	}

	
	
	//리뷰 검색 
	@Override
	public List<Review> searchReview(String keyword, String[] category, String[] hashtag) {
		

		List<Review> searchKeyword = new ArrayList<Review>();
		List<Review> searchCategory = new ArrayList<Review>();
		List<Review> searchHashtag = new ArrayList<Review>();
		
		
		if(!keyword.equals("")) {
			searchKeyword = reviewRepository.findReviewByResNameContaining(keyword);
		}
		
		if(category.length > 0) {
			searchCategory = reviewRepository.findReviewByCategoryLike(category);
		}
			for (int i = 0; i < searchCategory.size(); i++) {
				System.out.println("카테고리  검색결과 : "+searchCategory.get(i).getResName());
				}
		//카테고리 검색 문제ㅇ 
			
			
			
		
		if(hashtag.length > 0) {
			searchHashtag = reviewRepository.findReviewByHashtagLike(hashtag);
		}
			for (int i = 0; i < searchHashtag.size(); i++) {
				System.out.println("해시 검색결과 : "+searchHashtag.get(i).getResName());			
				}

		
		List<Review> searchReview_ = new ArrayList<Review>();
		searchReview_.addAll(searchKeyword);
		searchReview_.addAll(searchCategory);
		searchReview_.addAll(searchHashtag);		
		
		Set<Review> searchReviewSet = new HashSet<Review>(searchReview_);	//중복제거 
		List<Review> searchReview = new ArrayList<Review>(searchReviewSet);
		
		for (Review review : searchReview) {
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
			if(files.size() > 0) review.setThumUrl(files.get(0).getDownloadURL());
		}
		searchReview.toString();
		return searchReview;
		
	}


/////////////////////////////////////////////////////////////////////////////////////////	


}