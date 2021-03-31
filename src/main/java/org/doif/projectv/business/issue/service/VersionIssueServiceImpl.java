package org.doif.projectv.business.issue.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.issue.dto.VersionIssueDto;
import org.doif.projectv.business.issue.entity.Issue;
import org.doif.projectv.business.issue.entity.VersionIssue;
import org.doif.projectv.business.issue.repository.IssueRepository;
import org.doif.projectv.business.issue.repository.VersionIssueRepository;
import org.doif.projectv.business.version.entity.Version;
import org.doif.projectv.business.version.repository.VersionRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VersionIssueServiceImpl implements VersionIssueService {

    private final VersionIssueRepository versionIssueRepository;
    private final VersionRepository versionRepository;
    private final IssueRepository issueRepository;

    @Transactional(readOnly = true)
    @Override
    public List<VersionIssueDto.Result> searchByIssueId(Long issueId) {
        return versionIssueRepository.searchByIssueId(issueId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<VersionIssueDto.Result> searchByVersionId(Long versionId) {
        return versionIssueRepository.searchByVersionId(versionId);
    }

    @Override
    public CommonResponse insert(VersionIssueDto.Insert dto) {
        Optional<Version> optionalVersion = versionRepository.findById(dto.getVersionId());
        Version version = optionalVersion.orElseThrow(() -> new IllegalArgumentException("버전을 찾을 수 없음"));

        Optional<Issue> optionalIssue = issueRepository.findById(dto.getIssueId());
        Issue issue = optionalIssue.orElseThrow(() -> new IllegalArgumentException("이슈를 찾을 수 없음"));

        VersionIssue versionIssue = new VersionIssue(version, issue, dto.getIssueYm(), dto.getProgress(), dto.getAssignee(), dto.getRemark());
        versionIssueRepository.save(versionIssue);

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse update(Long id, VersionIssueDto.Update dto) {
        Optional<VersionIssue> optionalVersionIssue = versionIssueRepository.findById(id);
        VersionIssue versionIssue = optionalVersionIssue.orElseThrow(() -> new IllegalArgumentException("버전-이슈를 찾을 수 없음"));
        versionIssue.changeVersionIssue(dto.getIssueYm(), dto.getProgress(), dto.getAssignee(), dto.getRemark());

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse delete(Long id) {
        Optional<VersionIssue> optionalVersionIssue = versionIssueRepository.findById(id);
        VersionIssue versionIssue = optionalVersionIssue.orElseThrow(() -> new IllegalArgumentException("버전-이슈를 찾을 수 없음"));
        versionIssueRepository.delete(versionIssue);

        return ResponseUtil.ok();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<VersionIssueDto.ResultOverview> searchOverview(VersionIssueDto.Search search, Pageable pageable) {
        return versionIssueRepository.searchOverview(search, pageable);
    }

}
