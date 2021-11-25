package com.kh.eatsMap.myeats.model.repository;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.myeats.model.dto.Group;

public interface GroupRepository extends MongoRepository<Group, String>{	
	
	//groupIdx제외 예정
	List<Group> findById(ObjectId Id);


//	@Query("{ 'nickname' : { '$regex' : ?0 }}")
//    List<Member> findAllByMembers(String regex);






}
