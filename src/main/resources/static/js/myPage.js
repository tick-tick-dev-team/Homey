/**
 * 마이페이지 스크립트
 */

window.onload=function(){
	const realUpload = document.querySelector('.real-upload');
    const upload = document.querySelector('.upload');

    upload.addEventListener('click', () => realUpload.click());
    realUpload.addEventListener('change', getProfileImg);
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
	
	const input_file = document.querySelector('#uploadFile');
	const userId_input = document.querySelector('#userId');
	
	const userId = userId_input !=null ? userId_input.value : null;
	
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

//파일 서버이름으로 url 생성해서 이미지 src에 넣어주는 메소드
function displayProfile (attach) {
	const img = document.querySelector('.upload');
	img.setAttribute('src', "/images/" + attach.attf_SERNM);
}



function imgInsertForm() {
	
}
function imgReset() {
	alert("이미지 삭제")
}
function imgInsert() {
	alert("이미지 등록!")
}
