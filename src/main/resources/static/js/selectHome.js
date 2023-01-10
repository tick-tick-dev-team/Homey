function deletePost(postId) {
	console.log('js');
	swal({
		text : "정말 삭제하시겠습니까?",
		button: true,
	})
	.then((willDelete)=> {
		if(willDelete) {
			var formId = "removeForm" + postId;
			document.getElementById(formId).submit();	
		}
		return false;
	});		
}

var limitNum = 450;

function setContentLen (){
	var len = getContentLen();
	var postLength = document.getElementById("postLength");
	
	var showLength = len + 'byte / ' + limitNum + 'byte';
	postLength.textContent = showLength;       
}

function getContentLen () {
	var len=0, j; 
	var content = document.getElementById("POST_CONT");
	var contentValue = content.value;

	for (var i=0, j=contentValue.length; i<j; i++, len++) {
		if ((contentValue.charCodeAt(i)<0)||(contentValue.charCodeAt(i)>127) ){ 
		   len = len+1; 
		}
	}
	return i, len;	
}

function limitByte () { // 글자수 체크

	var i, len = getContentLen();

	if (len >= limitNum) {
		swal("글자 수가 초과되었습니다.");
		content.value = contentValue.substring(0,i); 
		content.focus(); 
		setContentLen();
		return;     
	}
	setContentLen();
 }

window.onload = function(){
	limitByte();
}