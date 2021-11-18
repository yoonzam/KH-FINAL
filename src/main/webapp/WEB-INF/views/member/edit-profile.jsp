<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/resources/css/member/edit-profile.css" />
<script defer type="text/javascript" src="/resources/js/member/chkNickname.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
  <section>
    <div class="container-wrap">
      <div class="container">
        <ul class="myeats-tab">
          <li class="selected">그룹관리</li>
          <li>작성글관리</li>
          <li>맛찜리스트</li>
        </ul>
        <h2 class="tit-edit-profile">회원정보 수정</h2>
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
            <form action="/member/edit-profile" method="post" class="edit-profile-form">
              <label for="nickname">닉네임</label>
              <div class="wrap-nickname">
                <input type="text" name="nickname" id="nickname">
                <input type="button" value="중복확인" id="check_nick">
              </div>
              <label for="password">비밀번호</label>
              <input type="text" name="password" id="password">
              <label for="chk-password">비밀번호 확인</label>
              <input type="text" name="chkPassword" id="chk-password">
              <div class="wrap-btn">
                <button>수정하기</button>
                <a href="/myeats/post" class="btn-cancel">취소하기</a>
              </div>
            </form>
          </div>
        </div>  
      </div>
    </div>
  </section>  

<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script type="text/javascript">

document.querySelector('#profile').addEventListener('change', (e) => {
	
	//e.preventDefault();	 
	console.dir(document.getElementById('profile'));
	let files = document.getElementById('profile').files;
	
	for (let file of files) {
		if(validFileType(file)){
			document.getElementById('target_img').src = URL.createObjectURL(file);
			document.img.submit();
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