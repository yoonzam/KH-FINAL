package com.kh.eatsMap.member.model.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.timeline.model.dto.Review;

public interface MemberReviewRepository  extends MongoRepository<Review, ObjectId>{

	Optional<List<Review>> findOptionalByMemberIdOrderByIdDesc(ObjectId memberId);

}
