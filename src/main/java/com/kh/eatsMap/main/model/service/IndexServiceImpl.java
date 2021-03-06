package com.kh.eatsMap.main.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.common.util.PageObject;
import com.kh.eatsMap.main.model.repository.IndexFileRepository;
import com.kh.eatsMap.main.model.repository.IndexFollowRepository;
import com.kh.eatsMap.main.model.repository.IndexLikeRepository;
import com.kh.eatsMap.main.model.repository.IndexRepository;
import com.kh.eatsMap.main.model.repository.ReviewRepository;
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
		
		List<Review> reviews = new ArrayList<Review>();
		if(hashReviews.size() > 0) {
			for (int i = 0; i < hashReviews.size(); i++) {
				if (!(hashReviews.get(i).getMemberId().toString().equals(member.getId().toString())) ) {
					reviews.add(hashReviews.get(i));
				}
			}			
		}
		
		//?????? ??????
		List<Like> likes = likeRepository.findByMemberId(member.getId());
		for (Review review : reviews) {
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
			if(files.size() > 0) review.setThumUrl(files.get(0).getDownloadURL());
			for (Like like : likes) if(like.getRevId().equals(review.getId())) review.setLike(1);
			review.toString();
		}
		
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
	
		//??? ?????? ????????? ?????????
		logger.debug(reviews.toString());
		for (Review review : reviews) {
			
			for (int i = 0; i < review.getHashtag().length; i++) {
				switch (review.getHashtag()[i]) {
				case "?????????":md01++; break;
				case "?????????":md02++; break;
				case "??????":md03++; break;
				case "?????????":md04++; break;
				case "??????":md05++; break;
				case "??????":md06++; break;
				case "?????????":pr01++; break;
				case "?????????":pr02++; break;
				case "1~2?????????":pr03++; break;
				case "2~3?????????":pr04++; break;
				case "3?????? ??????":pr05++; break;
				}
			}
		}
		int[] cntArr = { md01,md02,md03,md04,md05,md06,pr01,pr02,pr03,pr04,pr05 };
		int max = cntArr[0];	//?????? ???????????? ???????????? ????????? ??? 
		int maxIndex = 0;		//?????? ???????????? ???????????? ????????? 
		int max2 = cntArr[0];	//???????????? ???????????? ???????????? ????????? ??? 
		int maxIndex2 = 0;		//???????????? ???????????? ???????????? ????????? 
		
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
			case "md01": hashtags[i] = "?????????"; break;
			case "md02": hashtags[i] = "?????????"; break;
			case "md03": hashtags[i] = "??????"; break;
			case "md04": hashtags[i] = "?????????"; break;
			case "md05": hashtags[i] = "??????"; break;
			case "md06": hashtags[i] = "??????"; break;
			case "pr01": hashtags[i] = "?????????"; break;
			case "pr02": hashtags[i] = "?????????"; break;
			case "pr03": hashtags[i] = "1~2?????????"; break;
			case "pr04": hashtags[i] = "2~3?????????"; break;
			case "pr05": hashtags[i] = "3?????? ??????"; break;
			}
		}

		return hashtags;
	}
	

/////////////////////////////////////////////////////////////////////////////////////////////	
	
	//?????????????????? ??????????????? ?????? 
	@Override
	public void updateLocation(Member member, GeoJsonPoint location) {
		member.setLocation(location);
		indexRepository.save(member);
	}

