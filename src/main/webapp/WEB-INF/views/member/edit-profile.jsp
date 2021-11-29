<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/resources/css/member/edit-profile.css" />
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
  <section>
    <div class="container-wrap">
      <div class="container">
        <ul class="myeats-tab">
          <li><a href="/myeats/group">그룹관리</a></li>
          <li><a href="/myeats/post">작성글관리</a></li>
          <li><a href="/myeats/detail">맛찜리스트</a></li>
          <li class="selected">회원정보 수정</li>
        </ul>
        <h2 class="tit-edit-profile">회원정보 수정</h2>
        <form:form modelAttribute="modifyForm" action="/member/edit-profile" method="post" class="edit-profile-form" name="editForm" enctype="multipart/form-data">
	        <div class="profile">
	          <div class="wrap-profile-img">
	            <div class="wrap-img">
	            <div class="profile-img">
		          <c:if test="${not empty authentication and not empty authentication.profile}">
		          	<img id="target_img" src="http://localhost:9090/file/${authentication.profile}">
		          </c:if>
		          <c:if test="${not empty authentication and empty authentication.profile}">
		            <img id="target_img" src="/resources/img/member/user.png">
		          </c:if>   
	            </div>
	            </div>
	            
	            <div class="wrap-file">
	              <label for="profile" class="btn-edit-profile">Edit Profile</label>
	              <input type="file" name="profile" id="profile" style="display: none;">
	            </div> 
	          </div>
	          <div class="wrap-form">
	              <label for="nickname">닉네임</label><span class="valid-msg" id="alert_nick"></span><form:errors cssClass="valid-msg" path="nickname"/>
	              <div class="wrap-nickname">
	                <input type="text" name="nickname" id="nickname" value="${authentication.nickname }">
	                <input type="button" value="중복확인" id="check_nick">
	              </div>
	              <label for="password">비밀번호</label><form:errors cssClass="valid-msg" path="password"/>
	              <input type="password" name="password" id="password">
	              <label for="chk-password">비밀번호 확인</label><form:errors cssClass="valid-msg" path="chkPassword"/>
	              <input type="password" name="chkPassword" id="chkPassword">
	              <div class="wrap-btn">
	                <button id="btn-edit">수정하기</button>
	                <a href="/myeats/post" class="btn-cancel">취소하기</a>
	              </div>
	          </div>
	        </div>  
        </form:form>
        <div class="wrap-quit">
          <span class="quit">탈퇴를 원하시면 우측 버튼을 클릭하세요</span>
          <a href="/member/quit" class="btn-quit">탈퇴하기</a>
        </div>
      </div>
    </div>
  </section>  

<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script type="text/javascript">
(() => {    	/* authentication에 접근하므로 분리x */
	  let confirmNick = '';
	  
	  document.querySelector('#check_nick').addEventListener('click', ()=>{
		  let nickname = document.querySelector('#nickname').value;

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
	  
	  document.querySelector('#btn-edit').addEventListener('click', e => {
			e.preventDefault();
			
			if(nickname == `${authentication.nickname}`){
				document.editForm.submit();
				alert('닉네임 유지');
			}
			
			if(nickname != `${authentication.nickname}` && confirmNick == ""){
				document.querySelector('#alert_nick').style.color = 'var(--red-color)';
				document.querySelector('#alert_nick').innerHTML = '중복확인을 하지 않았습니다.';
				return;
			}else{
				document.editForm.submit();
			}
		})

})();


document.querySelector('#nickname').addEventListener('keydown',e => {
	  if (e.keyCode === 13) {
			e.preventDefault();
	  };
})



document.querySelector('#profile').addEventListener('change', (e) => {
	
	//e.preventDefault();	 
	let files = document.getElementById('profile').files;
	
	for (let file of files) {
		if(validFileType(file)){
			document.getElementById('target_img').src = URL.createObjectURL(file);
		}
	}
	
});
   
let fileTypes = [
   "image/gif",
   "image/jpeg",
   "image/pjpeg",
   "image/png",
   "image/tiff",
   "image/webp",
   "image/x-icon"
 ];
 
function validFileType(file) {
   return fileTypes.includes(file.type);
 }
  

</script>
</body>
</html>