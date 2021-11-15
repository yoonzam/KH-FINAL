package com.kh.eatsMap.calendar.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.calendar.model.dto.Calendar;

public interface CalendarRepository extends MongoRepository<Calendar, String>{

}
