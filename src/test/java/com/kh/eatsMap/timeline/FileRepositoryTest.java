package com.kh.eatsMap.timeline;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.timeline.model.repository.FileRepository;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
public class FileRepositoryTest {

	@Autowired
	FileRepository fileRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	WebApplicationContext context;
	private MockMvc mockMvc;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(context).build();
	}
	
	@Test //file 저장
	public void saveFile() {
		Fileinfo fileInfo = new Fileinfo();
		fileInfo.setTypeId(new ObjectId("6194d4e8b1271f6645746389"));
		fileInfo.setSavePath("2021/11/27/");
		fileInfo.setRenameFileName("4f2a6749-83f1-430d-8886-6d20031635c4.jpg");
		fileRepository.save(fileInfo);
	}
	
	@Test //typeId(리뷰ID)로 파일 삭제
	public void deleteByTypeId() {
		fileRepository.deleteByTypeId(new ObjectId("6194d4e8b1271f6645746389"));
	}
	
	@Test //savePath & renameFileName으로 파일 삭제
	public void deleteFile() {
		Fileinfo fileInfo = new Fileinfo();
		fileInfo.setSavePath("2021/11/27/");
		fileInfo.setRenameFileName("4f2a6749-83f1-430d-8886-6d20031635c4.jpg");
		fileRepository.deleteBySavePathAndRenameFileName(fileInfo.getSavePath(), fileInfo.getRenameFileName());
	}
}
