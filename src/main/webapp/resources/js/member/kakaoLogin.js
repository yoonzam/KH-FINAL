	
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

	fetch('/member/kakao-login?kakaoId=' + res.id , {
		method : 'POST',
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
			location.href ='/member/kakao-join?kakaoId='+res.id;
		}
	}).catch(error => {
		alert(error + '응답에 실패하였습니다.');
	})
	
}