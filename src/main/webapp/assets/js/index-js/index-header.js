
function mainHeaderPage() {
	s = "<div class='navbar-logo'>";
		s += "<a href=''>배달의 민족</a>";
		s +="<i class='fas fa-space-shuttle'></i>"
		s +="</div>";
	
		s +="<ul class='navbar-menu'>";
		s +="<li><a href='#main' id='mainpage'>main</div><li>";
		s +="<li><a href='#storecus' id='storecustomer'>storecus</a><li>";
		s +="<li><a href='#basket'>basket</a><li>";
		s +="</ul>";
	    console.log(deli.isLoggedIn());
		if(deli.isLoggedIn()===false){
            console.log("false진입");
            s +="<ul class='navbar-login'>";
            s +="<li><i class='fas fa-user-plus' id='signbtn' data-target='#signmodal'></i></li>";
            s +="<li><i class='far fa-id-card' id='loginbtn' data-target='#logmodal'></i></li></ul>";
        }else{
            if(deli.getUserRole()=="SELLER"){
                s +="<ul class='navbar-login'>";
            s +="<li><i id='mypagebtn' onclick='mypage();'>"+deli.getUserEmail()+"(SELLER)님</i></li>";//변경요망
            s +="<li><i></i></li></ul>";
            }else{
            console.log("true진입");
            s +="<ul class='navbar-login'>";
            s +="<li><i id='mypagebtn' onclick='mypage();'>"+deli.getUserEmail()+"(CLIENT)님</i></li>";//변경요망
            s +="<li><i><button type='submit' id='sellersignbtn'>seller등록</button></i></li>";
            s +="<li><i></i></li></ul>";
            }
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
		$("#loginbtn").click(function(){

            $("#logmodal").modal();
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

        //sellersign이벤트
        $(document).on("click","#sellersignbtn",function(){
            alert("click")
            $.ajax({
                type:"post",
                //url:"<config.domain + "/login>",
                url:config.domain + "/user/signup/seller",
                success:function(d){
                    console.log(d)
                    alert("seller등록이 되었습니다.");
                }
            });
        });


    function signup(){
        //회원가입이벤트
        var s="";
        var userRole="";
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
                url:config.domain + "/user/signup",
                data:JSON.stringify({"userEmail":userEmail,"userPw":userPw,"userRole":userRole, "userType":userType,"userTelephone":userTelephone,"userAddr":userAddr}),
                success:function(d){
                    console.log(d);
                    alert("회원가입을 축하드립니다!!");
                    alert("로그인을 해주세요");
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
            $("#logbody").html(s);
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
                //url:"<config.domain + "/login>",
                url:config.domain + "/login",
                data:JSON.stringify({"userEmail":userEmail,"userPw":userPw}),
                dataType:"json",
                success:function(login_result){
                    deli.handleLoginSuccess(login_result)
                    console.log(login_result);
                    if(deli.isLoggedIn()===true){
                        $("#loginform").hide();
                        alert("로그인 성공!!");
                        mainHeaderPage();
                        mainBodyPage();
                    }else{
                        alert("로그인 실패!!");
                    }
                    //deli.handleLoginSuccess(login_result);
                }
            });
        });
    }
function mypage(){
    $(document).on("click","#mypagebtn",function(){
        $.ajax({
            type:"get",
            url:config.domain + "/user/",//E-Mail 변경요망 
            dataType:"json",
            success:function(data){
                console.log(data);
                    var s="";
                    s= "<form>";
                    //s+="<input type='hidden' name='userPw' value='"+data.data.userPw+"'>";
                    s+="<table>";
                    s+="<caption>회원정보</caption> &nbsp;&nbsp;&nbsp;";
                    s+="<tr><th>E-Mail</th><td userEmail='userEmail'>"+data.data.userEmail+"</td></tr>";
                    s+="<tr><th>UserRole</th><td userRole='userRole'>"+data.data.userRole+"</td></tr>";
                    s+="<tr><th>UserType</th><td userType='userType'>"+data.data.userType+"</td></tr>";
                    s+="<tr><th>UserAddr</th><td userAddr='userAddr'>"+data.data.userAddr+"</td></tr>";
                    s+="<tr><th>userTelephone</th><td userTelephone='userTelephone'>"+data.data.userTelephone+"</td></tr>";
                    s+="<tr><td  colspan='2'><button id='userupdatebtn'>정보수정</button>&nbsp;<button id='userdeletebtn'>회원탈퇴</button>&nbsp;<button id='logoutbtn'>로그아웃</button><td></tr>";
                    s+="</table>";
                    s+="</form>";
                $("#index-main").html(s);
            }
        });
    });

    //로그아웃버튼이벤트
    $(document).on("click","#logoutbtn",function(){
        $.ajax({
            type:"get",
            url:config.domain + "/logout",
            dataType:"json",
            success:function(data){
                console.log(data);
            }
        });
    })

    $(document).on("click","#userupdatebtn",function(e){
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
            s+="<table>";
            s+="<caption>회원정보수정</caption> &nbsp;&nbsp;&nbsp;";
            s+="<tr><th>E-Mail</th><td><input type='text' id='userEmail' value='"+userEmail+"'></td></tr>";
            s+="<tr><th>Password</th><td><input type='password' id='userPw' value='"+userPw+"'></td></tr>";
            s+="<tr><th>UserRole</th><td><input type='text' id='userRole' value='"+userRole+"'></td></tr>";
            s+="<tr><th>UserType</th><td><input type='text' id='userType' value='"+userType+"'></td></tr>";
            s+="<tr><th>UserAddr</th><td><input type='text' id='userAddr' value='"+userAddr+"'></td></tr>";
            s+="<tr><th>userTelephone</th><td><input type='text' id='userTelephone' value='"+userTelephone+"'></td></tr>";
            s+="<tr colspan='2'><td><button id='userupdatesuccessbtn'>수정완료</button><td></tr>";
            s+="</table>";
            s+="</form>";
            $("#userupdateform").html(s);
    });

    $(document).on("click","#userupdatesuccessbtn",function(e){
        e.preventDefault();
        var userEmail=$("#userupdateform").find("#userEmail").val();
        var userPw=$("#userupdateform").find("#userPw").val();
        var userRole=$("#userupdateform").find("#userRole").val();
        var userType=$("#userupdateform").find("#userType").val();
        var userAddr=$("#userupdateform").find("#userAddr").val();
        var userTelephone=$("#userupdateform").find("#userTelephone").val();
        console.log(userEmail);
        console.log(userPw);
        console.log(userRole);
        console.log(userType);
        console.log(userAddr);
        console.log(userTelephone);
        $.ajax({
            type:"PUT",
            url:config.domain + "/user",
            dataType: "json",
            data:JSON.stringify({"userEmail":userEmail,"userPw":userPw,"userRole":userRole,"userType":userType,"userAddr":userAddr,"userTelephone":userTelephone}),
            success:function(data){
                alert("정보가 수정되었습니다.");
                console.log(data);
            }
        });
    });

    $(document).on("click", "#userdeletebtn", function(e){
        e.preventDefault();
        var userEmail=$("td[userEmail]").text()
        console.log(userEmail);
        $.ajax({
            type:"DELETE",
            url:config.domain + "/user/"+userEmail,
            success:function(data){
                alert("회원탈퇴가 완료되었습니다.");
                console.log(data);
            }
        });
    });
}

function mainBodyPage() {
	if(login_id == "SELLER")
		sellerPage();
	else
		clientPage();
} 

        //sellersign이벤트
        $(document).on("click","#sellersignbtn",function(){
            alert("click")
            $.ajax({
                type:"post",
                //url:"<config.domain + "/login>",
                url:config.domain + "/user/signup/seller",
                beforeSend:function(xhr){
                    xhr.withCredentials = true;
                },
                success:function(d){
                    console.log(d)
                    alert("seller등록이 되었습니다.");
                }
            });
        });


    function signup(){
        //회원가입이벤트
        var s="";
        var userRole="";
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
                url:config.domain + "/user/signup",
                data:JSON.stringify({"userEmail":userEmail,"userPw":userPw,"userRole":userRole, "userType":userType,"userTelephone":userTelephone,"userAddr":userAddr}),
                success:function(d){
                    console.log(d);
                    alert("회원가입을 축하드립니다!!");
                    alert("로그인을 해주세요");
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
            $("#logbody").html(s);
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
                //url:"<config.domain + "/login>",
                url:config.domain + "/login",
                data:JSON.stringify({"userEmail":userEmail,"userPw":userPw}),
                dataType:"json",
                success:function(login_result){
                    deli.handleSuccess(login_result)
                    console.log(login_result);
                    if(deli.isLoggedIn()===true){
                        $("#loginform").hide();
                        alert("로그인 성공!!");
                        mainHeaderPage();
                        mainBodyPage();
                    }else{
                        alert("로그인 실패!!");
                    }
                    //simpleDeli.handleLoginSuccess(login_result);
                }
            });
        });
    }
