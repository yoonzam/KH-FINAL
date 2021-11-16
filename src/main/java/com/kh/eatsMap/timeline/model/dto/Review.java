package com.kh.eatsMap.timeline.model.dto;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import lombok.Data;

@Data
public class Review {
	
	@Id
	private ObjectId id;
	private String nickname;	//작성자
	private String revName;		
	private String[] photo;		//사진 여러개면 array로
	private String addr;
	private GeoJsonPoint point;//타입 다시 체크
	private String review;
	private int taste;			//평점, .5면 long
	private int clean;
	private int service;
	private String category;	//cg01(한식),cg02(중식),cg03(양식),cg04(일식),cg05(아시아),cg06(분식),cg07(카페/디저트),cg08(술집)
	private String group;		//개인이면 my / 그룹이면 그룹명
	private int privacy;
	private Hashtag hashtag;	//가능한지 알아볼 필요o
	private int like;
	
}
