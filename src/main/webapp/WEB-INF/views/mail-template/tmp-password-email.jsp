<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<style type="text/css">
@media screen {
    @font-face {
        font-family: 'Nanum Gothic';
        src: url(https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@400;700;800&display=swap);
    }
}
/* notosans */
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap');

/* nanumgothic */
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@400;700;800&display=swap');

body,div,h1,h2,h3,h4,h5,h6,h7,ul,li,dl,dt,dd,input,button,p,a,table,tr,th,td{margin:0;padding:0;}
a{cursor:pointer;text-decoration:none;}
.container-wrap{max-width:1300px; margin:0 auto; padding:30px;}
.container{border-radius: 12px; background-color: #fff; padding: 40px; box-shadow: 0px 20px 80px 0px rgb(153 153 153 / 30%);}

.logo-box{display: flex;justify-content: center;padding: 35px;}
.logo-box img{width: 30%;}
.tit2{text-align: center; padding: 20px;}
.content{display: flex; justify-content: center; padding: 20px;}
.tmp-box{width: 40%; border: 3px solid #ccc; text-align: center;padding: 40px;}
.btn-box{width: 40%; margin: auto; text-align: center; padding: 40px;}
.btn-box a{padding: 20px; background-color: #fa8633; border-radius: 5px; color: #fff; font-weight: 800; font-size: 18px; width: 100%;}
.btn-box a:hover{background-color: #ffa54f;}
</style>
</head>

<body>
  <section>
  <div class="container-wrap" style="max-width:1300px; margin:0 auto; padding:30px;">
    <div style=" box-shadow: 0px 20px 80px 0px rgb(153 153 153 / 30%); border-radius: 12px; background-color: #fff; padding: 40px;">
      <div class="logo-box" style="display: flex; justify-content: center; padding: 35px;">
      	<img style="width: 30%;" src="resources/img/common/logo_ver1.png" alt="">
      </div>
      <h2 style="font-family: 'Nanum Gothic', sans-serif; text-align: center; font-size: 32px !important; color:#333;">임시 비밀번호입니다</h2>
      <h3 style="font-family: 'Nanum Gothic', sans-serif; text-align: center; padding: 20px; font-size: 23px !important; " class="tit2">로그인 즉시 비밀번호를 변경하시길 바랍니다.</h3>
      <div class="content" style="display: flex; justify-content: center; padding: 20px; margin: 0 auto;">
        <div class="tmp-box" style="font-family: 'Nanum Gothic', sans-serif; width: 40%; margin: auto; border: 3px solid #ccc; text-align: center; padding: 40px;"><h3 style=" font-size: 20px; color:#666;">${param.tmp }</h3></div>
      </div>
      <div class="btn-box" style="width: 40%; margin: auto; text-align: center; padding: 40px;">
      	<a href="http://localhost:9090/member/login" style="font-family: 'Nanum Gothic', sans-serif; cursor:pointer;text-decoration:none; padding: 20px; background-color: #fa8633; border-radius: 5px; color: #fff; font-weight: 800; font-size: 18px; width: 100%;">잇츠맵 로그인하러가기</a>
      </div>
    </div>
  </div>
</section>
</body>
</html>