package com.example.myapplication.domain;

public class User {
    private long id;
    private String deviceId;
    private long roomId;

    public User(long id, String deviceId, long roomId) {
        this.id = id;
        this.deviceId = deviceId;
        this.roomId = roomId;
    }

    public long getId() {
        return id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }
}
