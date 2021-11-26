package com.kh.eatsMap.batch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.kh.eatsMap.calendar.model.repository.CalendarRepository;
import com.kh.eatsMap.common.code.ErrorCode;
import com.kh.eatsMap.common.exception.HandlableException;
import com.kh.eatsMap.index.model.repository.ReviewRepository;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.dto.Notice;
import com.kh.eatsMap.member.model.repository.MemberRepository;
import com.kh.eatsMap.member.model.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PushBatch {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final CalendarRepository calendarRepository;
	private final MemberRepository memberRepository;
	private final NoticeRepository noticeRepository;
	private final ReviewRepository reviewRepository;
	
	//d-day 일정 조회 -> 남은시간이 24h 이내면 알림 발송
	//친구 초대 알림인 경우, 친구 초대 시간이 24h이내면 알림 발송
	
	//그룹 초대 알림 : 그룹생성 24h이내면 해당 id를 갖는 그룹의 팀원들 조회해 알림 발송
	@Scheduled(cron = "0 0 12 * * *")
	public void pushAboutSchedule() {
		calendarRepository.findAll().forEach(e -> {
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date = format.parse(e.getDate().toString());
				
				Member member = memberRepository.findById(e.getMemberId());
				Notice notice = noticeRepository.findByMemberId(member.getId());
				
				//현재시간보다 이후면서, 24h 이내
				if(date.after(new Date()) && (date.getTime() - new Date().getTime()) / 3600000 < 24) {
					//1. db에 알림 컬럼 추가
					notice.setCalendarNotice(1);
					memberRepository.save(member);
					
					//2. 푸시 전송 
					
				}else {
					notice.setCalendarNotice(0);
					memberRepository.save(member);
				}
				
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		});
	}
	

}
