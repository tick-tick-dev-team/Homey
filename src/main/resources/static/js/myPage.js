/**
 * 마이페이지 스크립트
 */

window.onload=function(){
	const realUpload = document.querySelector('.real-upload');
    const upload = document.querySelector('.upload');

    upload.addEventListener('click', () => realUpload.click());
    realUpload.addEventListener('change', getImageFiles);
}

function getImageFiles(e) {
	const files = e.currentTarget.files;
	console.log(files);
	var frm = document.getElementById('profile');
	var fileData = new FormData();
	for(var i = 0; i < files.length; i++) {
		fileData.append("uploadFile",files[i]);
	}
	console.log(fileData);
	
    const uploadFiles = [];
    
    [...files].forEach(file => {
        if (!file.type.match("image/.*")) {
          alert('이미지 파일만 업로드가 가능합니다.');
          return;
        }
        
        if ([...files].length < 2) {
            uploadFiles.push(file);
            const reader = new FileReader();
            const img = document.querySelector('.upload');
            console.log(img);
            reader.onload = (e) => {
            	console.log(e);
            	console.log(e.target.result);
            	img.setAttribute('src', e.target.result);
            	img.setAttribute('data-file', file.name);
            };
            reader.readAsDataURL(file);
            
            
            var result =  JSON.parse(AjaxFn('POST', '/profile' , fileData));
            console.log(result);
        }
    });
}



function imgInsertForm() {
	
}
function imgReset() {
	alert("이미지 삭제")
}
function imgInsert() {
	alert("이미지 등록!")
}
