package com.kh.eatsMap.common.code;

public enum HashCode {
	
	MD01("친근함"),
	MD02("고급짐"),
	MD03("가족"),
	MD04("데이트"),
	MD05("혼밥"),
	MD06("회식"),
	PR01("가성비"),
	PR02("가심비"),
	PR03("1~2만원대"),
	PR04("2~3만원대"),
	PR05("3만원 이상");
	
	private String hashtag;
	
	private HashCode(String hashtag) {
		this.hashtag = hashtag;
	}
	
	public String desc() {
		return this.hashtag;
	}
	

}
