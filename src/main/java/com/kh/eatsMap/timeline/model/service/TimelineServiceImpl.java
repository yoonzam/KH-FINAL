package com.kh.eatsMap.timeline.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
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
import com.kh.eatsMap.member.model.service.MemberService;
import com.kh.eatsMap.myeats.model.dto.Like;
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
	private final MongoTemplate mongoTemplate;

	@Override
	public void insertReview(Review review, double latitude, double longitude, List<MultipartFile> photos, Member member) {
		review.setLocation(new GeoJsonPoint(longitude, latitude));
		review.setMemberId(member.getId());
		if(review.getGroup().equals("")) review.setGroup(null);
		
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
		List<Review> reviews = timelineRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		
		for (Review review : reviews) {
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
			if(files.size() > 0) review.setThumUrl(files.get(0).getDownloadURL());
			review.toString();
		}
		return reviews;
	}
	
	@Override
	public List<Review> findAllReviews(PageObject pageObject, Member member) {
		Query query = new Query();
		query = query.with(Sort.by(Sort.Direction.DESC, "id"));
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
		review.setMemberId(new ObjectId(review.getMemberId().toString()));
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
		
		//잇친여부
		Follow follow = followingRepository.findOptionalByMemberIdAndFollowingId(member.getId(), review.getMemberId().toString()).orElse(new Follow());
		System.out.println(follow);
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
	public List<Review> searchReview(String keyword, String[] category, String[] hashtag) {
		List<Review> searchKeyword = new ArrayList<Review>();
		List<Review> searchCategory = new ArrayList<Review>();
		List<Review> searchHashtag = new ArrayList<Review>();
		
		//키워드로 검색
//		if(!keyword.equals("")) {
//			searchKeyword = timelineRepository.findReviewByResNameContaining(keyword);
//		}
		
		//카테고리로 검색
		if(category.length > 0) {
			Query query = new Query();
			Criteria criteria = new Criteria();
		    Criteria criteriaArr[]  = new Criteria[category.length];
		    
		    for(int i = 0; i < category.length; i++){
	            String question = category[i];
	            criteriaArr[i] = Criteria.where("category").regex(question);
	        }	
		    query.addCriteria(criteria.orOperator(criteriaArr));
		    
		    searchCategory = mongoTemplate.find(query, Review.class, "review");
		}
			
		//해시태그로 검색
		if(hashtag.length > 0) {
			searchHashtag = timelineRepository.findReviewByCategoryLike(hashtag);
		}
		for (int i = 0; i < searchHashtag.size(); i++) {
			System.out.println("해시 검색결과 : "+searchHashtag.get(i).getResName());			
		}

		List<Review> searchReview_ = new ArrayList<Review>();
		//searchReview_.addAll(searchKeyword);
		searchReview_.addAll(searchCategory);
		searchReview_.addAll(searchHashtag);
		
		Set<Review> searchReviewSet = new HashSet<Review>(searchReview_);	//중복제거
		List<Review> searchReview = new ArrayList<Review>(searchReviewSet);
		
		for (Review review : searchReview) {
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
			if(files.size() > 0) review.setThumUrl(files.get(0).getDownloadURL());
		}
		return searchReview;
	}

}
