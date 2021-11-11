<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" href="/resources/css/member/join.css">
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
       <form action="/member/join" method="post" class="join-form" name="join-form">
         <h2 class="tit-join">회원가입</h2>
         <label for="email">이메일</label>
         <input type="email" name="email" id="email" placeholder="이메일을 입력하세요">
         <label for="password">비밀번호</label>
         <input type="password" name="password" id="password" placeholder="비밀번호를 입력하세요">
         <div class="box-valid-msg password">
            <em></em>
            <ul class="valid-msg">
              <li>비밀번호는 영문자, 특수문자, 숫자 조합 8~15자입니다.</li>
              <li><i class="far fa-check-circle"></i>특수문자를 포함합니다.</li>
              <li><i class="fas fa-check-circle"></i>영문자를 포함합니다.</li>
              <li><i class="far fa-check-circle"></i>숫자를 포함합니다.</li>
            </ul>
          </div>
          
          <label for="chk-password">비밀번호 확인</label>
          <input type="password" name="chk-password" id="chk-password">
          <div class="box-valid-msg chk-password">
            <em></em>
            <ul class="valid-msg">
              <li><i class="far fa-check-circle"></i>비밀번호가 일치하지 않습니다.</li>
              <li><i class="fas fa-check-circle"></i>비밀번호가 일치합니다.</li>
            </ul>
          </div>
          <label for="nickname">닉네임</label>
          <div class="wrap-nick">
            <input type="text" name="nickname" id="nickname" placeholder="닉네임을 입력하세요">
            <input type="button" value="인증하기" class="btn-chk-nick">
          </div>
          <div class="box-valid-msg nickname">
            <em></em>
            <ul class="valid-msg">
              <li><i class="far fa-check-circle"></i>닉네임을 사용할 수 없습니다.</li>
              <li><i class="fas fa-check-circle"></i>사용가능한 닉네임입니다.</li>
            </ul>
          </div>
          <button>가입하기</button>
        </form>
      </div>
    </section>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script type="text/javascript">

  document.querySelector('#password').addEventListener('focus', () => {
    document.querySelector('.box-valid-msg.password').style.display = 'inline-block';
  })
  document.querySelector('#password').addEventListener('blur', () => {
    document.querySelector('.box-valid-msg.password').style.display = 'none';
  })
  document.querySelector('#chk-password').addEventListener('focus', () => {
    document.querySelector('.box-valid-msg.chk-password').style.display = 'inline-block';
  })
  document.querySelector('#chk-password').addEventListener('blur', () => {
    document.querySelector('.box-valid-msg.chk-password').style.display = 'none';
  })
  document.querySelector('#nickname').addEventListener('focus', () => {
    document.querySelector('.box-valid-msg.nickname').style.display = 'inline-block';
  })
  document.querySelector('#nickname').addEventListener('blur', () => {
    document.querySelector('.box-valid-msg.nickname').style.display = 'none';
  })


</script>
</body>
</html>