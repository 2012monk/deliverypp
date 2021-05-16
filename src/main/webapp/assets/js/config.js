window.config = {
    domain : "https://deli.alconn.co",
    // domain : "http://localhost:47788",
    userEmail : window.deli !== undefined ? deli.isLoggedIn==false ? "비회원" : deli.getUserEmail() : null
}

console.log(config)