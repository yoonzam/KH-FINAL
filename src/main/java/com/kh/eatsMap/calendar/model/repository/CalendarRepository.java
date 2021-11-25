package com.kh.eatsMap.calendar.model.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.calendar.model.dto.Calendar;


	public interface CalendarRepository extends MongoRepository<Calendar,String>{
		
		}

