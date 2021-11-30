package com.kh.eatsMap.member.model.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.member.model.dto.Follower;

public interface FollowerRepository extends MongoRepository<Follower, ObjectId>{

	Optional<Follower> findOptionalByMemberIdAndFollowerId(ObjectId memberId, ObjectId id);

	long countByMemberId(Object memberId);

	
}
