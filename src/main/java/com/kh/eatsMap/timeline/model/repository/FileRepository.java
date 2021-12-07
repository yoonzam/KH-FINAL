package com.kh.eatsMap.timeline.model.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.kh.eatsMap.common.util.Fileinfo;

public interface FileRepository extends MongoRepository<Fileinfo, String>{

	List<Fileinfo> findByTypeId(ObjectId typeId);
	
	void deleteByTypeId(ObjectId typeId);

	void deleteBySavePathAndRenameFileName(String savePath, String renameFileName);

	Fileinfo findTopByTypeId(ObjectId id, Sort descending);

	Optional<Fileinfo> findOptionalTopByTypeId(ObjectId id, Sort descending);

}
