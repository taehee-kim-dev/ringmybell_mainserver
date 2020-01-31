package com.team555.inu.ringmybell_mainserver.server.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 노선 검색결과 버스정류장 객체
@Getter
@Setter
@NoArgsConstructor
public class SearchedBusRoute {

    // 인덱스 번호(예:1)
    private Integer num;
    // 버스 노선번호(예:"780-1")
    private String bus_route;
    // 버스 노선 타입(예:"간선")
    private String bus_type;
}
