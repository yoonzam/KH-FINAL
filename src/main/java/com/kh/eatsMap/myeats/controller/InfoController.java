package com.kh.eatsMap.myeats.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eatsMap.common.util.PageObject;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.timeline.model.dto.Review;
import com.kh.eatsMap.timeline.model.service.TimelineService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.service.MemberService;
import com.kh.eatsMap.myeats.model.dao.GroupDAO;
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.myeats.model.service.GroupService;
import com.kh.eatsMap.timeline.model.service.TimelineService;

@Controller
@RequestMapping("info")
public class InfoController {

	@Autowired
	private GroupService groupService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private TimelineService timelineService;

	@Autowired
	private GroupDAO dao;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private static Group group;

	@Autowired
	private static Member member;

	// 전체 멤버 리스트
	@GetMapping("ajaxTest")
	public void ajaxTest() {}

	@GetMapping("memberInfotwo")
	@ResponseBody
	public List<Member> list() throws Exception {
		List<Member> memberList = null;
		memberList = dao.listMember();
		return memberList;
	}
	
	@PostMapping("memberInfo")
	@ResponseBody
	public List<Member> listPost() throws Exception {
		List<Member> memberList = null;
		memberList = dao.listMember();
		return memberList;
	}
	
    //유진 12/01
   @GetMapping("memberInfo")
   @ResponseBody
   public List<Map<String,Object>> memberInfo(@SessionAttribute("authentication") Member member){
      List<Map<String,Object>> members = memberService.findAllFollowingToMap(member);
      
      return members;
   }

	 

}
