package com.kh.eatsMap.index.model.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.kh.eatsMap.member.model.dto.Follow;

public interface FollowRepository extends MongoRepository<Follow, String> {
	
	@Query("{ 'member_id' : ?0 }")		
	List<Follow> findFollowByMemberId(ObjectId memberid);


}
