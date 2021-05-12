/**
 * @use 로그인 및 유저 관리용 입니다 for simple login and control user
 * @author monk
 *
 */
/**
 * 
 * @function checkUserRole() 
 * @returns user role 
 * 
 * 
 * @function isLoggedIn()
 * @returns true if logged in
 * 
 * 
 */

window.simpleDeli = {
    host : "http://112.169.196.76:47788/",
    auth : {
        token : null
    },

    // TODO user 변수는 인식을 못함
    user : "heelo",
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
        console.log(this.user)
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




simpleDeli.unit = {
    init() {
        this.silentRefresh();
        setInterval(this.autoCheck, 1000 * 60 * 10);

        /**
         * @use
         * over ride XMLHttpRequest to set credentials , header
         * over ride global fetch
         */
        (function() {
            const fetch = window.fetch;
            window.fetch = function(){
                console.log(arguments)
                const headers = simpleDeli.unit.generateAuthHeader();
                const args = [].slice.call(arguments);
                if (headers !== null) {
         
                    const originHeaders = args.headers;
                    if (originHeaders !== null) {
         
                    }
                }
                return Promise.resolve(fetch.apply(window, arguments))
            }
         
            const send = window.XMLHttpRequest.prototype.send;
            window.XMLHttpRequest.prototype.send = function() {
                const pointer = this;
                // console.log(arguments)
                // console.log(this);
                const header = generateAuthHeader();
                const args = [].slice.call(arguments)
                // console.log(args)
         
                if (!args.includes("authorization")){
                    for (let k of header) {
                        args.push(k[0], k[1]);
                        console.log(k)
                    }
                    this.withCredentials = true;
                }
         
         
                // console.log(args)
                return send.apply(this, [].slice.call(args));
            }
         })();
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
}







setTimeout(function() {
    $.ajax({
        type: "get",
        url: "http://localhost:47788/user/test@test.com",
        dataType: "json",
        beforeSend:function(xhr) {
            xhr.setRequestHeader("Authorization", "Bearer "+simpleDeli.auth.token);
        },
        success: function (data) {
            console.log(JSON.stringify(data));
            document.querySelector('body').innerText = JSON.stringify(data);
        }
    });
}, 200)

window.onload = () => {
    simpleDeli.loginAttempt({
        "userEmail" : "test@test.com",
        "userPw" : "abcd1234",
        "userType" : "DELI"
    })
}

setTimeout(() => {
    console.log(simpleDeli.checkUserRole());
    console.log(simpleDeli.checkUserType())
    console.log(simpleDeli.isLoggedIn());

},1000)

simpleDeli.unit.init();