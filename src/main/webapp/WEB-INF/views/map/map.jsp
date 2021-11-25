<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<link rel="stylesheet" type="text/css"
	href="/resources/css/timeline/timeline.css" />
<script type="text/javascript"
	src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=5bdae166c6881cf42916fd1d25349e6e&libraries=services,clusterer,drawing"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/map/map.css" />
<style type="text/css">
.popup {
	grid-column: 2/5;
	grid-row: 14/17;
	z-index: 5;
	display: flex;
	justify-content: center;
}

.popup-wrap {
	display: flex;
	justify-content: center;
	width: 65%;
	height: 100%;
	background-color: white;
	border-radius: 8px;
	padding-left: 20px;
	box-shadow: 2px 2px 4px rgb(0 0 0/ 30%);
}

.rest-info {
	padding: 20px 0;
	flex:6;
	display: flex;
    flex-direction: column;
    justify-content: space-around;
}


.rest-title {
	font-weight: 700;
	font-size: 20px;
	color: #111;
	margin-top:0;
	margin-bottom: 10px;
}
.wrap-btn{
	flex:1;
	position: relative; 
	display: flex;
	
}
.btn {
	padding: 18px;
	border-radius: 5px;
	color: #fff;
	background-color: #fa8633;
	font-weight: 700;
	transition-duration: 0.5s;
	position: absolute;
	top: 50%;
	transform : translateY(-50%);
}
.btn:hover{
	background-color:#ffa54f;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/header.jsp"%>
	<section>
		<div class="container">
			<div class="search">
				<div class="search-form">
					<div class="search-box">
						<label><input class="keyword" type="text"
							placeholder="식당 후기 검색하기"></label> <a id="search"
							class="search-btn">검색</a>
					</div>
					<div class="select-bar">
						<select id="friendList" name="friends" class="select" style="display: none;">
							<option disabled selected>🍟잇친이들의 맛집</option>
							<option>이지원</option>
							<option>김지원</option>
							<option>박지원</option>
							<option>최지원</option>
						</select>
						<select name="category" class="select" onchange="changeLangSelect()" id="checkCategory">
							<option disabled="disabled" selected="selected">🎈잇츠맵카테고리</option>
							<option value="group">니캉내캉</option>
							<option value="follower">잇친맵</option>
							<option value="social">소셜맵</option>
						</select> 
					</div>
				</div>
			</div>
			<div class="map-review">
				<div class="review_wrap">
					<div class="img-box">
						<img class="image-thumbnail" src="/resources/img/upload/01.jpg">
					</div>
					<div class="info">
						<div class="eats-name">
							스시 아루히 &emsp;&emsp;&emsp;<i onclick="clickLock(this);"
								class="fas fa-lock"></i>
						</div>
						<div class="eats-location">서울 영등포구</div>
						<div class="eats-tag">
							<span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span>
						</div>
					</div>
				</div>

				<div class="review_wrap">
					<div class="img-box">
						<img class="image-thumbnail" src="/resources/img/upload/01.jpg">
					</div>
					<div class="info">
						<div class="eats-name">


							스시 아루히 &emsp;&emsp;&emsp;<i onclick="clickLock(this);"
								class="fas fa-unlock"></i>
						</div>
						<div class="eats-location">서울 영등포구</div>
						<div class="eats-tag">
							<span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span>
						</div>
					</div>
				</div>
				<div class="review_wrap">
					<div class="img-box">
						<img class="image-thumbnail" src="/resources/img/upload/01.jpg">
					</div>
					<div class="info">
						<div class="eats-name">


							스시 아루히 &emsp;&emsp;&emsp;<i onclick="clickLock(this);"
								class="fas fa-unlock"></i>
						</div>
						<div class="eats-location">서울 영등포구</div>
						<div class="eats-tag">
							<span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span>
						</div>
					</div>
				</div>
				<div class="review_wrap">
					<div class="img-box">
						<img class="image-thumbnail" src="/resources/img/upload/01.jpg">
					</div>
					<div class="info">
						<div class="eats-name">


							스시 아루히 &emsp;&emsp;&emsp;<i onclick="clickLock(this);"
								class="fas fa-unlock"></i>
						</div>
						<div class="eats-location">서울 영등포구</div>
						<div class="eats-tag">
							<span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span>
						</div>
					</div>
				</div>
				<div class="review_wrap">
					<div class="img-box">
						<img class="image-thumbnail" src="/resources/img/upload/01.jpg">
					</div>
					<div class="info">
						<div class="eats-name">


							스시 아루히 &emsp;&emsp;&emsp;<i onclick="clickLock(this);"
								class="fas fa-unlock"></i>
						</div>
						<div class="eats-location">서울 영등포구</div>
						<div class="eats-tag">
							<span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span>
						</div>
					</div>
				</div>
				<div class="review_wrap">
					<div class="img-box">
						<img class="image-thumbnail" src="/resources/img/upload/01.jpg">
					</div>
					<div class="info">
						<div class="eats-name">
							스시 아루히 &emsp;&emsp;&emsp;<i onclick="clickLock(this);"
								class="fas fa-unlock"></i>
						</div>
						<div class="eats-location">서울 영등포구</div>
						<div class="eats-tag">
							<span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span>
						</div>
					</div>
				</div>
			</div>
			<div class="eatsMap">
				<div id="map"></div>
			</div>
			<div class="popup">
				<div class="popup-wrap" style="display: none;">
					<div class="rest-info">
						<h2 class="rest-title" id="place-name"></h2> 
						<span class="rest-content" id="road-address"></span>
					</div>
					<div class="wrap-btn">
						<span class="btn" id="map_reviewBtn">후기등록</span>
					</div>
				</div>

			</div>
		</div>

	</section>

	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
	<script type="text/javascript">
	
	/* 유진 추가내용 */
	document.querySelector('.btn').addEventListener('click',() => {
		document.querySelector('#pop-review-form').style.display='flex';
	})
	
		let clickLock = (e) =>{
			if (e.className.match("fas fa-unlock")) {
				e.className = "fas fa-lock";
			}else{
				e.className = "fas fa-unlock";
			}
		}
		var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
		var options = { //지도를 생성할 때 필요한 기본 옵션
			center : new kakao.maps.LatLng(37.54699, 127.09598), //지도의 중심좌표.
			level : 3
		//지도의 레벨(확대, 축소 정도)
		};

		var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
		
		document.querySelector('#search').addEventListener('click', (e) => {
		    let keyword = document.querySelector('.keyword').value;
			searchMap(keyword);
			searchKeyword(keyword);

			document.querySelector(".popup-wrap").style.display = 'none';
		});
		
		document.querySelector('.keyword').addEventListener('keyup', (e)=> {
		    if (e.keyCode === 13) {
		    	let keyword = document.querySelector('.keyword').value;
				searchMap(keyword);
				searchKeyword(keyword);

				document.querySelector(".popup-wrap").style.display = 'none';
		  }  
		});
		

		/*비동기로 백으로 값보내기 */
		let searchKeyword = (keyword) =>{
			fetch("/map/search?keyword=" + keyword)
			  .then(response => {
				  if(response.ok){	//통신 성공시
					  return response.text();
				  }else{
					  throw new Error(response.status);
				  }
			  }).then(text => {	//promise객체의 text
				  alert("성공");
			  }).catch(error => {
				  alert("실패");
			  });
		}
		
		
		/* 맵에 표시된 가게의 json정보를 담는 변수 */
		let markerInfo;
		
		
		let searchMap = (keyword) =>{
			
			// 장소 검색 객체를 생성합니다
			var ps = new kakao.maps.services.Places(); 

			// 키워드로 장소를 검색합니다
			ps.keywordSearch(keyword, placesSearchCB); 

			// 키워드 검색 완료 시 호출되는 콜백함수 입니다
			function placesSearchCB (data, status, pagination) {
			    if (status === kakao.maps.services.Status.OK) {

			        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
			        // LatLngBounds 객체에 좌표를 추가합니다
			        var bounds = new kakao.maps.LatLngBounds();

			        for (var i=0; i<data.length; i++) {
			            displayMarker(data[i]);    
			            bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
			        }       

			        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
			        map.setBounds(bounds);
			    } 
			}

			// 지도에 마커를 표시하는 함수입니다
			function displayMarker(place) {
			    
			    // 마커를 생성하고 지도에 표시합니다
			    var marker = new kakao.maps.Marker({
			        map: map,
			        position: new kakao.maps.LatLng(place.y, place.x) 
			    });
			    
			    

			    // 마커에 클릭이벤트를 등록합니다
			    kakao.maps.event.addListener(marker, 'click', function() {
			    	
			    	
			    	let reviewShow = document.querySelector(".popup-wrap");
					if (reviewShow.style.display == "none") {
						reviewShow.style.display = "";
					}else{
						reviewShow.style.display = "none";
					}
					alert('마커를 클릭했습니다!');
			    		
					
					//https://dapi.kakao.com/v2/local/search/keyword.json?y=37.514322572335935&x=127.06283102249932&radius=20000&query=카카오프렌즈
					
					fetch('https://dapi.kakao.com/v2/local/search/keyword.json?y='+ marker.getPosition().getLat() +'&x='+ marker.getPosition().getLng() +'&radius=1&query=' +keyword, {
						  headers: {
						    Authorization: `KakaoAK de36fa19e556a7179bb149f25fa41a95` 
						  }
						})
						.then(response => response.json())
						.then(json => {
						// 받은 json으로 기능 구현
						console.dir(json.documents[0]);
						markerInfo = json.documents[0];
						//음식점 이름, 주소 텍스트로 변환 
						let placeName = json.documents[0].place_name;
						let roadAddress = json.documents[0].road_address_name;
						
						//팝업창에 내용 기입
						document.querySelector('#place-name').innerHTML = placeName;
						document.querySelector('#road-address').innerHTML = roadAddress;
						
						
					});
				    
				});
			}
			
		}
		
		let shwoBox = () =>{
			let reviewShow = document.querySelector(".popup-wrap");
			if (reviewShow.style.display == "none") {
				reviewShow.style.display = "";
			}else{
				reviewShow.style.display = "none";
			}
			
			
		}
		
		
		/* 커스텀 마커 생성 */
		var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png', // 마커이미지의 주소입니다    
	    imageSize = new kakao.maps.Size(64, 69), // 마커이미지의 크기입니다
	    imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
	
		// 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
		var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
		    markerPosition = new kakao.maps.LatLng(37.54699, 127.09598); // 마커가 표시될 위치입니다
		
		// 마커를 생성합니다
		var marker = new kakao.maps.Marker({
		  position: markerPosition,
		  image: markerImage // 마커이미지 설정 
		});
		
		// 마커가 지도 위에 표시되도록 설정합니다
		marker.setMap(map);  
		
		// 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
		var content = '<div class="customoverlay">' +
		    '  <a href="https://map.kakao.com/link/map/11394059" target="_blank">' +
		    '    <span class="title">구의야구공원</span>' +
		    '  </a>' +
		    '</div>';
		
		// 커스텀 오버레이가 표시될 위치입니다 
		var position = new kakao.maps.LatLng(37.54699, 127.09598);  
		
		// 커스텀 오버레이를 생성합니다
		var customOverlay = new kakao.maps.CustomOverlay({
		    map: map,
		    position: position,
		    content: content,
		    yAnchor: 1 
		});
		
		/* 니캉내캉 선택 후 나타나는 group 친구창 */
		let changeLangSelect = () => {
			let check = document.getElementById("checkCategory");
			if ('group' == check.options[check.selectedIndex].value) {	
				document.querySelector('#friendList').style.display = "";
			}else{
				document.querySelector('#friendList').style.display = "none";
			}
		}
		
		
		
	</script>

</body>
</html>