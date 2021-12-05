package com.kh.eatsMap.index.model.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.common.util.PageObject;
import com.kh.eatsMap.index.model.repository.IndexFileRepository;
import com.kh.eatsMap.index.model.repository.IndexFollowRepository;
import com.kh.eatsMap.index.model.repository.IndexLikeRepository;
import com.kh.eatsMap.index.model.repository.IndexRepository;
import com.kh.eatsMap.index.model.repository.ReviewRepository;
import com.kh.eatsMap.member.model.dto.Follow;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.repository.FollowingRepository;
import com.kh.eatsMap.member.model.repository.MemberRepository;
import com.kh.eatsMap.myeats.model.dto.Like;
import com.kh.eatsMap.timeline.model.dto.Review;
import com.kh.eatsMap.timeline.model.repository.TimelineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService{
	
	@Autowired
	private final IndexRepository indexRepository;
	
	@Autowired
	private final ReviewRepository reviewRepository;
	
	@Autowired
	private final IndexFileRepository fileRepository;
	
	@Autowired
	private final IndexLikeRepository likeRepository;
	
	@Autowired
	private final IndexFollowRepository followRepository;
	
	@Autowired
	private final MongoTemplate mongoTemplate;
	Logger logger = LoggerFactory.getLogger(this.getClass());


	
	@Override
	public Map<String,Object> findAllReview(Member member) {
		List<Review> reviews_ = reviewRepository.findByMemberId(member.getId());
		int[] cnt = hashtagCnt(reviews_);
		String[] hashtags = maxOfHashtag(cnt);
		List<Review> hashReviews  = reviewRepository.findReviewByPrivacyAndHashtagLike(0,hashtags);
//		debug(reviews.toString());
		
		List<Review> reviews = new ArrayList<Review>();
		if(hashReviews.size() > 0) {
			for (int i = 0; i < hashReviews.size(); i++) {
				if (!(hashReviews.get(i).getMemberId().toString().equals(member.getId().toString())) ) {
					reviews.add(hashReviews.get(i));
				}
			}			
		}
		
		for (Review review : reviews) {
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
			if(files.size() > 0) review.setThumUrl(files.get(0).getDownloadURL());
		}
		reviews.toString();
		String[] hashTags = hashToString(hashtags);
	
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
		int max2 = cntArr[0];	//두번째로 많이나온 해시태그 카운트 수 
		int maxIndex2 = 0;		//두번째로 많이나온 해시태그 인덱스 
		
		for (int i = 0; i < cntArr.length; i++) {
			if (cntArr[i] > max) {
				max2 = max;
				max = cntArr[i];
				maxIndex2 = maxIndex;
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
		String[] hashtags = new String[cnt.length];

		for (int i = 0; i < cnt.length; i++) {

			switch (cnt[i]) {
			case 0: hashtags[i] = "md01"; break;
			case 1: hashtags[i] = "md02"; break;
			case 2: hashtags[i] = "md03"; break;
			case 3: hashtags[i] = "md04"; break;
			case 4: hashtags[i] = "md05"; break;
			case 5: hashtags[i] = "md06"; break;
			case 6: hashtags[i] = "pr01"; break;
			case 7: hashtags[i] = "pr02"; break;
			case 8: hashtags[i] = "pr03"; break;
			case 9: hashtags[i] = "pr04"; break;
			case 10: hashtags[i] = "pr05"; break;
			}
		}

		return hashtags;
	}


	public String[] hashToString(String[] hashTags_){
		
		String[] hashtags = new String[hashTags_.length];
		for (int i = 0; i < hashTags_.length; i++) {
			switch (hashTags_[i]) {
			case "md01": hashtags[i] = "친근함"; break;
			case "md02": hashtags[i] = "고급짐"; break;
			case "md03": hashtags[i] = "가족"; break;
			case "md04": hashtags[i] = "데이트"; break;
			case "md05": hashtags[i] = "혼밥"; break;
			case "md06": hashtags[i] = "회식"; break;
			case "pr01": hashtags[i] = "가성비"; break;
			case "pr02": hashtags[i] = "가심비"; break;
			case "pr03": hashtags[i] = "1~2만원대"; break;
			case "pr04": hashtags[i] = "2~3만원대"; break;
			case "pr05": hashtags[i] = "3만원 이상"; break;
			}
		}

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
	public List<HashMap<String, Object>> localReview(Member member) {
		List<HashMap<String, Object>> reviews = new ArrayList<HashMap<String,Object>>();
		
		//내 반경 5키로 미터 이내의 공개된 모든 식당 리뷰 조회
		List<Review> locationReviewList_ = 
				reviewRepository.findByPrivacyAndLocationNear(0, member.getLocation(), new Distance(50, Metrics.KILOMETERS));

		List<Review> locationReviewList = new ArrayList<Review>();
				
			if(locationReviewList_.size() > 0) {
				for (int i = 0; i < locationReviewList_.size(); i++) {
					if (!(locationReviewList_.get(i).getMemberId().toString().equals(member.getId().toString())) ) {
						locationReviewList.add(locationReviewList_.get(i));
					}
				}			
			}
			
		locationReviewList.toString();
		
		for (Review review : locationReviewList) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
			
			if(files.size() > 0) review.setThumUrl(files.get(0).getDownloadURL());
			
			map.put("reviewId", review.getId().toString());
			map.put("reviews", review);
			reviews.add(map);
		}
		return reviews;
	}



	@Override
	public List<Review> searchReview(String keyword, String[] area, String[] category, String[] hashtag, Member member,
			PageObject pageObject) {
		
		Query query = new Query();
		
		Optional<List<Follow>> followings = followRepository.findByMemberId(member.getId());
		List<String> followingIds = new ArrayList<String>();
		if(followings.isPresent()) for (Follow following : followings.get()) followingIds.add(following.getFollowingId().toString());
		
		Criteria followingCriteria = new Criteria();
		Criteria followingArr[] = new Criteria[followingIds.size()+1];
		if(followingIds.size() > 0) {
			for(int i = 0; i < followingIds.size()+1; i++){
				if(i < followingIds.size()) {
					followingArr[i] = Criteria.where("memberId").is(new ObjectId(followingIds.get(i))).and("privacy").is(1);	//팔로우공개
				} else {
					followingArr[i] = Criteria.where("privacy").is(0);//전체공개 
				}
			}
			query.addCriteria(followingCriteria.orOperator(followingArr));
		} else {
			query.addCriteria(Criteria.where("privacy").is(0));	//전체공개 
		}

		query.with(Sort.by(Sort.Direction.DESC, "id"));
		
		query.skip((pageObject.getPage()-1) * pageObject.getPerPageNum());
		System.out.println("몇 건씩 ? " + (int)pageObject.getPerPageNum());
		
		query.limit((int)pageObject.getPerPageNum());
		
		List<Review> reviews = mongoTemplate.find(query, com.kh.eatsMap.timeline.model.dto.Review.class);
		
		if(area.length < 17 || category.length < 8 || hashtag.length < 11) {
			//지역으로 검색
			String[] areaName = getAreaName(area);
			List<Review> searchArea = new ArrayList<Review>();
			if(areaName.length > 0) {
				query = new Query();
				Criteria criteria = new Criteria();
				Criteria areaArr[] = new Criteria[areaName.length];
				
				for(int i = 0; i < areaName.length; i++){
					String question = areaName[i];
					areaArr[i] = Criteria.where("addr").regex(question);
				}
				
				query.with(Sort.by(Sort.Direction.DESC, "id"));
				query.addCriteria(criteria.orOperator(areaArr));
				searchArea = mongoTemplate.find(query, Review.class, "review");
				reviews.retainAll(searchArea);
			}
			
			//카테고리로 검색
			List<Review> searchCategory = new ArrayList<Review>();
			if(category.length > 0) {
				query = new Query();
				Criteria criteria = new Criteria();
				Criteria categoryArr[] = new Criteria[category.length];
				
				for(int i = 0; i < category.length; i++){
					String question = category[i];
					categoryArr[i] = Criteria.where("category").regex(question);
				}
				
				query.with(Sort.by(Sort.Direction.DESC, "id"));
				query.addCriteria(criteria.orOperator(categoryArr));
				searchCategory = mongoTemplate.find(query, Review.class, "review");
				reviews.retainAll(searchCategory);
			}
			
			//해시태그로 검색
			List<Review> searchHashtag = new ArrayList<Review>();
			if(hashtag.length > 0) {
				searchHashtag = reviewRepository.findByHashtagLike(hashtag);
				reviews.retainAll(searchHashtag);
			}
			
			//키워드로 검색
			List<Review> searchKeyword = new ArrayList<Review>();
			if(!keyword.equals("")) {
				searchKeyword = reviewRepository.findByResNameIgnoreCaseContaining(keyword);
				reviews.retainAll(searchKeyword);
			}
		}
		
		//리뷰 가공
		List<Like> likes = likeRepository.findByMemberId(member.getId());
		for (Review review : reviews) {
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
			if(files.size() > 0) review.setThumUrl(files.get(0).getDownloadURL());
			for (Like like : likes) if(like.getRevId().equals(review.getId())) review.setLike(1);
			review.toString();
		}
		
		return reviews;
	}

	
	private String[] getAreaName(String areaNum[]) {
		String[] areaName = new String[areaNum.length];
		for (int i = 0; i < areaNum.length; i++) {
			switch (areaNum[i]) {
				case "02": areaName[i] = "서울"; break;
				case "032": areaName[i] = "인천"; break;
				case "031": areaName[i] = "경기"; break;
				case "033": areaName[i] = "강원"; break;
				case "044": areaName[i] = "세종"; break;
				case "043": areaName[i] = "충북"; break;
				case "041": areaName[i] = "충남"; break;
				case "042": areaName[i] = "대전"; break;
				case "062": areaName[i] = "광주"; break;
				case "063": areaName[i] = "전북"; break;
				case "061": areaName[i] = "전남"; break;
				case "053": areaName[i] = "대구"; break;
				case "054": areaName[i] = "경북"; break;
				case "055": areaName[i] = "경남"; break;
				case "052": areaName[i] = "울산"; break;
				case "051": areaName[i] = "부산"; break;
				case "064": areaName[i] = "제주"; break;
				default: areaName[i] = ""; break;
			}
		}
		return areaName;
	}

	@Override
	public long count(String keyword, String[] area, String[] category, String[] hashtag, Member member) {
		
		Query query = new Query();
		
		Optional<List<Follow>> followings = followRepository.findByMemberId(member.getId());
		List<String> followingIds = new ArrayList<String>();
		if(followings.isPresent()) for (Follow following : followings.get()) followingIds.add(following.getFollowingId().toString());
		
		Criteria followingCriteria = new Criteria();
		Criteria followingArr[] = new Criteria[followingIds.size()+1];
		if(followingIds.size() > 0) {
			for(int i = 0; i < followingIds.size()+1; i++){
				if(i < followingIds.size()) {
					followingArr[i] = Criteria.where("memberId").is(new ObjectId(followingIds.get(i))).and("privacy").is(1);	//팔로우공개
				} else {
					followingArr[i] = Criteria.where("privacy").is(0);//전체공개 
				}
			}
			query.addCriteria(followingCriteria.orOperator(followingArr));
		} else {
			query.addCriteria(Criteria.where("privacy").is(0));	//전체공개 
		}

		query.with(Sort.by(Sort.Direction.DESC, "id"));
		
		List<Review> reviews = mongoTemplate.find(query, com.kh.eatsMap.timeline.model.dto.Review.class);
		
		if(area.length < 17 || category.length < 8 || hashtag.length < 11) {
			//지역으로 검색
			String[] areaName = getAreaName(area);
			List<Review> searchArea = new ArrayList<Review>();
			if(areaName.length > 0) {
				query = new Query();
				Criteria criteria = new Criteria();
				Criteria areaArr[] = new Criteria[areaName.length];
				
				for(int i = 0; i < areaName.length; i++){
					String question = areaName[i];
					areaArr[i] = Criteria.where("addr").regex(question);
				}
				
				query.with(Sort.by(Sort.Direction.DESC, "id"));
				query.addCriteria(criteria.orOperator(areaArr));
				searchArea = mongoTemplate.find(query, Review.class, "review");
				reviews.retainAll(searchArea);
			}
			
			//카테고리로 검색
			List<Review> searchCategory = new ArrayList<Review>();
			if(category.length > 0) {
				query = new Query();
				Criteria criteria = new Criteria();
				Criteria categoryArr[] = new Criteria[category.length];
				
				for(int i = 0; i < category.length; i++){
					String question = category[i];
					categoryArr[i] = Criteria.where("category").regex(question);
				}
				
				query.with(Sort.by(Sort.Direction.DESC, "id"));
				query.addCriteria(criteria.orOperator(categoryArr));
				searchCategory = mongoTemplate.find(query, Review.class, "review");
				reviews.retainAll(searchCategory);
			}
			
			//해시태그로 검색
			List<Review> searchHashtag = new ArrayList<Review>();
			if(hashtag.length > 0) {
				searchHashtag = reviewRepository.findByHashtagLike(hashtag);
				reviews.retainAll(searchHashtag);
			}
			
			//키워드로 검색
			List<Review> searchKeyword = new ArrayList<Review>();
			if(!keyword.equals("")) {
				searchKeyword = reviewRepository.findByResNameIgnoreCaseContaining(keyword);
				reviews.retainAll(searchKeyword);
			}
		}
		int count = reviews.size();
		
		return count;
	}

}