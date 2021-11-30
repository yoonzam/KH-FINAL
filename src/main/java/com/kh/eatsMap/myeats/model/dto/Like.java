package com.kh.eatsMap.myeats.model.dto;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Like {
	
	@Id
	private ObjectId id;
	private ObjectId memberId;
	private ObjectId revId;
}