function mypage(){
    $(document).on("click","#mypagebtn",function(){
        $.ajax({
            type:"get",
            url:config.domain + "/user/",//E-Mail 변경요망 
            dataType:"json",
            beforeSend:function(xhr){
                xhr.withCredentials = true;
            },
            success:function(data){
                console.log(data);
                    var s="";
                    s= "<form>";
                    //s+="<input type='hidden' name='userPw' value='"+data.data.userPw+"'>";
                    s+="<table>";
                    s+="<caption>회원정보</caption> &nbsp;&nbsp;&nbsp;";
                    s+="<tr><th>E-Mail</th><td userEmail='userEmail'>"+data.data.userEmail+"</td></tr>";
                    s+="<tr><th>UserRole</th><td userRole='userRole'>"+data.data.userRole+"</td></tr>";
                    s+="<tr><th>UserType</th><td userType='userType'>"+data.data.userType+"</td></tr>";
                    s+="<tr><th>UserAddr</th><td userAddr='userAddr'>"+data.data.userAddr+"</td></tr>";
                    s+="<tr><th>userTelephone</th><td userTelephone='userTelephone'>"+data.data.userTelephone+"</td></tr>";
                    s+="<tr><td  colspan='2'><button id='userupdatebtn'>정보수정</button>&nbsp;<button id='userdeletebtn'>회원탈퇴</button>&nbsp;<button id='logoutbtn'>로그아웃</button><td></tr>";
                    s+="</table>";
                    s+="</form>";
                $("#index-main").html(s);
            }
        });
    });

    //로그아웃버튼이벤트
    $(document).on("click","#logoutbtn",function(){
        $.ajax({
            type:"get",
            url:config.domain + "/logout",
            dataType:"json",
            success:function(data){
                console.log(data);
            }
        });
    })

    $(document).on("click","#userupdatebtn",function(e){
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
            s+="<table>";
            s+="<caption>회원정보수정</caption> &nbsp;&nbsp;&nbsp;";
            s+="<tr><th>E-Mail</th><td><input type='text' id='userEmail' value='"+userEmail+"'></td></tr>";
            s+="<tr><th>Password</th><td><input type='password' id='userPw' value='"+userPw+"'></td></tr>";
            s+="<tr><th>UserRole</th><td><input type='text' id='userRole' value='"+userRole+"'></td></tr>";
            s+="<tr><th>UserType</th><td><input type='text' id='userType' value='"+userType+"'></td></tr>";
            s+="<tr><th>UserAddr</th><td><input type='text' id='userAddr' value='"+userAddr+"'></td></tr>";
            s+="<tr><th>userTelephone</th><td><input type='text' id='userTelephone' value='"+userTelephone+"'></td></tr>";
            s+="<tr colspan='2'><td><button id='userupdatesuccessbtn'>수정완료</button><td></tr>";
            s+="</table>";
            s+="</form>";
            $("#userupdateform").html(s);
    });

    $(document).on("click","#userupdatesuccessbtn",function(e){
        e.preventDefault();
        var userEmail=$("#userupdateform").find("#userEmail").val();
        var userPw=$("#userupdateform").find("#userPw").val();
        var userRole=$("#userupdateform").find("#userRole").val();
        var userType=$("#userupdateform").find("#userType").val();
        var userAddr=$("#userupdateform").find("#userAddr").val();
        var userTelephone=$("#userupdateform").find("#userTelephone").val();
        console.log(userEmail);
        console.log(userPw);
        console.log(userRole);
        console.log(userType);
        console.log(userAddr);
        console.log(userTelephone);
        $.ajax({
            type:"PUT",
            url:config.domain + "/user",
            dataType: "json",
            data:JSON.stringify({"userEmail":userEmail,"userPw":userPw,"userRole":userRole,"userType":userType,"userAddr":userAddr,"userTelephone":userTelephone}),
            success:function(data){
                alert("정보가 수정되었습니다.");
                console.log(data);
            }
        });
    });

    $(document).on("click", "#userdeletebtn", function(e){
        e.preventDefault();
        var userEmail=$("td[userEmail]").text()
        console.log(userEmail);
        $.ajax({
            type:"DELETE",
            url:config.domain + "/user/"+userEmail,
            success:function(data){
                alert("회원탈퇴가 완료되었습니다.");
                console.log(data);
            }
        });
    });
}

