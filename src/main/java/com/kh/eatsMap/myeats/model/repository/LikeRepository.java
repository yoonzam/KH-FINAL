package com.kh.eatsMap.myeats.model.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.myeats.model.dto.Like;

public interface LikeRepository extends MongoRepository<Like, String>{

	List<Like> findByMemberId(ObjectId memberId);
	
	void deleteByMemberIdAndRevId(ObjectId id, ObjectId objectId);

	void deleteByRevId(ObjectId revId);

}
