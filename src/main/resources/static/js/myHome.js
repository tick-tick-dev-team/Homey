/* 마이홈 업데이트 스크립트 */

window.onload=function(){
	const realUpload = document.querySelector('.real-upload');
    const upload = document.querySelector('.imgbox');

    upload.addEventListener('click', () => realUpload.click());
    realUpload.addEventListener('change', setThumbnail);  
    
    //readOnly 회색처리
    document.getElementById("readOnly").style.background = "#e5e5e5";
    
}


//multipartform으로 변경하면서 쓰이는 것
function setThumbnail(event) {

    // fileSize 체크
    const files = event.currentTarget.files;

    if(!fileSizeValidation(files)) {
        event.target.value = "";
        return;
    }

    //img인지 체크
    if(!imgValidation(files)){
    	event.target.value ="";
    	return;
    }
	
    const reader = new FileReader();
    reader.onload = function(event) {
    	
    	// img를 넣을 부모 태그
        const parent = document.querySelector(".imgbox"); 
        
        // 부모 태그 안 기존 이미지 있으면 삭제 //이미지 박스 안 이미지를 아예 삭제하고 새로운 이미지를 넣어줘야한다.
        while (parent.innerHTML!='') {
            parent.innerHTML="";
        }
        
        //새로운 이미지 자리 만들기
        const img = document.createElement("img");
        //이미지 src 넣어주기
        img.setAttribute("src", event.target.result);

        //원 안에 이미지 넣기
        parent.appendChild(img);
    };
    reader.readAsDataURL(event.target.files[0]); //얘는 꼭 필요
}

//파일 사이즈 검증
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

//이미지인지 검증
function imgValidation(files){	

    if (![...files][0].type.match("image/.*")) {
    	swal("이미지 파일만 업로드가 가능합니다.", {
	      icon: "warning",
	    });
		return false;
    }
	
	return true;
}




//이미지 리셋하기
function imgReset(e){
	swal({
	  text: "이미지를 리셋하시겠습니까?",
	  buttons: true,
	})
	.then((willDelete) => {
		if (willDelete) {
			const attfId = document.getElementById('attf_id').value;
			
			// img를 넣을 부모 태그
	        const parent = document.querySelector(".imgbox"); 
	        
	        // 부모 태그 안 기존 이미지 있으면 삭제 //이미지 박스 안 이미지를 아예 삭제하고 새로운 이미지를 넣어줘야한다.
	        while (parent.innerHTML!='') {
	            parent.innerHTML="";
	        }
	        
	        //deleteAttach 속성 true로 변경
	        if(attfId) { 
	        	// id값 이미 있으면 deleteAttach 속성 true로 변경
	            const deleteAttach = document.querySelector('#deleteAttach');
	            deleteAttach.value = true;
	        }

			// 파일 객체 있으면 삭제
			const input_attachObj = document.querySelector("input#uploadFile");
			if(input_attachObj) {
				input_attachObj.value = "";   
			}
	
	        //기본 이미지로 만들어준다.
			const img = document.createElement("img");
			img.setAttribute('src', "/img/defaultHome.png");
			//document.getElementById('attf_id').value = "";
			parent.appendChild(img);
	
			//reader.readAsDataURL(event.target.files[0]); //바이너리 파일을 읽어들일때 사용
		} else {
			swal("잠시후 다시 시도해주시기 바랍니다.", {
		      icon: "error",
		    });
			return;
		}
	});
}


//수정버튼 눌렀을시
function gowith(){
	//alert("수정완료되었습니다");
	swal({
	  text: "홈을 수정하시겠습니까?",
	  buttons: true,
	})
	.then((willDelete) => {
		if (willDelete) {
			document.getElementById("frm").submit();	
		} else {
			return;
		}
	});
}