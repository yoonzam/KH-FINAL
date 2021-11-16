package com.kh.eatsMap.myeats.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("myeats")
public class MyeatsController {

	@GetMapping("/group")
	public String groupView() {
		return "myeats/group";
	}
	@GetMapping("/createGroup")
	public String createGroupView() {
		return "myeats/createGroup";
	}
	@GetMapping("/groupDetail")
	public String groupDetailView() {
		return "myeats/groupDetail";
	}
	@GetMapping("/post")
	public String postView() {
		return "myeats/post";
	}
	@GetMapping("/detail")
	public String detailView() {
		return "myeats/detail";
	}
	
}
