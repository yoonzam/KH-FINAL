package com.kh.eatsMap.member.model.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	
	private final RestTemplate template;
	private final MailSender mailSender;
	private final MemberRepository memberRepository;

	
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
	public Member authenticateUser(Member member) {
		Member storedMember = new Member();		
		
		storedMember = memberRepository.findByEmail(member.getEmail());
		
		//password encode 이전
		if(storedMember != null && storedMember.getPassword().equals(member.getPassword())) {
			return storedMember;
		}
//		if(storedMember != null  && passwordEncoder.matches(member.getPassword(), storedMember.getPassword())) {
//			return storedMember;
//		}
		
		return null;
	}

	@Override
	public void insertMember(JoinForm form) {
//		form.setPassword(passwordEncoder.encode(form.getPassword()));
		logger.debug(form.toString());
		
		Member member = new Member();
		member.setNickname(form.getNickname());
		member.setPassword(form.getPassword());
		member.setEmail(form.getEmail());
		member.setRegDate(LocalDate.now());
		member.setIsLeave(0);

		memberRepository.insert(member);
	}

	@Override
	public void insertProfileImg(Member member, MultipartFile file) {

		FileUtil fileUtil = new FileUtil();
		member.setProfile(fileUtil.fileUpload(file));
		
		memberRepository.insert(member);
	}

	@Override
	public void updatePassword(String email, String tmpPassword) {
		Member member = memberRepository.findByEmail(email);
		member.setPassword(tmpPassword);
		memberRepository.save(member);
	}

	@Override
	public Member findMember(String kakaoId) {
		return memberRepository.findByKakaoId(kakaoId);
	}

	@Override
	public void saveMember(Member member) {
		memberRepository.save(member);
	}



}
