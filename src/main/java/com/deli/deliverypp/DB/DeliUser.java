package com.deli.deliverypp.DB;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    private String userRole;
    private String userType;

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

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserType() {
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
