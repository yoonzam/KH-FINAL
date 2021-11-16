package com.kh.eatsMap.member.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.kh.eatsMap.member.model.repository.MemberRepository;

@Component
public class JoinFormValidator implements Validator{
	
	private final MemberRepository memberRepository;

	public JoinFormValidator(MemberRepository memberRepository) {
		super();
		this.memberRepository = memberRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(JoinForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		JoinForm form = (JoinForm) target;
		
		//1. 닉네임 중복확인
		if(memberRepository.findMemberByNickname(form.getNickname()) != null ) {
			errors.rejectValue("nickname", "err-nickname", "이미 존재하는 닉네임 입니다.");
		}
		
		//2. 비밀번호가 8글자 이상, 숫자 영문자 특수문자 조합인 지 확인
		if(!Pattern.matches("(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Zㄱ-힣0-9]).{8,}", form.getPassword())) {
			errors.rejectValue("password", "err-password", "비밀번호는 숫자 영문자 특수문자 조합인 8글자 이상의 문자열입니다.");
		}

		//3. 비밀번호 일치 여부
		if(!form.getChkPassword().equals(form.getPassword())) {
			errors.rejectValue("chkPassword", "err-chk-password", "비밀번호가 일치하지 않습니다.");
		}
	}

	
	
	
	
	
	
	
	
}
