package com.kh.eatsMap.calendar.model.service;

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
	public Map<String, Object> selectAllSchedule(Member member) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Calendar> calendars = calendarRepository.findByMemberId(member.getId());
		calendars.forEach(e -> {
			
		});
		return map;
	}


	@Override
	public Calendar detailSchedule(ObjectId id) {
		return calendarRepository.findById(id);
	}
	 
	
	/*
	 * public Map<String, Object> selectScheduleById(String id){
	 * 
	 * List<Map> calendar = calendarRepository.selectSchedule(id);
	 * 
	 * return null; }
	 */

}
