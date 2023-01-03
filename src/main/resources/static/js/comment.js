/**
 * 
 * 댓글, 대댓글
 * 
 */


/*
	window.addEventListener('DOMContentLoaded', ()=>{
	var spanList = document.querySelectorAll('#commImg');
	for (let i=0; i< spanList.length; i++){
		var userId = spanList[i].getAttribute('userId');
		imgFetch(userId, spanList[i]);
	}
});

function imgFetch(userId, span){
	
	const spanList = span;
	var commImg = document.createElement("img");
	
	fetch('/users/' + userId + '/img', {
		method : 'POST'
	})
	.then((response) => response.json())
	.then((attach) => {
		var result = attach.attf_id;
		if(result != null){
			commImg.setAttribute('src', "/images/" + attach.attf_SERNM);
			spanList.append(commImg);
		} else {
			commImg.setAttribute('src', "/img/user_icon.png");
			spanList.append(commImg);
		}
		
	})
	.catch((error) => {
		console.error('==============error: ',  error);
	});
}
*/

document.addEventListener('keyup', (e) => {
	byteCal(e.target);
});

/* 댓글 등록 버튼 */
function CommentAdd(e){
	var commCont = e.previousElementSibling.value;
	
	if(!commContValidation(e.previousElementSibling)){
		return false;
	}
	
	var postId = e.parentNode.parentNode.getAttribute( 'postId' );	
	var data = {
	    		commCont   : commCont,
	    		commWriter : document.getElementById("writer").value,
	    		postId     : postId
	};
	var result =  JSON.parse(AjaxFn('POST', '/commentAdd' , data));
	
	var div = e.parentNode.parentNode;
	//var div = document.querySelector('[postid="'+postId+'"]');
	var li = document.createElement("li");
	li.setAttribute('commId', result.commId );
	li.setAttribute('commUpid', result.commUpid );
	var imgSrc;
	var ul = div.querySelector('ul');
	if(result.attf_OBJ != null){
		imgSrc = '/images/'+ result.attf_OBJ.attf_SERNM;
	} else {
		imgSrc = '/img/user_icon.png';
	}
	resultDate = result.commUdate.split("T")[0];
	resultTime = convertFromStringToTime(result.commUdate);

	li.innerHTML = '<div class="flex-between">'
	              + 	'<div class="info flex-start">'
				  +			'<span id="commImg">'
				  +				'<img src="'+ imgSrc +'" alt="">'
				  +			'</span>'
	              + 		'<span>'+ result.userNick +'</span>&nbsp;'
	              + 		'<span>'+ resultDate +' ' + resultTime +'</span>'
	              + 	'</div>'
	              + 	'<div class="btn-wrap flex-end">'
	              + 		'<a class="btn-border bg-white" href="javascript:;" onclick="CommUpdateForm(this)" th:text="수정">수정</a>&nbsp;'
	              + 		'<a class="btn-border bg-white" href="javascript:;" onclick="CommentDelete(this)" th:text="삭제">삭제</a>&nbsp;'
	              +			'<a id="replyBtn" class="btn-border bg-white" href="javascript:;" onclick="CommentReplyAdd(this)" >답글</a>'
	              + '	</div>'
	           	  + '</div>'
	              + '<p class="content">'+ result.commCont +'</p>';

	ul.appendChild(li);
	e.previousElementSibling.value= ""; 
	console.log(e.nextElementSibling);
	e.nextElementSibling.innerHTML = "0 /300 Byte";
} 	// function 댓글 등록 END


