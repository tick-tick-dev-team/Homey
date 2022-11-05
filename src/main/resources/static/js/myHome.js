/**
 * 마이홈 업데이트 스크립트
 */

window.onload=function(){
	const realUpload = document.querySelector('.real-upload');
    const upload = document.querySelector('.upload');

    upload.addEventListener('click', () => realUpload.click());
    realUpload.addEventListener('change', getProfileImg);    
    
}


/*'/home/' + homeId + '/profile' 는 AttachController에 있음*/
/*검증 및 이미지 등록*/
function getProfileImg(e) {
	const file = e.currentTarget.files;
	
	const input_file = document.querySelector('#uploadFile');
	const homeid_input = document.querySelector('#homeid');
	const homeId = homeid_input !=null ? homeid_input.value : null;

	var maxSize  = 1048576;
	
    [...file].forEach(file => {
	    if (!file.type.match("image/.*")) {

	    	alert('이미지 파일만 업로드가 가능합니다.');
	    	return false;
	    }
		if(file.size > maxSize){
			alert('파일 사이즈는 1MB까지 등록 가능합니다.');
	   		return false;
		}
		else{
			
				const formData = new FormData();
				formData.append('file', input_file.files[0]);
			
				fetch('/homes/' + homeId + '/profile', {
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
	});
	
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
		const homeId = document.getElementById('homeid').value;
		
		fetch('/homes/' + homeId + '/profileReset/' + attfId, {
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


function gowith(){
		alert("수정완료되었습니다.");
}
