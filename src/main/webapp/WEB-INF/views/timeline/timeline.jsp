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
<script defer type="text/javascript" src="/resources/js/timeline/timeline.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
	<section>
		<div class="section">
			<div class="search-wrap">
				<div class="search-area">
					<div class="search-form">
						<label>
							<input type="text" placeholder="찾으시는 맛집이나 유저 이름을 입력하세요."/>
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
								<span>종류</span>
								<label class="checked">#한식<input type="checkbox" value="" checked/></label>
								<label class="checked">#중식<input type="checkbox" value="" checked/></label>
								<label class="checked">#양식<input type="checkbox" value="" checked/></label>
								<label class="checked">#일식<input type="checkbox" value="" checked/></label>
								<label class="checked">#카페,디저트<input type="checkbox" value="" checked/></label>
								<label class="checked">#술집<input type="checkbox" value="" checked/></label>
							</li>
							<li>
								<span>분위기</span>
								<label class="checked">#친근함<input type="checkbox" value="" checked/></label>
								<label class="checked">#고급짐<input type="checkbox" value="" checked/></label>
								<label class="checked">#가족<input type="checkbox" value="" checked/></label>
								<label class="checked">#데이트<input type="checkbox" value="" checked/></label>
								<label class="checked">#혼밥<input type="checkbox" value="" checked/></label>
							</li>
							<li>
								<span>가격대</span>
								<label class="checked">#가성비<input type="checkbox" value="" checked/></label>
								<label class="checked">#가심비<input type="checkbox" value="" checked/></label>
								<label class="checked">#1~2만원대<input type="checkbox" value="" checked/></label>
								<label class="checked">#2~3만원대<input type="checkbox" value="" checked/></label>
								<label class="checked">#3만원 이상<input type="checkbox" value="" checked/></label>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="timeline-wrap">
				<h2><i class="fas fa-utensils color-m"></i> 실시간 <span class="color-m">잇친 PICK</span> 맛집을 소개합니다!</h2>
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
									<div class="eats-name">${reviews.resName} <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
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
	<script>
		let photoReviewCnt;
		let viewTimeline = (reviewId) => {
			$.ajax({
				type: 'GET',
				url: '/timeline/detail',
				data:{ id:reviewId },
				dataType: 'json',
				success: (data) => {
					photoReviewCnt = 0;
					$('#pop-review-detail .slide-img').css('transform','translateX(0px)');
					//사진
					let html = '';
					for (var i = 0; i < data.files.length; i++) html += '<li><img src="'+data.files[i]+'"></li>';
					$('#pop-review-detail .slide-img').html(html);
					html = '';
					for (var i = 0; i < data.files.length; i++) html += '<div class="dot" data-num="'+i+'"></div>';
					$('#pop-review-detail .dot-btn').html(html);
					$('#pop-review-detail .dot-btn > div:first-child').addClass('selected');
					
					//사진 슬라이드 버튼
					if(data.files.length > 1) {
						$('#pop-review-detail .slide-btn').html(
							'<i onclick="prevPhoto('+data.files.length+');" class="fas fa-arrow-circle-left"></i>'+
							'<i onclick="nextPhoto('+data.files.length+');" class="fas fa-arrow-circle-right"></i>');
					} else{
						$('#pop-review-detail .slide-btn').html('');
					}
					
					//작성자
					$('#pop-review-detail .writer').html('<a>'+data.review.memberNick+'</a><a onclick="follow(\''+data.memberId+'\')" class="follow">잇친맺기</a>');
					
					//별점
					html = '<p>맛</p>';
					for (var i = 0; i < 5; i++) {
						if(i < data.review.taste) html += '<i class="fas fa-star">';
						else html += '<i class="far fa-star"></i>';
					}
					$('#pop-review-detail .score > div:first-child').html(html);
					html = '<p>청결</p>';
					for (var i = 0; i < 5; i++) {
						if(i < data.review.clean) html += '<i class="fas fa-star">';
						else html += '<i class="far fa-star"></i>';
					}
					$('#pop-review-detail .score > div:nth-child(2)').html(html);
					html = '<p>서비스</p>';
					for (var i = 0; i < 5; i++) {
						if(i < data.review.service) html += '<i class="fas fa-star">';
						else html += '<i class="far fa-star"></i>';
					}
					$('#pop-review-detail .score > div:last-child').html(html);
					
					//해시태그
					html = '';
					for (var i = 0; i < data.review.hashtag.length; i++) html += '<span>#'+data.review.hashtag[i]+'</span>';
					$('#pop-review-detail .review > .tag').html(html);
					
					//리뷰
					$('#pop-review-detail .review > p').html(data.review.review);
					
					//음식점정보&카테고리
					$('#pop-review-detail .info > .title').html('<i class="fas fa-home"></i> '+data.review.resName+' ('+data.review.category+')');
					$('#pop-review-detail .info > .location').html(data.review.addr);
					
					//세부버튼
					$('#pop-review-detail .pop-btn-edit').attr('onclick','editReview(\''+reviewId+'\');');
					$('#pop-review-detail .pop-btn-delete').attr('onclick','deleteReview(\''+reviewId+'\');');

					$('#pop-review-detail').fadeIn(200);
					resizeSlideImgHeight();
				},
				error: function (e) {
					alert('에러발생');
				}
			});
		}
		
		//사진 슬라이드
		let movePhoto = () => {
			$('#pop-review-detail .dot-btn > div').removeClass('selected');
			$('#pop-review-detail .dot-btn > div[data-num="'+photoReviewCnt+'"]').addClass('selected');
			let slideWidth = $('#pop-review-detail .slide-img img').width();
			$('#pop-review-detail .slide-img').css('transform','translateX('+(-slideWidth*photoReviewCnt)+'px)');
		}
		
		let nextPhoto = (length) => {
			if(photoReviewCnt == length-1) return;
			if(photoReviewCnt == length-2) $('#pop-review-detail .slide-btn i.fa-arrow-circle-right').hide();
			photoReviewCnt++;
			movePhoto();
			$('#pop-review-detail .slide-btn i.fa-arrow-circle-left').show();
		}
		
		let prevPhoto = (length) => {
			if(photoReviewCnt == 0) return;
			if(photoReviewCnt == 1) $('#pop-review-detail .slide-btn i.fa-arrow-circle-left').hide();
			photoReviewCnt--;
			movePhoto();
			$('#pop-review-detail .slide-btn i.fa-arrow-circle-right').show();
		}
		
		//리뷰수정&삭제
		let editReview = (reviewId) => {
			$('#pop-review-detail').hide();
			editFlag = true;
			uploadReview(reviewId);
		}
		
		let deleteReview = (reviewId) => {
			$('#pop-review-detail').hide();
			$.ajax({
				type: "POST",
				url: "${contextPath}/timeline/delete?id="+reviewId,
				contentType: false,
				processData: false,
			 	cache:false,
				success: () => {
					alert("성공");
					location.reload();
				},
				error: (e) => {
					alert("실패");
				}
			});
		}
		
	</script>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>