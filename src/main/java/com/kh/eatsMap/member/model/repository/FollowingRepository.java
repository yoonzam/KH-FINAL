package com.kh.eatsMap.member.model.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.member.model.dto.Follow;

public interface FollowingRepository extends MongoRepository<Follow, String>{

	Optional<Follow> findOptionalByMemberIdAndFollowingId(ObjectId memberId, String followingId);

	long countByMemberId(String memberId);

}
