package com.kh.eatsMap.timeline.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eatsMap.common.code.ErrorCode;
import com.kh.eatsMap.common.exception.HandlableException;
import com.kh.eatsMap.common.util.FileUtil;
import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.common.util.PageObject;
import com.kh.eatsMap.member.model.dto.Follow;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.repository.FollowingRepository;
import com.kh.eatsMap.member.model.repository.MemberRepository;
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.myeats.model.dto.Like;
import com.kh.eatsMap.myeats.model.repository.GroupRepository;
import com.kh.eatsMap.myeats.model.repository.LikeRepository;
import com.kh.eatsMap.timeline.model.dto.Review;
import com.kh.eatsMap.timeline.model.repository.FileRepository;
import com.kh.eatsMap.timeline.model.repository.TimelineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimelineServiceImpl implements TimelineService{

	private final TimelineRepository timelineRepository;
	private final LikeRepository likeRepository;
	private final FileRepository fileRepository;
	private final MemberRepository memberRepository;
	private final FollowingRepository followingRepository;
	private final GroupRepository groupRepository;
	private final MongoTemplate mongoTemplate;

	@Override
	public void insertReview(Review review, double latitude, double longitude, List<MultipartFile> photos, Member member) {
		review.setLocation(new GeoJsonPoint(longitude, latitude));
		review.setMemberId(member.getId());
		if(review.getGroup().equals("my")) review.setGroup(null);
		
		review = timelineRepository.insert(review);
		
		FileUtil fileUtil = new FileUtil();
		for (MultipartFile photo : photos) {
			if(!photo.isEmpty()) {
				Fileinfo fileInfo = fileUtil.fileUpload(photo);
				fileInfo.setTypeId(review.getId());
				fileRepository.save(fileInfo);
			}
		}
	}

	@Override
	public List<Review> findAllReviews() {
		List<Review> reviews = timelineRepository.findByPrivacy(0, Sort.by(Sort.Direction.DESC, "id"));
		
		for (Review review : reviews) {
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
			if(files.size() > 0) review.setThumUrl(files.get(0).getDownloadURL());
			review.toString();
			System.out.println(review);
		}
		return reviews;
	}
	
	@Override
	public List<Review> findAllReviews(PageObject pageObject, Member member) {
		
		Optional<List<Follow>> followings = followingRepository.findByMemberId(member.getId());
		List<String> followingIds = new ArrayList<String>();
		if(followings.isPresent()) {
			for (Follow following : followings.get()) followingIds.add(following.getFollowingId().toString());
		}
		
		Query query = new Query();
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
		query.skip((pageObject.getPage()-1) * pageObject.getPerPageNum());
		query.limit((int)pageObject.getPerPageNum());
		List<Review> reviews = mongoTemplate.find(query, com.kh.eatsMap.timeline.model.dto.Review.class);
		List<Like> likes = likeRepository.findByMemberId(member.getId());
		
		for (Review review : reviews) {
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
			if(files.size() > 0) review.setThumUrl(files.get(0).getDownloadURL());
			
			//찜리스트
			for (Like like : likes) {
				if(like.getRevId().equals(review.getId())) review.setLike(1);
			}
			
			review.toString();
		}
		return reviews;
	}

	@Override
	public List<HashMap<String, Object>> findReviewsForPaging(PageObject pageObject, Member member) {
		List<HashMap<String, Object>> mapList = new ArrayList<>();
		List<Review> reviews = findAllReviews(pageObject, member);
		
		for (Review review : reviews) {
			HashMap<String, Object> hashmap = new HashMap<>();
			hashmap.put("review", review);
			hashmap.put("reviewId", review.getId().toString());
			mapList.add(hashmap);
		}
		return mapList;
	}
	
	@Override
	public Map<String, Object> findReviewById(String id, Member member) {
		Map<String, Object> map = new HashMap<>();
		
		//review
		Optional<Review> findReview = timelineRepository.findById(id);

		Review review = findReview.get();
		review.setMemberId(review.getMemberId());
		review.setMemberNick(memberRepository.findById(review.getMemberId().toString()).get().getNickname());
		
		//찜리스트
		List<Like> likes = likeRepository.findByMemberId(member.getId());
		for (Like like : likes) {
			if(like.getRevId().equals(review.getId())) review.setLike(1);
		}
		
		review.toString();
		map.put("review", review);
		
		//objectId
		map.put("reviewId", review.getId().toString());
		map.put("memberId", review.getMemberId().toString());
		map.put("myId", member.getId().toString());
		
		//잇친여부
		Follow follow = followingRepository.findOptionalByMemberIdAndFollowingId(member.getId(), review.getMemberId()).orElse(new Follow());
		map.put("follow", follow);
		
		//file
		List<Fileinfo> findFiles = fileRepository.findByTypeId(new ObjectId(id));
		List<String> files = new ArrayList<>();
		for (Fileinfo fileinfo : findFiles) {
			files.add(fileinfo.getDownloadURL());
		}
		map.put("files", files);
		return map;
	}
	
	@Override
	public void editReview(Review review, String reviewId, Member member, String latitude, String longitude,
			List<MultipartFile> photos, List<String> hdPhotos) {
		//수정자와 작성자가 다르면 예외 처리
		Review storedReview = timelineRepository.findById(reviewId).get();
		if(!storedReview.getMemberId().toString().equals(member.getId().toString())) throw new HandlableException(ErrorCode.AUTHENTICATION_FAILED_ERROR);

		review.setId(new ObjectId(reviewId));
		review.setMemberId(member.getId());
		review.setLocation(new GeoJsonPoint(Double.parseDouble(longitude), Double.parseDouble(latitude)));
		if(review.getGroup().equals("")) review.setGroup(null);
		
		//hdPhotos 중에서 _d가 붙은 파일만 삭제
		for (String hdPhoto : hdPhotos) {
			boolean flag = (hdPhoto.substring(hdPhoto.length()-2, hdPhoto.length())).equals("_d");
			if(flag) fileRepository.deleteBySavePathAndRenameFileName(hdPhoto.substring(6, 17), hdPhoto.substring(17, hdPhoto.length()-2));
		}
		
		timelineRepository.save(review);
		
		FileUtil fileUtil = new FileUtil();
		for (MultipartFile photo : photos) {
			if(!photo.isEmpty()) {
				Fileinfo fileInfo = fileUtil.fileUpload(photo);
				fileInfo.setTypeId(review.getId());
				fileRepository.save(fileInfo);
			}
		}
	}

	@Override
	public void deleteReview(String reviewId, Member member) {
		//수정자와 작성자가 다르면 예외 처리
		Review storedReview = timelineRepository.findById(reviewId).get();
		if(!storedReview.getMemberId().toString().equals(member.getId().toString())) throw new HandlableException(ErrorCode.AUTHENTICATION_FAILED_ERROR);
		
		timelineRepository.deleteById(reviewId);
		fileRepository.deleteByTypeId(new ObjectId(reviewId));
		likeRepository.deleteByRevId(new ObjectId(reviewId));
	}

	@Override
	public void saveLike(String revId, Member member) {
		Like like = new Like();
		like.setMemberId(member.getId());
		like.setRevId(new ObjectId(revId));
		likeRepository.save(like);
	}

	@Override
	public void deleteLike(String revId, Member member) {
		likeRepository.deleteByMemberIdAndRevId(member.getId(), new ObjectId(revId));
	}

	@Override
	public List<Map<String, String>> findGroup(Member member) {
		List<Map<String, String>> result = new ArrayList<>();
		List<Group> groups = groupRepository.findByMemberIdOrParticipants(member.getId(), member.getId());
		for (Group group : groups) {
			Map<String, String> map = new HashMap<>();
			map.put("id", group.getId().toString());
			map.put("name", group.getGroupName().toString());
			result.add(map);
		}

		return result;
	}
	
	@Override
	public List<Review> searchReview(PageObject pageObject, String keyword, String[] area, String[] category, String[] hashtag, Member member) {
		Query query = new Query();
		
		Optional<List<Follow>> followings = followingRepository.findByMemberId(member.getId());
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
				searchHashtag = timelineRepository.findByHashtagLike(hashtag);
				reviews.retainAll(searchHashtag);
			}
		}
		
		//키워드로 검색
		List<Review> searchKeyword = new ArrayList<Review>();
		if(!keyword.equals("")) {
			searchKeyword = timelineRepository.findByResNameIgnoreCaseContaining(keyword);
			reviews.retainAll(searchKeyword);
		}
		
		long firstIdx = (pageObject.getPage()-1) * pageObject.getPerPageNum();
		if(firstIdx > reviews.size()) return null;
		long lastIdx = (firstIdx*8 == 0 ? 8 : reviews.size() < firstIdx*8 ? reviews.size() : firstIdx*8);
		reviews = reviews.subList((int) firstIdx, (int) lastIdx);
		
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
	
	@Override
	public List<HashMap<String, Object>> searchReviewForPaging(PageObject pageObject, String keyword, String[] area, String[] category, String[] hashtag, Member member) {
		List<HashMap<String, Object>> mapList = new ArrayList<>();
		List<Review> reviews = searchReview(pageObject, keyword, area, category, hashtag, member);
		if(reviews != null) {
			for (Review review : reviews) {
				HashMap<String, Object> hashmap = new HashMap<>();
				hashmap.put("review", review);
				hashmap.put("reviewId", review.getId().toString());
				mapList.add(hashmap);
			}
			return mapList;			
		} else {
			return null;
		}
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
}
