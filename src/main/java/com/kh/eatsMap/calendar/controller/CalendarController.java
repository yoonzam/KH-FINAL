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
import com.kh.eatsMap.member.model.dto.Member;

@Controller
@RequestMapping("calendar")
public class CalendarController {
	
	@Autowired
	private CalendarService calendarService;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/")
	public String calendar() {
		return "calendar/calendar";
	}
	
	@GetMapping("getSchedule")
	@ResponseBody
	public List<HashMap<String, Object>> getSchedule(@SessionAttribute("authentication") Member member) {
		return calendarService.selectAllSchedule(member);
	}
	
	@PostMapping("upload")
	public String makeSchedule(Calendar calendar,double latitude, double longitude, @SessionAttribute("authentication") Member member){
		calendar.setLocation(new GeoJsonPoint(longitude, latitude));
		calendar.setMemberId(member.getId());
		calendarService.makeSchedule(calendar);
		
		return "redirect:/calendar/";
	}
	
	@GetMapping("detail")
	@ResponseBody
	public Calendar detail(String id){
		logger.debug("calendarId : " + id);
		Calendar calendarDetail = calendarService.detailSchedule(id);
		logger.debug(calendarDetail.toString());
		return calendarDetail;
	}
}
