<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/resources/css/timeline/timeline.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/myeats/post.css" />
<script defer type="text/javascript" src="/resources/js/timeline/timeline.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
    <section>
      <div class="container-wrap">
        <div class="container">
          <ul class="myeats-tab">
            <li>그룹관리</li>
            <li class="selected">작성글관리</li>
            <li>맛찜리스트</li>
          </ul>
          <div class="profile">
            <div class="wrap-profile-img">
              <div class="profile-img">
                <img src="/resources/img/upload/02.jpg">
              </div>
            </div>
            <div class="wrap-profile-info">
              <div class="postCnt">
                <h3 class="postCnt-txt">게시물</h3>
                <span class="cnt">11</span>
              </div>
              <div class="followCnt">
                <h3 class="postCnt-txt">내잇친</h3>
                <span class="cnt">111</span>
              </div>
              <div class="followingCnt">
                <h3 class="postCnt-txt">나를 추가한 잇친</h3>
                <span class="cnt">101</span>
              </div>
            </div>
          </div>
          <div class="btn-wrap">
            <a href="/member/edit-profile" class="btn-edit-profile">회원정보 수정</a>
          </div> 
          <div class="myeats-post-wrap">
            <!-- <h2><i class="fas fa-utensils color-m"></i> 내 게시글</h2> -->
            <ul class="timeline-brd">
              <li>
                <div class="eats-list">
                  <div class="thum">
                    <img src="/resources/img/upload/01.jpg">
                  </div>
                  <div class="info">
                    <div class="eats-location">서울 관악구</div>
                    <div class="eats-name">스시 아루히 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
                    <div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
                    <div class="eats-score"><i class="fas fa-star"></i>5.0</div>
                  </div>
                </div>
              </li>
              <li>
                <div class="eats-list">
                  <div class="thum" id="">
                    <img src="/resources/img/upload/02.jpg">
                  </div>
                  <div class="info">
                    <div class="eats-location">서울 영등포구</div>
                    <div class="eats-name">스시 아루히 <i class="eats-like far fa-heart"></i></div>
                    <div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
                    <div class="eats-score"><i class="fas fa-star"></i>5.0</div>
                  </div>
                </div>
              </li>
              <li>
                <div class="eats-list">
                  <div class="thum">
                    <img src="/resources/img/upload/03.jpg">
                  </div>
                  <div class="info">
                    <div class="eats-location">서울 영등포구</div>
                    <div class="eats-name">스시 아루히 <i class="eats-like far fa-heart"></i></div>
                    <div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
                    <div class="eats-score"><i class="fas fa-star"></i>5.0</div>
                  </div>
                </div>
              </li>
              <li>
                <div class="eats-list">
                  <div class="thum">
                    <img src="/resources/img/upload/04.jpg">
                  </div>
                  <div class="info">
                    <div class="eats-location">서울 영등포구</div>
                    <div class="eats-name">스시 아루히 <i class="eats-like far fa-heart"></i></div>
                    <div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
                    <div class="eats-score"><i class="fas fa-star"></i>5.0</div>
                  </div>
                </div>
              </li>
              <li>
                <div class="eats-list">
                  <div class="thum" id="">
                    <img src="/resources/img/upload/01.jpg">
                  </div>
                  <div class="info">
                    <div class="eats-location">서울 영등포구</div>
                    <div class="eats-name">스시 아루히 <i class="eats-like far fa-heart"></i></div>
                    <div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
                    <div class="eats-score"><i class="fas fa-star"></i>5.0</div>
                  </div>
                </div>
              </li>
              <li>
                <div class="eats-list">
                  <div class="thum">
                    <img src="/resources/img/upload/02.jpg">
                  </div>
                  <div class="info">
                    <div class="eats-location">서울 영등포구</div>
                    <div class="eats-name">스시 아루히 <i class="eats-like far fa-heart"></i></div>
                    <div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
                    <div class="eats-score"><i class="fas fa-star"></i>5.0</div>
                  </div>
                </div>
              </li>
              <li>
                <div class="eats-list">
                  <div class="thum">
                    <img src="/resources/img/upload/03.jpg">
                  </div>
                  <div class="info">
                    <div class="eats-location">서울 영등포구</div>
                    <div class="eats-name">스시 아루히 <i class="eats-like far fa-heart"></i></div>
                    <div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
                    <div class="eats-score"><i class="fas fa-star"></i>5.0</div>
                  </div>
                </div>
              </li>
              <li>
                <div class="eats-list">
                  <div class="thum">
                    <img src="/resources/img/upload/04.jpg">
                  </div>
                  <div class="info">
                    <div class="eats-location">서울 영등포구</div>
                    <div class="eats-name">스시 아루히 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
                    <div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
                    <div class="eats-score"><i class="fas fa-star"></i>5.0</div>
                  </div>
                </div>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </section>  

<%@ include file="/WEB-INF/views/include/footer.jsp" %>


</body>
</html>