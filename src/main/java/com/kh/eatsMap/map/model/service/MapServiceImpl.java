package com.kh.eatsMap.map.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.eatsMap.map.model.repository.MapRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService{

	@Autowired
	private MapRepository mapRepository;

}
