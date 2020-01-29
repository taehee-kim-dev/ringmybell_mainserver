package com.team555.inu.ringmybell_mainserver.server.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 버스 정류장 객체
@Getter
@Setter
@NoArgsConstructor
public class BusStop {
    private Integer stop_no;
    private String stop_identifier;
    private String stop_name;
}
