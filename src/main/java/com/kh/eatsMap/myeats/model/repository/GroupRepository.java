package com.kh.eatsMap.myeats.model.repository;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.myeats.model.dto.Group;

public interface GroupRepository extends MongoRepository<Group, String>{	
	
	List<Group> findById(ObjectId Id);

	Optional<List<Group>> findByParticipants(ObjectId memberId);

	List<Group> findByMemberIdOrParticipants(ObjectId id, ObjectId id2);

	Optional<List<Group>> findByMemberId(ObjectId id);






}
