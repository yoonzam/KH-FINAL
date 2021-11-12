<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<link rel="stylesheet" type="text/css"
	href="/resources/css/timeline/timeline.css" />
<script type="text/javascript"
	src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=5bdae166c6881cf42916fd1d25349e6e&libraries=services,clusterer,drawing"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/map/map.css" />
</head>
<body>
	<%@ include file="/WEB-INF/views/include/header.jsp"%>
	<section>
		<div class="container">
			<div class="search">
				<div class="search-form">
					<div class="search-box">
						<label><input type="text" placeholder="식당 후기 검색하기"></label>
						<a class="search-btn">검색</a>
					</div>
					<div class="select-bar">
						<select name="category" class="select">
							<option disabled="disabled" selected="selected">🎈잇츠맵카테고리</option>
							<option>니캉내캉</option>
							<option>잇친맵</option>
							<option>소셜맵</option>
						</select> <select name="friends" class="select">
							<option disabled selected>🍟잇친이들의 맛집</option>
							<option>이지원</option>
							<option>김지원</option>
							<option>박지원</option>
							<option>최지원</option>
						</select>
					</div>
				</div>
			</div>
			<div class="review">
				<div class="review_wrap">
					<div class="img-box">
						<img class="image-thumbnail" src="/resources/img/upload/01.jpg">
					</div>
					<div class="info">
						<div class="eats-name">
							스시 아루히 &emsp;&emsp;&emsp;<i onclick="clickLike();" class="fas fa-lock"></i>
						</div>
						<div class="eats-location">서울 영등포구</div>
						<div class="eats-tag">
							<span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span>
						</div>
					</div>
				</div>

				<div class="review_wrap">
					<div class="img-box">
						<img class="image-thumbnail" src="/resources/img/upload/01.jpg">
					</div>
					<div class="info">
						<div class="eats-name">


							스시 아루히 &emsp;&emsp;&emsp;<i onclick="clickLike();" class="fas fa-unlock"></i>
						</div>
						<div class="eats-location">서울 영등포구</div>
						<div class="eats-tag">
							<span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span>
						</div>
					</div>
				</div>
				<div class="review_wrap">
					<div class="img-box">
						<img class="image-thumbnail" src="/resources/img/upload/01.jpg">
					</div>
					<div class="info">
						<div class="eats-name">


							스시 아루히 &emsp;&emsp;&emsp;<i onclick="clickLike();" class="fas fa-unlock"></i>
						</div>
						<div class="eats-location">서울 영등포구</div>
						<div class="eats-tag">
							<span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span>
						</div>
					</div>
				</div>
				<div class="review_wrap">
					<div class="img-box">
						<img class="image-thumbnail" src="/resources/img/upload/01.jpg">
					</div>
					<div class="info">
						<div class="eats-name">


							스시 아루히 &emsp;&emsp;&emsp;<i onclick="clickLike();" class="fas fa-unlock"></i>
						</div>
						<div class="eats-location">서울 영등포구</div>
						<div class="eats-tag">
							<span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span>
						</div>
					</div>
				</div>
				<div class="review_wrap">
					<div class="img-box">
						<img class="image-thumbnail" src="/resources/img/upload/01.jpg">
					</div>
					<div class="info">
						<div class="eats-name">


							스시 아루히 &emsp;&emsp;&emsp;<i onclick="clickLike();" class="fas fa-unlock"></i>
						</div>
						<div class="eats-location">서울 영등포구</div>
						<div class="eats-tag">
							<span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span>
						</div>
					</div>
				</div>
				<div class="review_wrap">
					<div class="img-box">
						<img class="image-thumbnail" src="/resources/img/upload/01.jpg">
					</div>
					<div class="info">
						<div class="eats-name">


							스시 아루히 &emsp;&emsp;&emsp;<i onclick="clickLike();" class="fas fa-unlock"></i>
						</div>
						<div class="eats-location">서울 영등포구</div>
						<div class="eats-tag">
							<span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span>
						</div>
					</div>
				</div>
			</div>
			<div class="eatsMap">
				<div id="map"></div>
			</div>
		</div>

	</section>

	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
	<script type="text/javascript">
		var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
		var options = { //지도를 생성할 때 필요한 기본 옵션
			center : new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
			level : 3
		//지도의 레벨(확대, 축소 정도)
		};

		var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
	</script>

</body>
</html>