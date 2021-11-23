package com.kh.eatsMap.map.model.dto;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import lombok.Data;

@Data
public class Map {
	
	@Id
	private ObjectId id;			//mapid
	private String name;			//식당명
	private ObjectId typeId;		//그룹id 또는 회원id
	private String revAddress;
	
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private GeoJsonPoint location;
	private String revPhoto;		//map 마크 출력용 이미지
	private int privacy; 			//공개여부
	


}