package com.kh.eatsMap.member.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.kh.eatsMap.member.model.repository.MemberRepository;

@Component
public class ModifyValidator implements Validator{
	
	private final MemberRepository memberRepository;

	public ModifyValidator(MemberRepository memberRepository) {
		super();
		this.memberRepository = memberRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(ModifyForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ModifyForm form = (ModifyForm) target;
		
		if(memberRepository.findMemberByNickname(form.getNickname()) != null ) {
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
