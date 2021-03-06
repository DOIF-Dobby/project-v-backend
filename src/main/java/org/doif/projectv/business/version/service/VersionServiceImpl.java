package org.doif.projectv.business.version.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.buildtool.operator.BuildToolOperator;
import org.doif.projectv.business.module.entity.Module;
import org.doif.projectv.business.module.repository.ModuleRepository;
import org.doif.projectv.business.vcs.dto.VcsDto;
import org.doif.projectv.business.vcs.operator.VcsOperator;
import org.doif.projectv.business.version.dto.VersionDto;
import org.doif.projectv.business.version.entity.Version;
import org.doif.projectv.business.version.repository.VersionRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VersionServiceImpl implements VersionService {

    private final VersionRepository versionRepository;
    private final ModuleRepository moduleRepository;
    private final Map<String, VcsOperator> vcsOperatorMap;
    private final Map<String, BuildToolOperator> buildToolOperatorMap;

    @Transactional(readOnly = true)
    @Override
    public Page<VersionDto.Result> searchByCondition(Long id, VersionDto.Search search, Pageable pageable) {
        return versionRepository.searchByCondition(id, search, pageable);
    }

    @Override
    public CommonResponse insert(VersionDto.Insert dto) {
        Optional<Module> optionalModule = moduleRepository.findById(dto.getModuleId());
        Module module = optionalModule.orElseThrow(() -> new IllegalArgumentException("모듈을 찾을 수 없음"));

        // Svn, Git Operator 중에서 해당 모듈에 해당하는 버전관리 Operator 선택
        VcsOperator vcsOperator = vcsOperatorMap.get(module.getVcsType().getOperatorBeanName());
        // Maven, Gradle Operator 중에서 해당 모듈에 해당하는 빌드 도구 Operator 선택
        BuildToolOperator buildToolOperator = buildToolOperatorMap.get(module.getBuildTool().getOperatorBeanName());

        // 버전 엔티티 등록
        Version version = new Version(dto.getVersionName(), dto.getDescription(), module);
        versionRepository.save(version);

        // SVN은 Checkout, Git은 Clone으로 저장소를 로컬 임시 디렉토리에 복사
        File checkoutDirectory = vcsOperator.checkout(module.getVcsRepository());
        File buildToolFile = new File(checkoutDirectory.getAbsolutePath(), module.getBuildTool().getBuildToolFileName());

        // 버전명 변경하고 커밋 Maven은 pom.xml, Gradle은 build.gradle
        buildToolOperator.updateVersionName(buildToolFile, dto.getVersionName());
        vcsOperator.commit(buildToolFile, "버전 변경: " + dto.getVersionName() + " by ProjectV");

        // 체크아웃 받은 임시폴더 삭제
        vcsOperator.deleteDirectory(checkoutDirectory);

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse update(Long id, VersionDto.Update dto) {
        Optional<Version> optionalVersion = versionRepository.findById(id);
        Version version = optionalVersion.orElseThrow(() -> new IllegalArgumentException("버전을 찾을 수 없음"));
        version.changeDescription(dto.getDescription());

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse delete(Long id) {
        Optional<Version> optionalVersion = versionRepository.findById(id);
        Version version = optionalVersion.orElseThrow(() -> new IllegalArgumentException("버전을 찾을 수 없음"));
        versionRepository.delete(version);

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse release(Long id) {
        Optional<Version> optionalVersion = versionRepository.findFetchModuleById(id);
        Version version = optionalVersion.orElseThrow(() -> new IllegalArgumentException("버전을 찾을 수 없음"));
        Module module = version.getModule();

        // Svn, Git Operator 중에서 해당 모듈에 해당하는 버전관리 Operator 선택
        VcsOperator vcsOperator = vcsOperatorMap.get(module.getVcsType().getOperatorBeanName());

        // SVN은 tags 폴더에 복사, Git은 커밋 번호에 Tagging
        VcsDto.Tag tag = vcsOperator.tag(module.getVcsRepository(), version.getName());

        if(tag == null) {
            throw new IllegalArgumentException("버전관리 태그 실패");
        }

        // 배포상태로 변경
        version.release(tag.getRevision(), tag.getTag());

        return ResponseUtil.ok();
    }

    @Override
    public Page<VersionDto.Result> searchVersionsNotMappingIssue(Long issueId, Pageable pageable) {
        return versionRepository.searchVersionsNotMappingIssue(issueId, pageable);
    }

    @Override
    public Page<VersionDto.Result> searchVersionsNotMappingPatchLog(Long patchLogId, Pageable pageable) {
        return versionRepository.searchVersionsNotMappingPatchLog(patchLogId, pageable);
    }
}
