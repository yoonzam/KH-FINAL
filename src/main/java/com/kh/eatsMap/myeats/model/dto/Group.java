package com.kh.eatsMap.myeats.model.dto;


import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.kh.eatsMap.member.model.dto.Member;

import lombok.Data;

@Data
@Document
public class Group {
	
	@Id
	private ObjectId id;
	private String groupName;
	private ObjectId mapId;
	private String groupImg;
	private Date groupcreatedate; 
	private ObjectId memberId;
	private String[] memberNickName;
	private String thumUrl;			//썸네일URL

	
}
