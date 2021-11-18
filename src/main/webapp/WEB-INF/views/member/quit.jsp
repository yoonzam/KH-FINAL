<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/resources/css/member/quit.css" />
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
        <h2 class="tit-quit">탈퇴하기</h2>
        <div class="box-content">
          <div class="content">
            <div class="wrap-quit-content">
              <h3>회원 탈퇴 전 유의사항을 확인해주세요!</h3>
              <p><i class="far fa-hand-point-right"></i>
                회원 탈퇴 시 잇츠맵 모든 서비스는 이용하실 수 없습니다.
              </p>
              <p><i class="far fa-hand-point-right"></i>
                회원 탈퇴 후 잇츠맵 서비스에 입력하신 후기 및 댓글은 삭제되지 않으며, 
                회원정보 삭제로 인해 작성자 본인을 확인할 수 없어 편집 및 삭제처리가 원천적으로 불가능합니다.
              </p>
              <p><i class="far fa-hand-point-right"></i>
                후기 및 댓글 삭제를 원하시는 경우에는 먼저 해당 게시물을 삭제하신 후 탈퇴를 신청하시기 바랍니다.
              </p>
            </div>
          </div>
          <div class="wrap-form">
            <form action="/member/quit" method="post" class="quit-form">
              <label for="nickname">닉네임</label>
              <input type="text" name="nickname" id="nickname" value="${authentication.nickname }" disabled>

              <label for="password">비밀번호</label>
              <input type="text" name="password" id="password">
              <span class="message">보안을 위해 닉네임 및 비밀번호를 확인 합니다.</span>
              <div class="wrap-btn">
                <button>본인확인 및 탈퇴</button>
                <a href="/member/edit-profile" class="btn-cancel">취소하기</a>
              </div>
            </form>
          </div>

        </div>  

      </div>
    </div>
  </section>  
</body>
</html>