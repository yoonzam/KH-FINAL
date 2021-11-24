<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/resources/css/main/search.css" />
<script defer type="text/javascript" src="/resources/js/main/search.js"></script>

<!-- paging -->

<script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
	<section>
		<div class="section">
			<!--div class="main-visual">
			</div-->
			<div class="search-wrap">
				<div class="search-area">
				<form action="/main/search" method="post">
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
								<span>종류</span>
								<label class="checked">#한식<input type="checkbox" name="category_" value="cg01" checked value="cg01" checked/></label>
								<label class="checked">#중식<input type="checkbox" name="category_" value="cg02" checked/></label>
								<label class="checked">#양식<input type="checkbox" name="category_" value="cg03" checked/></label>
								<label class="checked">#일식<input type="checkbox" name="category_" value="cg04" checked/></label>
								<label class="checked">#카페,디저트<input type="checkbox" name="category_" value="cg04" checked/></label>
								<label class="checked">#술집<input type="checkbox" name="category_" value="cg05" checked/></label>
							</li>
							<li>
								<span>분위기</span>
								<label class="checked">#친근함<input type="checkbox" name="hashtag_" value="md01" checked/></label>
								<label class="checked">#고급짐<input type="checkbox" name="hashtag_" value="md02" checked/></label>
								<label class="checked">#가족<input type="checkbox" name="hashtag_" value="md03" checked/></label>
								<label class="checked">#데이트<input type="checkbox" name="hashtag_" value="md04" checked/></label>
								<label class="checked">#혼밥<input type="checkbox" name="hashtag_" value="md05" checked/></label>
							</li>
							<li>
								<span>가격대</span>
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
				<h2><i class="fas fa-search color-m"></i> <span class="color-m">영등포 술집</span> 검색결과</h2>
				<ul class="timeline-brd">
					<li>
						<div class="eats-list">
							<div class="thum">
								<img src="${photo}">
							</div>
							<div class="info">
								<div class="eats-location">서울 영등포구</div>
								<div class="eats-name">스시 아루히 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
								<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
								<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
							</div>
						</div>
					</li>
					<li>
						<div class="eats-list">
							<div class="thum" id="">
								<img src="/resources/img/upload/02.jpg">
							</div>
							<div class="info">
								<div class="eats-location">서울 영등포구</div>
								<div class="eats-name">스시 아루히 <i class="eats-like far fa-heart"></i></div>
								<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
								<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
							</div>
						</div>
					</li>
					<li>
						<div class="eats-list">
							<div class="thum">
								<img src="/resources/img/upload/03.jpg">
							</div>
							<div class="info">
								<div class="eats-location">서울 영등포구</div>
								<div class="eats-name">스시 아루히 <i class="eats-like far fa-heart"></i></div>
								<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
								<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
							</div>
						</div>
					</li>
					<li>
						<div class="eats-list">
							<div class="thum">
								<img src="/resources/img/upload/04.jpg">
							</div>
							<div class="info">
								<div class="eats-location">서울 영등포구</div>
								<div class="eats-name">스시 아루히 <i class="eats-like far fa-heart"></i></div>
								<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
								<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
							</div>
						</div>
					</li>
					<li>
						<div class="eats-list">
							<div class="thum" id="">
								<img src="/resources/img/upload/01.jpg">
							</div>
							<div class="info">
								<div class="eats-location">서울 영등포구</div>
								<div class="eats-name">스시 아루히 <i class="eats-like far fa-heart"></i></div>
								<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
								<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
							</div>
						</div>
					</li>
					<li>
						<div class="eats-list">
							<div class="thum">
								<img src="/resources/img/upload/02.jpg">
							</div>
							<div class="info">
								<div class="eats-location">서울 영등포구</div>
								<div class="eats-name">스시 아루히 <i class="eats-like far fa-heart"></i></div>
								<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
								<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
							</div>
						</div>
					</li>
					<li>
						<div class="eats-list">
							<div class="thum">
								<img src="/resources/img/upload/03.jpg">
							</div>
							<div class="info">
								<div class="eats-location">서울 영등포구</div>
								<div class="eats-name">스시 아루히 <i class="eats-like far fa-heart"></i></div>
								<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
								<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
							</div>
						</div>
					</li>
					<li>
						<div class="eats-list">
							<div class="thum">
								<img src="/resources/img/upload/04.jpg">
							</div>
							<div class="info">
								<div class="eats-location">서울 영등포구</div>
								<div class="eats-name">스시 아루히 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
								<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
								<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
							</div>
						</div>
					</li>
				</ul>
			</div>

			
			<!-- paging -->
			<div class="container">
				<ul class="pagination">
	              <li class="disabled"><a href="#" >«</a></li>
	              <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
	              <li><a href="#">2</a></li>
	              <li><a href="#">3</a></li>
	              <li><a href="#">4</a></li>
	              <li><a href="#">5</a></li>
	              <li><a href="#">»</a></li>
	            </ul>
			</div>



		</div>
	</section>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>