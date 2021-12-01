package com.kh.eatsMap.map.model.dto;

import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.kh.eatsMap.member.model.dto.Follow;
import com.kh.eatsMap.timeline.model.dto.Review;

import lombok.Data;

@Data
public class Map {
	
	@Id
	private ObjectId id;			//mapid
	
	private ObjectId memberId; //회원 아이디
		/*
		 * private String name; //식당명 private ObjectId typeId; //그룹id 또는 회원id private
		 * String revAddress;
		 * 
		 * @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE) private
		 * GeoJsonPoint location; private String revPhoto; //map 마크 출력용 이미지 private int
		 * privacy; //공개여부
		 * 
		 */

	private List<Review> reviews;
	
	private List<Follow> follows;

	public void setFollows(List<Follow> follows) {
		// TODO Auto-generated method stub
		this.follows = follows;
	}
	
	public void setReviews(List<Review> reviews) {
		// TODO Auto-generated method stub
		this.reviews = reviews;
	}


}