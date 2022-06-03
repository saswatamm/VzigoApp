package com.example.vzigo;

public class LinkDevices {
    String deviceId, passcode;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public LinkDevices(String deviceId, String passcode) {
        this.deviceId = deviceId;
        this.passcode = passcode;
    }
}