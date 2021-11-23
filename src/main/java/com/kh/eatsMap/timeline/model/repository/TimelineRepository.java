package com.kh.eatsMap.timeline.model.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.kh.eatsMap.timeline.model.dto.Review;

public interface TimelineRepository extends MongoRepository<Review, String>{

	List<Review> findByResNameOrderByIdAsc(String resName, Sort sort);

}
