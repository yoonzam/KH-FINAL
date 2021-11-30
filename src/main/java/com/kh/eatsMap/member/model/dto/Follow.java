package com.kh.eatsMap.member.model.dto;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Follow {
	
	@Id
	private ObjectId id;	
	private ObjectId memberId;	//로그인 유저(본인)
	private ObjectId followingId;	//팔로우한 회원 id
	
}
