function setThumbnail(event) {
    const reader = new FileReader();

    reader.onload = function(event) {

        const img_wrap = document.createElement('li');

        const i = document.createElement('i');
        i.setAttribute('class', 'fa-solid fa-trash-can btn-delete');
        i.addEventListener('click', deleteAttach);

        const img = document.createElement("img");
        img.setAttribute("src", event.target.result);

        img_wrap.appendChild(img);
        img_wrap.appendChild(i);
        
        // img가 들어갈 부모 찾기
        const parent = document.querySelector("ul#image-container");
        // 기존 이미지 있으면 삭제
        while (parent.firstChild) {
            console.log("111111 첨부파일 객체 = " + parent.firstChild);
            parent.firstChild.remove()
        }
        // 부모 안에 이미지 삽입
        parent.appendChild(img_wrap);
    };
    reader.readAsDataURL(event.target.files[0]);
}

function deleteAttach() {

    const parent = document.querySelector("ul#image-container");
    const attachId = document.querySelector("input#ATTF_ID").value

    console.log("2222222첨부파일 객체 = " + parent.firstChild);
    console.log("첨부파일 삭제 id = " + attachId);

    if(attachId) { // id값 이미 있으면 파일도 삭제하기
        const deleteAttach = document.querySelector('#deleteAttach');
        deleteAttach.value = true;
        // ajax
        // httpRequest = new XMLHttpRequest();
        // httpRequest.open('DELETE', '/attach/' + attachId, true);
        // // httpRequest.setRequestHeader('Content-Type', 'application/json');

        // // var data = {
    	// // 	attachId : attachId
        // // };
        
        // httpRequest.send();
        
        // httpRequest.onreadystatechange = () => {
        //     if (httpRequest.readyState === XMLHttpRequest.DONE) {
        //         if (httpRequest.status === 200) {
        //             alert("기존파일 삭제 성공");
        //         } else {
        //             alert("실패 : " +  httpRequest.status);
        //             console.log(httpRequest.response);
        //         }
        //     }
        // }
    }
    // 화면에서 이미지 삭제
    // parent.firstChild.remove();
    parent.innerHTML = "";
    document.querySelector("input#ATTF_OBJ").value == "";
}


