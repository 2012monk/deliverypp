window.config = {
    domain : "https://deli.alconn.co",
    userEmail : deli.isLoggedIn==false?"비회원":deli.getUserEmail()
}

function mainHeaderPage() {

	s = "";
	
		s +="<ul class='navbar-menu'>";
		s +="<li><a href='' id='mainpage'>배달의 민족</a><i class='fas fa-space-shuttle'></i></li>";
		s +="</ul>";
        
        s +="<ul class='navbar-login'>";
        s +="<li><i class='fas fa-2x fa-shopping-cart' id='basket-modal'></i></li>";
    
		if(!deli.isLoggedIn()){
            s +="<li><i class='fas fa-2x fa-user-plus' id='signbtn'></i></li>";
            s +="<li><i class='far fa-2x fa-id-card' id='loginbtn'></i></li></ul>";
        }else{
            s +="<li><i id='mypagebtn'>"+deli.getUserEmail()+'['+deli.getUserRole()+"]님</i></li>";//변경요망
            if(deli.getUserRole() !=="SELLER"){
                s +="<li id='sellerForm'><i><button type='submit' id='sellersignbtn'>seller등록</button></i></li>";
            }
            s +="<li><i></i></li></ul>";
        }
        
		
		/*모달 코드div 렌더링 처음에 해놔야 나중에 */
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
    // }
    $("#loginbtn").click(function(){
        $("#logmodal").modal();
        console.log(this);
        login();
    });
    $("#signbtn").click(function(){
        $("#signmodal").modal();
        signup();
    });
}


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


//장바구니 아이콘 클릭
$(document).on("click","#basket-modal",function(){
    
    if(localStorage.getItem("cartList") == null)
    {
        alert("장바구니가 비었습니다!");
        return;
    }
    else
    {
        CartMain();
        CartMain2();
        $("#basket-myModal").modal();
    }
})

//sellersign이벤트
$(document).on("click","#sellersignbtn",function(){
    alert("click")
    $.ajax({
        type:"post",
        //url:"<http://deli.alconn.co/login>",
        url:"http://deli.alconn.co/user/signup/seller",
        success:function(d){
            if (d === "SUCCESS"){
                alert("seller등록이 되었습니다.");
                $("#sellerForm").remove();
            }else {
                alert('failed')
            }
        }
    });
});

// singup render
$(document).on("click","#signbtn",function(){
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
    $("#signbody").html(s);
});

// sing up start
$(document).on("submit","#deliform", function(e){
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
        url:"http://deli.alconn.co/user/signup",
        data:JSON.stringify({"userEmail":userEmail,"userPw":userPw,"userRole":userRole, "userType":userType,"userTelephone":userTelephone,"userAddr":userAddr}),
        success:function(d){
            console.log(d);
            alert("회원가입을 축하드립니다!!");
            alert("로그인을 해주세요");
            $('#signmodal').modal('hide');
        }
    });
    
});

// login modal
$(document).on("click","#loginbtn",function(){
    s="<form id='loginform'>";
    s+="<table>";
    s+="<caption><b>로그인</b></caption>"
    s+="<tr><td><input type='email' name='userEmail'pattern='[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}' required placeholder='E-MAIL'></td><td rowspan='2'><button type='submit'>로그인</button><td></tr>";
    s+="<tr><td><input type='password' name='userPw' required placeholder='PASSWORD'></td></tr>";
    s+="</table>";
    s+="</form>";
    $("#logbody").html(s);
    $('#logmodal').modal();
    console.log($('#logmodal'))
});

// login
$(document).on("submit","#loginform",function(e){
    e.preventDefault();
    console.log(this);
    var userEmail = $(this).find('input[name="userEmail"]').val();
    var userPw = $(this).find('input[name="userPw"]').val();
    console.log(userEmail);
    console.log(userPw);
    
    $.ajax({
        type:"post",
        //url:"<http://deli.alconn.co/login>",
        url:config.domain+"/login",
        data:JSON.stringify({"userEmail":userEmail,"userPw":userPw}),
        dataType:'json',
        success:function(login_result){
            console.log(login_result)
            deli.handleSuccess(login_result)
            console.log(login_result);
            if(deli.isLoggedIn()){
                $("#loginform").hide();
                alert("로그인 성공!!");
                mainHeaderPage();
                mainBodyPage();
                config.userEmail = deli.getUserEmail();
            }else{
                alert("로그인 실패!!");
            }
            $('#logmodal').modal('hide')
            //deli.handleLoginSuccess(login_result);
        },
        error:function(data) {
            console.log(data);
            deli.logout();
            alert('failed')
            $('#logmodal').modal('hide')
        }
    })
});




//로그아웃버튼이벤트
$(document).on("click","#logoutbtn",function(){
    deli.logout();
    $.ajax({
        type:"get",
        url:config.domain+"/logout",
        dataType:"json",
        success:function(data){
            console.log(data);
        }
    });
    mainHeaderPage();
    mainBodyPage();
})

