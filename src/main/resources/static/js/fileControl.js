function deleteAttach() {
	
	// img 삭제할 부모 태그
    const parent = document.querySelector("ul#image-container");
    
    // 기존파일 id 정보 담긴 태그
    const input_attachId = document.querySelector("input#ATTF_ID");

    if(input_attachId) { // id값 이미 있으면 deleteAttach 속성 true로 변경
        const deleteAttach = document.querySelector('#deleteAttach');
        deleteAttach.value = true;
    }
    // 파일 객체 있으면 삭제
    const input_attachObj = document.querySelector("input#ATTF_OBJ");
    if(input_attachObj) {
    	input_attachObj.value = "";   
    }
    parent.innerHTML = "";
}

function setThumbnail(event) {

    // fileSize 체크
    const files = event.currentTarget.files;

    if(!fileSizeValidation(files)) {

        event.target.value = "";
        return;
    }
	
    const reader = new FileReader();
    reader.onload = function(event) {
    	
    	// img를 넣을 부모 태그
        const parent = document.querySelector("ul#image-container");
        
        // 부모 태그 안 기존 이미지 있으면 삭제
        while (parent.firstChild) {
            parent.firstChild.remove()
        }
        
        // 자식태그 감싸는 li태그 생성
        const img_wrap = document.createElement('li');
        img_wrap.style.setProperty("min-height", "2rem");

        // isFileImage 체크해서  image이면 img태그 생성
        if(isFileImage(files[0])) {

            const img = document.createElement("img");
            img.setAttribute("src", event.target.result);
            img_wrap.appendChild(img);
        }        

        // 삭제 아이콘 생성 및 클래스, onclick 삽입
        const icon = document.createElement('i');
        icon.setAttribute('class', 'fa-solid fa-trash-can btn-delete fa-xl');
        icon.addEventListener('click', deleteAttach);

        // 부모 안에 li 태그 삽입
        img_wrap.appendChild(icon);
        parent.appendChild(img_wrap);
    };
    reader.readAsDataURL(event.target.files[0]);
}

// 이미지인지 검사
function isFileImage(file) {
    return file && file['type'].split('/')[0] === 'image';
}

function fileSizeValidation(files) {
	var maxSize  = 1048576;
    if([...files][0].size > maxSize) {
        swal("파일 사이즈는 1MB까지 등록 가능합니다.", {
	   		icon: "warning",
	    });
        return false;
    }
	return true;
}



