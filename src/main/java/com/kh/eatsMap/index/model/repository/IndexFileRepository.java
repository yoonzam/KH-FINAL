package com.kh.eatsMap.index.model.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.kh.eatsMap.common.util.Fileinfo;

public interface IndexFileRepository extends MongoRepository<Fileinfo, String>{

	List<Fileinfo> findByTypeId(Object id);
	
	}
