package com.kh.eatsMap.main.model.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.kh.eatsMap.member.model.dto.Follow;

public interface IndexFollowRepository extends MongoRepository<Follow, String> {


	Optional<List<Follow>> findByMemberId(ObjectId id);

	Optional<Follow> findOptionalByMemberIdAndFollowingId(ObjectId memberId, ObjectId followingId);

}
