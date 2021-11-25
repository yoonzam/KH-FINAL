package com.kh.eatsMap.calendar.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

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
	public String calendar(Model model) {
		List<Calendar> scheduleList = calendarService.selectAllSchedule();
		model.addAttribute("schedule", scheduleList);
		scheduleList.forEach(e -> logger.info(e.toString()));
		return "calendar/calendar";
	}
	
	@PostMapping("upload")
	public String makeSchedule(Calendar calendar, @SessionAttribute("authentication") Member member){
		calendar.setMemberId(member.getId());
		logger.debug(calendar.toString());
		calendarService.makeSchedule(calendar);
		
		return "redirect:/calendar/";
	}
	
	/*
	 * @GetMapping("schedule") public void scheduleDetail(String id, Model model) {
	 * Map<String,Object> commandMap = CalendarService.selectScheduleById(id);
	 * model.addAttribute("datas",commandMap);
	 * 
	 * }
	 */
}
