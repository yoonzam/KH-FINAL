package com.kh.eatsMap.member.model.dto;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.kh.eatsMap.common.util.FileDTO;

import lombok.Data;

@Data
@Document
public class Member {
	
	@Id
	private ObjectId id;
	private String nickname;
	private String password;
	private String email;
	private LocalDate regDate;
	private int isLeave;
	private String profile;


	public void setProfile(FileDTO file) {	
		if(file.getSavePath() == null) {
			this.profile = null;
		}else {
			this.profile = file.getSavePath() + file.getRenameFileName();

		}	
	}

	
}
