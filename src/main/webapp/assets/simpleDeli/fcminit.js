const firebaseConfig = {
    apiKey: "AIzaSyAFebVP2F7B8NnlELOMjIlAdm-qebvjd_8",
    authDomain: "delideli-c7932.firebaseapp.com",
    projectId: "delideli-c7932",
    storageBucket: "delideli-c7932.appspot.com",
    messagingSenderId: "559577135117",
    appId: "1:559577135117:web:e184e44ca5ea7235537d44"
};


function messageHandler(payload) {
    console.log(payload)
}

const firebaseModule = {
    async init() {
        if ('serviceWorker' in navigator) {
            window.onload = () => {

                navigator.serviceWorker.register("/firebase-messaging-sw.js")
                .then(registration => {
                    firebase.initializeApp(firebaseConfig)
                    
                    
                    const messaging = firebase.messaging();
                    messaging.requestPermission()
                    .then(function() {
                        return messaging.getToken({
                            vapidKey : "BD8t3eWyIFdGZeenucGiQTQLzZUNkQSAWDsV_oiguajByXvpnAeXR-Nx2BUQcWeWy-ilTJf4Wd9-RNvPl-_vLlo"
                        })
                    })
                    .then(async token => {
                        console.log(token)
                        localStorage.setItem('deviceId', token);
                        const user = simpleDeli.user;
                        if (user !== undefined){
                            user.fcmToken = token;
                            await fetch('http://localhost:47788/push/regiester', {
                                method: 'post',
                                body: JSON.stringify(user)
                            })
                        }

                    })
                    .catch(err => {
                        console.error(err);
                    })
                })
            }
        }
    }
}

// firebaseModule.init();


