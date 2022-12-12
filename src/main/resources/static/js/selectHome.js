window.onload = function(){
	document.getElementById("btnDelete").onclick = function() {
		swal({
			text : "정말 삭제하시겠습니까?",
			button: true,
		})
		.then((willDelete)=> {
			if(willDelete) document.getElementById("removeForm").submit();	
			return false;
		});		
	}
}