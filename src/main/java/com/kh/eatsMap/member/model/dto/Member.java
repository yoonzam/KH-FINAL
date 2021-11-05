package com.kh.eatsMap.member.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class Member {
	
	   private String userId;
	   private String password;
	   private String email;
	   private Date regDate;
	   private int isLeave;
}