//////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	//??????????????? ?????? ????????? ??????????????? ?????? 	
	@Override
	public List<HashMap<String, Object>> localReview(Member member) {
		List<HashMap<String, Object>> reviews = new ArrayList<HashMap<String,Object>>();
		
		//??? ?????? 5?????? ?????? ????????? ????????? ?????? ?????? ?????? ??????
		List<Review> locationReviewList_ = 
				reviewRepository.findByPrivacyAndLocationNear(0, member.getLocation(), new Distance(10, Metrics.KILOMETERS));

		List<Review> locationReviewList = new ArrayList<Review>();
				
			if(locationReviewList_.size() > 0) {
				for (int i = 0; i < locationReviewList_.size(); i++) {
					if (!(locationReviewList_.get(i).getMemberId().toString().equals(member.getId().toString())) ) {
						locationReviewList.add(locationReviewList_.get(i));
					}
				}			
			}
		
		locationReviewList.toString();
		
		//?????? ??????
		List<Like> likes = likeRepository.findByMemberId(member.getId());
		for (Review review : locationReviewList) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
			if(files.size() > 0) review.setThumUrl(files.get(0).getDownloadURL());
			for (Like like : likes) if(like.getRevId().equals(review.getId())) review.setLike(1);
			
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
		
		if(followingIds.size() > 0) {
			Criteria citeria = new Criteria();
			Criteria citeriaArr[] = new Criteria[followingIds.size()+2];
			
			for(int i = 0; i < followingIds.size()+2; i++){
				if(i < followingIds.size()) {
					citeriaArr[i] = Criteria.where("memberId").is(new ObjectId(followingIds.get(i))).and("privacy").is(1);
				} else if(i == followingIds.size()) {
					citeriaArr[i] = Criteria.where("memberId").is(member.getId());
				} else {
					citeriaArr[i] = Criteria.where("privacy").is(0);
				}
			}
			query.addCriteria(citeria.orOperator(citeriaArr));
	 	} else {
	 		Criteria citeria = new Criteria();
	 		Criteria citeriaArr[] = new Criteria[2];
	 		citeriaArr[0] = Criteria.where("memberId").is(member.getId());
	 		citeriaArr[1] = Criteria.where("privacy").is(0);
	 		query.addCriteria(citeria.orOperator(citeriaArr));
	 	}
		

		query.with(Sort.by(Sort.Direction.DESC, "id"));
		
		List<Review> reviews = mongoTemplate.find(query, com.kh.eatsMap.timeline.model.dto.Review.class);
		
		if(area.length < 17 || category.length < 8 || hashtag.length < 11) {
			//???????????? ??????
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
			
			//??????????????? ??????
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
			
			//??????????????? ??????
			List<Review> searchHashtag = new ArrayList<Review>();
			if(hashtag.length > 0) {
				searchHashtag = reviewRepository.findByHashtagLike(hashtag);
				reviews.retainAll(searchHashtag);
			}
		}
		
		//???????????? ??????
		List<Review> searchKeyword = new ArrayList<Review>();
		if(!keyword.equals("")) {
			searchKeyword = reviewRepository.findByResNameIgnoreCaseContaining(keyword);
			reviews.retainAll(searchKeyword);
		}
		
		pageObject.setPerPageNum(8);
		pageObject.setPerGroupPageNum(8);
		pageObject.setTotalRow(reviews.size());		
		long lastIdx = pageObject.getEndRow() < pageObject.getTotalRow() ? pageObject.getEndRow() : pageObject.getTotalRow();
		reviews = reviews.subList((int)pageObject.getStartRow()-1, (int)lastIdx);
		
		//?????? ??????
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
				case "02": areaName[i] = "??????"; break;
				case "032": areaName[i] = "??????"; break;
				case "031": areaName[i] = "??????"; break;
				case "033": areaName[i] = "??????"; break;
				case "044": areaName[i] = "??????"; break;
				case "043": areaName[i] = "??????"; break;
				case "041": areaName[i] = "??????"; break;
				case "042": areaName[i] = "??????"; break;
				case "062": areaName[i] = "??????"; break;
				case "063": areaName[i] = "??????"; break;
				case "061": areaName[i] = "??????"; break;
				case "053": areaName[i] = "??????"; break;
				case "054": areaName[i] = "??????"; break;
				case "055": areaName[i] = "??????"; break;
				case "052": areaName[i] = "??????"; break;
				case "051": areaName[i] = "??????"; break;
				case "064": areaName[i] = "??????"; break;
				default: areaName[i] = ""; break;
			}
		}
		return areaName;
	}

}