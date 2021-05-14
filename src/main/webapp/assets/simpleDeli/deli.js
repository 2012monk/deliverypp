$.ajaxSetup(
    {
         xhrFields:{
                withCredentials:true
            },
    }
)

window.deli = {
    checkRefresh() {
        fetch("https://deli.alconn.co/login/exchange",{
            method:"post",
            credentials:'include'
        })
        .then(res => res.json())
        .then(data => {
            this.setUser(data.data);
        })
    },

    login(data){
        fetch("https://deli.alconn.co/login",{
            method:'post',
            body:JSON.stringify(data)
        })
    },

    handelSuccess(data){
        this.setUser(data.data)
    },

    logout() {
        fetch("https://deli.alconn.co/logout")
    },
    
    setUser(data) {
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
    }

}

window.onload = () => {
    // deli.checkRefresh();
    const u = deli.getUser();
    console.log(u)

}

