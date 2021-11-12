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
<style type="text/css">
.container {
	width: 100%;
	margin: auto;
	display: grid;
	grid-template-columns: repeat(4, 1fr); /* 균일한 크기로 3등분 */
	grid-template-rows: repeat(18, 40px);
	/* 40px로 24등분 -> <main> 영역의 전체 height가 계산된다면 height 지정 후 1fr로 사용하셔도 좋습니다.*/
}

.search {
	grid-column: 1/5; /* 시작column번호 / 끝column번호 */
	grid-row: 1/3; /* 시작row번호 / 끝row번호 */
	padding-top: 8px;
}

.search-form {
	position: relative;
	display: flex;
	justify-content: space-between;
}

.review {
	grid-column: 1/2;
	grid-row: 3/19;
	display: flex;
	flex-direction: column;
	overflow: scroll;
	z-index: 3;
	padding-left: 10px;
	z-index: 3;
}

.review::-webkit-scrollbar {
	display: none;
}
body::-webkit-scrollbar {
	display: none;
}

.eatsMap {
	grid-column: 1/5;
	grid-row: 3/19;
}

.search-form input {
	width: 80%;
	padding: 20px 40px 20px 20px;
	font-size: 18px;
	border: none;
}

.search-form .search-btn {
	top: 9px;
	left: 9px;
	width: 120px;
	border: 1px solid #ddd;
	background-color: #fff;
	padding: 10px;
	text-align: center;
	cursor: pointer;
	color: #fa8633;
	font-weight: 900;
}

.search-form .search-btn:hover {
	border: 1px solid #fa8633;
}

.friends {
	position: absolute;
	right: 10px;
}

.category {
	position: absolute;
	right: 10;
}

.search-box {
	width: 30%;
	margin-left: 10px;
}

.select {
	width: inherit;
	height: inherit;
	background: transparent;
	border: 0 none;
	outline: 0 none;
	padding: 0 5px;
	position: relative;
	z-index: 3; /*  select가 위로 올라와야 함 */
	font-size: 20px;
}

select option {
	background: #fa8633;
	color: #fff;
	padding: 3px 0;
	font-size: 16px;
}

.select-bar {
	align-self: center;
	border: dashed;
	margin-right: 10px;
	border-color: #ff7f50;
}

.review_wrap {
	margin-bottom: 10px;
	margin-top: 10px;
	width: 98%;
	height: 25%;
	display: flex;
	border-radius: 8px;
	border: 1px;
	background-color: white;
	box-shadow: 2px 2px 4px rgb(0 0 0 / 30%);
}

.img-box {
	width: 50%;
	height: 100%;
	margin-right: 10px;
}

.image-thumbnail {
	width: 100%;
	height: 100%;
	object-fit: cover;
	border-radius: 8px 0px 0px 8px;
	
}

.eats-tag span {
	display: inline-block;
	background-color: #e7e2df;
	border-radius: 5px;
	padding: 3px;
	margin-bottom: 3px;
	margin-right: 3px;
}

.eats-name {
	color: #333;
	font-size: 20px;
	font-weight: 700;
	margin-top: 10px;
	margin-bottom: 10px;
}

.info {
	margin-left: 10px;
}

.eats-location {
	margin-bottom: 10px;
}

#map {
	width: 100%;
	height: 100%;
}
</style>
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