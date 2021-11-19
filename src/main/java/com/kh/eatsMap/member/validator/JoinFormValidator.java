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
		
		//이메일 조회 -> 미가입 회원 여부 검증
		if(memberRepository.findByEmailAndIsLeave(form.getEmail(), 0) != null) {
			errors.rejectValue("email", "err-email", "이미 가입된 이메일입니다.");
		}
		
		if(memberRepository.findByNickname(form.getNickname()) != null ) {
			errors.rejectValue("nickname", "err-nickname", "이미 존재하는 닉네임 입니다.");
		}
		
		if(!Pattern.matches("(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Zㄱ-힣0-9]).{8,15}", form.getPassword())) {
			errors.rejectValue("password", "err-password", "비밀번호는 영문자, 특수문자, 숫자 조합 8~15자입니다.");
		}

		if(!form.getChkPassword().equals(form.getPassword())) {
			errors.rejectValue("chkPassword", "err-chk-password", "비밀번호가 일치하지 않습니다.");
		}
	}

	
	
	
	
	
	
	
	
}
