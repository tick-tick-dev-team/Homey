// function sendTmpFile (e) {
//     // html 요소 선택 : input-file에 값이 들어오면 실행

//     // ajax 호출
// }

// function openAjax(postId) {
//     httpRequest = new XMLHttpRequest();  /* 통신에 사용 될 XMLHttpRequest 객체 정의 */
//     httpRequest.open('POST', '/tmp/new', true);
//     httpRequest.setRequestHeader('Content-Type', 'application/json');
//     var data = {
//     		commCont   : commCont,
//     		commWriter : 'popdo',
//     		postId     : postId
//     };
// }

function selectFile (input) {
    const filename = input.value;
    alert("파일이름: " + filename.substring(filename.lastIndexOf("\\")+1)); 
}