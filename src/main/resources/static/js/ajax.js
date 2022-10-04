/**
 * 순수 자바스크립트로 Ajax를 호출하는 js 파일
 * -- 넘길 데이터 값
 *  1. 호출 방식 : GET, POST 등
 *  2. 경로 URL
 *  3. 넘기는 데이터 : 데이터 이름은 data, 형식은 JSON 형식으로 생성
 *  	ex) resources/static/js/comment.js 참조
 *  	var data = {
 *   		commCont   : commCont,
 *   		commWriter : 1,
 *   		postId     : postId
 *   	};
 *   	
 * -- js 사용방법 예시
 *   
 *   var result = javascript:Ajax('GET', '/test/url', data)
 *   return 받는 데이터가 있다면 데이터는 JSON.parse(result) 해서 사용해야함;
 *   
 */


function AjaxFn(api, url , data){
	
	var result;
	
	// AJAX 호출
	httpRequest = new XMLHttpRequest(); 
    httpRequest.onreadystatechange = () => {
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
		     if (httpRequest.status === 200) {
		    	 // JSON 타입
		    	 result = httpRequest.response;
		     } else { 
		    	 result = null;
		     }
		     
		}
	} 	// AJAX 호출 END
    
	httpRequest.open(api, url, false);
    httpRequest.setRequestHeader('Content-Type', 'application/json');  
    httpRequest.send(JSON.stringify(data));
    
    return result;
}