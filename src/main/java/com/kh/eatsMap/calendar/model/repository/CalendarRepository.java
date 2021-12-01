package com.kh.eatsMap.calendar.model.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.calendar.model.dto.Calendar;
import com.kh.eatsMap.member.model.dto.Member;

public interface CalendarRepository extends MongoRepository<Calendar,String>{

	List<Calendar> findByMemberId(ObjectId objectId);

	Optional<Calendar> findById(String id);

	void deleteById(String objectId);
}

