package com.kh.eatsMap.calendar.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.kh.eatsMap.calendar.model.dto.Calendar;
import com.kh.eatsMap.member.model.dto.Member;

public interface CalendarService {

	void makeSchedule(Calendar calendar);

	List<HashMap<String, Object>> selectAllSchedule(Member member);

	Map<String, Object> detailSchedule(String id);

	Calendar findCalendarById(String scheduleId);

	

	
	/*
	 * static Map<String, Object> selectScheduleById(String id) { Auto-generated
	 * method stub return null; }
	 */
	 

}
