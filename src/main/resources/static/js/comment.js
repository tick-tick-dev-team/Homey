/**
 * 
 * @param e
 * @returns
 */

/* 댓글 등록 버튼 */
function CommentAdd(e){
	var commCont = document.getElementById("commCont").value;
	var postId = e.parentNode.parentNode.getAttribute( 'postId' );	
	
	var data = {
	    		commCont   : commCont,
	    		commWriter : 1,
	    		postId     : postId
	};
	var result =  AjaxFn('POST', '/commentAdd' , data);
	result = JSON.stringify(result);
	console.log(result);
	
	var div = document.querySelector('[postid="'+result.postId+'"]');
	console.log()
	var ul = div.querySelector('ul');
	var li = document.createElement("li");
	li.setAttribute('commId', result.commId );
	li.setAttribute('commUpid', result.commUpid );
	li.innerHTML = '<div class="flex-between">'
	              + 	'<div class="info flex-start">'
	              + 		'<span>작성자id = '+ result.commWriter +'</span>&nbsp;'
	              + 		'<span>작성일자 = '+ result.commDate +'</span>'
	              + 	'</div>'
	              + 	'<div class="btn-wrap flex-end">'
	              + 		'<a class="btn-border" href="javascript:;" onclick="CommUpdateForm(this)" th:text="수정">수정</a>&nbsp;'
	              + 		'<a class="btn-border" href="javascript:;" onclick="CommentDelete(this)" th:text="삭제">삭제</a>&nbsp;'
	              +			'<a class="btn-border" href="javascript:;" onclick="CommentReplyAdd(this)" th:text="답글">답글</a>'
	              + '	</div>'
	           	  + '</div>'
	              + '<p class="content">'+ result.commCont +'</p>';
	ul.appendChild(li);
	document.getElementById("commCont").value= ""; 
		    
} 	// function 댓글 등록 END


/* 댓글 삭제 버튼 */
function CommentDelete(e){
	
	console.log(e);
	var li = e.closest("li");
	
	var commId = li.getAttribute( 'commId' );

 	// AJAX 호출
	httpRequest = new XMLHttpRequest();
    httpRequest.open('POST', '/commentDelete', true);
    httpRequest.setRequestHeader('Content-Type', 'application/json');
    var data = {
    		commId : commId
    };
    
    console.log(data);
    console.log(JSON.stringify(data));
    httpRequest.send(JSON.stringify(data));
    
    httpRequest.onreadystatechange = () => {
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
		     if (httpRequest.status === 200) {
		    	 if(httpRequest.response){
		    		 // 대댓글 삭제 영역
		    		 var list = document.querySelectorAll('li');
		    		 for (let i=0; i< list.length; i++){
		    			 if(list[i].getAttribute('commupid') == commId){
		    				list[i].remove(); 
		    			 }
		    		 }
		    		 li.remove();
		    	 }else {
		    		 alert("댓글 삭제 실패!");
		    	 };
		     } else { 
		    	 alert("ajax 연결 실패!"); 
		     }
		}
	} 	// AJAX 호출 END 
}   // function 댓글 삭제 END


/* 댓글 수정 폼 생성 */
function CommUpdateForm(e){

	var li = e.closest("li");
	var p = li.querySelector('p');
	var content = p.textContent;
	
	
	var div = document.createElement("div");
	div.innerHTML = '<input autocomplete="off" type="text" id="commCont" name="commCont" value="'+content+'">'
					+ '&nbsp;<a class="btn-border" href="javascript:;" onclick="CommUpdate(this)" th:text="수정">수정</a>'
					+ '&nbsp;<a class="btn-border" href="javascript:;" onclick="UpdateCancel(this)" th:text="취소">취소</a>';
	li.appendChild(div);
	
	p.setAttribute('style',"display:none;");
	e.setAttribute('style',"display:none;");
	
}

/* 댓글 수정 */
function CommUpdate(e){
	
	var div = e.parentNode;
	var li = div.parentNode;
	var p = li.querySelector('p');
	
	var commId = li.getAttribute( 'commId' );
	var postId = li.parentNode.parentNode.getAttribute( 'postid' );
	var content = div.querySelector('[id=commCont]').value;
	
	// AJAX 호출
	httpRequest = new XMLHttpRequest();
    httpRequest.open('POST', '/commentUpdate', true);
    httpRequest.setRequestHeader('Content-Type', 'application/json');
    var data = {
    		commId : commId,
    		commCont : content,
    		postId : postId
    };
    
    httpRequest.send(JSON.stringify(data));
    
    httpRequest.onreadystatechange = () => {
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
		     if (httpRequest.status === 200) {
		    	 console.log(httpRequest.response);
		    	 var result = JSON.parse(httpRequest.response);
		    	 
		    	 li.querySelectorAll('span')[1].textContent = result.commUdate;
		    	 p.textContent = result.commCont;
		    	 p.setAttribute('style',"display:block;");
		    	 li.querySelectorAll('a')[0].setAttribute('style',"display:inline;");
		    	 div.remove();   	 
		    	 
		     } else { 
		    	 alert("ajax 연결 실패!"); 
		     }
		} 
	} 	// AJAX 호출 END 
}

