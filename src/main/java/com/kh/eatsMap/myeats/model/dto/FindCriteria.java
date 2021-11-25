package com.kh.eatsMap.myeats.model.dto;

public class FindCriteria extends PageObject{
	//extends PageCriteria상속받은 이유: page,perPageNum를 그대로 유지하기 위해서
	private String findType;
	private String keyword;
	
	public String getFindType(){
		return findType;
	}
	
	public void setFindType(String findType){
		this.findType = findType;
	}
	
	public String getKeyword(){
		return keyword;
	}
	
	public void setKeyword(String keyword){
		this.keyword = keyword;
	}
		
	@Override
	public String toString(){
		return super.toString() + " findCriteria" + "[findType="+findType+", keyword="
				+keyword+"]";
	}
}
