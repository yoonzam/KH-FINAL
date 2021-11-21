package com.kh.eatsMap.myeats.model.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.myeats.model.repository.GroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService{
	
	private final RestTemplate template;
	private final MailSender mailSender;
	private final GroupRepository groupRepository;
	
	
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Override
	public void write(Group group) {
		String StringgroupIdx = Integer.toString((int)groupRepository.count()+1);
		group.setGroupIdx(StringgroupIdx);//1부터시작에서 1씩 증가하도록
		groupRepository.save(group);
	}
	
	@Override
	public List<Group> list(){
		return groupRepository.findAll();
	}

	@Override
	public List<Group> read(String groupIdx){
		return groupRepository.findByGroupIdx(groupIdx);
	}
	
	@Override
	public void remove(String id){
		groupRepository.deleteById(id);
	}
	



}
