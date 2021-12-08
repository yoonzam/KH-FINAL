<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<link rel="stylesheet" type="text/css"
	href="/resources/css/timeline/timeline.css" />
<script type="text/javascript"
	src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=478e845e80d3b693f2a53821b0866272&libraries=services,clusterer,drawing"></script>
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
	flex: 5;
	display: flex;
	flex-direction: column;
	justify-content: center;
}

.rest-title {
	font-weight: 700;
	font-size: 20px;
	color: #111;
	margin-top: 0;
	margin-bottom: 10px;
}

.wrap-btn {
	flex: 1;
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
	transform: translateY(-50%);
}

.btn:hover {
	background-color: #ffa54f;
}
.customoverlay {
    position: relative;
    border-radius: 6px;
    border: 1px solid #ccc;
    border-bottom: 2px solid #ddd;
    float: left;
}	
.customoverlay a {
    display: block;
    text-decoration: none;
    color: #000;
    text-align: center;
    border-radius: 6px;
    font-size: 14px;
    font-weight: bold;
    overflow: hidden;
    background: #d95050;
    background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center;
}
.customoverlay .title {
    display: block;
    text-align: center;
    background: #fff;
    margin-right: 35px;
    padding: 10px 15px;
    font-size: 14px;
    font-weight: bold;
}
.imgBox {
    width: 50px;
    height: 50px; 
    border-radius: 70%;
    overflow: hidden;
}
.profile {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
.noSearch{
	width: 100%;
	align-self: center;
    text-align: center;
}
.review-create-btn{
    top: 9px;
    left: 9px;
    width: 120px;
    border: 1px solid #ddd;
    background-color: #fff;
    padding: 10px;
    text-align: center;
    cursor: pointer;
    color: #fa8633;
    font-weight: 900;
}
.review-create-btn:hover {
    border: 1px solid #fa8633;
}
.no_review_wrap{
	margin-bottom: 10px;
    margin-top: 10px;
    width: 98%;
    height: 25%;
    display: flex;
    border-radius: 8px;
    border: 1px;
    background-color: white;
    box-shadow: 2px 2px 4px rgb(0 0 0 / 30%);
}
.noSearch div{
	font-size: 20px;
    padding: 6px 15px;
    font-weight: 700;
    margin-top: 10px;
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
							placeholder="식당 후기 검색하기"></label> 
							<a id="search" class="search-btn">검색</a>
					</div>
					<div class="search-option">
						<a id="search" class="search-btn" onclick="myEatsMap()">마이잇츠맵</a>
						<a id="search" class="search-btn" onclick="myFollowMap()">소셜맵</a>
					
						<div class="select-bar">
							
							<select id="friendList" name="friends" class="select"
								style="display: none;" onchange="changeMemberSelect('${groupId.id}')">
								<option disabled selected>🍟잇친이들의 맛집</option>
								<c:forEach items="${groups}" var="groups">
									<option value="${groups.id}">${groups.groupName}</option>
								</c:forEach>
							</select> 
							<select name="category" class="select"
								onchange="changeLangSelect()" id="checkCategory">
								<option selected="selected">🎈잇츠맵카테고리</option>
								<option value="group">니캉내캉</option>
								
							</select>
						</div>
					</div>
				</div>
			</div>
			<div class="map-review">
				

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
			e.stopPropagation();
		}else{
			e.className = "fas fa-unlock";
			e.stopPropagation();
		}
	}
		//마커 담을 배열
		var markers = [];
		var overlays = [];
		var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
		var options = { //지도를 생성할 때 필요한 기본 옵션
			center : new kakao.maps.LatLng(37.54699, 127.09598), //지도의 중심좌표.
			level : 3
		//지도의 레벨(확대, 축소 정도)
		};
		var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
		
			
		
		document.querySelector('#search').addEventListener('click', (e) => {
		    let keyword = document.querySelector('.keyword').value;
			
			searchKeyword(keyword);
			document.querySelector(".popup-wrap").style.display = 'none';
		});
		
		document.querySelector('.keyword').addEventListener('keyup', (e)=> {
		    if (e.keyCode === 13) {
		    	let keyword = document.querySelector('.keyword').value;
				
				searchKeyword(keyword);
				document.querySelector(".popup-wrap").style.display = 'none';
		  }  
		});
		
		/*비동기로 백으로 값보내기 */
		let searchKeyword = (keyword) =>{
			fetch("/map/search?keyword=" + keyword)
			  .then(response => {
				  if(response.ok){	//통신 성공시
					  return response.json();
				  }else{
					  throw new Error(response.status);
				  }
			  }).then(json => {	//promise객체의 json
				
				removeMarker();
			  	removeOverlay();
			  	searchMap(keyword,json);		  
				
			  	markerCreate(json);
			  	
				
			  }).catch(error => {
				  alert("실패");
			  });
			
			
		  
				
		}
		//이전 검색 삭제
		function removeAllChildNodes() {
			let parent = document.querySelector('.map-review');
		    while (parent.firstChild) {
		        parent.removeChild(parent.firstChild);
		    }
		}
		
		//divCreate
		let divCreate = (data) => {
			
			removeAllChildNodes();
			if (data.length == 0) {
				let noSearchDiv = '<div class="no_review_wrap">'
					+'<div class="noSearch">'
					+'<div>후기가 존재하지 않습니다</div>'
					+'<div><a id="search" class="review-create-btn" onclick="uploadReview();">후기 등록</a></div></div></div>'
				var $div = $(noSearchDiv);
				  $('.map-review').append($div);
			}else{
				for (var i = 0; i < data.length; i++) {
					  let returnDiv = takeReview(data[i].reviewId,data[i].review.addr,data[i].review.resName,data[i].review.hashtag,data[i].review.thumUrl);
					  
					  var $div = $(returnDiv);
					  $('.map-review').append($div);
				}
			}
			
		}
		
		//리뷰검색 마크 생성
		let markerCreate = (reviews,Id) =>{
			
			var positions = [];
			var bounds = new kakao.maps.LatLngBounds();
			let filterdMap;
			if (Id != null) {
				filterdMap = reviews.filter((review)=>{
					
					if (review.review.memberId.date == Id.date 
							&& review.review.memberId.timestamp == Id.timestamp) {
						return true;
					}else{
						return false;
					} 
				});
				if (filterdMap.length == 0) {
					return alert("후기글이 없습니다.");
				}
				
			}else{
				filterdMap = reviews;
			}
			removeMarker();
		  	removeOverlay();
			
			divCreate(filterdMap);
			
			
			for (var i = 0; i < filterdMap.length; i++) {
				
				let mark = {
						place_name: filterdMap[i].review.resName, 
				    	img : filterdMap[i].review.thumUrl,
						x : filterdMap[i].review.location.coordinates[0],
				        y : filterdMap[i].review.location.coordinates[1]
				 }
				positions.push(mark);
			}

			
			positions.forEach(function(position, idx) {
				
			 	// 마커를 생성합니다
			    displayOverlay(position,position.img);
			    
			    bounds.extend(new kakao.maps.LatLng(position.y, position.x));
			});
			 map.setBounds(bounds);
			
		}
		let showDiv = (placeName,roadAddress) =>{
			let reviewShow = document.querySelector(".popup-wrap");
			//같은 내용일경우 
			if (document.querySelector('#place-name').innerHTML == placeName 
					&& document.querySelector('#road-address').innerHTML == roadAddress 
					&& reviewShow.style.display == "") {
				
				reviewShow.style.display = "none";
			}else if(document.querySelector('#place-name').innerHTML != placeName 
					&& document.querySelector('#road-address').innerHTML != roadAddress 
					&& reviewShow.style.display == ""){
				reviewShow.style.display = "";
			}else{
				reviewShow.style.display = "";
			}
		}
		
		let restInfo = (y,x,keyword) =>{
			 
			 
			fetch('https://dapi.kakao.com/v2/local/search/keyword.json?y='+ y +'&x='+ x +'&radius=1&query=' +keyword, {
				  headers: {
				    Authorization: `KakaoAK de36fa19e556a7179bb149f25fa41a95` 
				  }
				})
				.then(response => response.json())
				.then(json => {
				// 받은 json으로 기능 구현
				markerInfo = json.documents[0];
				//음식점 이름, 주소 텍스트로 변환 
				let placeName = json.documents[0].place_name;
				let roadAddress = json.documents[0].road_address_name;
				
				
				//
				showDiv(placeName,roadAddress);
				
				//팝업창에 내용 기입
				document.querySelector('#place-name').innerHTML = placeName;
				document.querySelector('#road-address').innerHTML = roadAddress;
			});
			
			
		}
		
		
		
		//review div 받아온 리뷰에 맞게 수정하는 함수
		let takeReview = (id,address,name,tag,file) =>{
			
			
			let tags = '';
			for (var i = 0; i < tag.length; i++) {
				tags += '<span>#'+tag[i]+'</span>';
			}
			let img = '';
			if (!file) {
				img = "/resources/img/upload/01.jpg";
			}else{
				img = "http://localhost:9090" + file;
			}
			
			let addr = address;
			let na = name;
			let t = tag;
			//문자열 자르기
			let splitAddr = address.split(' ');
			let splitName = name.split(' ');
			
			let reviewContent = 
				'<div class="review_wrap" onclick="viewTimeline(\'' + id + '\')">'
				+'<div class="img-box">'
				+'<img class="image-thumbnail" src='+ img +'>'
				+'</div>'
				+'<div class="info">'
				+'<div class="title-wrap">'
				+'<div class="eats-name">'+splitName[0]+'</div>'	
				+'<div class="icons"></div>'		
				+'</div>'
				+'<div class="eats-location">'+ splitAddr[0] + ' ' +splitAddr[1] +'</div>'
				+'<div class="eats-tag">'+ tags +'</div>'	
				+'</div>';
			
			return reviewContent;
		}
		
		
	
		/* 맵에 표시된 가게의 json정보를 담는 변수 */
		let markerInfo;	
				
		let searchMap = (keyword,markerData) =>{
			
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
			        
			        
			        //데이타 중복 마커 처리
			        let processingData = processingMarker(data,markerData);
			        if (processingData.length == 0) {
						return;
					}
			        for (var i=0; i<processingData.length; i++) {
			        	
			            displayMarker(processingData[i]);    
			            bounds.extend(new kakao.maps.LatLng(processingData[i].y, processingData[i].x));
			        }       
			        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
			        map.setBounds(bounds);
			    } 
			}
			
		}
		
		
		
		// 지도에 마커를 표시하는 함수입니다
		function displayMarker(place,markerImage) {
		    
		    // 마커를 생성하고 지도에 표시합니다
		    var marker = new kakao.maps.Marker({
		    	title : place.place_name,
		        map: map,
		        image : markerImage,
		        position: new kakao.maps.LatLng(place.y, place.x) 
		    });
		    markers.push(marker);	
		    
		    // 마커에 클릭이벤트를 등록합니다
		    kakao.maps.event.addListener(marker, 'click', function() {
		    	restInfo(marker.getPosition().getLat(),marker.getPosition().getLng(),marker.getTitle());
				    	
			});
		}
		
		//이미지 오버레이 생성
		function displayOverlay(place,markerImage) {
		    
		    // 마커를 생성하고 지도에 표시합니다
		    let img = '';
			if (!markerImage) {
				img = "/resources/img/upload/01.jpg";
			}else{
				img = "http://localhost:9090" + markerImage;
			}
		   
		    var customOverlay;
		    var content =
			     '<div class="imgBox" style="background: #BDBDBD;" onclick="restInfo(\'' + place.y + '\',\'' +  place.x + '\',\'' + place.place_name + '\');">'
				    +'<img class="profile" src='+ img +'>'   
				    +'</div>';
		    
		    var customOverlay = new kakao.maps.CustomOverlay({
		        map: map,
		        clickable: true,
		        content: content,
		        position: new kakao.maps.LatLng(place.y, place.x),
		        xAnchor: 0.5,
		        yAnchor: 1,
		        zIndex: 3
		    });
		    overlays.push(customOverlay);	
		    
		    
		    // 마커에 클릭이벤트를 등록합니다
		    
		}
		
		//마커 중복제거 
		let processingMarker = (data,markerData) =>{
			let processedMarker = data.filter((review)=>{
	        	for (var i = 0; i < markerData.length; i++) {
	        		if (review.x  == markerData[i].review.location.x 
	        				&& review.y == markerData[i].review.location.y ) {
						return false;
					}
				}
	        	return true;
	        });
			
			return processedMarker;
		}
		
		
		/* 니캉내캉 선택 후 나타나는 group 친구창 */
		let changeLangSelect = () => {
			let check = document.getElementById("checkCategory");
			if ('group' == check.options[check.selectedIndex].value) {
				document.querySelector('#friendList').style.display = "";
			}else{
				document.querySelector('#friendList').style.display = "none";
			}
		}
		
		//그룹창 리뷰 리스트 출력
		let findGroupMember = (groupId) =>{
			fetch("/map/group?groupId=" + groupId)
			  .then(response => {
				  if(response.ok){	//통신 성공시
					  return response.json();
				  }else{
					  throw new Error(response.status);
				  }
			  }).then(json => {	//promise객체의 json
				//optionAdd(json.memberList,groupId);
				//groupFilterMap(groupId);
				if (document.querySelector(".popup-wrap").style.display == "") {
					document.querySelector(".popup-wrap").style.display = "none";
				}
				//맵에 그룹 마크 출력
				if (!json.groupReview) {
					return alert("그룹 리뷰가 존재하지 않습니다.");
				}else{
					//그룹 리스트 출력
					
					//원래 있는 지도에 마커 지우기
					removeOverlay();
					removeMarker();
					//지도 위치 수정 및 마커 출력 //"http://t1.daumcdn.net/localimg/localimages/07/2012/img/marker_normal.png";
					markerCreate(json.groupReview);
				}
						  
			  }).catch(error => {
				  alert("실패");
			  });
			
		}
		
		let groupFilterMap = (groupId) => {
			let filteredMap = myEetsReview.filter((data)=>{
	        	if (data.review.group == groupId) {
					return true;
				}
	        	return false;
	        });
			return filteredMap;
		}
		
		
		//마크 지우기 메서드
		function removeMarker() {
		    for ( var i = 0; i < markers.length; i++ ) {
		        markers[i].setMap(null);
		    }   
		    markers = [];
		}
		
		//오버레이 지우기 메서드
		let removeOverlay = () => {
			 for ( var i = 0; i < overlays.length; i++ ) {
				 	overlays[i].setMap(null);
			    }   
			 overlays = [];
			 
		}
		
		
		//map 초기 화면에 리뷰 리스트와 마커 뿌려주기
		var myEetsReview = 	${reviews};	
		
		let changeMemberSelect = (groupId) => {
			let check = document.getElementById("friendList");
			findGroupMember(check.options[check.selectedIndex].value);
		}
		
		
		//마이잇츠맵 불러오기
		let myEatsMap = () => {
			let myEetsMap = ${reviews};
			let myId = ${myObjectId};
			
		  	
			markerCreate(myEetsMap,myId);
		}
		
		//내 팔로워맵 불러오기
		let myFollowMap = () => {
			let myEetsMap = ${reviews};
			let following = ${jsonFollow};
			if (following.length == 0) {	
				return alert("내가 팔로워한 잇친이들이 없습니다.");
			}
		  	let filterdMap = filterFollowMap(myEetsMap,following);
			markerCreate(filterdMap); 
		}
		
		let filterFollowMap = (review,follows) =>{
			let filterdMap = review.filter((review)=>{
				for (var i = 0; i < follows.length; i++) {
					if (review.review.memberId.date == follows[i].followingId.date 
							&& review.review.memberId.timestamp == follows[i].followingId.timestamp) {
						return true;
					}
				}
				return false;
				
			});
			
			return filterdMap;
		}
		let myEetsMap = ${reviews};
		//loadMap
		//처음 로딩 리뷰 리스트 마커 출력
		let loadMap = () =>{
			let myEetsMap = ${reviews};
			markerCreate(myEetsMap);
		}
		
		loadMap();
	</script>
	<script type="text/javascript" src="/resources/js/map/Geolocation.js"></script>

</body>
</html>
