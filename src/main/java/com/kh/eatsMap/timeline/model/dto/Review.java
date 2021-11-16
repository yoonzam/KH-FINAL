package com.kh.eatsMap.timeline.model.dto;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import lombok.Data;

@Data
public class Review {
	
	private ObjectId id;
	private String nickname;	//작성자
	private String revName;		
	private String photo;		//사진 여러개면 array로
	private String addr;
	private GeoJsonPoint point;
	private String review;
	private int taste;			//평점, .5면 long
	private int clean;
	private int service;
	private String category;
	private String group;		//개인이면 my / 그룹이면 그룹명
	private int privacy;
	private Hashtag hashtag;	//가능한지 알아볼 필요o
	private int like;
	
}
