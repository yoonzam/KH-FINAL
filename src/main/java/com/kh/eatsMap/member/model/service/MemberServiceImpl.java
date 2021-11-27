package com.kh.eatsMap.member.model.service;

import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eatsMap.common.code.Config;
import com.kh.eatsMap.common.mail.MailSender;
import com.kh.eatsMap.common.util.FileUtil;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.dto.Notice;
import com.kh.eatsMap.member.model.repository.MemberRepository;
import com.kh.eatsMap.member.model.repository.NoticeRepository;
import com.kh.eatsMap.member.validator.EmailForm;
import com.kh.eatsMap.member.validator.JoinForm;
import com.kh.eatsMap.member.validator.ModifyForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	
	private final RestTemplate template;
	private final MailSender mailSender;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final NoticeRepository noticeRepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void authenticateByEmail(JoinForm form, String token) {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("mail-template", "join-auth-email");
		body.add("nickname", form.getNickname());
		body.add("persistToken", token);
		
		//RestTemplate의 기본 ContentType이 application/json이다.
		RequestEntity<MultiValueMap<String, String>> request = RequestEntity.post(Config.DOMAIN.DESC+"/mail")
													.accept(MediaType.APPLICATION_FORM_URLENCODED)
													.body(body);
		
		String htmlText = template.exchange(request, String.class).getBody();
		mailSender.sendEmail(form.getEmail(), "회원가입을 축하합니다.", htmlText);
	}

	@Override
	public void sendTmpPassword(EmailForm form, String tmpPassword) {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("mail-template", "tmp-password-email");
		body.add("tmp", tmpPassword);
		
		//RestTemplate의 기본 ContentType이 application/json이다.
		RequestEntity<MultiValueMap<String, String>> request = RequestEntity.post(Config.DOMAIN.DESC+"/mail")
													.accept(MediaType.APPLICATION_FORM_URLENCODED)
													.body(body);
		
		String htmlText = template.exchange(request, String.class).getBody();
		mailSender.sendEmail(form.getEmail(), "[Eat's Map] 임시 비밀번호입니다.", htmlText);
	}

	@Override
	public Map<String,Object> authenticateUser(Member member) {
		Member storedMember = memberRepository.findByEmailAndIsLeave(member.getEmail(), 0);
	
		if(storedMember != null  && passwordEncoder.matches(member.getPassword(), storedMember.getPassword())) {
			Notice notice = noticeRepository.findByMemberId(storedMember.getId());
			int noticeCnt = notice.getCalendarNotice() + notice.getGroupNotice() + notice.getParticipantNotice();
			
			return Map.of("noticeCnt", noticeCnt, "notice", notice, "member",storedMember);
		}		
		return null;
	}

	@Override
	public void insertMember(JoinForm form) {
		Member member = new Member();	//mongoDB ValidatorForm <-> 객체매핑 X
		member.setEmail(form.getEmail());
		member.setPassword(passwordEncoder.encode(form.getPassword()));
		member.setRegDate();
		member.setNickname(form.getNickname());
		member.setIsLeave(0);
		memberRepository.save(member);

		Notice notice = new Notice();
		Member joinUser = memberRepository.findById(member.getId());
		notice.setMemberId(joinUser.getId());
		notice.setCalendarNotice(0);
		notice.setGroupNotice(0);
		notice.setParticipantNotice(0);
		notice.setFollowNotice(0);
	}

	@Override
	public void insertProfileImg(Member member, MultipartFile file) {
		FileUtil fileUtil = new FileUtil();
		member.setProfile(fileUtil.fileUpload(file));
		
		memberRepository.save(member);
	}

	@Override
	public void updatePassword(String email, String tmpPassword) {
		Member member = memberRepository.findByEmail(email);
		member.setPassword(tmpPassword);
		memberRepository.save(member);
	}

	@Override
	public Member findKakaoMember(String kakaoId) {
		return memberRepository.findByKakaoId(kakaoId);
	}

	@Override
	public void saveMember(Member member) {
		memberRepository.save(member);
	}

	@Override
	public Member findMemberByNickname(String nickname) {
		return memberRepository.findByNicknameIgnoreCase(nickname);
	}

	@Override
	public void updateMemberProfile(Member member, ModifyForm form) {
		member.setNickname(form.getNickname());
		member.setPassword(form.getPassword());
		
		memberRepository.save(member);
	}

	@Override
	public void isLeaveMember(Member member) {
		member.setIsLeave(1);
		memberRepository.save(member);
	}

	@Override
	public void updateNotice(String id, Notice notice) {
		switch (id) {
		case "dday": notice.setCalendarNotice(0); break;
		case "calendar": notice.setParticipantNotice(0); break;
		case "follow": notice.setFollowNotice(0); break;
		case "group": notice.setGroupNotice(0); break;	}	
		
		noticeRepository.save(notice);
	}



}
