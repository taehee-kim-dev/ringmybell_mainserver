package com.team555.inu.ringmybell_mainserver.server.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 버스 정류장 객체
@Getter
@Setter
@NoArgsConstructor
public class BusStop {
    // 인덱스 번호(예:1)
    private Integer stop_no;
    // 정류장 고유번호(예:"38-034")
    private String stop_identifier;
    // 정류장 이름(예:"인천대학교자연과학대학")
    private String stop_name;
}
