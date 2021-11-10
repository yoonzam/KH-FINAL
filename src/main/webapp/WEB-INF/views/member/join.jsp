<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" href="/resources/css/member/join.css">
</head>
<body>
   <header class="header-join">
 		<div class="header pc join">
       <h1><img src="/resources/img/common/logo_ver1.png"></h1>
     </div>    
   </header>
   <section>
     <div class="container">
       <div class="box-logo">
         <img src="/resources/img/common/EatsMap_logo.png" alt="">
       </div>
       <div class="line"></div>
       <form action="#" class="join-form" name="join-form">
         <h2 class="tit-join">회원가입</h2>
         <label for="email">이메일</label>
         <input type="email" name="email" id="email" placeholder="이메일을 입력하세요">
         <label for="password">비밀번호</label>
         <input type="password" name="password" id="password" placeholder="비밀번호를 입력하세요">
         <label for="chk-password">비밀번호 확인</label>
         <input type="password" name="chk-password" id="chk-password">
         <label for="nickname">닉네임</label>
         <div class="wrap-nick">
           <input type="text" name="nickname" id="nickname" placeholder="닉네임을 입력하세요">
           <input type="button" value="인증하기" class="btn-chk-nick">
         </div>
         <button>가입하기</button>
       </form>
     </div>
     
   </section>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>

</body>
</html>