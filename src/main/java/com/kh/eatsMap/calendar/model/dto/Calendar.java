package com.kh.eatsMap.calendar.model.dto;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Calendar {
	
	@Id
	private ObjectId id;
	private ObjectId memberId;	//회원 본인
	private String title;
	private String date;
	private String time;
	
	private String resName;
	
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE) 
	private GeoJsonPoint location;
	
	private String participant;
	/*
	 * private ObjectId[] participant; //참가자
	 */
}
