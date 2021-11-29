package com.kh.eatsMap.timeline.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eatsMap.common.code.ErrorCode;
import com.kh.eatsMap.common.exception.HandlableException;
import com.kh.eatsMap.common.util.FileUtil;
import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.repository.MemberRepository;
import com.kh.eatsMap.timeline.model.dto.Review;
import com.kh.eatsMap.timeline.model.repository.FileRepository;
import com.kh.eatsMap.timeline.model.repository.TimelineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimelineServiceImpl implements TimelineService{

	private final TimelineRepository timelineRepository;
	private final FileRepository fileRepository;
	private final MemberRepository memberRepository;

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
	public Map<String, Object> findReviewById(String id) {
		Map<String, Object> map = new HashMap<>();
		
		//review
		Optional<Review> findReview = timelineRepository.findById(id);
		if(findReview.isPresent()) {
			Review review = findReview.get();
			review.setMemberId(new ObjectId(review.getMemberId().toString()));
			review.setMemberNick(memberRepository.findById(review.getMemberId().toString()).get().getNickname());
			review.toString();
			map.put("review", review);
			
			//objectId
			map.put("reviewId", review.getId().toString());
			map.put("memberId", review.getMemberId().toString());
		}
		
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
		//수정자와 작성자가 다르면 예외처리
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
		//수정자와 작성자가 다르면 예외처리
		Review storedReview = timelineRepository.findById(reviewId).get();
		if(!storedReview.getMemberId().toString().equals(member.getId().toString())) throw new HandlableException(ErrorCode.AUTHENTICATION_FAILED_ERROR);
		
		timelineRepository.deleteById(reviewId);
		fileRepository.deleteByTypeId(new ObjectId(reviewId));
	}
}
