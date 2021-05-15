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
    console.log( document.getElementById('hiddenlogin'));
    document.getElementById('hiddenlogin').innerHTML = s;
    
}