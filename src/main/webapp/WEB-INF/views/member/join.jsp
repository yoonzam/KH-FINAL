<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" href="/resources/css/member/join.css">
<script defer type="text/javascript" src="/resources/js/member/joinValidation.js"></script>
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
       <form:form modelAttribute="joinForm" action="/member/join" method="post" class="join-form" name="join-form">
         <h2 class="tit-join">회원가입</h2>
         <label for="email">이메일</label>
         <form:errors path="email" cssClass="err-msg" />
         <input type="email" name="email" id="email" placeholder="이메일을 입력하세요"
         	<c:if test="${empty error.email }">
         		value="${joinForm.email}"
         	</c:if>
         required/>
         <div class="box-valid-msg email">
            <em></em>
            <ul class="valid-msg email">
              <li><i class='fas email fa-check-circle'></i>이메일 양식을 입력하세요.</li>
            </ul>
          </div>
         
         <label for="password">비밀번호</label>
         <form:errors path="password" cssClass="err-msg" element="span" />
         <input type="password" name="password" id="password" placeholder="비밀번호를 입력하세요"
         	<c:if test="${empty error.password }">
         		value="${joinForm.password}"
         	</c:if>
         required/>
         
         
         <div class="box-valid-msg password">
            <em></em>
            <ul class="valid-msg password">
              <li><i class="fas password fa-check-circle"></i>영문자, 숫자, 특수문자 포함 8~15자입니다.</li>
            </ul>
          </div>
          
          <label for="chk-password">비밀번호 확인</label>
          <form:errors path="chkPassword" cssClass="err-msg" />
          <input type="password" name="chkPassword" id="chkPassword"
         	<c:if test="${empty error.chkPassword }">
         		value="${joinForm.chkPassword}"
         	</c:if>                  
          required/>
          
          <div class="box-valid-msg chkPassword">
            <em></em>
            <ul class="valid-msg chkPassword">
              <li><i class="fas chkPassword fa-check-circle"></i>비밀번호를 한번 더 입력하세요.</li>
            </ul>
          </div>
          <label for="nickname">닉네임</label>
          <div class="wrap-nick">
            <input type="text" name="nickname" id="nickname" placeholder="닉네임을 입력하세요">
            <input type="button" value="중복확인" class="btn-chk-nick">
          </div>
          <div class="box-valid-msg nickname">
            <em></em>
            <ul class="valid-msg nickname">
            	<li><i class="fas nickname fa-check-circle"></i>특수문자는 입력할 수 없습니다.</li>
            	<li></li>
            </ul>
          </div>
          <button>가입하기</button>
        </form:form>
      </div>
    </section>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>