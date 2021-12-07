<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<div class="search-form">
		<label>
			<input type="text" name="keyword_" placeholder="찾으시는 맛집을 입력하세요." value="${keyword}"/>
		</label>
		<a class="filter-btn"><i class="fas fa-filter">검색필터</i></a>
		<button type="submit"><i class="fas fa-search"></i></button>
	</div>
	<div class="filter-menu">
		<ul>
			<li>
				<span>선택</span>
				<label id="allCheck" class="checked">전체선택</label>
			</li>
			<li>
				<span>지역</span>
				<label class="checked">서울<input type="checkbox" name="area_" value="02" checked/></label>
				<label class="checked">인천<input type="checkbox" name="area_" value="032" checked/></label>
				<label class="checked">경기<input type="checkbox" name="area_" value="031" checked/></label>
				<label class="checked">강원<input type="checkbox" name="area_" value="033" checked/></label>
				<label class="checked">세종<input type="checkbox" name="area_" value="044" checked/></label>
				<label class="checked">충북<input type="checkbox" name="area_" value="043" checked/></label>
				<label class="checked">충남<input type="checkbox" name="area_" value="041" checked/></label>
				<label class="checked">대전<input type="checkbox" name="area_" value="042" checked/></label>
				<label class="checked">광주<input type="checkbox" name="area_" value="062" checked/></label>
				<label class="checked">전북<input type="checkbox" name="area_" value="063" checked/></label>
				<label class="checked">전남<input type="checkbox" name="area_" value="061" checked/></label>
				<label class="checked">대구<input type="checkbox" name="area_" value="053" checked/></label>
				<label class="checked">경북<input type="checkbox" name="area_" value="054" checked/></label>
				<label class="checked">경남<input type="checkbox" name="area_" value="055" checked/></label>
				<label class="checked">울산<input type="checkbox" name="area_" value="052" checked/></label>
				<label class="checked">부산<input type="checkbox" name="area_" value="051" checked/></label>
				<label class="checked">제주<input type="checkbox" name="area_" value="064" checked/></label>
			</li>
			<li>
				<span>카테고리</span>
				<label class="checked">#한식<input type="checkbox" name="category_" value="cg01" checked/></label>
				<label class="checked">#중식<input type="checkbox" name="category_" value="cg02" checked/></label>
				<label class="checked">#양식<input type="checkbox" name="category_" value="cg03" checked/></label>
				<label class="checked">#일식<input type="checkbox" name="category_" value="cg04" checked/></label>
				<label class="checked">#아시아<input type="checkbox" name="category_" value="cg05" checked/></label>
				<label class="checked">#분식<input type="checkbox" name="category_" value="cg06" checked/></label>
				<label class="checked">#카페/디저트<input type="checkbox" name="category_" value="cg07" checked/></label>
				<label class="checked">#술집<input type="checkbox" name="category_" value="cg08" checked/></label>
			</li>
			<li>
				<span>해시태그</span>
				<label class="checked">#친근함<input type="checkbox" name="hashtag_" value="md01" checked/></label>
				<label class="checked">#고급짐<input type="checkbox" name="hashtag_" value="md02" checked/></label>
				<label class="checked">#가족<input type="checkbox" name="hashtag_" value="md03" checked/></label>
				<label class="checked">#데이트<input type="checkbox" name="hashtag_" value="md04" checked/></label>
				<label class="checked">#혼밥<input type="checkbox" name="hashtag_" value="md05" checked/></label>
				<label class="checked">#회식<input type="checkbox" name="hashtag_" value="md06" checked/></label>
				<label class="checked">#가성비<input type="checkbox" name="hashtag_" value="pr01" checked/></label>
				<label class="checked">#가심비<input type="checkbox" name="hashtag_" value="pr02" checked/></label>
				<label class="checked">#1~2만원대<input type="checkbox" name="hashtag_" value="pr03" checked/></label>
				<label class="checked">#2~3만원대<input type="checkbox" name="hashtag_" value="pr04" checked/></label>
				<label class="checked">#3만원 이상<input type="checkbox" name="hashtag_" value="pr05" checked/></label>
			</li>
		</ul>
	</div>