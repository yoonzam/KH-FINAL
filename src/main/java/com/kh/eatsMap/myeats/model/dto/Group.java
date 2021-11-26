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
	//private String groupIdx; //int에서 String변경 (매개변수로 받을 때 불편), 사용중
	private String groupName;
	private ObjectId mapId;
	private String groupImg;
	private Date groupcreatedate; //추가된 필드:그룹생성일자
	private ObjectId memberId;
	private String[] memberNickName;//임시추가: 아마도 Member담는 배열로 바꿀듯
	
	private Group[] groupMembers;
	
	private String thumUrl;			//썸네일URL(추가됨, erd추가예정)

	
}
