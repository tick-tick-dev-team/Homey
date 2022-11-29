/**
 * 마이페이지 스크립트
 */

window.onload=function(){
	const realUpload = document.querySelector('.real-upload');
    const upload = document.querySelector('.upload');
    const userpass = document.getElementById('userpass');
    const uChange = document.getElementById('nickChage');
	const eyeIcon = document.getElementById('eyeIcon');

    upload.addEventListener('click', () => realUpload.click());
    realUpload.addEventListener('change', getProfileImg);
    uChange.addEventListener('keyup', nickChange );
    eyeIcon.addEventListener('click', passwordShowHide);
}

function nickChange(){
	document.getElementById('nickCheckBtn').style.backgroundColor= "#fff";
	document.getElementById('nickCheckBtn').style.color= "#000";
}

function getImageFiles(e) {
	const files = e.currentTarget.files;

	var frm = document.getElementById('profile');
	const fileData = new FormData();
	for(var i = 0; i < files.length; i++) {
		fileData.append("files", files[0], files[0].name);
	}

    const uploadFiles = [];

	if(!fileSizeValidation(files)) return;
	if(!imgValidation) return;

    [...files].forEach(file => {       
        if ([...files].length < 2) {
        	
        	FetchFn('POST', '/profile' , fileData);
            
            uploadFiles.push(file);
            const reader = new FileReader();
            const img = document.querySelector('.upload');
  
            reader.onload = (e) => {
            	img.setAttribute('src', e.target.result);
            	img.setAttribute('data-file', file.name);
            };
            reader.readAsDataURL(file);
        }
    });
}

function getProfileImg(e) {
	const files = e.currentTarget.files;
	
	const input_file = document.querySelector('#uploadFile');
	const userId_input = document.querySelector('#user_id');
	
	const userId = userId_input !=null ? userId_input.value : null;

	if(!fileSizeValidation(files)) return;

	if(!imgValidation(files)){
		return;
	} else {
		if(userId) {
		const formData = new FormData();
		formData.append('file', files[0]);
		
		fetch('/users/' + userId + '/profile', {
			method : 'POST',
			body : formData
		})
		.then((response) => response.json())
		.then((attach) => {
			swal("프로필 변경 성공" + attach.attf_REALNM);
			displayProfile(attach);
		})
		.catch((error) => {
			console.error('==============error: ',  error);
		});
	}
	}
	
} 

//파일 서버이름으로 url 생성해서 이미지 src에 넣어주는 메소드
function displayProfile (attach) {
	const img = document.querySelector('.upload');
	const attfId = document.getElementById('attf_id');

	img.setAttribute('src', "/images/" + attach.attf_SERNM);
	attfId.value = attach.attf_ID;
}

// 이미지 리셋하기
function imgReset(e){
	swal({
	  text: "이미지를 기본 이미지로 변경하시겠습니까?",
	  buttons: true,
	})
	.then((willDelete) => {
	  if (willDelete) {
		const attfId = document.getElementById('attf_id').value;
		const userId = document.getElementById('user_id').value;
			
			fetch('/users/' + userId + '/profileReset/' + attfId, {
				method : 'POST'
			})
			.then(function(response){
				response.text().then(function(result){
					if(Boolean(result)){
						swal("리셋 성공!");
						const img = document.querySelector('.upload');
						img.setAttribute('src', "/img/user_icon.png");
						document.getElementById('attf_id').value = "";
					}
				})
			})
	    swal("변경이 완료되었습니다.", {
	      icon: "success",
	    });
		
	  } else {
		return;
	  }
	});
	
}

function imgValidation(files){	
    [...files].forEach(file => {
	    if (!file.type.match("image/.*")) {
	    	swal('이미지 파일만 업로드가 가능합니다.');
			return false;
	    }
	});
	return true;
}
function fileSizeValidation(files) {
	var maxSize  = 1048576;
    if([...files][0].size > maxSize) {
        swal('파일 사이즈는 1MB까지 등록 가능합니다.');
        return false;
    }
	return true;
}


function pwdCheck() {
	const userpass = document.getElementById('userpass');
	const btn = document.getElementById('pwdCheckBtn');
	
	if(userpass.value == ""){
		swal("비밀번호를 입력하세요.");
		return;
	}
	const formData = new FormData();
	formData.append('userpass', userpass.value);
	fetch('/users/pwdCheck/' ,{
				method : 'POST',
				body : formData
			})
			.then(res => res.text())
			.then(function(text){
					var isFalseBoolean = (text==='true');
					if(isFalseBoolean){
						userpass.setAttribute('readonly', "readonly");
						btn.innerHTML = "체크완료✔";
						btn.style.background = "#2585D9";
						btn.style.color = "#ffffff";
						btn.disabled = true;
					} else {
						userpass.value == ""
						swal("비밀번호가 일치하지 않습니다.");
					}
				}
			);
	
}

