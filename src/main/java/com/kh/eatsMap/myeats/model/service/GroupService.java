package com.kh.eatsMap.myeats.model.service;


import java.util.List;

import org.bson.types.ObjectId;

import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.myeats.model.dto.FindCriteria;
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.myeats.model.dto.PageObject;

public interface GroupService {

	//회원 검색
	//테스트용
	public List<Member> listMember(PageObject pageObject) throws Exception;
	//실사용
	public List<Member> listMemberFind(FindCriteria findCri) throws Exception;
	//실사용
	public int findMemberCountData(FindCriteria findCri) throws Exception;
	
	
	//페이징 및 조회/group.jsp
	public List<Group> list(PageObject pageObject);
	
	//groupIdx제외 예정
	public List<Group> read(ObjectId id);

	//NickName으로 Member조회
	//public List<Member> memberlistByNickName(String nickname);
	
	public void write(Group group);
	
	public void remove(String id);
	
	public void modify(Group group) throws Exception;
	
	
}
