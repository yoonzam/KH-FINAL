package com.kh.eatsMap.myeats.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("myeats")
public class MyeatsController {

	@GetMapping("/group")
	public String groupView() {
		return "mypage/group";
	}
	@GetMapping("/createGroup")
	public String createGroupView() {
		return "mypage/createGroup";
	}
	@GetMapping("/groupDetail")
	public String groupDetailView() {
		return "mypage/groupDetail";
	}
	@GetMapping("/post")
	public String postView() {
		return "mypage/post";
	}
	
	@GetMapping("/detail")
	public String detailView() {
		return "mypage/detail";
	}
	
}
