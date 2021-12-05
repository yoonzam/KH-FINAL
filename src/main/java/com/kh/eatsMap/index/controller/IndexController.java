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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.common.util.PageObject;
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

	@GetMapping("setLocation")
	@ResponseBody
	public List<HashMap<String, Object>> setLocation(String longitude_ ,String latitude_, @SessionAttribute("authentication") Member member) {
		
		List<HashMap<String, Object>> reviews = new ArrayList<HashMap<String,Object>>();
		
		if(longitude_ != null && latitude_ != null) {
			
			double longitude = Double.parseDouble(longitude_);
			double latitude = Double.parseDouble(latitude_);
			
			//GeoJsonPoint : (경도, 위도) 
			GeoJsonPoint location = new GeoJsonPoint(longitude, latitude);		

			//member 테이블에 location 추가 
			indexService.updateLocation(member, location);
		}
		
		if(member.getLocation() != null) {	
			//*위치기반 잇친픽 출력 
			reviews = indexService.localReview(member);	
//			logger.debug(reviews.toString());
		}

		return reviews;
	}
	
	//메인화면 
	//hashtag픽
	@GetMapping("/")
	public String index(@SessionAttribute("authentication") Member member ,Model model) throws Exception {
		
		Map<String,Object> reviewsAndHashtag = indexService.findAllReview(member);
		model.addAttribute("reviewsAndHashtag", reviewsAndHashtag);
		
		return "main/main";
	}

	
///////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	@GetMapping("/search")
	public String searchImpl(
							String keyword_
							, String[] area_
							, String[] category_
							, String[] hashtag_
							, PageObject pageObject
							, Model model
							, @SessionAttribute("authentication") Member member
							) {
		
		String keyword = keyword_ == null ? "" : keyword_;
		
		String[] category = new String[0];
		if(category_ != null) {
			category = new String[category_.length];		
			for (int i = 0; i < category_.length; i++) {
				category[i] = category_[i];
			}
		}
		String[] hashtag = new String[0];
		if(hashtag_ != null) {
			hashtag = new String[hashtag_.length];		
			for (int i = 0; i < hashtag_.length; i++) {
				hashtag[i] = hashtag_[i];
			}	
		}
		String[] area = new String[0];
		if(area_ != null) {
			area = new String[area_.length];		
			for (int i = 0; i < area_.length; i++) {
				area[i] = area_[i];
			}	
		}
		
		pageObject.setPerPageNum(8);
		pageObject.setPerGroupPageNum(8);
		// 데이터 건수를 세팅
		long count = indexService.count(keyword, area, category, hashtag, member);
		
		pageObject.setTotalRow(count);
		
		List<Review> searchedReviewList = indexService.searchReview(keyword, area, category, hashtag, member, pageObject);
		
//		System.out.println(pageObject);
		
		model.addAttribute("reviews", searchedReviewList);
		model.addAttribute("keyword", keyword_);
		model.addAttribute("pageObject", pageObject);

		return "main/search";
	}
	
}
