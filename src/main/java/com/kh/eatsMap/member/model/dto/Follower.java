package com.kh.eatsMap.member.model.dto;

import org.bson.types.ObjectId;

import lombok.Data;

@Data
public class Follower {

	private ObjectId id;
	private ObjectId memberId;	//로그인 유저(본인)
	private ObjectId followerId;	//자신을 팔로우하는 회원 id
}
