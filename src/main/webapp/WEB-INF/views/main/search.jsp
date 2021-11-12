<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="/resources/css/main/search.css" />
<!-- <script defer type="text/javascript" src="/resources/js/main/main.js"></script> -->

<meta charset="UTF-8">
<title>Insert title here</title>

<!-- 화살표 아이콘 -->
<link rel="stylesheet" href="http://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">


</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp"%>

<section>
		<div class="section">

			<div class="search-wrap">
				<div class="search-area">
					<div class="search-form">
						<li>관심 음식<i class='xi-angle-down'></i>
							<div class='dropdown'>
								<a class='drop-link' href='#'>한식</a> 
								<a class='drop-link' href='#'>중식</a> 
								<a class='drop-link' href='#'>양식</a> 
								<a class='drop-link' href='#'>일식</a> 
								<a class='drop-link' href='#'>카페/디저트</a>
								<a class='drop-link' href='#'>술집</a>
							</div>
						</li> 
						<label>
							<input type="text" placeholder="찾으시는 맛집이나 유저 이름을 입력하세요."/>
						</label>
					
						<a class="search-btn" href="/main/search">검색하기<button type="submit"></button></a>
					</div>
				
				</div>
			</div>
			
			
		</div>
	</section>



<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>