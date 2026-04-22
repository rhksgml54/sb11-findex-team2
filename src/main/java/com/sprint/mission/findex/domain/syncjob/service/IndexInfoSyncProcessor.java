package com.sprint.mission.findex.domain.syncjob.service;

import com.sprint.mission.findex.domain.indexinfo.dto.IndexInfoCreateRequest;
import com.sprint.mission.findex.domain.indexinfo.dto.IndexInfoResponse;
import com.sprint.mission.findex.domain.indexinfo.dto.IndexInfoUpdateRequest;
import com.sprint.mission.findex.domain.indexinfo.entity.IndexInfo;
import com.sprint.mission.findex.domain.indexinfo.repository.IndexInfoRepository;
import com.sprint.mission.findex.domain.indexinfo.service.IndexInfoService;
import com.sprint.mission.findex.global.exception.ApiException;
import com.sprint.mission.findex.global.exception.ApiException.ERROR;
import com.sprint.mission.findex.domain.syncjob.dto.SyncJobResponse;
import com.sprint.mission.findex.domain.syncjob.entity.JobResult;
import com.sprint.mission.findex.domain.syncjob.entity.JobType;
import com.sprint.mission.findex.domain.syncjob.mapper.SyncJobMapper;
import com.sprint.mission.findex.domain.syncjob.repository.SyncJobRepository;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class IndexInfoSyncProcessor {

    private final IndexInfoService indexInfoService;
    private final IndexInfoRepository indexInfoRepository;
    private final SyncJobRepository syncJobRepository;
    private final SyncJobMapper syncJobMapper;

    @Transactional
    public SyncJobResponse createAndSaveHistory(IndexInfoCreateRequest createRequest, LocalDate targetDate, String workerIp) {
        IndexInfoResponse created = indexInfoService.createByOpenAPI(createRequest);
        IndexInfo indexInfo = indexInfoRepository.findById(created.id())
            .orElseThrow(() -> new ApiException(ERROR.INDEX_INFO_NOT_FOUND));
        return saveHistory(indexInfo, targetDate, workerIp, JobResult.SUCCESS, null);
    }

    @Transactional
    public SyncJobResponse updateAndSaveHistory(IndexInfo indexInfo, IndexInfoUpdateRequest updateRequest, LocalDate targetDate, String workerIp) {
        indexInfoService.updateByOpenAPI(indexInfo, updateRequest);
        return saveHistory(indexInfo, targetDate, workerIp, JobResult.SUCCESS, null);
    }

    private SyncJobResponse saveHistory(IndexInfo indexInfo, LocalDate targetDate, String workerIp, JobResult result, String errorMessage) {
        return syncJobMapper.toResponse(syncJobRepository.save(
            syncJobMapper.toEntity(indexInfo, JobType.INDEX_INFO, targetDate, workerIp, result, errorMessage)
        ));
    }
}