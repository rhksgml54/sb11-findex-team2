package com.sprint.mission.findex.domain.syncclient.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class IndexDataApiResponse {

    private String basDt;
    private String idxNm;
    private String idxCsf;
    private Integer epyItmsCnt;

    private BigDecimal clpr;
    private BigDecimal vs;
    private BigDecimal fltRt;
    private BigDecimal mkp;
    private BigDecimal hipr;
    private BigDecimal lopr;

    private Long trqu;
    private BigDecimal trPrc;
    private BigDecimal lstgMrktTotAmt;

    private String basPntm;
    private BigDecimal basIdx;
}