function sellerPage(){
	//가게 리스트 출력 
	var storeId = $(this).attr("value");
	$.ajax({
		type:"get",
		url:config.domain + "/stores/list",
		dataType:"json",
		success:function(data){
			var s="";
				s+="<table>";
				s+="<caption><b>가게 상세보기</b><button id='storehost-btn-add'>추가</button></caption>";
				s+="<tr><th>가게ID</th><th>가게명</th><th>가게 정보</th><th>가게 이미지</th><th>상품리스트</th><th>가게 주소</th></tr>";
				$.each(data.data, function(i,elt){
					s +="<tr value='"+elt.storeId+"'><td class='storehostdetail-page' name='storeId' value='"+elt.storeId+"'>"+elt.storeId+"</td><td class='storehostdetail-page' name='storeName' value='"+elt.storeName+"'>"+elt.storeName+"</td><td class='storehostdetail-page' name='storeDesc'>"+elt.storeDesc+"</td><td class='storehostdetail-page' name='storeImage'>"+elt.storeImage+"</td><td name=''>"+elt.productList+"</td><td name='storeAddr'>"+elt.storeAddr+"</td>";
					s +="<td><button type='button' class='storelist-btn-delete' value='"+elt.storeId+"'>delete</button></td>";
					s +="<td><button type='button' class='storelist-btn-update' value='"+elt.storeId+"'>update</button></td><tr>";
				});
				s+="</table>"; 
			
			$("#index-main").html(s);
			
		}
	});
	/*storeHost();*/
}

function clientPage(){
	/*비화원 손님 회원도 여기가 렌더링*/
	var a = "";
	$.ajax({
		type:"get",
		url:config.domain + "/stores/list",
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
				a +="</div>";
				
			});
			$("#index-main").html(a);
			storeCustomerProductList();
		}
	});
}