package com.kh.eatsMap.calendar.controller;

import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.jose4j.json.internal.json_simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kh.eatsMap.calendar.model.dto.Calendar;
import com.kh.eatsMap.calendar.model.service.CalendarService;
import com.kh.eatsMap.firebase.PushMessaging;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.dto.Notice;
import com.kh.eatsMap.member.model.service.MemberService;

@Controller
@RequestMapping("calendar")
public class CalendarController {
	
	@Autowired
	private CalendarService calendarService;
	@Autowired
	private MemberService memberService;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private PushMessaging push = PushMessaging.getInstance();

	@GetMapping("/")
	public String calendar() {
		return "calendar/calendar";
	}
	
	@GetMapping("getSchedule")
	@ResponseBody
	public List<HashMap<String, Object>> getSchedule(@SessionAttribute("authentication") Member member) {
		 List<HashMap<String, Object>> map = calendarService.selectAllSchedule(member);
		return map ;
	}
	
	@PostMapping("upload")
	@ResponseBody
	public void makeSchedule(Calendar calendar, String scheduleId, double latitude, double longitude, @SessionAttribute("authentication") Member member){
		
		if(scheduleId.equals("")) {
			calendar.setMemberId(member.getId());
			calendar.setLocation(new GeoJsonPoint(longitude, latitude));
			calendarService.makeSchedule(calendar);	
		}else {
			Calendar originCalendar = calendarService.findCalendarById(scheduleId);
			originCalendar.setTitle(calendar.getTitle());
			originCalendar.setDate(calendar.getDate());
			originCalendar.setResName(calendar.getResName());
			originCalendar.setParticipants(calendar.getParticipants());
			originCalendar.setLocation(new GeoJsonPoint(longitude, latitude));		
			calendarService.makeSchedule(originCalendar);	
		}
		//유진 12/02 알람
		if(calendar.getParticipants().length > 0) {
			for (int i = 0; i < calendar.getParticipants().length; i++) {
		    	  Member to = memberService.findMemberById(calendar.getParticipants()[i]);
		    	  Notice notice = memberService.findNotice(to.getId());
		    	  memberService.updateNotice("calendar", notice);
		    	  push.push(to);
			}			
		}
		//return "redirect:/calendar/";
	}
	
	@GetMapping("detail")
	@ResponseBody
	public Map<String, Object> detail(String id){
		Map<String, Object> calendarDetail = calendarService.detailSchedule(id);
		return calendarDetail;
	}

	//delete메서드 로직
	//1. 받아온 String id값으로 서비스에게 전달
	//2. 서비스단에서 삭제 과정 진행 : 먼저 Calendar객체 조회 
	//	--> calendarRepository.delete(객체); 로 전달하면 몽고db가 삭제해줌
	
	@PostMapping("delete")
	@ResponseBody
	public void deleteSchedule(String id) {
		calendarService.deleteSchedule(id);
	}
	
	//유진12/02
	@GetMapping("memberList")
	@ResponseBody
	public List<Map<String,Object>> memberInfo(@SessionAttribute("authentication") Member member){
		return memberService.findAllFollowingToMap(member);
	}

}
