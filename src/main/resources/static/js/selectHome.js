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

function setContentLen (len, id){
	// var len = getContentLen();
	var showLength = len + 'byte / ' + limitNum + 'byte';
	document.getElementById(id).textContent = showLength;       
 }

function limitByte () { // 글자수 체크
	var len=0, j; 

	var content = document.getElementById("POST_CONT");

	var contentValue = content.value;

	for (var i=0, j=contentValue.length; i<j; i++, len++) {
		if ((contentValue.charCodeAt(i)<0)||(contentValue.charCodeAt(i)>127) ){ 
		   len = len+1; 
		}
		if (len >= limitNum) {
			swal("글자 수가 초과되었습니다.");
			content.value = contentValue.substring(0,i); 
			content.focus(); 
			setContentLen();
			return;     
		}
	 }
	 setContentLen(len,"postLength");
 }

window.onload = function(){
	limitByte();
}