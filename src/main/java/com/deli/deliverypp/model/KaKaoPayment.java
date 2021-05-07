package com.deli.deliverypp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KaKaoPayment extends Payment{

    private String redirectUri;
    private String tid;
    private String next_redirect_pc_url;
    private String pg_token;
    private String code;
    private String msg;
    private Map<String, String > extras;


    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getNext_redirect_pc_url() {
        return next_redirect_pc_url;
    }

    public void setNext_redirect_pc_url(String next_redirect_pc_url) {
        this.next_redirect_pc_url = next_redirect_pc_url;
    }

    public String getPg_token() {
        return pg_token;
    }

    public void setPg_token(String pg_token) {
        this.pg_token = pg_token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }

    @Override
    public String toString() {
        return "KaKaoPayment{" +
                "redirectUri='" + redirectUri + '\'' +
                ", tid='" + tid + '\'' +
                ", next_redirect_pc_url='" + next_redirect_pc_url + '\'' +
                ", pg_token='" + pg_token + '\'' +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", extras=" + extras +
                '}';
    }
}
