package com.kh.eatsMap.common.util;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Fileinfo {
	
	@Id
	private ObjectId flId;
	private ObjectId typeId;		//회원 id, review id, group id
	private String originFileName;
	private String renameFileName;
	private String savePath;
	private LocalDate regDate;
	private int isDel;
	
	public String getDownloadURL() {
		return "/file/" + savePath + renameFileName;
	}
}
