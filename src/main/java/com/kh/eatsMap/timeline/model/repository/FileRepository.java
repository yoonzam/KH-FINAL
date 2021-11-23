package com.kh.eatsMap.timeline.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.common.util.Fileinfo;

public interface FileRepository extends MongoRepository<Fileinfo, String>{

}
