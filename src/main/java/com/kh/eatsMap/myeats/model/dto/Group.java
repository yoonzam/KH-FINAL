package com.kh.eatsMap.myeats.model.dto;


import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Data;

@Data
@Document
public class Group {
	
	@Id
	private ObjectId id;
	private String groupIdx; //int에서 String변경 (매개변수로 받을 때 불편), 사용중
	private String groupName;
	private ObjectId mapId;
	private String groupImg;
	private Date groupcreatedate; //추가된 필드:그룹생성일자
	private ObjectId memberId;	

	
}