/* 댓글 수정 취소 */
function UpdateCancel(e){
	
	var li = e.parentNode.parentNode;
	var updateBtn = li.querySelectorAll('a')[0];
	var p = li.querySelector('p');
	var div = e.parentNode;
	
	updateBtn.setAttribute('style',"display:inline;");
	p.setAttribute('style',"display:block;");
	div.remove();
	
}

/* 댓글의 답글 등록 폼 */
function CommentReplyAdd(e){
	
	const li = e.closest("li");
	const commId = li.getAttribute( 'commId' );
	
	// 다음 댓글 노드 찾기
	var nexSibling = li.nextElementSibling;
	var preElement = li;
	if(li.nextElementSibling != null){
		console.log("비교 : " + nexSibling.getAttribute('commupid'))
	 	while(nexSibling.getAttribute('commupid')!= nexSibling.getAttribute('commid')){
			if(nexSibling.nextElementSibling == null){
				preElement = nexSibling;
				nexSibling = li.parentNode;
				break;
			} else {
				nexSibling = nexSibling.nextElementSibling;
				preElement = nexSibling.previousElementSibling;
			}
		}		
	} else {
		nexSibling = li.parentNode;
	}
	console.log(nexSibling);
	console.log(preElement);
 	var addli = document.createElement("li");
 	addli.innerHTML = '<input autocomplete="off" type="text" id="replyCommCont" name="commCont">'
 					+ '<input type="hidden" id="commUpid" name="commUpid" value="'+ commId +'">'
					+ '&nbsp;<a class="btn-border" href="javascript:;" onclick="replyAdd(this)" th:text="등록">등록</a>'
					+ '&nbsp;<a class="btn-border" href="javascript:;" onclick="replyCancel(this)" th:text="취소">취소</a>';
 	preElement.after(addli);
 	e.setAttribute('style',"display:none;");
}

/* 대댓글 등록 */
function replyAdd(e){
	const li = e.closest("li");
	const commUpid = document.getElementById("commUpid").value;
	const commCont = document.getElementById("replyCommCont").value;
	const postId = li.parentNode.parentNode.getAttribute( 'postid' );
	
	// AJAX 호출
	httpRequest = new XMLHttpRequest();
    httpRequest.open('POST', '/commentAdd', true);
    httpRequest.setRequestHeader('Content-Type', 'application/json');
    var data = {
    		commCont   : commCont,
    		commWriter : 1,
    		commUpid   : commUpid,
    		postId     : postId
    };

    httpRequest.send(JSON.stringify(data));
    
    httpRequest.onreadystatechange = () => {
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
		     if (httpRequest.status === 200) {
		    	 // JSON 타입
		    	 console.log(httpRequest.response);
		    	 var result = JSON.parse(httpRequest.response);
		    	 
		    	 li.setAttribute('commId', result.commId );
	    		 li.setAttribute('commUpid', result.commUpid );
		    	 li.innerHTML = '<div class="flex-between">'
				              + 	'<div class="info flex-start">'
				              + 		'<span>작성자id = '+ result.commWriter +'</span>&nbsp;'
				              + 		'<span>작성일자 = '+ result.commDate +'</span>'
				              + 	'</div>'
				              + 	'<div class="btn-wrap flex-end">'
				              + 		'<a class="btn-border" href="javascript:;" onclick="CommUpdateForm(this)" th:text="수정">수정</a>&nbsp;'
				              + 		'<a class="btn-border" href="javascript:;" onclick="CommentDelete(this)" th:text="삭제">삭제</a>&nbsp;'
				              + '	</div>'
				           	  + '</div>'
				              + '<p class="content"> └ '+ result.commCont +'</p>';
				 // 댓글 노드에 답글 버튼 추가!
	             var list = document.querySelectorAll('li');
	          	 for (let i=0; i< list.length; i++){
	          	 	if(list[i].getAttribute('commid') == commUpid){
	          			list[i].querySelectorAll('a')[2].setAttribute('style',"display:inline;");
	          		}
	          	 }
		     } else { 
		    	 alert("실패"); 
		     }
		}
	} 	// AJAX 호출 END	
}

/* 대댓글 취소 */
function replyCancel(e){
	const li = e.closest("li");
	const commUpid = document.getElementById("commUpid").value;
	
	var list = document.querySelectorAll('li');
	for (let i=0; i< list.length; i++){
		if(list[i].getAttribute('commid') == commUpid){
			list[i].querySelectorAll('a')[2].setAttribute('style',"display:inline;");
		}
	}
	li.remove();
}