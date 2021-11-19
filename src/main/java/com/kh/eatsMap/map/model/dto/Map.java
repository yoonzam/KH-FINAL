package com.kh.eatsMap.map.model.dto;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import lombok.Data;

@Data
public class Map {
	
	private int memberIdx;
	private String name;
	private String typeIdx;
	private String revAddress;
	
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private GeoJsonPoint location;
	private String revPhoto;
	private int pub; //public
	
	

}
