package com.kh.eatsMap.myeats.model.service;


import java.util.List;

import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.myeats.model.dto.PageObject;

public interface GroupService {

	
	public void write(Group group);
	
//	public List<Group> list();
	
	public List<Group> list(PageObject pageObject);
	
	public List<Group> read(String groupIdx);
	
	public void remove(String id);
	
	
	
	
}
