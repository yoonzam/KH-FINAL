package com.kh.eatsMap.timeline.model.dto;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import lombok.Data;

@Data
public class Review {
	
	@Id
	private ObjectId id;
	private ObjectId memberId;	//작성자
	private String resName;		//식당명
	private ObjectId[] photo;	//사진 여러개 -> fileId array로
	private String addr;
	private String review;		//내용
	
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private GeoJsonPoint location;
	
	private int taste;			//평점
	private int clean;
	private int service;
	
	private String category;	//cg01(한식),cg02(중식),cg03(양식),cg04(일식),cg05(아시아),cg06(분식),cg07(카페/디저트),cg08(술집)
	private String[] hashtag;	//md01(친근),md02(고급),md03(가족),md04(데이트),md05(혼밥),md06(회식),pr01(가성비),pr02(가심비)
	private String group;		//그룹이면 그룹명 save (개인이면 생략)
	private int privacy;		//공개여부(0,1,-1)
	private int like;
	
}
