package com.kh.eatsMap.myeats.model.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.kh.eatsMap.myeats.model.dto.Group;

public interface GroupRepository extends MongoRepository<Group, String>{	
	
	List<Group> findByGroupIdx(String groupIdx);








}
