package com.kh.eatsMap.myeats.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.eatsMap.myeats.model.repository.MyeatsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyeatsServiceImpl implements MyeatsService{

	@Autowired
	private MyeatsRepository myeatsRepository;
}
