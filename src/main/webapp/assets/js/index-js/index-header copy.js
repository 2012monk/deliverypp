	function mainPage(){
		s = "<div class='navbar-logo'>";
		s += "<a href=''>배달의 민족</a>";
		s +="<i class='fas fa-space-shuttle'></i>"
		s +="</div>";
	
		s +="<ul class='navbar-menu'>";
		s +="<li><a href='#main' id='mainpage'>main</div><li>";
		s +="<li><a href='#storecus' id='storecustomer'>storecus</a><li>";
		s +="<li><a href='#basket'>basket</a><li>";
		s +="</ul>";
				
		s +="<ul class='navbar-login'>";
		s +="<li><i class='fas fa-user-plus' id='signbtn' onclick='signup();'></i></li>";
		s +="<li><i class='far fa-id-card' id='loginbtn' onclick='login();'></i></li></ul>";
		
		/*모달 코드 렌더링 처음에 해놔야 나중에 */
		s += '<div id="myModal" class="modal" tabindex="-1" role="dialog">';
  		s += '<div class="modal-dialog" role="document">';
        s += '<div class="modal-content">';
    	s += '<div class="modal-header">';
        s += '<h5 class="modal-title">Modal title</h5>';
        s += '<button type="button" class="close" data-dismiss="modal" aria-label="Close">';
        s += ' <span aria-hidden="true">&times;</span>';
        s += '</button>';
      	s += '</div>';
      	s += '<div class="modal-body">';
       	s += ' <p class="modal-body-p">Modal body text goes here.</p>';
      	s += '</div>';
      	/*s += '<div class="modal-footer">';
       	s += ' <button type="button" class="btn btn-primary">Save changes</button>';
       	s += ' <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>';
      	s += '</div>';*/
    	s += '</div>';
  		s += '</div>';
		s += '</div>';
		$("#index-header").html(s); 
		
		console.log(simpleDeli.checkUserRole());
		if(simpleDeli.checkUserRole()=="CLIENT"){
			$(function(){
				//가게 리스트 출력 
				var storeId = $(this).attr("value");
				$.ajax({
					type:"get",
					url:"http://112.169.196.76:47788/stores/list",
					dataType:"json",
					success:function(data){
						var s="";
							s+="<table>";
							s+="<caption><b>가게 상세보기</b></caption>";
							s+="<tr><th>가게ID</th><th>가게명</th><th>가게 정보</th><th>가게 이미지</th><th>상품리스트</th><th>가게 주소</th></tr>";
							$.each(data.data, function(i,elt){
							    s +="<tr><td name='storeId' value='"+elt.storeId+"'>"+elt.storeId+"</td><td name='storeName' value='"+elt.storeName+"'>"+elt.storeName+"</td><td name='storeDesc'>"+elt.storeDesc+"</td><td name='storeImage'>"+elt.storeImage+"</td><td name=''>"+elt.productList+"</td><td name='storeAddr'>"+elt.storeAddr+"</td>";
							    s +="<td><button type='button' class='storelist-btn-delete' value='"+elt.storeId+"'>delete</button></td>";
							    s +="<td><button type='button' class='storelist-btn-update' value='"+elt.storeId+"'>update</button></td><tr>";
						});
							s+="</table>"; 
						
						$("#index-main").html(s);
						
					}
				});
			});
		} else {
			/*비화원 손님 회원도 여기가 렌더링*/
			var a = "";
			$.ajax({
				type:"get",
				url:"http://112.169.196.76:47788/stores/list",
				dataType:"json",
				success:function(d){
					$.each(d.data, function(i, elt) {
						console.log(elt.storeId,elt.storeName);
						//console.log(i);
						//a += "<div storename='"+elt.storeId+"'>";
						a += "<div class='main-storelist' data-value='"+elt.storeId+"'>";
						/*a += "<img src='"+elt.storeImage+"'>";*/
						a += "<div>"+elt.storeName+"</div>";
						a +="<div><span>리뷰</span></div>";
						a +="</div>"
						
					});
					$("#index-main").html(a);
				}
			});
		}
}

function signup(){
//회원가입이벤트
var s="";
var userroll="";
$(document).on("click","#signbtn",function(){
	console.log();
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
	$("#signlogindiv").html(s);
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
}

function login(){
	//로그인 이벤트
	var s="";
	$(document).on("click","#loginbtn",function(){
	s="<form id='loginform'>";
	s+="<table>";
	s+="<caption><b>로그인</b></caption>"
	s+="<tr><td><input type='email' name='userEmail'pattern='[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}' required placeholder='E-MAIL'></td><td rowspan='2'><button type='submit'>로그인</button><td></tr>";
	s+="<tr><td><input type='password' name='userPw' required placeholder='PASSWORD'></td></tr>";
	s+="</table>";
	s+="</form>";
	$("#signlogindiv").html(s);
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
				$("#loginform").hide();
				alert("로그인 성공!!");
			}else{
				alert("로그인 실패!!");
			}
			//simpleDeli.handleLoginSuccess(login_result);
		}
	});
});
}