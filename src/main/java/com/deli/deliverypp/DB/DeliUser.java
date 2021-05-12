package com.deli.deliverypp.DB;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliUser {


    public enum UserRole{
        GUEST(0),
        CLIENT(1),
        SELLER(2),
        ADMIN(3);

        private final int hierarchy;

        UserRole(int i) {
            hierarchy = i;
        }

        public int getHierarchy(){
            return hierarchy;
        }

        public boolean isHigher(UserRole target, UserRole current) {
            return target.getHierarchy() <= current.getHierarchy();
        }

        public boolean isHigher(UserRole target) {
            return this.hierarchy <= target.hierarchy;
        }
    }

    public enum UserType {
        KAKAO,
        GOOGLE,
        DELI
    }
    private String userEmail;
    private String userPw;
    private UserRole userRole = UserRole.CLIENT;
    private UserType userType = UserType.DELI;
    private String userAddr;
    private String userTelephone;

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public String getUserTelephone() {
        return userTelephone;
    }

    public void setUserTelephone(String userTelephone) {
        this.userTelephone = userTelephone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @JsonIgnore
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
