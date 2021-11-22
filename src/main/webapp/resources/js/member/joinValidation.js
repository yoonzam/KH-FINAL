
let emailReg = new RegExp("^[A-Za-z0-9](([_\.\-]?[a-zA-Z0-9]+)*)@([A-Za-z0-9]+)(([\.\-]?[a-zA-Z0-9]+)*)\.([A-Za-z]{2,})$");
let pwReg = new RegExp("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Zㄱ-힣0-9])");
let nicknameReg = new RegExp("^([0-9a-fA-Fㄱ-힣]){2,}$");

  document.querySelector('#email').addEventListener('input', () => {
	  let email = document.querySelector('#email').value;

	  if(!emailReg.test(email)){
		  document.querySelector('.box-valid-msg.email').style.display = 'inline-block';
		  document.querySelector('.fas.email').style.color = '#ddd';
	  }else{
		  document.querySelector('.fas.email').style.color = 'var(--main-color)';
	  }
  });

  document.querySelector('#password').addEventListener('input', () => {
	  let password = document.querySelector('#password').value;
	  console.dir(pwReg.test(password));
	  
	  if(!pwReg.test(password)){
		  document.querySelector('.box-valid-msg.password').style.display = 'inline-block';
		  document.querySelector('.fas.password').style.color = '#ddd';
	  }else{
		  document.querySelector('.fas.password').style.color = 'var(--main-color)';
	  }
  });

  document.querySelector('#chkPassword').addEventListener('input', () => {
	  let password = document.querySelector('#password').value;
	  let chkPassword = document.querySelector('#chkPassword').value;
	  
	  if(password != chkPassword){
		  document.querySelector('.box-valid-msg.chkPassword').style.display = 'inline-block';
		  document.querySelector('.fas.chkPassword').style.color = '#ddd';
	  }else{
		  document.querySelector('.fas.chkPassword').style.color = 'var(--main-color)';
	  }
  });

  document.querySelector('#nickname').addEventListener('input', () => {
	  let nickname = document.querySelector('#nickname').value;
	  console.dir(nicknameReg.test(nickname));

	  if(!nicknameReg.test(nickname)){
		  document.querySelector('.box-valid-msg.nickname').style.display = 'inline-block';
		  document.querySelector('.fas.nickname').style.color = '#ddd';
	  }else{
		  document.querySelector('.fas.nickname').style.color = 'var(--main-color)';
	  }	  
  });
  
  document.querySelector('#email').addEventListener('blur', () => {
	  document.querySelector('.box-valid-msg.email').style.display = 'none';
  })
  document.querySelector('#password').addEventListener('blur', () => {
	  document.querySelector('.box-valid-msg.password').style.display = 'none';
  })  
  document.querySelector('#chkPassword').addEventListener('blur', () => {
      document.querySelector('.box-valid-msg.chkPassword').style.display = 'none';
  })
  document.querySelector('#nickname').addEventListener('blur', () => {
	   document.querySelector('.box-valid-msg.nickname').style.display = 'none';
  })
