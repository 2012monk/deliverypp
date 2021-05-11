package com.deli.deliverypp.model;

import com.deli.deliverypp.DB.DeliUser;

public class AuthInfo {

    private String access_token;
    private String auth_type;
    private DeliUser user;
    private long exp;

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAuth_type() {
        return auth_type;
    }

    public void setAuth_type(String auth_type) {
        this.auth_type = auth_type;
    }

    public DeliUser getUser() {
        return user;
    }

    public void setUser(DeliUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AuthInfo{" +
                "access_token='" + access_token + '\'' +
                ", auth_type='" + auth_type + '\'' +
                ", user=" + user +
                '}';
    }
}
