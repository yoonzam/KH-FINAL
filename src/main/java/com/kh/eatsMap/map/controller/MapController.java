package com.kh.eatsMap.map.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.eatsMap.map.model.dto.Map;
import com.kh.eatsMap.map.model.service.MapService;
import com.kh.eatsMap.member.model.dto.Follow;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.service.MemberService;
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.timeline.model.dto.Review;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("map")
@RequiredArgsConstructor
public class MapController {

	private final MapService mapService;
	private final MemberService memberService;

	@GetMapping("/")
	public String map(Model model, @SessionAttribute("authentication") Member member) throws JsonProcessingException {
		// map에 보여줘야할 리뷰
		// 리뷰 목록 내가 팔로워 한 사람
		// 내가 리뷰한 음식점
		// 공개 설정이 0인 리뷰

		Map myMap = new Map();

		// 검색을 위한 자신의 팔로우 리스트
		List<Follow> follows = mapService.findFollowList(member.getId());

		// 공개된 리뷰목록과 팔로워한 리뷰를 가져옴
		List<HashMap<String, Object>> reviews = mapService.myEatsMap(member.getId(), follows);

		// 자신의 group리스트
		List<Group> groups = mapService.findGroupList(member.getId());

		// js 에서 사용하기 위해 json으로 변환
		ObjectMapper mapper = new ObjectMapper();
		String jsonReview = mapper.writeValueAsString(reviews);
		String jsonId = mapper.writeValueAsString(member.getId());
		String jsonFollow = mapper.writeValueAsString(follows);

		// view단에 사용하기 위해 model에 데이터 담기
		model.addAttribute("reviews", jsonReview);
		model.addAttribute("groups", groups);
		model.addAttribute("myObjectId", jsonId);
		model.addAttribute("jsonFollow", jsonFollow);

		// hashmap에서 데이터 추출후 분리 저장
		List<Review> saveReview = new ArrayList();
		for (HashMap<String, Object> hashMap : reviews) {

			saveReview.add((Review) hashMap.get("review"));
		}
		// 맵 콜랙션에 저장
		myMap.setMemberId(member.getId());
		myMap.setFollows(follows);
		myMap.setReviews(saveReview);
		myMap.setGroups(groups);

		// 저장 전에 map collection에 자신의 맵이 존재하는지 확인
		Map mymap = mapService.findByMemberId(member.getId());

		try {
			// 존재하면 맵 업데이트

			myMap.setId(mymap.getId());
			mapService.insertMap(myMap);

		} catch (NullPointerException n) {
			// 없으면 생성
			mapService.insertMap(myMap);
		}

		//

		return "map/map";
	}

	@ResponseBody
	@GetMapping("search")
	public List<HashMap<String, Object>> searchReview(@SessionAttribute("authentication") Member member,String keyword) {
		
		// 검색을 위한 자신의 팔로우 리스트
		List<Follow> follows = mapService.findFollowList(member.getId());
		List<HashMap<String, Object>> reviewList = mapService.findReviewByKeyword(keyword,member.getId(),follows);

		

		return reviewList;
	}

	@ResponseBody
	@GetMapping("group")
	public HashMap<String, List<HashMap<String, Object>>> groupMember(Model model, String groupId)
			throws JsonProcessingException {
		HashMap<String, List<HashMap<String, Object>>> reviewMap = new HashMap<String, List<HashMap<String, Object>>>();
		// 그룹 리뷰
		List<HashMap<String, Object>> groupReviewList = mapService.findGroupReview(groupId);
		ObjectMapper mapper = new ObjectMapper();
		String groupReview = mapper.writeValueAsString(groupReviewList);
		if (groupReviewList.isEmpty()) {
		} else {
			reviewMap.put("groupReview", groupReviewList);
		}

		// 검색을 위한 그룹 멤버
		List<HashMap<String, Object>> memberList = mapService.findMemberList(groupId);

		reviewMap.put("memberList", memberList);

		return reviewMap;
	}

	@ResponseBody
	@GetMapping("group-member")
	public List<HashMap<String, Object>> groupMemberReview(String groupId, String memberId) {
		List<HashMap<String, Object>> memberReview = mapService.findByGroupIdAndMemberId(groupId, memberId);

		return memberReview;
	}

}
