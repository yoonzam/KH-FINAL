<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" href="/resources/css/member/find-password.css">
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
       <form:form modelAttribute="emailForm" action="/member/find-password" method="post" class="find-pw-form" name="find-pw">
         <h2 class="tit-find-pw">비밀번호 찾기</h2>
         <h3 class="txt-msg">가입한 이메일로 임시비밀번호가 발송됩니다.</h3>

         <div class="wrap-find-pw">
           <input type="text" name="email" id="email" placeholder="이메일을 입력하세요"/>
           <button class="btn-tmp-pw">비밀번호 발급</button>
         </div>
         <form:errors path="email" cssClass="err-msg" element="span"/>       
         <c:if test="${not empty msg}">
         	<h5 class="msg">메일을 확인해보세요!</h5>
         	<h5 class="msg txt2"><span>임시비밀번호</span>가 발송되었습니다.</h5>
         </c:if>
       </form:form>
     </div>
   </section>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>