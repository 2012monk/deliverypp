$.ajaxSetup(
    {
        beforeSend:function(xhr){
            xhr.witCredentials = true;
        }
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
        fetch("http://deli.alconn.co/login",{
            method:'post',
            body:JSON.stringify(data)
        })
    },
    
    
    setUser(data) {
        localStorage.setItem('deli', JSON.stringify(data))
    },
    
    getUser () {
        const data = JSON.parse(localStorage.getItem('deli'));
        return data.user;
    },
    
    isLoggedIn() {
        return this.getUser().user.userEmail !== null;
    },
    
    getUserRole() {
        return this.getUser().user.userRole;
    },
    
    getUserEmail() {
        return this.getUser().user.userEmail;
    }

}

window.onload = () => {
    // deli.login();    
    deli.checkRefresh();
}

