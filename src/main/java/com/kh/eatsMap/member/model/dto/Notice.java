package com.kh.eatsMap.member.model.dto;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Notice {

	@Id
	private ObjectId id;
	private ObjectId memberId;
	private int calendarNotice;	
	private int participantNotice;
	private int groupNotice;
	private int followNotice;
	
}
