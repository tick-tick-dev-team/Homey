/**
 * 마이페이지 스크립트
 */

window.onload=function(){
	const realUpload = document.querySelector('.real-upload');
    const upload = document.querySelector('.upload');
    const userpass = document.getElementById('userpass');
    const uChange = document.getElementById('nickChage');

    upload.addEventListener('click', () => realUpload.click());
    realUpload.addEventListener('change', getProfileImg);
    uChange.addEventListener('keyup', nickChange );
    
}

function nickChange(){
	document.getElementById('nickCheckBtn').style.backgroundColor= "#fff";
	document.getElementById('nickCheckBtn').style.color= "#000";
}

function getImageFiles(e) {
	const files = e.currentTarget.files;
	console.log(files);
	var frm = document.getElementById('profile');
	const fileData = new FormData();
	for(var i = 0; i < files.length; i++) {
		fileData.append("files", files[0], files[0].name);
	}
	console.log(fileData);

    const uploadFiles = [];
    
    [...files].forEach(file => {
        if (!file.type.match("image/.*")) {
          alert('이미지 파일만 업로드가 가능합니다.');
          return;
        }
        
        if ([...files].length < 2) {
        	
        	FetchFn('POST', '/profile' , fileData);
        	//var result =  AjaxAttachFn('POST', '/profile' , fileData);
            //console.log(result);
            
            uploadFiles.push(file);
            const reader = new FileReader();
            const img = document.querySelector('.upload');
            console.log(img);
            reader.onload = (e) => {
            	img.setAttribute('src', e.target.result);
            	img.setAttribute('data-file', file.name);
            };
            reader.readAsDataURL(file);
            
        }
    });
}

function getProfileImg(e) {
	const file = e.currentTarget.files;
	
	const input_file = document.querySelector('#uploadFile');
	const userId_input = document.querySelector('#user_id');
	
	const userId = userId_input !=null ? userId_input.value : null;

	if(imgValidation(file) == false){
		return;
	} else {
		if(userId) {
		const formData = new FormData();
		formData.append('file', input_file.files[0]);
		
		fetch('/users/' + userId + '/profile', {
			method : 'POST',
			body : formData
		})
		.then((response) => response.json())
		.then((attach) => {
			alert("프로필 변경 성공" + attach.attf_REALNM);
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
	console.log(attach);
	console.log(attach.attf_ID);
	img.setAttribute('src', "/images/" + attach.attf_SERNM);
	attfId.value = attach.attf_ID;
}

// 이미지 리셋하기
function imgReset(e){
	if(confirm("이미지를 리셋하시겠어요?")){
		const attfId = document.getElementById('attf_id').value;
		const userId = document.getElementById('user_id').value;
		
		fetch('/users/' + userId + '/profileReset/' + attfId, {
			method : 'POST'
		})
		.then(function(response){
			response.text().then(function(result){
				if(Boolean(result)){
					alert("리셋 성공!");
					const img = document.querySelector('.upload');
					img.setAttribute('src', "/img/user_icon.png");
					document.getElementById('attf_id').value = "";
				}
			})
		})
		// https://csdrive.tistory.com/22
		
	}else {
		alert("이미지 리셋 실패");
		return;
	}
}

function imgValidation(files){
	var maxSize  = 1048576;
	
    [...files].forEach(file => {
	    if (!file.type.match("image/.*")) {
	    	alert('이미지 파일만 업로드가 가능합니다.');
			return false;
	    }
		if(file.size > maxSize){
			alert('파일 사이즈는 1MB까지 등록 가능합니다.');
	   		return false;
		}
	});
}


function pwdCheck() {
	const userpass = document.getElementById('userpass');
	const btn = document.getElementById('pwdCheckBtn');
	
	if(userpass.value == ""){
		alert("비밀번호를 입력하세요.");
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
						btn.style.color = "#2585D9";
						btn.disabled = true;
					} else {
						userpass.value == ""
						alert("비밀번호가 일치하지 않습니다.");
					}
				}
			);
	
}

function check_pw(){
		if(document.getElementById('userpass').getAttribute('readonly') != "readonly" 
			&& document.getElementById('pwdCheckBtn').innerHTML != "체크완료✔"){
			alert("기존 비밀번호를 체크하세요.")
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
	const nickChage = document.getElementById('nickChage').value;
	
	if(nickChage == "" || nickChage == " "){
			alert("변경할 닉네임을 입력하세요!");
		return false;	
	}
	if(usernick == nickChage){
		console.log("닉네임이 그대로인 경우");
		document.getElementById('nickCheckBtn').style.backgroundColor = "#2585D9";
		document.getElementById('nickCheckBtn').style.color = "#fff";
	}
	const btncolor = document.getElementById('nickCheckBtn').style.backgroundColor;
	if(btncolor != "rgb(37, 133, 217)"){
		alert("닉네임 중복체크를 확인하세요!");
		return false;
	} else {
		return true;	
	}
}

async function check_id(){

		const userN = document.getElementById('nickChage');
		const btn = document.getElementById('nickCheckBtn');
		const u = document.getElementById('usernick');
		
		if(userN.value == "" || userN.value == " "){
			alert("변경할 닉네임을 입력하세요!");
			return;	
		}
		if( userN.value == u.value ){
			document.getElementById('nickCheckBtn').style.backgroundColor= "#2585D9";
			document.getElementById('nickCheckBtn').style.color= "#fff";
			return;
		} else {
			const formData = new FormData();
			formData.append('usernick', userN.value);
			formData.get('usernick');
			 
			fetch('/checkNick',{
				method : 'POST',
				body : formData
			})
			.then(res => res.text())
			.then(function(text){
					console.log(text);
					var result = text;
					if(result == "false"){
						document.getElementById('nickCheckBtn').style.backgroundColor= "#2585D9";
						document.getElementById('nickCheckBtn').style.color= "#fff";
						
					} else {
						document.getElementById('nickCheckBtn').style.backgroundColor= "#fff;";
						document.getElementById('nickCheckBtn').style.color= "#000";
						alert("사용 불가능한 별명입니다.");
					}
				}
			);
		}
		
}

function UpdatePwBtn(){
	const frm = document.getElementById('pwUpdateFrm');
	const check = document.getElementById('check');
	
	console.log(frm);
	console.log(check);
	
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
		alert("기존 비밀번호를 체크해주세요.");
		return false;
	}
	if(frm.updatePw.value == "" ){
		alert("변경할 비밀번호를 입력해주세요.");
		return false;
	}
	if(document.getElementById('pwdCheckBtn').innerHTML != "체크완료✔"){
		alert("기존 비밀번호를 입력 후 체크해주세요.");
		return false;
	}
	if(check.innerHTML == "비밀번호가 일치하지 않습니다."){
		alert("변경할 비밀번호가 일치하지 않습니다.");
		return false;
	}
	return true;
}



// 탭 관련
function openCity(evt, elementName) {
	// Declare all variables
	var i, tabcontent, tablinks;

	// Get all elements with class="tabcontent" and hide them
	tabcontent = document.getElementsByClassName("tabcontent");
	for (i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}

	// Get all elements with class="tablinks" and remove the class "active"
	tablinks = document.getElementsByClassName("tablinks");
	for (i = 0; i < tablinks.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(" active", "");
	}

	// Show the current tab, and add an "active" class to the button that opened the tab
	document.getElementById(elementName).style.display = "block";
	evt.currentTarget.className += " active";
}	

