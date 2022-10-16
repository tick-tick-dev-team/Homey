/*회원가입 페이지 스크립트*/

	/* https://inpa.tistory.com/entry/JS-%F0%9F%93%9A-AJAX-%EC%84%9C%EB%B2%84-%EC%9A%94%EC%B2%AD-%EB%B0%8F-%EC%9D%91%EB%8B%B5-fetch-api-%EB%B0%A9%EC%8B%9D
		fetch에는 기본적으로 첫번째 인자에 요청할 url
		기본적으로 http 메소드 중 Get으로 동작
		fetch를 통해 ajax 를 호출 시 해당 주소에 요청을 보낸 다음, 응답객체(Promise object Response 를 받는다.
		그러면 첫번째 then에서 그 응답을 받고
		res.text()메서드로 파싱한 text 값을 리턴
		그러면 다음 then에서 리턴받은 text값을 받고 원하는 처리
	*/
	
/*별명 중복 확인*/
async function check_id(){

		const userN = document.getElementById('formnick').value;
		
		if (document.getElementById('formnick').value==''){
			alert('별명을 입력해주세요.')
			return;
		}
		 
		const formData = new FormData();
		formData.append('usernick', userN);
		formData.get('usernick')
		 
		
		fetch('/checkNick',{
			method : 'POST',
			body : formData
		})
		.then(res => res.text())
		.then(text => alert(text));

}


/*패스워드 일치여부 확인*/
function check_pw(){
	const target = document.getElementById('target_btn');
	
	
	if(document.getElementById('pw').value !=" && document.getElementById('pw2').value!="){
		if(document.getElementById('pw').value==document.getElementById('pw2').value){
			document.getElementById('check').innerHTML='비밀번호가 일치합니다.'
            document.getElementById('check').style.color='blue';
			target.disabled = false;
		}
		else{
			document.getElementById('check').innerHTML='비밀번호가 일치하지 않습니다.';
			document.getElementById('check').style.color='red';
			target.disabled = true;
		}
	}
}