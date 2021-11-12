package com.kh.eatsMap.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("calendar")
public class CalendarController {

	@GetMapping("/")
	public String calendar() {
		return "calendar/calendar";
	}
}
