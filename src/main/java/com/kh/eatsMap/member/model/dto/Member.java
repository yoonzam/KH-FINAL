package com.kh.eatsMap.member.model.dto;

import java.sql.Date;
import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;

@Data
@Document
public class Member {
	
	@Id
	private ObjectId id;
	private String nickname;
	private String password;
	private String email;
	private LocalDate regDate;
	private int isLeave;
}
