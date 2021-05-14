/**
 * 
 */
$(function(){
	
	//테스트 데이터
	var test_json = 
		{
			userType : "DELI",
			userRole : "CLIENT",
			userEmail : "test@test.com",
			userPw : "abcd1234",
			userName : "kims",
			userTel : "010-2345-2345"
		};
	// 회원가입 데이터 JSON으로 받아와서 서버로 전송처리
	function MemberAdd(data){
		$.ajax({
			type:"post",
			//url:"http://deli.alconn.co/signup",
			url:"http://deli.alconn.co/signup",
			data:JSON.stringify(data),
			success:function(d){
				alert("회원가입 성공!");
			}
		})
	}
	
	function MemberLogin(data){
		$.ajax({
			type:"post",
			//url:"http://deli.alconn.co/login",
			url:"member_test_server.jsp",
			data:data,
			dataType:"json",
			success:function(login_result){
				alert("로그인 성공!");
				var token = login_result.data;
				localStorage.setItem("loginToken",JSON.stringify(token));
			}
		})
	}
	//MemberLogin(1);
	
	function MemberIdCheck(id){
		$.ajax({
			type:"post",
			url:"http://deli.alconn.co/login/id-check/"+id,
			dataType:"json",
			success:function(check_result){
				alert("아이디 체크");
				
			}
		})
	}
	
	function MemberLogout(id){
		$.ajax({
			type:"get",
			url:"http://deli.alconn.co/logout/"+id,
			dataType:"json",
			success:function(logout_result){
				alert("로그아웃 성공");
			}
		})
	}
	
	//MemberAdd(test_json);
})