package com.kh.eatsMap.index.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.common.util.Fileinfo;

public interface FileRepository extends MongoRepository<Fileinfo, String>{

//	Fileinfo findByTypeId(String fileInfoTypeId);

	
	}
