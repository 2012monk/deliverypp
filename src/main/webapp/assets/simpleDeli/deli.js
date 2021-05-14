$.ajaxSetup(
    {
         xhrFields:{
                withCredentials:true
            },
        beforeSend:function(xhr) {
            xhr.setRequestHeader("Authorization", "Bearer "+deli.getToken())
        }
    }
)

window.deli = {
    domain : "http://112.169.196.76:47788",
    checkRefresh() {
        console.log(this.getToken())
        fetch(this.domain+"/login/exchange",{
            method:"post",
            credentials:'include',
            headers:{
                'Authorization' : "Bearer "+this.getToken()
            }
        })
        .then(res => res.json())
        .then(data => {
            console.log(data)
            this.setUser(data.data);
        })
    },

    login(data){
        fetch(this.domain+"/login",{
            method:'post',
            body:JSON.stringify(data)
        })
        .then(res => res.json())
        .then(data => {
            this.setUser(data.data)
        })
    },

    handleSuccess(data){
        this.setUser(data.data)
    },

    logout() {
        localStorage.removeItem("deli");
        fetch(this.domain+"/logout")
    },
    
    setUser(data) {
        console.log(JSON.stringify(data))
        localStorage.setItem('deli', JSON.stringify(data))
    },
    
    getUser () {
        try{
            const data = JSON.parse(localStorage.getItem('deli'));
            return data.user;
        }catch(err) {
            console.log(err)
            return null;
        }
    },
    
    isLoggedIn() {
        try{
            return this.getUser().userEmail !== null;
        }catch(err) {
            return null;
        }
    },
    
    getUserRole() {
        try{
            return this.getUser().userRole;
        }catch(err){
            return null;
        }
    },
    
    getUserEmail() {
        try{
            return this.getUser().userEmail;
        }catch(err) {
            return null;
        }
    },

    getToken() {
        try{
            return JSON.parse(localStorage.getItem('deli')).access_token
        }catch(err){
            console.log(err)
        }
    }

}
// deli.login({
//     userEmail : "test@test.com",
//     userPw : "1234"
// });
// deli.checkRefresh();
// window.onload = () => {
//     const u = deli.getUser();
//     console.log(u)

// }

$.ajax({
    type:"get",
    url:"https://112.169.196.76:47788/user",
})

