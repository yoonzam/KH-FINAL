package com.kh.eatsMap.calendar.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.eatsMap.calendar.model.dto.Calendar;
import com.kh.eatsMap.calendar.model.repository.CalendarRepository;

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
	public List<Calendar> selectAllSchedule() {
		return calendarRepository.findAll();
	}
	 
	
	/*
	 * public Map<String, Object> selectScheduleById(String id){
	 * 
	 * List<Map> calendar = calendarRepository.selectSchedule(id);
	 * 
	 * return null; }
	 */

}
