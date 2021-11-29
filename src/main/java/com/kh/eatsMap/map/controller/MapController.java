package com.kh.eatsMap.map.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.eatsMap.map.model.dto.Map;
import com.kh.eatsMap.map.model.service.MapService;
import com.kh.eatsMap.timeline.model.dto.Review;
import com.kh.eatsMap.timeline.model.service.TimelineService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("map")
@RequiredArgsConstructor
public class MapController {
	
	private final MapService mapService;
	
	@GetMapping("/")
	public String map() {
		return "map/map";
	}
	
	@ResponseBody
	@GetMapping("search")
	public List<Review> searchReview(String keyword) {
		System.out.println("출력 되는중? "+keyword);
		
		  List<Review> reviewList = mapService.reviewList();
		  
		  System.out.println(reviewList.toString());
		 
		
		/*
		 * List<Review> reviewList = new ArrayList<Review>();
		 * 
		 * Review review1 = new Review(); Review review2 = new Review();
		 * 
		 * review1.setResName(keyword); review2.setResName("라쿠치나");
		 * reviewList.add(review1); reviewList.add(review2);
		 */
		
		return reviewList;
	}

}
