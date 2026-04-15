package com.sprint.mission.findex.domain.syncclient.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KrxResponseHeader {
    private String resultCode;
    private String resultMsg;
}