// function end!
$(document).on("click","#mypagebtn",function(){
    $.ajax({
        type:"get",
        url:config.domain+"/user/"+config.userEmail,//E-Mail 변경요망 
        dataType:"json",
        success:function(data){
            console.log(data);
            var s="";
            s= "<form>";
            //s+="<input type='hidden' name='userPw' value='"+data.data.userPw+"'>";
            s+="<h2>"+data.data.userEmail+"님의 회원정보</h2><br>";
            s+="<table style='width:500px;' class='table table-default'>";
            s+="<tr><th>E-Mail</th><td userEmail='userEmail'>"+data.data.userEmail+"</td></tr>";
            s+="<tr><th>UserRole</th><td userRole='userRole'>"+data.data.userRole+"</td></tr>";
            s+="<tr><th>UserType</th><td userType='userType'>"+data.data.userType+"</td></tr>";
            s+="<tr><th>UserAddr</th><td userAddr='userAddr'>"+data.data.userAddr+"</td></tr>";
            s+="<tr><th>userTelephone</th><td userTelephone='userTelephone'>"+5+"</td></tr>";
            s+="</table>";
            s+="<button id='userupdatebtn'>정보수정</button><button id='userdeletebtn'>회원탈퇴</button><button id='logoutbtn'>로그아웃</button>";
            s+="</form>";
            $("#index-main").html(s);
        }
    });

    //접속안될시 임시테스트용 이중출력
    // var s="";
    //     s= "<form>";
    //     //s+="<input type='hidden' name='userPw' value='"+data.data.userPw+"'>";
    //     s+="<h2>"+1+"님의 회원정보</h2><br>";
    //     s+="<table style='width:500px;' class='table table-default'>";
    //     s+="<tr><th>E-Mail</th><td userEmail='userEmail'>"+1+"</td></tr>";
    //     s+="<tr><th>UserRole</th><td userRole='userRole'>"+2+"</td></tr>";
    //     s+="<tr><th>UserType</th><td userType='userType'>"+3+"</td></tr>";
    //     s+="<tr><th>UserAddr</th><td userAddr='userAddr'>"+4+"</td></tr>";
    //     s+="<tr><th>userTelephone</th><td userTelephone='userTelephone'>"+5+"</td></tr>";
    //     s+="</table>";
    //     s+="<button id='userupdatebtn'>정보수정</button><button id='userdeletebtn'>회원탈퇴</button><button id='logoutbtn'>로그아웃</button>";
    //     s+="</form>";
    // $("#index-main").html(s);

});

$(document).on("click","#userupdatebtn", 
function(e) {
    e.preventDefault();
    var userEmail=$("td[userEmail]").text()
    var userPw=$('input[name="userPw"]').val()
    var userRole=$("td[userRole]").text()
    var userType=$("td[userType]").text()
    var userAddr=$("td[userAddr]").text()
    var userTelephone=$("td[userTelephone]").text()
    console.log(userEmail);
    //console.log(userPw);
    console.log(userRole);
    console.log(userType);
    console.log(userAddr);
    console.log(userTelephone);
    var s=""
    s= "<form>";
    s+="<h2>회원정보수정</h2>";
    s+="<table class='table table-default'>";
    s+="<tr><th>E-Mail</th><td><input type='text' id='userEmail' value='"+userEmail+"'></td></tr>";
    s+="<tr><th>Password</th><td><input type='password' id='userPw' placeholder='비밀번호 입력'></td></tr>";
    s+="<tr><th>Password Confirm</th><td><input type='password' id='userPwConfirm' placeholder='비밀번호 확인'></td></tr>";
    s+="<tr><th>UserRole</th><td><input type='text' id='userRole' value='"+userRole+"'></td></tr>";
    s+="<tr><th>UserType</th><td><input type='text' id='userType' value='"+userType+"'></td></tr>";
    s+="<tr><th>UserAddr</th><td><input type='text' id='userAddr' value='"+userAddr+"'></td></tr>";
    s+="<tr><th>userTelephone</th><td><input type='text' id='userTelephone' value='"+userTelephone+"'></td></tr>";
    s+="<tr colspan='2'><td><button id='userupdatesuccessbtn'>수정완료</button><button type='button' id='userupdatecancel'>취소</button><td></tr>";
    s+="</table>";
    s+="</form>";
    $("#index-main").html(s);
}
);

$(document).on("click","#userupdatecancel",function(){
    $("#mypagebtn").click();
})

