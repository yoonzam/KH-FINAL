package com.kh.eatsMap.map.controller;


import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.eatsMap.map.model.service.MapService;
import com.kh.eatsMap.timeline.model.dto.Review;

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
	public List<HashMap<String, Object>> searchReview(String keyword) {
		System.out.println(keyword);

		 

		System.out.println("출력 되는중? "+keyword);


		List<HashMap<String, Object>> reviewList = mapService.reviewList();
		  
		 System.out.println(reviewList.toString());
		 

		return reviewList;
	}

}
