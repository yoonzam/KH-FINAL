<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" href="/resources/css/member/login.css">
</head>
<body>
   <header class="header-login">
 		<div class="header pc login">
       <h1><img src="/resources/img/common/logo_ver1.png"></h1>
     </div>    
   </header>
   <section>
     <div class="container">
       <div class="box-logo">
         <img src="/resources/img/common/EatsMap_logo.png" alt="">
       </div>
       <form action="#" class="join-form" name="join-form">
         <h2 class="tit-login">로그인</h2>
         <input type="email" name="email" id="email" placeholder="이메일을 입력해주세요">
         <input type="password" name="chk-password" id="chk-password" placeholder="비밀번호를 입력해주세요">
         <button>로그인</button>
         <a href="#" class="btn-kakao">
           <img src="/resources/img/common/kakao_login_large_wide.png" alt="">
         </a>
         <div class="box-btn">
           <a href="#" class="search-pw">비밀번호 찾기</a>
           <span></span>
           <a href="#" class="join">아직 회원이 아니신가요?</a>
         </div>
       </form>

     </div>
   </section>


<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>