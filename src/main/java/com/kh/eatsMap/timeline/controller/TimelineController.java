package com.kh.eatsMap.timeline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("timeline")
public class TimelineController {

	
	@GetMapping("/")
	public String timeline() {
		return "timeline/timeline";
	}
}
