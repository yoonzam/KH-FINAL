<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>잇츠맵 나만의 맛집 지도</title>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" href="/resources/css/member/login.css">
</head>
<body>
  <header class="header-member">
    <div class="header pc member">
       <h1><img src="/resources/img/common/logo_ver1.png"></h1>
      </div>    
    <div class="header m member">
      <h1><img src="/resources/img/common/logo_ver1.png"></h1>
    </div>      
  </header>
   <section>
     <div class="container">
       <div class="box-logo">
         <img src="/resources/img/common/gif_logo.gif" alt="/resources/img/common/EatsMap_logo.png">
       </div>
       <form action="/member/login" method="post" class="login-form" name="loginForm">
         <h2 class="tit-login">로그인</h2>
         <c:if test="${not empty joinMsg}">
         	<h3 class="color-m" style="text-align: center; padding: 10px 0;">${joinMsg}</h3>
         </c:if>
         <c:if test="${not empty message }">
			<h3 class="color-m" style="text-align: center; padding: 10px 0;">${message}</h3>      	
         </c:if>
         <input type="email" name="email" id="email" placeholder="이메일을 입력해주세요">
         <input type="password" name="password" id="password" placeholder="비밀번호를 입력해주세요">

         
         <button id="btn-login">로그인</button>
         <a onclick="kakaoLogin()" class="btn-kakao">
         	<img src="/resources/img/common/kakao-logo.png">
         	<span>카카오 로그인</span>
         </a>
         <div class="box-btn">
           <a href="/member/find-password" class="search-pw">비밀번호 찾기</a>
           <span></span>
           <a href="/member/join" class="join">아직 회원이 아니신가요?</a>
         </div>
       </form>

     </div>
   </section>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script type="text/javascript" src="/resources/js/member/kakaoLogin.js"></script>
</body>
</html>