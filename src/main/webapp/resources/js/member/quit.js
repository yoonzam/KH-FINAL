 
  document.querySelector('#btn-quit').addEventListener('click', (e) => {
	  e.preventDefault();
	  
	  let nickname = document.querySelector('#nickname').value;
	  let password = document.querySelector('#password').value;
	  let id = document.querySelector('#id').value;
  	  
	  let data = {id : id, nickname : nickname, password : password};
	  let header = new Headers();
	  header.append('Content-Type', 'application/json;charset=UTF-8 ')
	  
	  fetch('/member/quit',{
		  method : 'POST',
		  headers : header,
		  body : JSON.stringify(data),
		  
	  }).then(response => {
		  if(response.ok){
			  return response.text();
		  }else{
			  throw new Error(response.status);
		  }
	  }).then(text => {
		  if(text == 'quit'){
			  alert('정말로 탈퇴하시겠습니까?');
			  location.href = '/member/leave';
		  }else{
			  document.querySelector('.message').style.color='var(--red-color)';
			  document.querySelector('.message').innerHTML = '비밀번호가 일치하지 않습니다.';
		  }
	  }).catch((error) => {
		  console.error('Error', error);
	  })
  
  })