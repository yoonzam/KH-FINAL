package com.kh.eatsMap.index.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.eatsMap.common.util.Fileinfo;
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
			,String longitude_ ,String latitude_
//			,Model model
			) throws Exception {

		
		
		////////////////////////////////////////////////////////////////////////
		//hashtag픽(잇친아님) 
		//like컬럼값이 1인 리뷰중(=내가 찜한 리뷰중) 높은 빈도의 hashtag 2개 선별
		//그 2개의 hashtag가 포함된 모든 리뷰 출력
		
		//List<Review> hashTagRecomendList = indexService.findReviewByHashtag(member);
		//model.addAttribute(hashTagList);
		
		
		
		
		//사용자 위치 받아오기 
		if(longitude_ != null && latitude_ != null) {
			
			double longitude = Double.parseDouble(longitude_);
			double latitude = Double.parseDouble(latitude_);
			
			//GeoJsonPoint : (경도, 위도) 
			GeoJsonPoint location = new GeoJsonPoint(longitude, latitude);		
		

			//member 테이블에 location 추가 
			indexService.updateLocation(member, location);
			logger.debug(member.getLocation().toString());
//			
//			if(member.getLocation() != null) {	
//				
//				//*위치기반 잇친픽 출력 
//				List<Review> locationFollowReviewList = indexService.localReview(member);	
//				model.addAttribute("locationFollowReviewList","locationFollowReviewList");
//
//			}
		}
		return "main/main";
	}

	
	
	
	
	

//	@GetMapping("/search")
//	public String search(
////						@SessionAttribute("authentication") Member member
////						,String id
//						) {
//		
//		//좋아요 테이블에 추가/삭제작업 필요
//		//좋아요 테이블에 findByReviewId로 조회한 후 null 이면 save하기,
//		//findByReviewId로 조회한 후 결과가 있으면 delete하기 
//
//		
//		
//		return "main/search";
//	}
//	
	
	
	
	@PostMapping("/search")
	public String searchImpl(
							String keyword_ 
							,String[] category_ 
							,String[] hashtag_ 
							,Model model
//							,RedirectAttributes rttr
							) {
		
		String keyword = keyword_ == null ? "" : keyword_;
		System.out.println("keyword : " + keyword);
	
		
		String[] category = new String[0];
		if(category_ != null) {
			category = new String[category_.length];		
			for (int i = 0; i < category_.length; i++) {
				category[i] = category_[i];
//				System.out.println("category : " + category[i]);
			}	
		}
		

		String[] hashtag = new String[0];
		if(hashtag_ != null) {
			hashtag = new String[hashtag_.length];		
			for (int i = 0; i < hashtag_.length; i++) {
				hashtag[i] = hashtag_[i];
//				System.out.println("hashtag : " + hashtag[i]);
			}	
		}


		//검색 결과 요청
		List<Review> searchedReviewList = indexService.searchReview(keyword, category, hashtag);	//searchReviewList : 항목들로 서치된 리뷰데이터  
		model.addAttribute("searchedReviewList", searchedReviewList);	

//		rttr.addFlashAttribute("searchedReviewList", searchedReviewList);
//		return "redirect:/main/search";
		return "main/search";
	}
	
	
	//유진 : 해시태그 추천 리스트
	@GetMapping("search")
	public String search(@SessionAttribute("authentication") Member member) {
		Map<String,Object> reviewsAndHashtag = indexService.findAllReview(member);
		logger.debug(reviewsAndHashtag.get("reviews").toString());
		logger.debug("가장 많은 해시태그 : " + reviewsAndHashtag.get("hashtag"));
		return "main/search";
	}
	
}
