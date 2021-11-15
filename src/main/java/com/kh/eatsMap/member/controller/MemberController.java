package com.kh.eatsMap.member.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.service.MemberService;

@Controller
@RequestMapping("member")
public class MemberController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private MemberService memberService;
	
	public MemberController(MemberService memberService) {
		super();
		this.memberService = memberService;
	}

	@GetMapping("login")
	public void login() {
		List<Member> member = memberService.findMember();
		member.forEach(e -> logger.debug(e.toString()));
	}
	
	@GetMapping("logout")
	public void logout() {}
	
	@GetMapping("find-password")
	public void findPassword() {}
	
	@GetMapping("join")
	public void join() {
		memberService.joinMember();
		
	}
	
	@GetMapping("kakao-join")
	public void kakaoJoin() {}

}
