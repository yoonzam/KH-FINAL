package com.kh.eatsMap.map.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("map")
public class MapController {
	
	@GetMapping("/")
	public String map() {
		return "map/map";
	}
	
	@GetMapping("search")
	public String searchReview() {
		
		
		
		return "";
	}

}
