/**
 * 관리자 회원목록 
 */

function roleChange(e){
	var role = e.value;
	var li = e.parentNode;
	var user_id = li.querySelector("#user_id").innerHTML;
	user_id = parseInt(user_id);
	
    fetch('/homes/' + user_id + '/select', {
    	method: "POST",	
  		headers: {
		    "Content-Type": "application/json"
		},
		body : JSON.stringify({
		    user_id : user_id ,
		    userpower : role
		})
	})
	.then((response) => response.json())
	.then((result) => {
		if(result.userpower != null || result.userpower != ""){
			var n = e.options.length;
			for (let i=0; i<n; i++){  
				if(e.options[i].value == result.userpower){
					e.options[i].selected = true;
				}
			}
			alert("변경이 완료되었습니다!");
		} else {
			alert("변경이 실패되었습니다. * 운영자한테 문의하시길 바랍니다");
		}
	});
}

function useChange(e){
	var use = e.value;
	console.log(use);
	var li = e.parentNode;
	var homeId = li.querySelector("#homeId").value;
	console.log(homeId);
	homeId = parseInt(homeId);
	
    fetch('/homes/' + homeId + '/homeUse', {
    	method: "POST",	
  		headers: {
		    "Content-Type": "application/json"
		},
		body : JSON.stringify({
		    homeid : homeId ,
		    homeuse : use
		})
	})
	.then((response) => response.json())
	.then((result) => {
		if(result.homeuse != null || result.homeuse != ""){
			var n = e.options.length;
			for (let i=0; i<n; i++){  
				if(e.options[i].value == result.homeuse){
					e.options[i].selected = true;
				}
			}
			alert("변경이 완료되었습니다!");
		} else {
			alert("변경이 실패되었습니다. * 운영자한테 문의하시길 바랍니다");
		}
	});
}
