$.ajaxSetup(
    {
         xhrFields:{
                withCredentials:true
            },
        beforeSend:function(xhr) {
            try{
                xhr.setRequestHeader("Authorization", "Bearer "+deli.getToken())
            }catch(err) {
                console.log(err);
            }
        }
    }
)


window.deli = {
    domain : "https://deli.alconn.co",
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
            this.setUser(data)
        })
    },

    handleSuccess(data){
        console.log(data);
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
            console.log(data)
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
            return false;
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
            return JSON.parse(localStorage.getItem('deli')).access_token;
        }catch(err){
            console.log(err)
        }
    }

}


deli.checkRefresh();
// deli.login({
//     userEmail : "test@test.com",
//     userPw : "1234"
// });
// deli.checkRefresh();
// window.onload = () => {
//     const u = deli.getUser();
//     console.log(u)

// }



