package com.team555.inu.ringmybell_mainserver.server.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 안드로이드 객체
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Android {
    // 안드로이드 식별자 (예:"android123")
    private String identifier;
    // 버스 차량 번호 (예:"인천74사1071")
    private String busNumPlate;
    // 버스 노선번호(예:"780-1번")
    private String routeNum;
    // 예약할 정류장 고유번호(예:"38-034")
    private String stopToBook;
}
