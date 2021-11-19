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
	private int privacy;		//공개여부	
	private String[] hashtag;	//md01(친근),md02(고급),md03(가족),md04(데이트),md05(혼밥),md06(회식),pr01(가성비),pr02(가심비)
	private int like;
	
}
