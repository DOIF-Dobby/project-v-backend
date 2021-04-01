package org.doif.projectv.business.issue.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.issue.dto.IssueDto;
import org.doif.projectv.business.issue.entity.Issue;
import org.doif.projectv.business.issue.repository.IssueRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<IssueDto.Result> searchByCondition(IssueDto.Search search, Pageable pageable) {
        return issueRepository.searchByCondition(search, pageable);
    }

    @Override
    public CommonResponse insert(IssueDto.Insert dto) {
        Issue issue = new Issue(dto.getIssueName(), dto.getContents(), dto.getStatus(), dto.getCategory());
        issueRepository.save(issue);

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse update(Long id, IssueDto.Update dto) {
        Optional<Issue> optionalIssue = issueRepository.findById(id);
        Issue issue = optionalIssue.orElseThrow(() -> new IllegalArgumentException("이슈를 찾을 수 없음"));
        issue.changeIssue(dto.getIssueName(), dto.getContents(), dto.getStatus(), dto.getCategory());

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse delete(Long id) {
        Optional<Issue> optionalIssue = issueRepository.findById(id);
        Issue issue = optionalIssue.orElseThrow(() -> new IllegalArgumentException("이슈를 찾을 수 없음"));
        issueRepository.delete(issue);

        return ResponseUtil.ok();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<IssueDto.Result> searchIssuesNotMappingVersion(Long versionId, Pageable pageable) {
        return issueRepository.searchIssuesNotMappingVersion(versionId, pageable);
    }
}
