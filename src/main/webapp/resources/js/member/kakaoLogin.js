	
Kakao.init('478e845e80d3b693f2a53821b0866272');

let kakaoLogin = () => {
    Kakao.Auth.login({
			 scope : 'account_email',
             success : (ACCESS_TOKEN) => {
						requestInfo();
			}
             ,fail : (err) => {
                showResult(JSON.stringify(err))
             },
          });
 };
 
 
 let requestInfo = () => {
    Kakao.API.request({
             url : '/v2/user/me',
             data : {
				property_keys : ["kakao_account.email"]
			},
             success : (res) => passUserInfo(res)
             ,fail : (error) => {
                alert('login success, but failed to request user information: '
                      + JSON.stringify(error))
             },
          });
};

let passUserInfo = res => {
	
	const kakaoId = res.id;
	const email = res.kakao_account.has_email ? res.kakao_account.email : 'kakao user';
	let data = {kakaoId : kakaoId, email : email};
	
	let header = new Headers();
	header.append('Content-Type', 'application/json;charset=UTF-8 ')

	fetch('/member/kakao-login', {
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
		if(text == 'kakaoLogin'){
			location.href = '/main/';
		}else if(text == 'kakaoJoin'){	
			location.href ='/member/kakao-join?kakaoId='+ kakaoId + '&email=' + email;
		}
	}).catch(error => {
		alert(error + '응답에 실패하였습니다.');
	})
	
}