function check_pw(){
		if(document.getElementById('userpass').getAttribute('readonly') != "readonly" 
			&& document.getElementById('pwdCheckBtn').innerHTML != "체크완료✔"){
			swal("기존 비밀번호를 체크하세요.")
			document.getElementById('updatePw').value = "";
			document.getElementById('updatePwConfirm').value = "";
			document.getElementById('userpass').focus();
			return;
		} else {
			if(document.getElementById('updatePw').value !=" && document.getElementById('updatePwConfirm').value!="){
				if(document.getElementById('updatePw').value==document.getElementById('updatePwConfirm').value){
					document.getElementById('check').innerHTML='비밀번호가 일치합니다.'
	                document.getElementById('check').style.color= '#2585D9';
				}
				else{
					document.getElementById('check').innerHTML='비밀번호가 일치하지 않습니다.';
					document.getElementById('check').style.color='red';
				}
			}
		}
		
}

function sumitBtn(){
	const frm = document.getElementById('myPageUpdateFrm');

	const usernick = document.getElementById('usernick').value;
	const nickChage = document.getElementById('nickChage').value.split(' ').join('');
	document.getElementById('nickChage').value = nickChage;
	
	
	if(nickChage == ""){
		swal("변경할 닉네임을 입력하세요!");
		return false;	
	}
	if(usernick == nickChage){

		document.getElementById('nickCheckBtn').style.backgroundColor = "#2585D9";
		document.getElementById('nickCheckBtn').style.color = "#fff";
	}
	const btncolor = document.getElementById('nickCheckBtn').style.backgroundColor;
	if(btncolor != "rgb(37, 133, 217)"){
		swal("닉네임 중복체크를 확인하세요!");
		return false;
	} else {
		return true;	
	}
}

async function check_id(){

		const userN = document.getElementById('nickChage').value.split(' ').join('');
		const btn = document.getElementById('nickCheckBtn');
		const u = document.getElementById('usernick').value;
		
		if(userN == ""){
			swal("변경할 닉네임을 입력하세요!");
			return;	
		}
		if( userN == u){
			document.getElementById('nickCheckBtn').style.backgroundColor= "#2585D9";
			document.getElementById('nickCheckBtn').style.color= "#fff";
			return;
		} else {
			
			const formData = new FormData();
			formData.append('usernick', userN);
			formData.get('usernick');
			 
			fetch('/checkNick',{
				method : 'POST',
				body : formData
			})
			.then(res => res.text())
			.then(function(text){
					var result = text;
					if(result == "false"){
						document.getElementById('nickCheckBtn').style.backgroundColor= "#2585D9";
						document.getElementById('nickCheckBtn').style.color= "#fff";
						swal("사용 가능한 별명입니다.");
					} else {
						document.getElementById('nickCheckBtn').style.backgroundColor= "#fff;";
						document.getElementById('nickCheckBtn').style.color= "#000";
						swal("사용 불가능한 별명입니다.");
					}
				}
			);
		}
		
}

function UpdatePwBtn(){
	const frm = document.getElementById('pwUpdateFrm');
	const check = document.getElementById('check');
	
	if(pwValication(frm)){
		frm.userpass.value = frm.updatePw.value;
		frm.submit();
		return true;
	} else {
		return false;
	}

}

function pwValication(frm){
	const check = document.getElementById('check');
	var result = true;
	
	if(frm.userpass.value == ""){
		swal("기존 비밀번호를 체크해주세요.");
		return false;
	}
	if(frm.updatePw.value == "" ){
		swal("변경할 비밀번호를 입력해주세요.");
		return false;
	}
	if(document.getElementById('pwdCheckBtn').innerHTML != "체크완료✔"){
		swal("기존 비밀번호를 입력 후 체크해주세요.");
		return false;
	}
	if(check.innerHTML == "비밀번호가 일치하지 않습니다."){
		swal("변경할 비밀번호가 일치하지 않습니다.");
		return false;
	}
	return true;
}

function passwordShowHide(){
	const updatePw = document.getElementById('updatePw');
	const updatePwConfirm = document.getElementById('updatePwConfirm');
	const eyeIcon = document.getElementById('eyeIcon').childNodes[0];
	
	var inputType = updatePw.getAttribute("type");
	
	if(inputType == "password"){
		updatePw.setAttribute("type", "text");
		updatePwConfirm.setAttribute("type", "text");
		eyeIcon.className ="fa-solid fa-eye-slash fa-lg";
		
	} else {
		updatePw.setAttribute("type", "password");
		updatePwConfirm.setAttribute("type", "password");
		eyeIcon.className = "fa-solid fa-eye fa-lg";
	}
	
}