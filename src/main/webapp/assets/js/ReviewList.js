function reviewPage(){

    var storeId = "stid22";
    var userEmail = "abc@abc.net";

/* 리뷰리스트 데이터 형식
    var ajax_data = 
        {
            data : [
            
            {
                reviewId : "reviewid",
                storeId: "stid",
                reviewRating : "rate",
                reviewImage : "photo",
                userEmail : "writer",
                reviewContent : "content300제한",
                reviewDate : "date",
                replyList : [
                    { reviewId:"72495aa-fff", replyId : "ida",userEmail : "reple writer1", replyContent : "reple content1", replyDate : "reple date1" } ,
                    { reviewId:"72495aa-fff", replyId : "idb",userEmail : "reple writer2", replyContent : "reple content2", replyDate : "reple date2" } ,
                    { reviewId:"72495aa-fff", replyId : "idc",userEmail : "reple writer3", replyContent : "reple content3", replyDate : "reple date3" } 
                ]
            },
            {
                reviewId : "글번호2",
                storeId: "stid2",
                reviewRating : "별점",
                reviewImage : "사진",
                userEmail : "리뷰작성자",
                reviewContent : "작성내용300자제한",
                reviewDate : "작성일시",
                replyList : [
                    { reviewId:"72495aa-ttt", replyId : "id1", userEmail : "리플 작성자4", replyContent : "리플 내용4", replyDate : "작성일시4" } ,
                    { reviewId:"72495aa-ttt", replyId : "id2",userEmail : "리플 작성자5", replyContent : "리플 내용5", replyDate : "작성일시5" } ,
                    { reviewId:"72495aa-ttt", replyId : "id3",userEmail : "리플 작성자6", replyContent : "리플 내용6", replyDate : "작성일시6" }
                ]
            }
            
            ]
        }
*/

    function reviewLoad(){
      
        //출력할 리뷰목록 DB에서 store id 로 받아오기
        $.ajax({
            type:"get",
            url:"http://112.169.196.76:47788/review/store/stid22",
            dataType:"json",
            success:function(ajax_data){
               
                var s="";
                s+= "<h2>리뷰 게시판("+storeId+")</h2><button id='review-write'>리뷰 쓰기</button>";
                s+= "<div id='write-form'></div>";
                var idx=0;
                ajax_data.data.forEach(function(w){
                    //테스트용 s+="<button review_id='"+w.reviewId+"' type='button' id='testbtn'>리뷰테스트</button>";
                    s+="<hr><div review_id='"+w.reviewId+"'>";
                    //리뷰수정,삭제버튼
                    s+="<button id='review-mod' idx='"+idx+"'>수정</button><button id='review-del' idx='"+idx+"'>삭제</button>";
                    //리뷰 테이블
                    s+= "<table class='table table-bordered'><tr><td>번호</td><td>작성자</td><td>내용</td><td>별점</td><td>작성일</td></tr>";
                    s+="<tr><td>"+w.reviewId+"</td><td>"+w.userEmail+"</td><td class='content'>"+w.reviewContent+"("+w.replyList.length+")</td><td>"+w.reviewRating+"</td><td>"+w.reviewDate+"</td></tr>";
                    s+="<tr class='photo'><td><img src=''>"+w.reviewImage+"</td></tr></table>";
                    //댓글 등록버튼
                    s+="<div><button idx='"+idx+"' id='reply-write'>Reply</button></div>";
                    //댓글 테이블
                    s+="<table review_id='"+w.reviewId+"' class='table table-bordered'>";
                    var idx_reply = 0;

                    w.replyList.forEach(function(r){
                        if(r!=null)
                        {
                            s+="<tr review_id='"+w.reviewId+"' reply_id='"+r.replyId+"'><td><span class='glyphicon glyphicon-arrow-right'></span></td>";
                            s+="<td>"+r.userEmail+"</td><td>"+r.replyContent+"</td><td>"+r.replyDate+"</td>";
                            //댓글 수정,삭제 버튼
                            //테스트용 s+="<button id='replytest' reply_id='"+r.replyId+"'>리플테스트</button>";
                            s+="<td><button id='reply-mod'>수정</button><button idx='"+idx+"' idx_reply='"+idx_reply+"' id='reply-del'>삭제</button></tr>";

                            idx_reply++;
                        }
                    })

                    s+="</table></div>";
                    idx++;
                })
                s+="";
                
                $("#test").html(s);
                

            }
        })


    }

    // 서버 리뷰 테스트용 버튼
    $(document).on("click","#testbtn",function(){
        //유저아이디로 불러오기
        $.ajax({
            type:"get",
            url:"http://112.169.196.76:47788/review/user/"+userEmail,
            dataType:"json",
            success:function(d){
                alert("유저아이디로 불러오기:"+JSON.stringify(d));
            }
        })
    })

    // 서버 리플 테스트용 버튼
    $(document).on("click","#replytest",function(){
        var reply_id = $(this).attr("reply_id");
        var review_id = $(this).closest("tr").attr("review_id");
        console.log("reply_id:"+reply_id);
        console.log("review_id:"+review_id);
        $.ajax({
            type:"get",
            url:"http://112.169.196.76:47788/reply/reply-id/"+reply_id,
            dataType:"json",
            success:function(d){
                alert("리플아이디로 불러오기:"+JSON.stringify(d));
            }
        })

        $.ajax({
            type:"get",
            url:"http://112.169.196.76:47788/reply/review-id/"+review_id,
            dataType:"json",
            success:function(d){
                alert("리뷰아이디로 불러오기:"+JSON.stringify(d));
            }
        })

    })


    // 리뷰 작성폼 출력
    $(document).on("click","#review-write",function(){
        
        // 로그인 정보에서 접속중인 아이디정보 가져오기 ->작성자명(이메일)
        var s = "<form method='post' enctype='multipart/form-data'><table class='table table-bordered'>";
        s += "<tr><td>작성자</td><td id='userEmail'>"+userEmail+"</td></tr>";
        s += "<tr><td>별점</td><td><select id='review-rate'><option>5</option><option>4</option><option>3</option><option>2</option><option>1</option></select></td></tr>";
        s += "<tr><td>사진 첨부</td><td><input id='photo' type='file'></td></tr>";
        s += "<tr><td>리뷰</td><td><textarea id='content' style='width:500px;height:150px;'></textarea></td></tr>";
        s += "<tr><td><button type='button' id='review-submit'>등록</button><button type='button' id='review-cancel'>취소</button></td></tr>";
        s += "</table></form>";
        $("#write-form").html(s);
    })

    // 리뷰 작성폼 닫기
    $(document).on("click","#review-cancel",function(){
        $("#write-form").html("");
        reviewLoad();
    })
    
    // 리뷰 작성후 전송
    // 사진 전송 기능 수정할 것
    $(document).on("click","#review-submit",function(){
        var table = $(this).closest("table");
        var review_json = {};
        review_json.userEmail = table.find("#userEmail").text();
        review_json.reviewRating = table.find("#review-rate").val();
        review_json.reviewContent = table.find("#content").val();
        review_json.userEmail = userEmail;
        review_json.storeId = storeId;
        review_json.replyLIst = [];
        

        $.ajax({
            type:"post",
            data:JSON.stringify(review_json),
            url:"http://112.169.196.76:47788/review",
            dataType:"json",
            success:function(d){
                alert("작성 성공:"+JSON.stringify(d));
                $("#write-form").html("");
                reviewLoad();
            }
        })
        
        var formData = new FormData();
        formData.append('photo',table.find("input#photo").val());
        

        $.ajax({
            type:"post",
            data:formData,
            url:"http://112.169.196.76:47788/upload",
            processData: false,
            contentType: false,
            success:function(d){
                alert("이미지 전송 성공:"+d);
            }
        })
        
    })

    //리뷰 수정폼 출력
    //사진 전송 기능 수정할 것
    $(document).on("click","#review-mod",function(){
        var review_div = $(this).parent();
        var review_id = $(this).parent().attr("review_id");
        $.ajax({
            type:"get",
            url:"http://112.169.196.76:47788/review/"+review_id,
            dataType:"json",
            success:function(r){
                

                var s = "<form method='post' enctype='multipart/form-data'><table class='table table-bordered'>";
                s += "<tr><td>작성자</td><td id='userEmail'>"+r.data.userEmail+"</td></tr>";
                s += "<tr><td>별점</td><td><select id='review-rate'><option>5</option><option>4</option><option>3</option><option>2</option><option>1</option></select></td></tr>";
                s += "<tr><td>사진 첨부</td><td><input id='photo' type='file'></td></tr>";
                s += "<tr><td>리뷰</td><td><textarea id='content' style='width:500px;height:150px;'>"+r.data.reviewContent+"</textarea></td></tr>";
                s += "<tr><td><button type='button' review_id='"+review_id+"' id='review-mod-submit'>등록</button><button type='button' id='review-mod-cancel'>취소</button></td></tr>";
                s += "</table></form>";
                review_div.html(s);
                review_div.find("#review-rate").val(r.data.reviewRating);
                
            }
        }) 
        
    })

    //리뷰 수정폼 닫기
    $(document).on("click","#review-mod-cancel",function(){
        reviewLoad();
    })

    //리뷰 수정후 전송
    //사진 전송 기능 수정할 것
    $(document).on("click","#review-mod-submit",function(){
        var table = $(this).closest("table");
        var review_json = {};
        review_json.reviewId = $(this).attr("review_id");
        review_json.userEmail = table.find("#userEmail").text();
        review_json.reviewRating = table.find("#review-rate").val();
        review_json.reviewContent = table.find("#content").val();
        review_json.userEmail = userEmail;
        review_json.storeId = storeId;
        review_json.replyList = [];
        

        $.ajax({
            type:"put",
            data:JSON.stringify(review_json),
            url:"http://112.169.196.76:47788/review",
            //dataType:"json",
            success:function(d){
                alert("리뷰 수정 성공:"+JSON.stringify(d));
                reviewLoad();
            }
        })

        var formData = new FormData();
        formData.append('photo',table.find("input#photo").val());

        $.ajax({
            type:"post",
            data:formData,
            url:"http://112.169.196.76:47788/upload",
            processData: false,
            contentType: false,
            success:function(d){
                alert("이미지 전송 성공:"+d);
            }
        })
    
    })

    //리뷰 삭제 처리
    $(document).on("click","#review-del",function(){
        var review_id = $(this).parent().attr("review_id");
        console.log(review_id);
        $.ajax({
            type:"delete",
            url:"http://112.169.196.76:47788/review/"+review_id,
            //url:"http://112.169.196.76:47788/review/null",
            //dataType:"json",
            success:function(d){
                alert("리뷰삭제 성공:"+d);
                reviewLoad();
            }
        })

    })


    // 리뷰 댓글 작성폼 출력
    $(document).on("click","#reply-write",function(){
        var review_id = $(this).parent().parent().attr("review_id");
        var idx = $(this).attr("idx");
        var s = "<table class='table table-bordered'>";
        s += "<tr><td>작성자</td><td>"+userEmail+"</td></tr>";
        s += "<tr><td>댓글 내용</td><td><textarea style='width:500px;height:150px;'></textarea></td></tr>";
        s += "<tr><td><button review_id='"+review_id+"' idx='"+idx+"' id='reply-submit'>등록</button><button id='reply-cancel'>취소</button></td></tr>";
        s += "</table>";
        $(this).parent().html(s);
    })

    //리뷰 댓글 작성/수정폼 닫기
    $(document).on("click","#reply-cancel",function(){
        reviewLoad();
    })

    //리뷰 댓글 작성후 전송
    $(document).on("click","#reply-submit",function(){
        var review_id = $(this).attr("review_id");
        var reply = {};
        reply.reviewId = review_id;
        reply.userEmail = userEmail;
        reply.replyContent = $(this).closest("table").find("textarea").val();


        $.ajax({
            type:"post",
            data:JSON.stringify(reply),
            url:"http://112.169.196.76:47788/reply",
            dataType:"json",
            success:function(d){
                alert("댓글 작성 성공"+JSON.stringify(d));
                reviewLoad();
            }
        })

    })

    //리뷰 댓글 삭제 버튼
    $(document).on("click","#reply-del",function(){
        var review_id = $(this).closest("tr").attr("review_id");
        var reply_id = $(this).closest("tr").attr("reply_id");
        console.log("삭제할 댓글id:"+reply_id);
        $.ajax({
            type:"delete",
            url:"http://112.169.196.76:47788/reply/"+reply_id,
            success:function(d){
                alert("댓글 삭제 성공!:"+d);
                reviewLoad();
            }
        })
     
    })

    //리뷰 댓글 수정폼 출력
    $(document).on("click","#reply-mod",function(){
        var this_tr = $(this).closest("tr");
        var reply_id = this_tr.attr("reply_id");
        var review_id = this_tr.attr("review_id");
        
        $.ajax({
            type:"get",
            url:"http://112.169.196.76:47788/review/store/stid22",
            dataType:"json",
            success:function(ajax_data){
                // 이미 열려있는 다른 댓글수정폼 닫기
                ajax_data.data.forEach(function(d){
                    d.replyList.forEach(function(r){
                        $("tr[reply_id]").each(function(index,item){
                            var reply_id = $(this).attr("reply_id");
                            var review_id = $(this).attr("review_id");
                            if(d.reviewId == review_id && r.replyId == reply_id)
                            {
                                var s="";
                                s+="<td><span class='glyphicon glyphicon-arrow-right'></span></td>";
                                s+="<td>"+r.userEmail+"</td><td>"+r.replyContent+"</td><td>"+r.replyDate+"</td>";
                                //댓글 수정,삭제 버튼
                                s+="<td><button id='reply-mod'>수정</button><button id='reply-del'>삭제</button>";
                                $(this).html(s);
                            }
                        })
                    })
                })

                //현재 클릭한 댓글 수정폼 열기
                var s = "<table class='table table-bordered'>";
                s += "<tr><td>작성자</td><td>"+userEmail+"</td></tr>";
                s += "<tr><td>댓글 내용</td><td><textarea style='width:300px;height:25px;'></textarea></td></tr>";
                s += "<tr><td><button review_id='"+review_id+"' reply_id='"+reply_id+"' id='reply-mod-submit'>등록</button><button id='reply-cancel'>취소</button></td></tr>";
                s += "</table>";
                $(this_tr).html(s);    

            }
        })

    })

    //리뷰 댓글 수정후 전송
    $(document).on("click","#reply-mod-submit",function(){
        //댓글작성코드 복붙후 수정안함. 전부 수정할것
        var reply_id = $(this).attr("reply_id");
        var review_id = $(this).attr("review_id");

        var reply = {};
        reply.replyId = reply_id;
        reply.reviewId = review_id;
        reply.userEmail = userEmail;
        reply.replyContent = $(this).closest("tr").find("textarea").val();


        $.ajax({
            type:"put",
            data:JSON.stringify(reply),
            url:"http://112.169.196.76:47788/reply",
            dataType:"json",
            success:function(d){
                alert("댓글 수정 성공"+JSON.stringify(d));
                reviewLoad();
            }
        })
        
    })

    reviewLoad();
    
}