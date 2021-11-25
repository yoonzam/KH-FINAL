package com.kh.eatsMap.calendar.model.service;

import java.util.List;
import java.util.Map;

import com.kh.eatsMap.calendar.model.dto.Calendar;

public interface CalendarService {

	void makeSchedule(Calendar calendar);

	List<Calendar> selectAllSchedule();


	
	/*
	 * static Map<String, Object> selectScheduleById(String id) { Auto-generated
	 * method stub return null; }
	 */
	 

}
