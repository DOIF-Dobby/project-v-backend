package org.doif.projectv.business.version.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.maven.service.MavenService;
import org.doif.projectv.business.module.entity.Module;
import org.doif.projectv.business.module.repository.ModuleRepository;
import org.doif.projectv.business.svn.dto.SvnTagDto;
import org.doif.projectv.business.svn.service.SvnService;
import org.doif.projectv.business.version.dto.VersionDto;
import org.doif.projectv.business.version.entity.Version;
import org.doif.projectv.business.version.repository.VersionRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VersionServiceImpl implements VersionService {

    private final VersionRepository versionRepository;
    private final ModuleRepository moduleRepository;
    private final SvnService svnService;
    private final MavenService mavenService;

    @Transactional(readOnly = true)
    @Override
    public Page<VersionDto.Result> searchByCondition(VersionDto.Search search, Pageable pageable) {
        return versionRepository.searchByCondition(search, pageable);
    }

    @Override
    public CommonResponse insert(VersionDto.Insert dto) {
        Optional<Module> optionalModule = moduleRepository.findById(dto.getModuleId());
        Module module = optionalModule.orElseThrow(() -> new IllegalArgumentException("모듈을 찾을 수 없음"));

        // 버전 엔티티 등록
        Version version = new Version(dto.getVersionName(), dto.getDescription(), module);
        versionRepository.save(version);

        // svn checkout -> pom.xml 버전 변경 -> svn commit
        File svnTempDirectory = svnService.checkout("");
        File pomFile = new File(svnTempDirectory.getAbsolutePath(), "pom.xml");

        mavenService.updateVersion(pomFile, dto.getVersionName());
        svnService.commit(pomFile, "버전 변경: " + dto.getVersionName());

        // 체크아웃 받은 임시폴더 삭제
        FileSystemUtils.deleteRecursively(svnTempDirectory);

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
        Optional<Version> optionalVersion = versionRepository.findById(id);
        Version version = optionalVersion.orElseThrow(() -> new IllegalArgumentException("버전을 찾을 수 없음"));

        // tags 폴더에 복사
        Optional<SvnTagDto> optionalSvnTagDto = svnService.tag("", version.getName());
        SvnTagDto svnTagDto = optionalSvnTagDto.orElseThrow(() -> new IllegalArgumentException("Svn 태깅 실패"));

        // 배포상태로 변경
        version.release(svnTagDto.getRevision(), svnTagDto.getTag());

        return ResponseUtil.ok();
    }
}
