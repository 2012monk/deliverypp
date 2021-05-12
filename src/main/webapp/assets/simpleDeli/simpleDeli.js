/**
 * @use 로그인 및 유저 관리용 입니다 for simple login and control user
 * @author monk
 *
 */
/**
 * 
 * @function checkUserRole() 
 * @returns {userRole || null} user role if logged in
 * 
 * 
 * @function isLoggedIn()
 * @returns {boolean} true if logged in
 * 
 * @function getUserInfo()
 * @returns {user || null}
 * user info if logged in null
 * 
 * @function getUserEmail()
 * @returns {userEmail || null}
 * user Email if logged in or null
 * 
 */

window.simpleDeli = {
    // host : "http://112.169.196.76:47788/",
    host : "https://deli.alconn.co/",
    auth : {
        token : null
    },

    // TODO user 변수는 인식을 못함
    // user : "heelo",
    // user : {
    //     userRole : null
    // },

    deliUser: {

    },


    

    async handleLogOut() {
        await fetch(simpleDeli.host + "logout")
        simpleDeli.auth.token = null;
        simpleDeli.deliUser = {};
    },
    
    /**
     *
     * @returns {null|string|*} if logged in return role or return null
     */
    checkUserRole() {
        console.log(simpleDeli.deliUser)
        if (this.deliUser.userRole !== null && this.deliUser.userRole !== undefined) {
            return simpleDeli.deliUser.userRole;
        }
    
        return null;
    },

    
    /**
     *
     * @returns {string|*|null} return user type if logged in or return null
     */
    checkUserType() {
        if (window.simpleDeli) {
            return simpleDeli.deliUser.userType;
        }
        return null;
    },


    /**
     *
     * @returns {user || null}
     */
     getUserInfo() {
        return simpleDeli.deliUser || null
    },

    getUserEmail() {
        return simpleDeli.deliUser.userEmail || null;
    },


    async loginAttempt(user) {
        const init = await fetch(simpleDeli.host+"login",{
            method:"POST",
            credentials:'include',
            body:JSON.stringify(user)
        })
        const res = await init.json();
        console.log(JSON.stringify(res));
        if (res.message !== "failed"){
            this.handleLoginSuccess(res);
        }
    },

    isLoggedIn() {
        return this.unit.isValidUser();
    },
    handleLoginSuccess(data) {

        const res = data.data;
        simpleDeli.deliUser = res.user;
        simpleDeli.auth = {
            "type" : res["auth_type"],
            "token" : res["access_token"],
            "exp" : parseInt(res["exp"])
        };
    },
}


$.ajaxSetup({
    beforeSend:function(xhr) {
        xhr.withCredentials = true;
        if (simpleDeli.deliUser.token){
            xhr.setRequestHeader("authorization", "Bearer "+simpleDeli.deliUSer.token)
        }
    }
})

simpleDeli.unit = {
    init() {
        this.silentRefresh();
        setInterval(this.autoCheck, 1000 * 60 * 10);

       
        
        /**
         * @use
         * over ride XMLHttpRequest to set credentials , header
         * over ride global fetch
         */
        // (function() {
            // const fetch = window.fetch;
            // window.fetch = function(){
            //     console.log(arguments)
            //     const headers = simpleDeli.unit.generateAuthHeader();
            //     const args = [].slice.call(arguments);
            //     if (headers !== null) {
         
            //         const originHeaders = args.headers;
            //         if (originHeaders !== null) {
         
            //         }
            //     }
            //     return Promise.resolve(fetch.apply(window, arguments))
            // }
         
            // const send = window.XMLHttpRequest.prototype.send;
            // window.XMLHttpRequest.prototype.send = function() {
            //     return send.apply(this, [].slice.call(args));
            //     const pointer = this;
            //     console.log(arguments)
            //     console.log(this);
            //     const header = generateAuthHeader();
            //     const args = [].slice.call(arguments)
            //     // console.log(args)
         
            //     // if (!args.includes("authorization")){
            //     // }
            //     try{
            //         for (let k of header) {
            //             console.log(k)
            //             this.setRequestHeader(k[0], k[1]);
            //             console.log(k)
            //         }
            //         this.withCredentials = true;
    
            //         console.log(this);

            //     }catch(err) {
            //         console.error(err);
            //     }
         
            //     // console.log(args)
            // }
        //  })();
    },
    checkExp(){
        const now = new Date().getTime();
        console.log(now)
        try{
            return simpleDeli.auth.exp > now;
        }catch(err){
            console.error(err);
        }
        return null;
    },
    autoCheck() {
        if(simpleDeli.unit.checkExp()) {
            simpleDeli.unit.silentRefresh();
        }
    },


    setDevice() {
    
    },
    
   



    // TODO implement silent refresh
    
    isValidUser() {
        return simpleDeli.auth.token !== null;
    },


    
    async silentRefresh() {
        const init = await fetch(simpleDeli.host + "login/exchange",{
            method:"POST",
            credentials:'include'
        });
        try{
            const res = await init.json();
            console.log(res);
            if (res.data.access_token != null) {
                simpleDeli.auth = {
                    "type" : res.data["auth_type"],
                    "token" : res.data["access_token"],
                    "exp" : res.data["exp"]
                };
            }
        }catch(err) {
            console.error(err);
        }
    },
    
    
    
    generateAuthHeader(options) {
        try{
            if (!simpleDeli) {
                return null;
            }
        }catch(err) {
            
        }
        const token = simpleDeli.auth.token;
        const header = new Headers();
    
        if (token === undefined || token === null) {
            return null;
        }
    
        header.append("Authorization", "Bearer " + token);
        if (options != null) {
            for (let k of options) {
                header.append(k, options[k]);
            }
        }
    
        return header;
    },
    
    
    deliAuth(header) {
    
    }
    // sub : {
    //     getUserEmail() {
            
    //     },
    //     getUserRole() {

    //     },

    //     getUserInfo() {
    //         const data = localStorage.getItem("sub");
    //         return user;
    //     },

    //     onSuccess(data){
    //         const sub = {};
    //         const res = data.data;
    //         sub.deliUser = data.data.user;
    //         sub.auth = {
    //             "type" : res["auth_type"],
    //             "token" : res["access_token"],
    //             "exp" : parseInt(res["exp"])
    //         };
            
    //         localStorage.setItem("sub", JSON.stringify(sub))
    //     },

    //     isLoggedIn(){
    //         return this.getUserInfo !== null;
    //     }
    // },
}






// setTimeout(function() {
//     $.ajax({
//         type: "get",
//         url: "http://localhost:47788/user/test@test.com",
//         dataType: "json",
//         // beforeSend:function(xhr) {
//         //     xhr.setRequestHeader("Authorization", "Bearer "+simpleDeli.auth.token);
//         // },
//         success: function (data) {
//             console.log(JSON.stringify(data));
//             document.querySelector('body').innerText = JSON.stringify(data);
//         }
//     });
// }, 2000)
// $.ajax({
//     type: "post",
//     data:JSON.stringify({
//         "userEmail" : "test@test.com",
//         "userPw" : "1234",
//         "userType" : "DELI"
//     }),
//     url: simpleDeli.host+"login",
//     success: function (response) {
//         console.log(response)
//     }
// });

simpleDeli.loginAttempt({
    "userEmail" : "test@test.com",
    "userPw" : "1234",
    "userType" : "DELI"
})

simpleDeli.unit.init();