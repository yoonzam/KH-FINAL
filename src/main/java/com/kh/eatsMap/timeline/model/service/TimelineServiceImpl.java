package com.kh.eatsMap.timeline.model.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

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
		review.setLocation(new GeoJsonPoint(latitude, longitude));
		review.setMemberId(member.getId());
		review.setRegDate(LocalDateTime.now().plusHours(9));
		if(review.getGroup().equals("")) review.setGroup(null);
		
		review = timelineRepository.save(review);
		
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
		List<Review> reviews = timelineRepository.findAll(Sort.by(Sort.Direction.DESC, "regDate"));
		for (Review review : reviews) {
			review.setCategory(getCategoryName(review.getCategory()));
			review.setHashtag(getHashtagName(review.getHashtag()));
			
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
			if(files.size() > 0) review.setThumUrl(files.get(0).getDownloadURL());
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
			review.setCategory(getCategoryName(review.getCategory()));
			review.setHashtag(getHashtagName(review.getHashtag()));
			review.setMemberId(new ObjectId(review.getMemberId().toString()));

			review.setMemberNick(memberRepository.findById(review.getMemberId().toString()).get().getNickname());
			map.put("review", review);
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
	
	private String getCategoryName(String category) {
		switch (category.toLowerCase()) {
			case "cg01" : return "한식";
			case "cg02" : return "중식";
			case "cg03" : return "양식";
			case "cg04" : return "일식";
			case "cg05" : return "아시아";
			case "cg06" : return "분식";
			case "cg07" : return "카페/디저트";
			case "cg08" : return "술집";
			default: return "기타";
		}
	}
	
	private String[] getHashtagName(String[] hashtag) {
		for (int i = 0; i < hashtag.length; i++) {
			switch (hashtag[i].toLowerCase()) {
				case "md01" : hashtag[i] = "친근함"; break;
				case "md02" : hashtag[i] = "고급짐"; break;
				case "md03" : hashtag[i] = "가족"; break;
				case "md04" : hashtag[i] = "데이트"; break;
				case "md05" : hashtag[i] = "혼밥"; break;
				case "md06" : hashtag[i] = "회식"; break;
				case "pr01" : hashtag[i] = "가성비"; break;
				case "pr02" : hashtag[i] = "가심비"; break;
				case "pr03" : hashtag[i] = "1~2만원대"; break;
				case "pr04" : hashtag[i] = "2~3만원대"; break;
				case "pr05" : hashtag[i] = "3만원 이상"; break;
				default: hashtag[i] = ""; break;
			}
		}
		return hashtag;
	}

}
