package com.kh.eatsMap.map.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.eatsMap.map.model.dto.Map;
import com.kh.eatsMap.map.model.service.MapService;
import com.kh.eatsMap.timeline.model.dto.Review;

@Controller
@RequestMapping("map")
public class MapController {
	
	private MapService mapService;
	
	@GetMapping("/")
	public String map() {
		return "map/map";
	}
	
	@ResponseBody
	@GetMapping("search")
	public List<Review> searchReview(String keyword) {
		System.out.println(keyword);
		
		List<Review> reviewList = mapService.reviewList(keyword);
		 
		
		
		
		return reviewList;
	}

}
