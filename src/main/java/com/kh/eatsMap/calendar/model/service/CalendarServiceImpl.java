package com.kh.eatsMap.calendar.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections.map.HashedMap;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.eatsMap.calendar.model.dto.Calendar;
import com.kh.eatsMap.calendar.model.repository.CalendarRepository;
import com.kh.eatsMap.member.model.dto.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService{
	
	@Autowired
	private CalendarRepository calendarRepository;

	
	@Override public void makeSchedule(Calendar calendar) {
		calendarRepository.save(calendar); 
	}


	@Override
	public List<HashMap<String, Object>> selectAllSchedule(Member member) {
		List<HashMap<String, Object>> map = new ArrayList<>();
		
		List<Calendar> calendars = calendarRepository.findByMemberId(member.getId());
		for (Calendar calendar : calendars) {
			HashMap<String, Object> hashmap = new HashMap<>();
			hashmap.put("calendar", calendar);
			hashmap.put("calendarId", calendar.getId().toString());
			map.add(hashmap);
		}
		return map;
	}


	@Override
	public Calendar detailSchedule(String id) {
		Calendar calendar = calendarRepository.findById(id).get();
		return calendar;
	}

	
	/*
	 * public Map<String, Object> selectScheduleById(String id){
	 * 
	 * List<Map> calendar = calendarRepository.selectSchedule(id);
	 * 
	 * return null; }
	 */

}
