package com.kh.eatsMap.calendar.model.dto;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import lombok.Data;

@Data
public class Calendar {
	
	@Id
	private ObjectId id;
	private ObjectId memberId;	//회원 본인
	private String title;
	private Date date;
	private int time;
	
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private GeoJsonPoint location;
	
	private ObjectId[] participant;	//참가자

}
