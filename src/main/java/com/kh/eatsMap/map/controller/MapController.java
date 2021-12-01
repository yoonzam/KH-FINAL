package com.kh.eatsMap.map.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
		

		

		List<Follow> follows = mapService.findFollowList(member.getId());
		System.out.println("팔로우 목록");
		System.out.println(follows);
		// 필터링된 값 model로 뿌려준후 map 콜렉션에 저장
		List<HashMap<String, Object>> reviews = mapService.myEatsMap(member.getId(), follows);
		//js 에서 사용하기 위해 json으로 변환
		ObjectMapper mapper = new ObjectMapper();
		String jsonText = mapper.writeValueAsString(reviews);
		
		model.addAttribute("reviews",jsonText);
		List<Review> saveReview = new ArrayList();
		for (HashMap<String, Object> hashMap : reviews) {
			
			saveReview.add((Review)hashMap.get("review"));
		}
		//맵 콜랙션에 저장
		myMap.setMemberId(member.getId());
		myMap.setFollows(follows);
		myMap.setReviews(saveReview);

		
		
		//저장 전에 map collection에 자신의 맵이 존재하는지 확인
		Map mymap = mapService.findByMemberId(member.getId());
		
		try {
			//존재하면 맵 업데이트
			
			myMap.setId(mymap.getId());
			mapService.insertMap(myMap);
			
		} catch (NullPointerException n) {
			//없으면 생성
			mapService.insertMap(myMap);
		}
		
		
		
		

		return "map/map";
	}

	@ResponseBody
	@GetMapping("search")
	public List<HashMap<String, Object>> searchReview(String keyword) {
		System.out.println(keyword);

		System.out.println("출력 되는중? " + keyword);

		List<HashMap<String, Object>> reviewList = mapService.reviewList();

		System.out.println(reviewList.toString());

		return reviewList;
	}

}