/* 댓글 삭제 버튼 */
function CommentDelete(e){
	var li = e.closest("li");
	
	const commupid = li.getAttribute('commupid');
	const commid = li.getAttribute('commid');
	if( commid == commupid ){
		//result = confirm("답글까지 모두 삭제됩니다. 삭제하시겠습니까?");
		swal({
		  text: "답글까지 모두 삭제됩니다. 삭제하시겠습니까?",
		  buttons: true,
		}).then((willDelete) => { 
			if(willDelete) {
				var li = e.closest("li");
				var commId = li.getAttribute( 'commId' );
				var data = {
			    		commId : commId
			    };
		
				var result = JSON.parse(AjaxFn('POST', '/commentDelete' , data));
				
				// 대댓글 삭제
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
		});
		
	} else {
		result = true;
		var li = e.closest("li");
		var commId = li.getAttribute( 'commId' );
		var data = {
	    		commId : commId
	    };

		var result = JSON.parse(AjaxFn('POST', '/commentDelete' , data));
		
		// 대댓글 삭제
		if(result) {
			var list = document.querySelectorAll('li');
			 for (let i=0; i< list.length; i++){
				 if(list[i].getAttribute('commupid') == commId){
					list[i].remove(); 
				 }
			 }
			 li.remove();
		}
	}
	
}   // function 댓글 삭제 END


/* 댓글 수정 폼 생성 */
function CommUpdateForm(e){

	var li = e.closest("li");
	var p = li.querySelector('p');
	var content = p.textContent;
	
	var div = document.createElement("div");
	div.setAttribute('class',"input-wrap");
	div.innerHTML = '<input autocomplete="off" class="updateInput" type="text" id="commCont" name="commCont" value="'+content+ '" maxlength="150">'
					+ '&nbsp;<a class="btn-border bg-white" href="javascript:;" onclick="CommUpdate(this)" th:text="수정">수정</a>'
					+ '&nbsp;<a class="btn-border bg-white" href="javascript:;" onclick="UpdateCancel(this)" th:text="취소">취소</a><span id="commLength">0 /300 Byte</span>';
	li.appendChild(div);
	
	p.setAttribute('style',"display:none;");
	e.setAttribute('style',"display:none;");
	
	var commCont = div.querySelector("#commCont");
	byteCal(commCont);
	
}

/* 댓글 수정 */
function CommUpdate(e){
	
	var div = e.parentNode;
	var li = div.parentNode;
	var p = li.querySelector('p');
	
	var commId = li.getAttribute( 'commId' );
	var postId = li.parentNode.parentNode.getAttribute( 'postid' );
	var content = div.querySelector('[id=commCont]').value;
	
	if(!commContValidation(div.querySelector('[id=commCont]'))){
		return false;
	}
	
    var data = {
    		commId : commId,
    		commCont : content,
    		postId : postId
    };
	
	var result = JSON.parse(AjaxFn('POST', '/commentUpdate' , data));
	if (result != null){
		resultDate = result.commUdate.split("T")[0];
		resultTime = convertFromStringToTime(result.commUdate);
		li.querySelectorAll('span')[2].textContent = resultDate + " " + resultTime;
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

 	var addli = document.createElement("li");
	addli.classList.add("replyContent");
	var div = document.createElement("div");
	div.classList.add("input-wrap");
 	div.innerHTML = '<input autocomplete="off" class="updateInput" type="text" id="replyCommCont" name="commCont" maxlength="150">'
 					+ '<input type="hidden" id="commUpid" name="commUpid" value="'+ commId +'">'
					+ '&nbsp;<a class="btn-border bg-white" href="javascript:;" onclick="replyAdd(this)" th:text="등록">등록</a>'
					+ '&nbsp;<a class="btn-border bg-white" href="javascript:;" onclick="replyCancel(this)" th:text="취소">취소</a><span id="commLength">0 /300 Byte</span>';
	addli.appendChild(div);				
 	preElement.after(addli);
 	e.setAttribute('style',"display:none;");
}

/* 대댓글 등록 */
function replyAdd(e){
	const li = e.closest("li");
	const commUpid = e.previousElementSibling.value;
	const commCont = e.previousElementSibling.previousElementSibling.value;
	const postId = li.parentNode.parentNode.getAttribute( 'postid' );
	
	if(!commContValidation(e.previousElementSibling.previousElementSibling)){
		return false;
	}
	
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
		li.classList.add("replyContent");
		var imgSrc;
		if(result.attf_OBJ != null){
			imgSrc = '/images/'+ result.attf_OBJ.attf_SERNM;
		} else {
			imgSrc = '/img/user_icon.png';
		}
		resultDate = result.commUdate.split("T")[0];
		resultTime = convertFromStringToTime(result.commUdate);
		li.innerHTML = '<div class="flex-between">'
		              + 	'<div class="info flex-start">'
					  +			'<span id="commImg">'
				  	  +				'<img src="'+ imgSrc +'" alt="">'
				  	  +			'</span>'
		              + 		'<span>'+ result.userNick +'</span>&nbsp;'
		              + 		'<span>'+ resultDate +' ' + resultTime +'</span>'
		              + 	'</div>'
		              + 	'<div class="btn-wrap flex-end">'
		              + 		'<a class="btn-border bg-white" href="javascript:;" onclick="CommUpdateForm(this)" th:text="수정">수정</a>&nbsp;'
		              + 		'<a class="btn-border bg-white" href="javascript:;" onclick="CommentDelete(this)" th:text="삭제">삭제</a>&nbsp;'
		              + '	</div>'
		           	  + '</div>'
		              + '<p class="content">'+ result.commCont +'</p>';
		
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
			list[i].querySelector('#replyBtn').setAttribute('style',"display:inline;");
		}
	}
	li.remove();
}

/* 문자 -> 날짜 객체로 변환 */
function convertFromStringToTime(responseDate) {
    var today = new Date(responseDate);   
	var hours = ('0' + today.getHours()).slice(-2); 
	var minutes = ('0' + today.getMinutes()).slice(-2);
	var seconds = ('0' + today.getSeconds()).slice(-2); 
	var timeString = hours + ':' + minutes ;
	
	return timeString
}

function commContValidation(content){
	
	var len = 0;
	var j;
	
	for (var i=0, j=content.value.length; i<j; i++, len++) {
        if ((content.value.charCodeAt(i)<0)||(content.value.charCodeAt(i)>300) ){
			len = len+1;
		}
		if(len > 300){
			content.value = content.value.substring(0,i);
			swal("최대 글자 수는 한글로 100자 미만입니다.");
			return false;
		} else {
			return true;
		} 
 
    }         

	//var stringByteLength = content.value.replace(/[\0-\x7f]|([0-\u07ff]|(.))/g,"$&$1$2").length;
	//console.log(stringByteLength + " Bytes");
	
	console.log(len + " Bytes");
	//lenMaxSize = 300;

}

function byteCal(e){
	
	console.log(e);
	var len=0, j; 
	var span = e.parentNode.querySelector("#commLength");
	var content = e.value;
	
	for (var i=0, j=content.length; i<j; i++, len++) {
        if ((content.charCodeAt(i)<0)||(content.charCodeAt(i)> 127) ){
         	len = len+1;
		}
       	if(len > 300){
			e.focus();
			content = content.substring(0, i);
			e.value = content;
			span.innerHTML = "<b>300</b> /300 Byte";
		} else {
			span.innerHTML = "<b>"+len + "</b> /300 Byte";
			e.focus();
			e.value = content;
    	}
    }         
//	var pattern = /[\0-\x7f]|([0-\u07ff]|(.))/g;
//	var stringByteLength = content.replace(pattern,"$&$1$2").length;

}