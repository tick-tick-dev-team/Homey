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