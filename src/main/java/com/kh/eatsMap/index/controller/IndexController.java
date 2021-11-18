package com.kh.eatsMap.index.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	
	
	
	@GetMapping("/")
	public String index(
			@SessionAttribute("authentication") Member member
			,Model model
			) {

		//--hashtag픽(잇친아님) 
		//like컬럼값이 1인 리뷰중(=내가 찜한 리뷰중) 높은 빈도의 hashtag 2개 선별
		//그 2개의 hashtag가 포함된 모든 리뷰 출력
		
		ObjectId id = member.getId();
//		List<Review> hashTagList = indexService.findByHashtag();
//		model.addAttribute(hashTagList);
		
		
		
		//내주변 잇친픽
		//나의 위치값 받아오기
		//내 반경안에 들어와있는 식당이면서, 내가 팔로우 한 사람이 작성한 리뷰 
//		List<Review> nearList = indexService.findByLocation();
//		model.addAttribute(nearList);
		
		return "main/main";
	}

	
	
	

	@GetMapping("/search")
	public String search() {
		return "main/search";
	}
	
	@PostMapping("/search")
	public String searchImpl(
//			String keyword_ 
//							,String[] category_ 
//							,String[] hashtag_ 
//							,Model model
							) {
		
//		String keyword = keyword_ == null ? "" : keyword_;
//		String[] category = category_ == null ? new String[0] : category_;
//		String[] hashtag = hashtag_ == null ? new String[0] : hashtag_;
//	
//		
//		//검색 결과 요청
//		List<Review> reviewList = indexService.findReview(keyword, category, hashtag);
//		
//		model.addAttribute(reviewList);

		return "redirect:/main/search";
	}
}
