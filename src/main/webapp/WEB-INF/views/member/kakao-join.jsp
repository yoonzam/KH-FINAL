<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" href="/resources/css/member/kakao-join.css">
<script defer type="text/javascript" src="/resources/js/member/chkNickname.js"></script>
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
        <img src="/resources/img/common/EatsMap_logo.png" alt="">
      </div>
      <form action="/member/kakao-join" method="post" class="kakao-form" name="kakao-form">
        <h2 class="tit-kakao">추가정보 입력</h2>
        <div class="desc">
          <h5>소셜 가입 인증을 성공하였습니다.</h5>
          <h5>가입을 완료하기 위해 아래 추가 정보를 입력해 주세요.</h5>
        </div>
        <div class="wrap-input">
          <input type="hidden" name="kakaoId" value="${param.kakaoId}" >
          <input type="text" name="nickname" id="nickname" placeholder="닉네임을 입력하세요">
          <input type="button" class="btn-chk-nick" id="checkNickname" value="중복확인">
          <span class="valid-msg" id="alert_nick"></span>
        </div>
        <button>가입하기</button>
      </form>
    </div>
  </section>
  <%@ include file="/WEB-INF/views/include/footer.jsp" %>
  <script type="text/javascript">
  
  let forwardLogin = () => {
	  alert('회원가입을 취소하시겠습니까?');
	  location.href='/member/login';
  }
  
  </script> 
</body>
</html>