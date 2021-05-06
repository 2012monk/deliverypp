$(document).ready(function(){
	
	s = "<div>";
	s += "<button type='button' id='btn-sign' href='#'>회원가입</button>";
	s += "<button type='button' id='btn-login' href='#'>로그인</button>";
	s += "<div>"; 

	$("#main-upbar").html(s);
	
	
	$("#btn-sign").click(function() {
		alert("회원가입 이동");
		$(location).attr("href","#");
	});
	$("#btn-login").click(function(){
		alert("로그인 이동");
		$(location).attr("href","#");
		
	});
});
