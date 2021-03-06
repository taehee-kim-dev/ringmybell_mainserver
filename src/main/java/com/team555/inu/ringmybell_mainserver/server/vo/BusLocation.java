package com.team555.inu.ringmybell_mainserver.server.vo;

// 버스 위치 정보 객체
public class BusLocation {

    // 버스의 현재 정류장 고유번호
    // 현재 위치가 특정 정류장의 반경 50m 안이라면 해당 정류장의 고유번호가 현재 위치값이 되고, 아니라면 null 값이 됨.
    private String recentStop;

    // 가장 최근 null이 아닌 정류장 고유번호
    private String recentNotNullStop;

    public BusLocation() {}

    public BusLocation(String recentStop, String recentNotNullStop) {
        this.recentStop = recentStop;
        this.recentNotNullStop = recentNotNullStop;
    }

    public String getRecentStop() {
        return recentStop;
    }

    public void setRecentStop(String recentStop) {
        this.recentStop = recentStop;
    }

    public String getRecentNotNullStop() {
        return recentNotNullStop;
    }

    public void setRecentNotNullStop(String recentNotNullStop) {
        this.recentNotNullStop = recentNotNullStop;
    }
}