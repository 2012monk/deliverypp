$(function(){
	var s="";
	$(document).on("click","#logbtn",function(){
		s+="<form>";
		s+="<table>";
		s+="<caption><b>로그인</b></caption>"
		s+="<tr><td><input type='email' name='userEmail'pattern='[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}' required placeholder='E-MAIL'></td><td rowspan='2'><button type='submit'>로그인</button><td></tr>";
		s+="<tr><td><input type='password' name='userPw' required placeholder='PASSWORD'></td></tr>";
		s+="</table>";
		s+="</form>";
		$("#show").html(s);
	});
});


$(document).on("submit","form",function(e){
	e.preventDefault();
	console.log(this);
	var userEmail = $(this).find('input[name="userEmail"]').val();
	var userPw = $(this).find('input[name="userPw"]').val();
	console.log(userEmail);
	console.log(userPw);
	
	$.ajax({
		type:"post",
		//url:"<http://deli.alconn.co/login>",
		url:"http://112.169.196.76:47788/login",
		data:JSON.stringify({"userEmail":userEmail,"userPw":userPw}),
		dataType:"json",
		success:function(login_result){
			console.log(login_result);
			if(simpleDeli.isLoggedIn()===true){
				window.location.href = 'http://127.0.0.1:5500/src/main/webapp/index%20copy.html';
			}else{
				alert("로그인 실패!!");
			}
			//simpleDeli.handleLoginSuccess(login_result);
		}
	});
});