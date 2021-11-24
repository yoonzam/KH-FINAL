package com.kh.eatsMap.index.controller;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.kh.eatsMap.index.model.service.IndexService;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.timeline.model.dto.Review;

@Controller
@RequestMapping("main")
public class IndexController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private IndexService indexService;
	
	public IndexController (IndexService indexService) {
		super();
		this.indexService = indexService;
	}

	
	//메인화면 
	@GetMapping("/")
	public String index(
			@SessionAttribute("authentication") Member member
//			,String longitude_ ,String latitude_
////			,Model model
			) throws Exception {
//
//		
//		String memberId = member.getId().toString();	
//		
//		if(longitude_ != null && latitude_ != null) {
//			
//			double longitude = Double.parseDouble(longitude_);
//			double latitude = Double.parseDouble(latitude_);
//			
//			//GeoJsonPoint : (경도, 위도) 
//			GeoJsonPoint location = new GeoJsonPoint(longitude, latitude);		
//		
//			//member 테이블에 location 추가 
//			indexService.updateLocation(memberId, location);
//		
//			logger.debug(member.getLocation().toString());
//			
////			
////		if(member.getLocation() != null) {
////
////			//위치기반 잇친픽 출력 
////			
////			
////			
////		}
//			
//
////		hashtag픽(잇친아님) 
////		세션에서 현재 접속한 멤버의 id값 받아옴 
////		멤버의 like컬럼값이 1인 리뷰중(=내가 찜한 리뷰중) 높은 빈도의 hashtag 2개 선별
////		그 2개의 hashtag가 포함된 모든 리뷰 출력
//
//
//		List<Review> hashTagRecomendList = indexService.findReviewByHashtag(memberId);
//
////		model.addAttribute(hashTagList);
//			
/////////////////////////////////////////////////////////////////////////////////////////////////		
//		
//		//2. 위치기반 잇친픽 리뷰리스트 출력 
//		
//		
//
//			
//
//		}
//
//		

		return "main/main";
	}

	
	
	
	
	

	@GetMapping("/search")
	public String search() {
		return "main/search";
	}
	
	
	
	
	@PostMapping("/search")
	public String searchImpl(
							String keyword_ 
							,String[] category_ 
							,String[] hashtag_ 
//							,Model model
							) {
		
		String keyword = keyword_ == null ? "" : keyword_;
		
		String[] category = null;
		if(category_ != null) {
			for (int i = 0; i < category_.length; i++) {
				category = new String[category_.length];
				category[i] = category_[i];
//				logger.debug(category[i]);
			}
		}else {
			category = new String[0];
		}
		
		
		String[] hashtag = null;
		if(hashtag_ != null) {
			for (int i = 0; i < hashtag_.length; i++) {
				hashtag = new String[hashtag_.length];
				hashtag[i] = hashtag_[i];
//				logger.debug(hashtag[i]);
			}
		}else {
			hashtag = new String[0];
		}	

		//검색 결과 요청
		List<Review> searchReviewList = indexService.searchReview(keyword, category, hashtag);
		
		for (int i = 0; i < searchReviewList.size(); i++) {
			logger.debug(searchReviewList.get(i).getResName());
		}
		
//		model.addAttribute(searchReviewList);

		return "redirect:/main/search";
	}
}
