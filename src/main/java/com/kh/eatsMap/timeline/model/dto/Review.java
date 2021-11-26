package com.kh.eatsMap.timeline.model.dto;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import com.kh.eatsMap.common.code.Category;
import com.kh.eatsMap.common.code.HashCode;

import lombok.Data;

@Data
public class Review {
	
	@Id
	private ObjectId id;
	private ObjectId memberId;		//작성자
	private String memberNick;		//작성자 닉네임
	private String resName;			//식당명
	private String addr;			//주소
	private String thumUrl;			//썸네일URL
	private String review;			//리뷰내용
	private LocalDateTime regDate;	//작성일
	
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private GeoJsonPoint location;
	private int taste;				//평점
	private int clean;
	private int service;
	
	private String category;		//cg01(한식),cg02(중식),cg03(양식),cg04(일식),cg05(아시아),cg06(분식),cg07(카페/디저트),cg08(술집)
	private String[] hashtag;		//md01(친근함),md02(고급짐),md03(가족),md04(데이트),md05(혼밥),md06(회식),pr01(가성비),pr02(가심비), pr03(1~2만원대), pr04(2~3만원대), pr05(3만원 이상)
	private String group;			//그룹명 (내피드는 생략)
	private int privacy;			//공개여부(0,1,-1)
	private int like;
	
	
	@Override
	public String toString() {
		
		switch (category) {
		case "cg01": category = Category.CG01.desc(); break;
		case "cg02": category = Category.CG02.desc(); break;
		case "cg03": category = Category.CG03.desc(); break;
		case "cg04": category = Category.CG04.desc(); break;
		case "cg05": category = Category.CG05.desc(); break;
		case "cg06": category = Category.CG06.desc(); break;
		case "cg07": category = Category.CG07.desc(); break;
		case "cg08": category = Category.CG08.desc(); break;
		default:
			break;
		}
		
		for (int i = 0; i < hashtag.length; i++) {
			switch (hashtag[i]) {
			case "md01": hashtag[i] = HashCode.MD01.desc(); break;
			case "md02": hashtag[i] = HashCode.MD02.desc(); break;
			case "md03": hashtag[i] = HashCode.MD03.desc(); break;
			case "md04": hashtag[i] = HashCode.MD04.desc(); break;
			case "md05": hashtag[i] = HashCode.MD05.desc(); break;
			case "md06": hashtag[i] = HashCode.MD06.desc(); break;
			case "pr01": hashtag[i] = HashCode.PR01.desc(); break;
			case "pr02": hashtag[i] = HashCode.PR02.desc(); break;
			case "pr03": hashtag[i] = HashCode.PR03.desc(); break;
			case "pr04": hashtag[i] = HashCode.PR04.desc(); break;
			case "pr05": hashtag[i] = HashCode.PR05.desc(); break;
			default:
				break;
			}			
		}

		return "Review [id=" + id + ", memberId=" + memberId + ", resName=" + resName + ", thumUrl=" + thumUrl
				+ ", addr=" + addr + ", review=" + review + ", regDate=" + regDate + ", location=" + location
				+ ", taste=" + taste + ", clean=" + clean + ", service=" + service + ", category=" + category
				+ ", hashtag=" + Arrays.toString(hashtag) + ", group=" + group + ", privacy=" + privacy + ", like="
				+ like + "]";
	}
	
}
