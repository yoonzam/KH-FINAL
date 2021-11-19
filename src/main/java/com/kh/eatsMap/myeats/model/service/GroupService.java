package com.kh.eatsMap.myeats.model.service;


import java.util.List;

import com.kh.eatsMap.myeats.model.dto.Group;

public interface GroupService {

	
	public void write(Group group);
	
	public List<Group> list();

}
