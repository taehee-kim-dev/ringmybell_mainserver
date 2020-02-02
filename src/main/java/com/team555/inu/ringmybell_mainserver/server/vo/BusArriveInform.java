package com.team555.inu.ringmybell_mainserver.server.vo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class BusArriveInform {

    // 차량번호
    private String busNumPlate;
    // 현재 떠나는 정류장
    private String leftStop;
}
