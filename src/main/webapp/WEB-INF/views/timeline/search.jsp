<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/resources/css/timeline/timeline.css" />
<script defer type="text/javascript" src="/resources/js/timeline/search.js"></script>

<!-- paging -->

<script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
	<section>
		<div class="section">
			<div class="search-wrap">
				<div class="search-area">
				<form action="/timeline/search" method="get">
					<div class="search-form">
						<label>
							<input type="text" name="keyword_" placeholder="찾으시는 맛집이나 유저 이름을 입력하세요."/>
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
					</form>
				</div>
			</div>
			<div class="timeline-wrap">
				<h2><i class="fas fa-search color-m"></i> <span class="color-m">${keyword}</span> 검색결과</h2>
				<ul class="timeline-brd">
					<c:forEach items="${reviews}" var="reviews">
						<li onclick="viewTimeline('${reviews.id}')">
							<div class="eats-list">
								<div class="thum">
									<img src="${!empty reviews.thumUrl ? reviews.thumUrl : '/resources/img/common/upload-logo.png'}">
								</div>
								<div class="info">
									<div class="eats-location">
										<c:set var="addr" value="${fn:split(reviews.addr,' ')}" />
										${addr[0]} ${addr[1]}&nbsp;&#62;&nbsp;${reviews.category}
									</div>
									<div class="eats-name">
										${reviews.resName} <i data-like="${reviews.id}" class="eats-like ${reviews.like > 0 ? 'fas fa-heart' : 'far fa-heart'}"></i>
									</div>
									<div class="eats-tag">
										<c:forEach items="${reviews.hashtag}" var="hashtag">
											<span>&#35;${hashtag}</span>
										</c:forEach>
									</div>
									<div class="eats-score">
										<i class="fas fa-star"></i>
										<fmt:formatNumber value="${(reviews.taste+reviews.clean+reviews.service)/3}" pattern=".0"/>
									</div>
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</section>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>