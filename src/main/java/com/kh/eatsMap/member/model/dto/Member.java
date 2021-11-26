package com.kh.eatsMap.member.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.kh.eatsMap.common.util.Fileinfo;

import lombok.Data;

@Data
@Document
public class Member {
	
	@Id
	private ObjectId id;
	private String kakaoId;		//카카오회원용
	private String nickname;
	private String password;
	private String email;
	private LocalDateTime regDate;
	private int isLeave;
	private String profile;
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private GeoJsonPoint location;

	public void setProfile(Fileinfo file) {	
		if(file.getSavePath() == null) {
			this.profile = null;
		}else {
			this.profile = file.getSavePath() + file.getRenameFileName();

		}	
	}

	public void setRegDate() {
		this.regDate = LocalDateTime.of(LocalDate.now(),LocalTime.now(ZoneId.of("Asia/Seoul"))).plusHours(9);	//mongoDB 특성상 불가피
	}

}
