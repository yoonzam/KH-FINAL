package com.kh.eatsMap.member.model.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.member.model.dto.Notice;

public interface NoticeRepository extends MongoRepository<Notice, ObjectId> {

	Optional<Notice> findByMemberId(ObjectId id);

}
