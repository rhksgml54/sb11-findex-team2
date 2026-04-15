package com.sprint.mission.findex.domain.syncclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class KrxItems {
    private List<IndexDataApiResponse> item;
}
