package com.kh.eatsMap.myeats.model.dto;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Data;

@Data
@Document
public class Group {
	
	@Id
	private ObjectId id;
	private int groupIdx;		
	private String groupName;
	private String memberId;
	private String mapId;
	private String groupImg;


	
}
