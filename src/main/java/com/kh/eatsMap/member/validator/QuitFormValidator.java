package com.kh.eatsMap.member.validator;


import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.kh.eatsMap.member.model.repository.MemberRepository;

public class QuitFormValidator implements Validator{
	
	private final MemberRepository memberRepository;
	
	public QuitFormValidator(MemberRepository memberRepository) {
		super();
		this.memberRepository = memberRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(QuitForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		QuitForm form = (QuitForm) target;
		
		if(!memberRepository.findByNickname(form.getNickname()).getPassword().equals(form.getPassword())) {
			errors.rejectValue("password", "err-password", "비밀번호가 일치하지 않습니다.");
		}
	}


}
