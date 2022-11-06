/*selectHome에서 사용*/
window.onload = function(){
	checkbirth();
}


async function checkbirth() {

	//날짜 (https://gent.tistory.com/413)
	var today = new Date();

	var year = today.getFullYear();
	var month = ('0' + (today.getMonth() + 1)).slice(-2);
	var day = ('0' + today.getDate()).slice(-2);

	var dateString = year + '-' + month  + '-' + day;

	console.log(dateString);
	console.log(typeof(dateString));
	

	const userId = document.getElementById('for_birth').value;
	const homename = document.getElementById('homename').value;
	console.log(userId);
	
	fetch('/users/' + userId + '/birth', {
		method : 'POST',
		body : userId
	})
	.then(response => response.text())
	.then(function(text){
		if(text == dateString){
		alert("이 집 주인의 생일은 " + text + "로 오늘입니다.\n생일을 진심으로 축하합니다!");
	
		}
	}

	)
	.catch((error) => {
		console.error('==============error: ',  error);
	});

}