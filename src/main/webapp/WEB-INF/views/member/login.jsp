<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
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
       <form action="/member/join" method="post" class="login-form" name="login-form">
         <h2 class="tit-login">로그인</h2>
         <input type="email" name="email" id="email" placeholder="이메일을 입력해주세요">
         <input type="password" name="chk-password" id="chk-password" placeholder="비밀번호를 입력해주세요">
         <button>로그인</button>
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