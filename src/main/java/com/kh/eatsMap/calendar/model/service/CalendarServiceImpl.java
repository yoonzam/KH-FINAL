package com.kh.eatsMap.calendar.model.service;

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

	@Override
	public void makeSchedule(Calendar calendar) {
		calendarRepository.save(calendar);
	}

}
