$(function(){
    $(document).on("click","#userinformation",function(){
        $.ajax({
            type:"get",
            url:"http://112.169.196.76:47788/user/"+simpleDeli.getUserEmail(),//E-Mail 변경요망 
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
                $("#userinformation").html(s);
            }
        });
    })

    //로그아웃버튼이벤트
    $(document).on("click","#logoutbtn",function(){
        $.ajax({
            type:"get",
            url:"http://112.169.196.76:47788/logout",
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
            url:"http://112.169.196.76:47788/user",
            dataType: "json",
            data:JSON.stringify({"userEmail":userEmail,"userPw":userPw,"userRole":userRole,"userType":userType,"userAddr":userAddr,"userTelephone":userTelephone}),
            success:function(data){
                alert("수정완료!!");
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
            url:"http://112.169.196.76:47788/user/"+userEmail,
            success:function(data){
                alert("회원탈퇴가 완료되었습니다.");
                console.log(data);
            }
        });
    });
});