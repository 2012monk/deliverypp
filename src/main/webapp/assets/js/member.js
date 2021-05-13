$(function(){
	var s="";
	var userRole="";
	$(document).on("click","#memberbtn",function(){
		userType="deli";
		s="<form id='deliform'>";
		s+="<table>";
		s+="<caption><b>회원가입</b></caption>";
		s+="<tr><td><input type='email' name='userEmail' pattern='[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}' required placeholder='예) id@domain.com'></td></tr>";
		s+="<tr><td><input type='password' id='pw' name='userPw' placeholder='비밀번호 입력' required></td></tr>";
		s+="<tr><td><input type='password' id='pw_cf' onkeyup='check_pw()' name='userPwok' placeholder='비밀번호 확인' required></td></tr>";
		s+="<tr><td><span id='pw_check_msg' style='color:red;'></span></td></tr>"
		s+="<tr><td><input type='text' name='userTelephone' placeholder='전화번호' required></td><td><button type='submit'>인증번호받기</button></td></tr>";
		s+="<tr><td><input type='text' name='userAddr' placeholder='주소' required></td></tr>";
		s+="<tr><td><input type='checkbox' name='userRole' value='seller'>SELLER</td><td><input type='checkbox' name='userRole' value='client'>CLIENT</td></tr>";
		s+="<tr><td colspan='2' align='center'><button type='submit'>회원가입</button></td></tr>";
		s+="</table>";
		s+="</form>";
		$("#show").html(s);
		
		$("#kakaoform").hide();
		$("#googleform").hide();

	});
	
	$(document).on("submit","form", function(e){
		e.preventDefault();
		console.log(this);
		userType="deli";
		var userEmail = $(this).find('input[name="userEmail"]').val();
		var userPw = $(this).find('input[name="userPw"]').val();
		var userTelephone = $(this).find('input[name="userTelephone"]').val();
		var userAddr = $(this).find('input[name="userAddr"]').val();
		var userRole= $(this).find('input:checkbox[name="userRole"]:checked').val();
		
		console.log(userType);
		console.log(userEmail);
		console.log(userPw);
		console.log(userRole);
		console.log(userAddr);
		console.log(userTelephone);
		
		$.ajax({
			type:"post",
			url:"http://112.169.196.76:47788/user/signup",
			data:JSON.stringify({"userEmail":userEmail,"userPw":userPw,"userRole":userRole, "userType":userType,"userTelephone":userTelephone,"userAddr":userAddr}),
			success:function(d){
				console.log(d);
			}
		});
	});
});

function check_pw(){  //비밀번호 확인 
    var p = document.getElementById('pw').value; 
    var p_cf = document.getElementById('pw_cf').value; 

    if (p!=p_cf) { 
        document.getElementById('pw_check_msg').innerHTML = "비밀번호가 다릅니다."; 
    } 
    else { 
        document.getElementById('pw_check_msg').innerHTML = ""; 
    } 
    if (p_cf=="") { 
        document.getElementById('pw_check_msg').innerHTML = ""; 
    } 
} 