function selectFile (input) {

    const reader = new FileReader();

    

    const filename = input.value;
    const file = input.files[0];
    alert("파일이름: " + filename.substring(filename.lastIndexOf("\\")+1)); 
    alert("파일이름: " + input.files[0].name); 
    
    

    
    // ajax
    httpRequest = new XMLHttpRequest();
    httpRequest.open('POST', '/tmp/new', true);
//    httpRequest.setRequestHeader('Content-Type', 'multipart/form-data');
    
    httpRequest.send(file);
    
    httpRequest.onreadystatechange = () => {
    	if (httpRequest.readyState === XMLHttpRequest.DONE) {
    		if (httpRequest.status === 200) {
    			const resourceUrl = JSON.parse(httpRequest.response);
    			console.log(resourceUrl);
    			
    			const img = document.querySelector("img#showImg");
    			img.setAttribute('src', resourceUrl);
    		} else {
    			alert("실패 : " +  httpRequest.status);
    		}
    	}
    }
}

function setThumbnail(event) {
    var reader = new FileReader();

    reader.onload = function(event) {
        // <img> 생성
        var img = document.createElement("img");
        img.setAttribute("src", event.target.result);

        // img가 들어갈 부모 찾기
        const parent = document.querySelector("div#image-container");
        // 기존 이미지 있으면 삭제
        while (parent.firstChild) {
            parent.firstChild.remove()
        }
        // 부모 안에 이미지 삽입
        parent.appendChild(img);
    };
    reader.readAsDataURL(event.target.files[0]);
}
