<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" href="/resources/css/member/kakao-join.css">
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
      <form action="/member/kakao-join" method="post" class="kakao-form" name="kakaoForm">
        <h2 class="tit-kakao">추가정보 입력</h2>
        <div class="desc">
          <h5>소셜 가입 인증을 성공하였습니다.</h5>
          <h5>가입을 완료하기 위해 아래 추가 정보를 입력해 주세요.</h5>
        </div>
        <div class="wrap-input">
          <input type="hidden" name="kakaoId" value="${param.kakaoId}" >
          <input type="hidden" name="email" value="${param.email}" >
          <input type="text" name="nickname" id="nickname" placeholder="닉네임을 입력하세요">
          <input type="button" id="check_nick"  class="btn-chk-nick" value="중복확인">
        </div>
        <span class="valid-msg" id="alert_nick"></span>
        <button id="btn-join">가입하기</button>
      </form>
    </div>
  </section>
  <%@ include file="/WEB-INF/views/include/footer.jsp" %>
  <script type="text/javascript">
  
  let forwardLogin = () => {
	  alert('회원가입을 취소하시겠습니까?');
	  location.href='/member/login';
  };
  
  (() => {    
	  let confirmNick = '';
	  let nickname = '';
	  
	  document.querySelector('#check_nick').addEventListener('click', ()=>{
		
		  nickname = document.querySelector('#nickname').value;
		  
		  if(nickname === "" || nickname == null){
			document.querySelector('#alert_nick').innerHTML = '닉네임을 입력하지 않았습니다.';
			return;
		  }
		  
		  fetch("/member/nickname-check?nickname=" + nickname)
		  .then(response => {
			  if(response.ok){	//통신 성공시
				  return response.text();
			  }else{
				  throw new Error(response.status);
			  }
		  }).then(text => {	//promise객체의 text
			  if(text == 'available'){
				  confirmNick = nickname;
				  
				  document.querySelector('#alert_nick').style.color = 'var(--main-color)';
				  document.querySelector('#alert_nick').innerHTML = '사용 가능한 닉네임입니다.';
			  }else{
				  document.querySelector('#alert_nick').style.color = 'var(--red-color)';
				  document.querySelector('#alert_nick').innerHTML = '사용 불가능한 닉네임입니다.';
			  }
		  }).catch(error => {
			  document.querySelector('#alert_nick').innerHTML = '응답에 실패하였습니다.';
		  });
		  
	  });
	  
	  document.querySelector('#btn-join').addEventListener('click', e => {
			e.preventDefault();
			
			if(confirmNick == ""){
				document.querySelector('#alert_nick').style.color = 'var(--red-color)';
				document.querySelector('#alert_nick').innerHTML = '중복확인을 하지 않았습니다.';
				return;
			}else{
				document.kakaoForm.submit();
			}
		});


 })();


  
  </script> 
</body>
</html>