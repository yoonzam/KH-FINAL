package com.kh.eatsMap.member.validator;

import lombok.Data;

@Data
public class JoinForm {
	
	private String nickname;
	private String password;
	private String email;
	private String chkPassword;

}
