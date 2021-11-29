package com.kh.eatsMap.member.model.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.member.model.dto.Follow;

public interface FollowingRepository extends MongoRepository<Follow, ObjectId>{

	Optional<Follow> findOptionalByMemberIdAndFollowingId(ObjectId memberId, ObjectId followingId);

	long countByMemberId(ObjectId memberId);

}
