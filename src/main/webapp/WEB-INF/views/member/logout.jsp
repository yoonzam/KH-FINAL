<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" href="/resources/css/member/logout.css">
</head>
<body>
   <section class="container">
     <div class="wrap-txt">
       <h2 class="tit-logout">로그아웃</h2>
       <span class="txt">로그인하려면 하단 로고를 <span>CLICK!</span> 하세요</span>
     </div>
     <div class="wrap-logo">
       <a href="/member/login" class="wrap-img">
         <img src="/resources/img/common/logo_ver2.png" alt="">
       </a>
     </div>
     
   </section>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
  <script>
    $('.wrap-img').hover(e => {
      document.querySelector('.wrap-img img').src = '/resources/img/common/EatsMap_logo.png';
      document.querySelector('.wrap-img img').style.width='300px';
    })
    $('.wrap-img').mouseout(e => {
      document.querySelector('.wrap-img img').src = '/resources/img/common/logo_ver2.png';
      document.querySelector('.wrap-img img').style.width='200px';
    })
  </script>
  
</body>
</html>