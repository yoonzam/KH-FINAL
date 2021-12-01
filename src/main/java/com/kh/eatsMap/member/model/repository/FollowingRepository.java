package com.kh.eatsMap.member.model.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.member.model.dto.Follow;
import com.kh.eatsMap.member.model.dto.Member;

public interface FollowingRepository extends MongoRepository<Follow, ObjectId>{

	Optional<Follow> findOptionalByMemberIdAndFollowingId(ObjectId memberId, ObjectId followingId);

	long countByMemberId(ObjectId memberId);

	Optional<Follow> findByMemberIdAndFollowingId(ObjectId id, ObjectId memberId);
	
	List<Follow> findByMemberId(ObjectId memberId);

}
