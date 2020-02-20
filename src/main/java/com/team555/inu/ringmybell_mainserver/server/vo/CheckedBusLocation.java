package com.team555.inu.ringmybell_mainserver.server.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckedBusLocation {
    private String stop_name;
    private String stop_identifier;
    private String direction;
    private Boolean direction_setting;
}
