package com.kh.eatsMap.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("member")
public class MemberController {
	
	@GetMapping("login")
	public void login() {}
	
	@GetMapping("logout")
	public void logout() {}
	
	@GetMapping("find-password")
	public void findPassword() {}
	
	@GetMapping("join")
	public void join() {}
	
	@GetMapping("kakao-join")
	public void kakaoJoin() {}

}
