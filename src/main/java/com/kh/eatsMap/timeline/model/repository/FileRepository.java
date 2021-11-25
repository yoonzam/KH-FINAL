package com.kh.eatsMap.timeline.model.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.common.util.Fileinfo;

public interface FileRepository extends MongoRepository<Fileinfo, String>{

	List<Fileinfo> findByTypeId(ObjectId id);

}
