package com.kh.eatsMap.timeline.model.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.kh.eatsMap.common.util.Fileinfo;

public interface FileRepository extends MongoRepository<Fileinfo, String>{

	List<Fileinfo> findByTypeId(ObjectId typeId);
	
	void deleteByTypeId(ObjectId typeId);

	void deleteBySavePathAndRenameFileName(String savePath, String renameFileName);

}
