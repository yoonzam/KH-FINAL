 (() => {    
	  let confirmNick = '';
	  
	  document.querySelector('#check_nick').addEventListener('click', ()=>{
		  let nickname = document.querySelector('#nickname').value;
		  
		  if(nickname == null){
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

 })();
