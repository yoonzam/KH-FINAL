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
import com.kh.eatsMap.member.model.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService{
	
	@Autowired
	private CalendarRepository calendarRepository;
	@Autowired
	private MemberRepository memberRepository;
	
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
	public Map<String, Object> detailSchedule(String id) {
		List<Member> participantInfoList = new ArrayList<Member>();
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar calendar = calendarRepository.findById(id).get();
		ObjectId[] participants = calendar.getParticipants();
		
		for (int i = 0; i < participants.length; i++) {
			 Member member = memberRepository.findById(participants[i]);
			 participantInfoList.add(member);
		}
		map.put("calendar", calendar);
		map.put("calendarId", calendar.getId().toString());
		map.put("participant", participantInfoList);
		
		return map;
	}


	@Override
	public Calendar findCalendarById(String scheduleId) {
		return calendarRepository.findById(scheduleId).orElse(new Calendar());
	}


	@Override
	public void deleteSchedule(String id) {
		calendarRepository.deleteById(id);
	}






}
