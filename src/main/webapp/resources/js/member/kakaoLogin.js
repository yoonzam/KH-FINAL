	
Kakao.init('478e845e80d3b693f2a53821b0866272');

let kakaoLogin = () => {
    Kakao.Auth.loginForm({
             success : authKakao()
             ,fail : (err) => {
                showResult(JSON.stringify(err))
             },
          });
 };
 
 let authKakao = () => {
    Kakao.Auth.login({
             scope : 'account_email,profile_nickname',
             success : requestInfo()
             
             ,fail : (error) => {
                console.dir(error)
             },
          });
};
 
 let requestInfo = () => {
    Kakao.API.request({
             url : '/v2/user/me',
             success : (res) => passUserInfo(res)
             ,fail : (error) => {
                alert('login success, but failed to request user information: '
                      + JSON.stringify(error))
             },
          });
};

let passUserInfo = res => {

	let hasEmail = res.kakao_account.has_email;
	let nickname = res.kakao_account.profile.nickname;
	
	fetch('/member/kakao-login?userId=' + res.id 
							+ '&email=' + hasEmail ? res.kakao_account.email : '' 
							+ '&nickname=' + nickname != null ? nickname : '', {
		method : 'GET',
	}).then(response => {
		if(response.ok){
			return response.text();
		}else{
			throw new Error(response.status);
		}
		
	}).then(text => {
		if(text == 'kakaoLogin'){
			location.href = '/';
		}else if(text == 'kakaoJoin'){	
			location.href = '/member/kakao-join?userId='+res.id 
							+ '&email=' + hasEmail ? res.kakao_account.email : '' 
							+ '&nickname=' + nickname != null ? nickname : '';		
		}
	}).catch(error => {
		alert(error + '응답에 실패하였습니다.');
	})
	
}