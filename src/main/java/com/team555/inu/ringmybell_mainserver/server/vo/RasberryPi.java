package com.team555.inu.ringmybell_mainserver.server.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RasberryPi {

    // 버스 차량 번호 (예:"인천74사1071")
    private String busNumPlate;
    // 버스 노선번호(예:"780-1")
    private String routeNum;
    // 버스 현재 위도
    private Double lat;
    // 버스 현재 경도
    private Double lon;
}
