package com.sprint.mission.findex.domain.syncclient.dto;

import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KrxItems {
    private List<IndexDataApiResponse> item = Collections.emptyList();
}