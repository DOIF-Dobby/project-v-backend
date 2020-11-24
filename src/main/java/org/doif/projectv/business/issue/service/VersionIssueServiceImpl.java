package org.doif.projectv.business.issue.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.issue.dto.VersionIssueDto;
import org.doif.projectv.business.issue.dto.VersionIssueOverviewDto;
import org.doif.projectv.business.issue.repository.VersionIssueRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VersionIssueServiceImpl implements VersionIssueService {

    private final VersionIssueRepository versionIssueRepository;

    @Override
    public List<VersionIssueDto.Result> searchByIssueId(VersionIssueDto.Search search) {
        return versionIssueRepository.searchByIssueId(search.getIssueId());
    }

    @Override
    public Page<VersionIssueOverviewDto> searchOverview(VersionIssueDto.Search search, Pageable pageable) {
        return versionIssueRepository.searchOverview(search, pageable);
    }

    @Override
    public Page<VersionIssueDto.Result> searchNonePatchModuleIssue(VersionIssueDto.Search search, Pageable pageable) {
        return versionIssueRepository.searchNonePatchModuleIssue(search, pageable);
    }
}
