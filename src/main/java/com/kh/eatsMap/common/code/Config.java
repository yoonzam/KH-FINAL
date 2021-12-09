package com.kh.eatsMap.common.code;

public enum Config {
	
	DOMAIN("https://www.eatsmap.ga"),	//배포
	//DOMAIN("http://localhost:9090"),
	SMTP_AUTHENTICATION_ID("khfinal3@gmail.com"),
	SMTP_AUTHENTICATION_PASSWORD("khfinal3131@"),
	COMPANY_EMAIL("khfinal3@gmail.com"),
	UPLOAD_PATH("/usr/local/upload"); //운영서버
	//UPLOAD_PATH("C:\\FINAL\\upload\\"); //개발서버
	
	
	public final String DESC;
	
	private Config(String desc) {
		this.DESC = desc;
	}	

}
