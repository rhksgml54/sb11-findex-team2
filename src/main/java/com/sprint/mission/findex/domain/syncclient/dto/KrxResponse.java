package com.sprint.mission.findex.domain.syncclient.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KrxResponse {
    private KrxResponseHeader header;
    private KrxResponseBody body;
}
