package com.example.vzigo;

public class LinkDevices1 {

    String accessToken,clientId;

    public LinkDevices1(String accessToken, String clientId) {
        this.accessToken = accessToken;
        this.clientId = clientId;
    }
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


}