$(document).on("click","#userupdatesuccessbtn", 
function (e) {
    e.preventDefault();
    var userEmail=$(this).closest("table").find("#userEmail").val();
    var userPw=$(this).closest("table").find("#userPw").val();
    var userPwConfirm=$(this).closest("table").find("#userPwConfirm").val();
    if(userPw == "" || userPw != userPwConfirm)
    {
        alert("비밀번호를 확인해 주세요");
        return;
    }
    var userRole=$(this).closest("table").find("#userRole").val();
    var userType=$(this).closest("table").find("#userType").val();
    var userAddr=$(this).closest("table").find("#userAddr").val();
    var userTelephone=$(this).closest("table").find("#userTelephone").val();
    console.log(userEmail);
    console.log(userPw);
    console.log(userRole);
    console.log(userType);
    console.log(userAddr);
    console.log(userTelephone);
    $.ajax({
        type:"PUT",
        url:config.domain+"/user",
        dataType: "json",
        data:JSON.stringify({"userEmail":userEmail,"userPw":userPw,"userRole":userRole,"userType":userType,"userAddr":userAddr,"userTelephone":userTelephone}),
        success:function(data){
            alert("정보가 수정되었습니다.");
            console.log(data);
        }
    });
}

)
$(document).on("click", "#userdeletebtn", function(e){
    e.preventDefault();
    var userEmail=$("td[userEmail]").text()
    console.log(userEmail);
    $.ajax({
        type:"DELETE",
        url:config.domain+"/user/"+userEmail,
        success:function(data){
            alert("회원탈퇴가 완료되었습니다.");
            console.log(data);
        }
    });
});

function mainBodyPage() {
	if(deli.getUserRole() == "SELLER")
		sellerPage();
	else
		clientPage();
} 
function sellerPage(){
	//가게 리스트 출력 
	var storeId = $(this).attr("value");
	$.ajax({
		type:"get",
		url:"http://deli.alconn.co/stores/list",
		dataType:"json",
		success:function(data){
			var s="";
				s+="<table class='table table-bordered'>";
				s+="<h2><b>"+config.userEmail+"님의 SHOP LIST</b></h2><hr><button id='storehost-btn-add'>가게 추가</button>";
				s+="<tr><th>가게명</th><th>가게 정보</th><th>가게 이미지</th><th>가게 주소</th><th>수정</th><th>삭제</th></tr>";
				$.each(data.data, function(i,elt){
					s +="<tr class='mouse-seller' value='"+elt.storeId+"'><td class='storehostdetail-page' name='storeName'>"+elt.storeName+"</td><td class='storehostdetail-page' name='storeDesc'>"+elt.storeDesc+"</td><td class='storehostdetail-page' name='storeImage'>"+elt.storeImage+"</td><td class='storehostdetail-page' name='storeAddr'>"+elt.storeAddr+"</td>";
					s +="<td><button type='button' class='storelist-btn-update' value='"+elt.storeId+"'>update</button></td>";
                    s +="<td><button type='button' class='storelist-btn-delete' value='"+elt.storeId+"'>delete</button></td></tr>";
					
				});
				s+="</table>"; 
			
			$("#index-main").html(s);
			
		}
	});
	/*storeHost();*/
}
// seller 가게리스트 마우스오버
$(document).on("mouseenter",".mouse-seller",function(){
    $(this).attr("style","background-color:lightgray;")
})
$(document).on("mouseleave",".mouse-seller",function(){
    $(this).attr("style","background-color:none;")
})


function clientPage(){
	/*비화원 손님 회원도 여기가 렌더링*/
	var a = "";
	$.ajax({
		type:"get",
		url:"http://deli.alconn.co/stores/list",
		dataType:"json",
		success:function(d){
			$.each(d.data, function(i, elt) {
				console.log(elt.storeId,elt.storeName);
				//console.log(i);
				//a += "<div storename='"+elt.storeId+"'>";
				a += "<div id='ttest' class='main-storelist' data-storeName='"+elt.storeName+"' data-storeId='"+elt.storeId+"'>";
				/*a += "<img src='"+elt.storeImage+"'>";*/
				a += "<div><h3>"+elt.storeName+"</h3></div>";
				a +="<div><span>"+elt.storeDesc+"</span></div>";
				a +="</div>";
				
			});
			$("#index-main").html(a);
		}
	});
}

// Client 가게리스트 mouseover
$(document).on("mouseenter",".main-storelist",function(){
    $(this).attr("style","background-color:skyblue;");
})
$(document).on("mouseleave",".main-storelist",function(){
    $(this).attr("style","background-color:none;");
})



function loginmodal(){
    var s="";
    s+="<div class='modal fade' id='logmodal' role='dialog'>";
    s+="<div class='modal-dialog'>";
    s+="<div class='modal-content'>"
    s+="<div class='modal-header'>"
    s+="<button type='button' class='close' data-dismiss='modal'>&times;</button>"
    s+="<h4 class='modal-title'>Modal Header</h4>"
    s+="</div>"
    s+="<div class='modal-body' id='logbody'>"
    s+="<p>Some text in the modal.</p>"
    s+="</div>"
    s+="<div class='modal-footer'>"
    s+="<button type='button' class='btn btn-default' data-dismiss='modal'>Close</button>"
    s+="</div>"
    s+="</div>"
    s+="</div>"
    s+="</div>"
    s+="</div>"
    $("#hiddenlogin").html(s);

}

