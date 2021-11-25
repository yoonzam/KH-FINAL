package com.kh.eatsMap.common.code;

public enum Category {
	
	CG01("한식"),
	CG02("중식"),
	CG03("양식"),
	CG04("일식"),
	CG05("아시아"),
	CG06("분식"),
	CG07("카페/디저트"),
	CG08("술집");
	
	private String food;

	private Category(String food) {
		this.food = food;
	}

	public String desc() {
		return this.food;
	}
}
