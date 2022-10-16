/**
 * 
 * @param e
 * @returns
 */

/* 댓글 등록 버튼 */
function CommentAdd(e){
	var commCont = e.previousElementSibling.value;
	var postId = e.parentNode.parentNode.getAttribute( 'postId' );	
	var data = {
	    		commCont   : commCont,
	    		commWriter : document.getElementById("writer").value,
	    		postId     : postId
	};
	var result =  JSON.parse(AjaxFn('POST', '/commentAdd' , data));
	
	var div = document.querySelector('[postid="'+postId+'"]');
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
	e.previousElementSibling.value= ""; 
		    
} 	// function 댓글 등록 END


/* 댓글 삭제 버튼 */
function CommentDelete(e){
	
	if(confirm("답글까지 모두 삭제됩니다. 삭제하시겠습니까?")){
		var li = e.closest("li");
		var commId = li.getAttribute( 'commId' );
		var data = {
	    		commId : commId
	    };

		var result = JSON.parse(AjaxFn('POST', '/commentDelete' , data));
		console.log(result);
	
		if(result) {
			var list = document.querySelectorAll('li');
			 for (let i=0; i< list.length; i++){
				 if(list[i].getAttribute('commupid') == commId){
					list[i].remove(); 
				 }
			 }
			 li.remove();
		}
	} else {
		return;
	}
	
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
	
    var data = {
    		commId : commId,
    		commCont : content,
    		postId : postId
    };
	
	var result = JSON.parse(AjaxFn('POST', '/commentUpdate' , data));
	if (result != null){
		li.querySelectorAll('span')[1].textContent = "작성일자 = "+result.commUdate;
		p.textContent = result.commCont;
		p.setAttribute('style',"display:block;");
		li.querySelectorAll('a')[0].setAttribute('style',"display:inline;");
		div.remove();
	}	 
} // function 댓글 수정 end 

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
	const commUpid = e.previousElementSibling.value;
	const commCont = e.previousElementSibling.previousElementSibling.value;
	const postId = li.parentNode.parentNode.getAttribute( 'postid' );
	
    var data = {
    		commCont   : commCont,
    		commWriter : document.getElementById("writer").value,
    		commUpid   : commUpid,
    		postId     : postId
    };
	var result = JSON.parse(AjaxFn('POST', '/commentAdd' , data));
	if(result != null){
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
	}
}

/* 대댓글 취소 */
function replyCancel(e){
	const li = e.closest("li");
	const commUpid = e.previousElementSibling.previousElementSibling.value;
	
	var list = document.querySelectorAll('li');
	for (let i=0; i< list.length; i++){
		if(list[i].getAttribute('commid') == commUpid){
			list[i].querySelectorAll('a')[2].setAttribute('style',"display:inline;");
		}
	}
	li.remove();
}