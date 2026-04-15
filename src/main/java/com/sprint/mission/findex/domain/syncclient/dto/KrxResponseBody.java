package com.sprint.mission.findex.domain.syncclient.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KrxResponseBody {
    private Integer numOfRows;
    private Integer pageNo;
    private Integer totalCount;
    private KrxItems items;
}
