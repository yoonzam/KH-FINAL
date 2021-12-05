
let viewFollowing = (memberId) => {
	
	$('.wrap-list-following').empty();

	let data = {id : memberId};
	let header = new Headers();
	header.append('Content-Type', 'application/json;charset=UTF-8 ');

	fetch('/member/follow-pop',{
	 	method : 'POST',
	 	headers : header,
	 	body : JSON.stringify(data),
	 	
	}).then(response => {
		if(response.ok){
			return response.json();
	 	}else{
	  		throw new Error(response.status);
	 	}
	}).then(data => {
		let html = '';
		if(data.length == 0){
			html += '<h3 style="text-align:center; padding-top:15px;">잇친을 팔로우해 보세요!</h3>';
		}
		
		if(data.length > 0){
			for (var i = 0; i < data.length; i++) {
				html += '<li id="' + data[i].memberId + '">'	//팔로워
					  + '	<a href="/member/follow/' + data[i].member.nickname + '">'+ data[i].member.nickname +'</a>'
					  + '	<a onclick="popUnfollow(\''+data[i].memberId+'\')" class="btn-pop unfollow">잇친끊기</a>'
					  + '</li>'
			}			
		}

		document.querySelector('#following-pop').style.display = 'flex';
		$('.wrap-list-following').append(html);
		
	}).catch((error) => {
		  console.error('Error', error);
	});
	
	
};

let viewFollower = (memberId) => {
	
	$('.wrap-list-follower').empty();

	let data = {id : memberId};
	let header = new Headers();
	header.append('Content-Type', 'application/json;charset=UTF-8 ');

	fetch('/member/follower-pop',{
	 	method : 'POST',
	 	headers : header,
	 	body : JSON.stringify(data),
	 	
	}).then(response => {
		if(response.ok){
			return response.json();
	 	}else{
	  		throw new Error(response.status);
	 	}
	}).then(data => {
		let html = '';
		let followEachOther = new Array();
		let followerInfo = new Array();
		followEachOther = data.followEachOther; //Follower 서로 친구인 멤버의 id를 담은 리스트
		followerInfo = data.memberInfo;			//List<Map>\
		followDiffId = data.followDiffId;
		
		if(followerInfo.length == 0){
			html += '<h3 style="text-align:center; padding-top:15px;">나를 팔로우한 잇친이 없습니다.</h3>';
			$('.wrap-list-follower').append(html);
		}
		
		if(followerInfo.length > 0){
			for (var i = 0; i < followerInfo.length; i++) {	//팔로워
				
				let list = document.createElement('li');
				let aNickname = document.createElement('a');
				
				list.id = followerInfo[i].memberId;
				$('.wrap-list-follower').append(list);
				
				aNickname.href='/member/follow/' + followerInfo[i].member.nickname;
				aNickname.innerHTML = followerInfo[i].member.nickname;
				
				$('#' + followerInfo[i].memberId).append(aNickname);				
			}
			
			//필터 안됨! Object로 넘어오는 문제 (forEach도 안됨!)
			function filterBySameId(obj) {
				let res = new Array();
				
				for (var i = 0; i < followEachOther.length; i++) {
					for (var j = 0; j < obj.length; j++) {
						if(followEachOther[i].memberId == obj[j].memberId) {
							res.push(followEachOther[i].memberId);								
						}
					}					
				}
				return res;
			}
			function filterByDiffId(obj) {
				let res = new Array();
				
				for (var i = 0; i < followEachOther.length; i++) {
					for (var j = 0; j < obj.length; j++) {
						if(followEachOther[i].memberId != obj[j].memberId) {
							res.push(followEachOther[i].memberId);							
						}
					}					
				}
				return res;
			}
			//let arrEachOther = followerInfo.filter(filterBySameId);
			//let arrDiff = followerInfo.filter(filterByDiffId);
			
			let aBtn = '';
			
			for (var i = 0; i < followEachOther.length; i++) {
				aBtn = document.createElement('a');				
				aBtn.className = 'btn-pop unfollow';
				aBtn.innerHTML = '잇친 끊기';
				$('#' + followEachOther[i].memberId ).append($(aBtn));
				$('#' + followEachOther[i].memberId + ' a:nth-child(2)').attr('onclick', "popUnfollow('"+ followEachOther[i].memberId +"')");
			}

			for (var i = 0; i < followDiffId.length; i++) {
				aBtn = document.createElement('a');				
				aBtn.className = 'btn-pop follow';
				aBtn.innerHTML = '잇친 맺기';
				$('#' + followDiffId[i].memberId ).append($(aBtn));
				$('#' + followDiffId[i].memberId + ' a:nth-child(2)').attr('onclick', "popfollow('"+ followDiffId[i].memberId +"')");	
			}
		}
		document.querySelector('#follower-pop').style.display = 'flex';
	
	}).catch((error) => {
		  console.error('Error', error);
	});	
	
	
}


let popUnfollow = (followingId) => {
	$.ajax({
		type: 'POST',
		url: '/member/follow-cancel',
		data: JSON.stringify({ followingId : followingId }),
		contentType: 'application/json',
	 	cache:false,
		success: (memberId) => {
			$('#' + memberId).remove();
		},
		error: (e) => {
			console.error("Error" , e);
		}
	});
}

let popfollow = (followingId) => {	//팔로우할 아이디
	$.ajax({
		type: 'POST',
		url: '/member/follow',
		data: JSON.stringify({ followingId : followingId }),
		contentType: 'application/json',
		cache: false,
		success: (memberId) => {
			$('#' + memberId + ' a:nth-child(2)').attr('class',"btn-pop unfollow");
			$('#' + memberId + ' a:nth-child(2)').attr('onclick', 'popUnfollow('+ memberId +')');
			$('#' + memberId + ' a:nth-child(2)').text('잇친 끊기');
		},
		error: (e) => {
			console.error("Error" , e);
		}
	});	
}