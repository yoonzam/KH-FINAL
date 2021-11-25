package com.kh.eatsMap.calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.eatsMap.calendar.model.repository.CalendarRepository;
import com.kh.eatsMap.member.model.repository.MemberRepository;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
public class CalendarMongoTest {
	
    @Autowired 
    private MemberRepository repository;
    
    @Autowired 
    private CalendarRepository calendarRepository;
    
    Logger logger = LoggerFactory.getLogger(this.getClass());

	
    @Test
    public void memberCount() {
    	logger.info("몇 명?"+ repository.count());
    }

    
    @Test
    public void existMemberById() {
    	logger.info("아이디가 ~인 멤버 존재? " + repository.existsById("618df2c53519c259d27dc5ef"));
    }
    
    @Test
    public void calendarCount() {
    	logger.debug("몇 개?" + calendarRepository.count());
    }
    @Test
    public void calendarAll() {
    	calendarRepository.findAll().forEach(e -> logger.debug(e.toString()));
    }

}
