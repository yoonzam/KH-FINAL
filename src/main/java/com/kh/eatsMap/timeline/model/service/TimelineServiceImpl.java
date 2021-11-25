package com.kh.eatsMap.timeline.model.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eatsMap.common.util.FileUtil;
import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.timeline.model.dto.Review;
import com.kh.eatsMap.timeline.model.repository.FileRepository;
import com.kh.eatsMap.timeline.model.repository.TimelineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimelineServiceImpl implements TimelineService{

	private final TimelineRepository timelineRepository;
	private final FileRepository fileRepository;

	@Override
	public void insertReview(Review review, List<MultipartFile> photos) {
		review.setMemberId(new ObjectId("6194d4e8b1271f6645746389")); //memberId 셋팅 나중에
		review = timelineRepository.save(review);
		
		FileUtil fileUtil = new FileUtil();
		for (MultipartFile photo : photos) {
			if(!photo.isEmpty()) {
				Fileinfo fileInfo = fileUtil.fileUpload(photo);
				fileInfo.setTypeId(review.getId());
				fileRepository.save(fileInfo);
			}
		}
	}
}
