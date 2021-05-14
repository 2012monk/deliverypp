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
 * 
 * @function getUserInfo()
 * @returns {user || null}
 * user info if logged in null
 * 
 * 
 * @function getUserEmail()
 * @returns {userEmail || null}
 * user Email if logged in or null
 * 
 */



//  const send = window.XMLHttpRequest.prototype.send;
//  window.XMLHttpRequest.prototype.send = function() {
//     const header = new Headers();
//     const pointer = this;
//     const args = [].slice.call(arguments)
//     this.withCredentials = true;
//     if (simpleDeli.auth.token === null) {
//         simpleDeli.unit.refresh().then(data => {
//             console.log(data)
//             this.setRequestHeader("Authorization", "Bearer "+ data.access_token);
//             send.call(this, args)
//         })
//     }
//     else {
//         this.setRequestHeader("Authorization", "Bearer "+ data.access_token);
//         send.call(this, args);
        
//     }
// }

window.simpleDeli = {
    // host : "http://112.169.196.76:47788/",
    host : "https://deli.alconn.co/",
    auth : {
        token : null
    },

    // TODO user 변수는 인식을 못함

    deliUser: {
        userEmail : null,
        userRole : null
    },

    async viewByRole (sellerCallback, clientCallback) {
        console.log(simpleDeli.deliUser)
        if (this.deliUser.userRole === null) {
            const data = await this.unit.refresh();
        }
        console.log(simpleDeli.deliUser)
        if (this.deliUser.userRole === 'SELLER'){
            // render as seller
            console.log("seller")
            // seller view();
            sellerCallback.call();
        }
        else {
            // render as client
            console.log("client")
            clientCallback.call();
        }
    },

    async checkUser () {
        // if (this.deliUser.userEmail === null) {
        //     const data = await this.unit.refresh();
        // }
        // else {

        // }
        return this.sub.getInfo
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
        // console.log(simpleDeli.deliUser)
        // if (this.deliUser.userRole !== null && this.deliUser.userRole !== undefined) {
        //     return simpleDeli.deliUser.userRole;
        // }
    
        // return null;
        return this.sub.getInfo().userRole
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
        return this.deliUser
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
        return this.sub.getInfo().userEmail !== null;
    },
    handleLoginSuccess(data) {

        const res = data.data;
        simpleDeli.deliUser = res.user;
        simpleDeli.auth = {
            "type" : res["auth_type"],
            "token" : res["access_token"],
            "exp" : parseInt(res["exp"])
        };
        const fToken = localStorage.getItem("deviceId");
        if (fToken!=null) {
            const user = res.user;
            user.fcmToken = fToken;
            fetch('http://localhost:47788/push/register',{
                method:'post',
                body: JSON.stringify(user)
            })
            .then(res => res.text())
            .then(console.log);
        }
        try{
            firebaseModule.init();
        }catch(err) {
            console.log(err)
        }
        // this.unit.moduleInit();
    },
}




simpleDeli.unit = {
    async refresh() {
        return new Promise((resolve, reject) => {
            fetch(simpleDeli.host+"login/exchange",{
                method:'post',
                credentials:'include'
            })
            .then(res => res.json())
            .then(d => {
                console.log(d);
                const data = d.data;
                simpleDeli.deliUser = data.user;
                simpleDeli.auth = {
                    "type" : data["auth_type"],
                    "token" : data["access_token"],
                    "exp" : data["exp"]
                };
                this.setUser(data);
                resolve(data)
            })
            .catch(err => reject(err))
        })
    },
    moduleInit() {
        try{
            firebaseModule.init();
        }catch(err) {
            console.log(err)
        }
    },
    init() {
        this.silentRefresh();
        setInterval(this.autoCheck, 1000 * 60 * 10);
        /**
         * @use
         * over ride XMLHttpRequest to set credentials , header
         * over ride global fetch
         */
        // (function() {
        //     const fetch = window.fetch;
        //     window.fetch = function(){
        //         console.log(arguments)
        //         const headers = simpleDeli.unit.generateAuthHeader();
        //         const args = [].slice.call(arguments);
        //         if (headers !== null) {
         
        //             const originHeaders = args.headers;
        //             if (originHeaders !== null) {
         
        //             }
        //         }
        //         return Promise.resolve(fetch.apply(window, arguments))
        //     }
         
            
        // })();
    },
    checkExp(){
        const now = new Date().getTime();
        console.log(now)
        try{
            return simpleDeli.auth.exp > now;
        }catch(err){
            console.log(err);
        }
        return null;
    },
    autoCheck() {
        if(simpleDeli.unit.checkExp()) {
            simpleDeli.unit.silentRefresh();
        }
    },

    setUser(data) {
        if (data.access_token != null) {
            const auth = {
                "type" : data["auth_type"],
                "token" : data["access_token"],
                "exp" : data["exp"]
            };

            localStorage.setItem('deli', JSON.stringify(data))
        }
    },

    setDevice() {
    
    },
    

    // TODO implement silent refresh
    
    isValidUser() {
        return simpleDeli.auth.token !== null;
    },


    
    async silentRefresh() {
        new Promise((resolve, reject) => {
        
            console.log('refreshing .....')
            fetch(simpleDeli.host + "login/exchange",{
                method:"POST",
                credentials:'include'
            }).then(res => res.json())
            .then(data => {
                try{
                    console.log(data);
                    if (data.access_token != null) {
                        simpleDeli.auth = {
                            "type" : data["auth_type"],
                            "token" : data["access_token"],
                            "exp" : data["exp"]
                        };
                    }
                    console.log('refresh complete!');
                    resolve(data);
                    try{
                        firebaseModule.init()
                    }catch(err) {
                        console.log(err)
                    }
                }catch(err) {
                    reject('failed')
                    console.log(err);
                }
            })
    })
},

    ref : () => new Promise((resolve, reject) => {
        fetch(simpleDeli.host + "login/exchange",{
            method:"POST",
            credentials:'include'
        }).then(res => res.json())
        .then(d => {
            try{
                console.log(d);
                const data = d.data; 
                if (data.access_token != null) {
                    simpleDeli.auth = {
                        "type" : data["auth_type"],
                        "token" : data["access_token"],
                        "exp" : data["exp"]
                    };
                }
                console.log('refresh complete!');
                // resolve(data["access_token"]);
                resolve(data);
                try{
                    firebaseModule.init()
                }catch(err) {
                    console.log(err)
                }
            }catch(err) {
                reject('failed')
                console.log(err);
            }
        })
    }),
    
    
    
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
        console.log(token);
        return header;
    },
    
    
    deliAuth(header) {
        
    }
}


simpleDeli.sub = {
    getInfo() {
        const user = JSON.parse(localStorage.getItem('deli'));
        return user.user;
    },

}

simpleDeli.unit.init();


// simpleDeli.loginAttempt({
//     "userEmail" : "test@test.com",
//     "userPw" : "1234",
//     "userType" : "DELI"
// })

document.getElementById('load').addEventListener('click', () => {
    main();
})

// function seller() {
//     console.log("start")
//     $.ajax({
//         type: "get",
//         url: "https://deli.alconn.co/stores/list",
//         success: function (response) {
//             document.querySelector('body').innerHTML = JSON.stringify(response);
//         },
        
//     });

//     // fetch("https://deli.alconn.co/stores/list")
//     // .then(res => res.json())
//     // .then(data => {
//     //     document.querySelector('body').innerHTML = JSON.stringify(data)
//     // })
//     console.log("end")
// }

// function cl() {
//     console.log('df')
// }

// function main() {
//     // seller()
//     simpleDeli.viewByRole(cl, seller);
// }
