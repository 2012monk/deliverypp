package com.deli.deliverypp.DB;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliUser {


    public enum UserRole{
        CLIENT,
        SELLER,
        ADMIN,
    }

    public enum UserType {
        KAKAO,
        GOOGLE,
        DELI
    }
    private String userEmail;
    private String userPw;
//    private String userRole;
//    private String userType;
    private UserRole userRole = UserRole.CLIENT;
    private UserType userType;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }


    @Override
    public String toString() {
        return "DeliUser{" +
                "userEmail='" + userEmail + '\'' +
                ", userPw='" + userPw + '\'' +
                ", userRole=" + userRole +
                ", userType=" + userType +
                '}';
    }
}
