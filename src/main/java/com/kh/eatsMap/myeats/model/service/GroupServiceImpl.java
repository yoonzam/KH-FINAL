package com.kh.eatsMap.myeats.model.service;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eatsMap.common.code.Config;
import com.kh.eatsMap.common.mail.MailSender;
import com.kh.eatsMap.common.util.FileUtil;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.repository.MemberRepository;
import com.kh.eatsMap.member.validator.EmailForm;
import com.kh.eatsMap.member.validator.JoinForm;
import com.kh.eatsMap.myeats.model.dao.GroupDAO;
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.myeats.model.repository.GroupRepository;
import com.webjjang.util.PageObject;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService{
	
	private final RestTemplate template;
	private final MailSender mailSender;
	private final GroupRepository groupRepository;
	
	 @Autowired
	 private MongoTemplate mongoTemplate;
	
	 @Autowired
	 private final GroupDAO dao;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Override
	public void write(Group group) {
		String StringgroupIdx = Integer.toString((int)groupRepository.count()+1);
		group.setGroupIdx(StringgroupIdx);//1부터시작에서 1씩 증가하도록
		groupRepository.save(group);
	}
//	@Override
//	public List<Group> list(){
//		return mongoTemplate.findAll(com.kh.eatsMap.myeats.model.dto.Group.class, "group");
//		//return groupRepository.findAll();
//		}
	
	//페이징
	@Override
	public List<Group> list(PageObject pageObject){
		System.out.println("GroupServiceIp.list(pageObject : " + pageObject);
		//GroupDAO의 전체 행의 개수를 받아서 pageObject객체의 TotalRow변수 초기화
		pageObject.setTotalRow(dao.getTotalCount());
		
		return dao.list(pageObject);
	}

	//groupIdx로 Group 읽어드리기
	@Override
	public List<Group> read(String groupIdx){
		return groupRepository.findByGroupIdx(groupIdx);
	}
	
	//id로 Group 삭제, groupRepository사용해서 id로 받음, mongtem으로 변경해도 됨
	@Override
	public void remove(String id){
		//String StringgroupIdx = Integer.toString(groupIdx);
		groupRepository.deleteById(id);
	}
	




}
