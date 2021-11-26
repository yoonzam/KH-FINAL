package com.kh.eatsMap.member.model.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.member.model.dto.Notice;

public interface NoticeRepository extends MongoRepository<Notice, ObjectId> {

	Notice findByMemberId(ObjectId id);
	
	

}
