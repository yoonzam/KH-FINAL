package com.kh.eatsMap.member.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.kh.eatsMap.member.model.repository.MemberRepository;

@Component
public class EmailFormValidator implements Validator{
	
	private final MemberRepository memberRepository;
	Logger logger =  LoggerFactory.getLogger(this.getClass());

	public EmailFormValidator(MemberRepository memberRepository) {
		super();
		this.memberRepository = memberRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(EmailForm.class);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		EmailForm form = (EmailForm) target;
		
		//이메일 확인
		if(memberRepository.findByEmail(form.getEmail()) == null ) {
			errors.rejectValue("email", "err-email", "해당 이메일로 가입한 회원은 존재하지 않습니다.");
		}
	}	
	

}

